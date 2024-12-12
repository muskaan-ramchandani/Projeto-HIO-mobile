<?php
// Configuração do banco de dados
$dsn = 'mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';


try {
    // Conexão com o banco de dados
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Início da transação
    $pdo->beginTransaction();

    // Verifica se o formulário foi enviado
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        // Recebe os dados do formulário
        $titulo = $_POST['titulo'];
        $profQuePostou = $_POST['profQuePostou'];
        $idConteudoPertencente = $_POST['idConteudoPertencente'];
        $perguntas = $_POST['perguntas'];

        // Insere os dados na tabela Questionario
        $sqlQuestionario = "INSERT INTO Questionario (titulo, profQuePostou, idConteudoPertencente) 
                            VALUES (:titulo, :profQuePostou, :idConteudoPertencente)";
        $stmtQuestionario = $pdo->prepare($sqlQuestionario);
        $stmtQuestionario->bindParam(':titulo', $titulo);
        $stmtQuestionario->bindParam(':profQuePostou', $profQuePostou);
        $stmtQuestionario->bindParam(':idConteudoPertencente', $idConteudoPertencente);
        $stmtQuestionario->execute();

        // Pega o último ID inserido na tabela Questionario
        $idQuestionario = $pdo->lastInsertId();

        // Insere cada pergunta na tabela Questao
        foreach ($perguntas as $pergunta) {
            $txtPergunta = $pergunta['texto'];
            $explicacaoResposta = $pergunta['explicacao'];

            $sqlQuestao = "INSERT INTO Questao (txtPergunta, explicacaoResposta, idQuestionarioPertencente) 
                           VALUES (:txtPergunta, :explicacaoResposta, :idQuestionarioPertencente)";
            $stmtQuestao = $pdo->prepare($sqlQuestao);
            $stmtQuestao->bindParam(':txtPergunta', $txtPergunta);
            $stmtQuestao->bindParam(':explicacaoResposta', $explicacaoResposta);
            $stmtQuestao->bindParam(':idQuestionarioPertencente', $idQuestionario);
            $stmtQuestao->execute();

            // Pega o último ID inserido na tabela Questao
            $idQuestao = $pdo->lastInsertId();

            // Insere as alternativas na tabela AlternativaQuestao
            if (!empty($pergunta['alternativas'])) {
                foreach ($pergunta['alternativas'] as $alternativa) {
                    $textoAlternativa = $alternativa['texto'];
                    $corretaOuErrada = isset($alternativa['correta']) && $alternativa['correta'] == 1 ? 1 : 0;

                    $sqlAlternativaQuestao = "INSERT INTO AlternativasQuestao (textoAlternativa, corretaOuErrada, idQuestionarioPertencente, idQuestaoPertencente) 
                                              VALUES (:textoAlternativa, :corretaOuErrada, :idQuestionarioPertencente, :idQuestaoPertencente)";
                    $stmtAlternativaQuestao = $pdo->prepare($sqlAlternativaQuestao);
                    $stmtAlternativaQuestao->bindParam(':textoAlternativa', $textoAlternativa);
                    $stmtAlternativaQuestao->bindParam(':corretaOuErrada', $corretaOuErrada);
                    $stmtAlternativaQuestao->bindParam(':idQuestionarioPertencente', $idQuestionario);
                    $stmtAlternativaQuestao->bindParam(':idQuestaoPertencente', $idQuestao);
                    $stmtAlternativaQuestao->execute();
                }
            }
        }

        // Confirma a transação
        $pdo->commit();
        echo "Questionário, questões e alternativas cadastrados com sucesso!";
    }
} catch (PDOException $e) {
    // Rollback em caso de erro
    $pdo->rollBack();
    echo "Erro ao cadastrar questionário: " . $e->getMessage();
}
?>
