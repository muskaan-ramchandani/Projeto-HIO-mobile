<?php
// Conectar ao banco de dados
$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "hio";

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "<p>Erro na conexão com o banco de dados: " . $e->getMessage() . "</p>";
    exit;
}

// Verificar se os dados foram enviados
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Obter o título do questionário
    $titulo = $_POST['titulo'] ?? '';

    // Obter o email do professor e o id do conteúdo da URL
    $profQuePostou = isset($_GET['email']) ? htmlspecialchars($_GET['email']) : '';
    $idConteudoPertencente = isset($_GET['id']) ? intval($_GET['id']) : 0;

    // Verificar se o título e os campos obrigatórios estão preenchidos
    if (empty($titulo) || empty($profQuePostou) || empty($idConteudoPertencente)) {
        echo "<p>Erro: Todos os campos do questionário são obrigatórios.</p>";
        exit;
    }

    // Inserir o questionário
    $sql = "INSERT INTO Questionario (titulo, profQuePostou, idConteudoPertencente) VALUES (:titulo, :profQuePostou, :idConteudoPertencente)";
    $stmt = $pdo->prepare($sql);
    
    try {
        $stmt->execute([
            ':titulo' => $titulo,
            ':profQuePostou' => $profQuePostou,
            ':idConteudoPertencente' => $idConteudoPertencente
        ]);
        
        // Obter o id do questionário inserido
        $idQuestionario = $pdo->lastInsertId();

        // Inserir perguntas e alternativas
        foreach ($_POST['perguntas'] as $pergunta) {
            $textoPergunta = $pergunta['texto'] ?? '';
            $explicacao = $pergunta['explicacao'] ?? '';
            $alternativas = $pergunta['alternativas'] ?? [];

            // Verificar se a pergunta e a explicação estão preenchidas
            if (empty($textoPergunta) || empty($explicacao)) {
                echo "<p>Erro: Todos os campos da pergunta são obrigatórios.</p>";
                exit;
            }

            // Inserir a pergunta
            $sqlPergunta = "INSERT INTO Questao (txtPergunta, explicacaoResposta, idQuestionarioPertencente) VALUES (:txtPergunta, :explicacaoResposta, :idQuestionarioPertencente)";
            $stmtPergunta = $pdo->prepare($sqlPergunta);
            $stmtPergunta->execute([
                ':txtPergunta' => $textoPergunta,
                ':explicacaoResposta' => $explicacao,
                ':idQuestionarioPertencente' => $idQuestionario
            ]);

            $idQuestao = $pdo->lastInsertId();

            // Inserir alternativas
            foreach ($alternativas as $alternativa) {
                $textoAlternativa = $alternativa['texto'] ?? '';
                $correta = isset($alternativa['correta']) ? 1 : 0; // Verificar se é correta

                // Verificar se a alternativa está preenchida
                if (empty($textoAlternativa)) {
                    continue; // Ignorar alternativas vazias
                }

                $sqlAlternativa = "INSERT INTO AlternativasQuestao (textoAlternativa, corretaOuErrada, idQuestionarioPertencente, idQuestaoPertencente) VALUES (:textoAlternativa, :corretaOuErrada, :idQuestionarioPertencente, :idQuestaoPertencente)";
                $stmtAlternativa = $pdo->prepare($sqlAlternativa);
                $stmtAlternativa->execute([
                    ':textoAlternativa' => $textoAlternativa,
                    ':corretaOuErrada' => $correta,
                    ':idQuestionarioPertencente' => $idQuestionario,
                    ':idQuestaoPertencente' => $idQuestao
                ]);
            }
        }

        echo "<p>Questionário adicionado com sucesso!</p>";
    } catch (PDOException $e) {
        echo "<p>Erro ao adicionar questionário: " . $e->getMessage() . "</p>";
    }
}

// Fechar conexão com o banco
$pdo = null;
?>
