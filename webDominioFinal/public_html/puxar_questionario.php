<?php
// Configuração do banco de dados
$dsn = 'mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';

try {
    // Conexão com o banco de dados
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Obtém o ID do questionário da URL
    $idQuestionario = isset($_GET['id']) ? $_GET['id'] : 0;

    // Consulta para buscar o título e o professor que postou o questionário
    $sql = "SELECT titulo, profQuePostou FROM Questionario WHERE id = :id";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':id', $idQuestionario);
    $stmt->execute();
    $questionario = $stmt->fetch(PDO::FETCH_ASSOC);

    // Verifica se o questionário existe
    if (!$questionario) {
        die("Questionário não encontrado.");
    }

    // Consulta para buscar as questões relacionadas ao questionário
    $sqlPerguntas = "SELECT id, txtPergunta FROM Questao WHERE idQuestionarioPertencente = :id";
    $stmtPerguntas = $pdo->prepare($sqlPerguntas);
    $stmtPerguntas->bindParam(':id', $idQuestionario);
    $stmtPerguntas->execute();
    $questoes = $stmtPerguntas->fetchAll(PDO::FETCH_ASSOC);

    // Array para armazenar as alternativas por questão
    $alternativasPorPergunta = [];

    // Consulta para buscar as alternativas de cada questão
    $sqlAlternativas = "SELECT a.idQuestaoPertencente, a.textoAlternativa, a.corretaOuErrada
                        FROM AlternativasQuestao a
                        JOIN Questao q ON a.idQuestaoPertencente = q.id
                        WHERE q.idQuestionarioPertencente = :id";
    $stmtAlternativas = $pdo->prepare($sqlAlternativas);
    $stmtAlternativas->bindParam(':id', $idQuestionario);
    $stmtAlternativas->execute();
    $alternativas = $stmtAlternativas->fetchAll(PDO::FETCH_ASSOC);

    // Organiza as alternativas no array associativo usando o idQuestaoPertencente
    foreach ($alternativas as $alternativa) {
        $alternativasPorPergunta[$alternativa['idQuestaoPertencente']][] = $alternativa;
    }

} catch (PDOException $e) {
    echo "Erro ao conectar ao banco de dados: " . $e->getMessage();
    exit;
}
?>
