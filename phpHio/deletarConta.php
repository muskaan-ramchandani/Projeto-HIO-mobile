<?php
header('Content-Type: application/json; charset=utf-8');

$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "hio";

try {
    // Conexão com o banco de dados
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if (isset($_POST['email'])) {
        $email = $_POST['email'];

        // Inicia a transação
        $pdo->beginTransaction();

        // Deleta as respostas do fórum relacionadas às perguntas feitas pelo aluno
        $sqlRespostas = '
            DELETE FROM RespostasForum 
            WHERE idPergunta IN (
                SELECT id FROM PerguntasForum WHERE emailAluno = :email
            )';
        $stmtRespostas = $pdo->prepare($sqlRespostas);
        $stmtRespostas->bindParam(':email', $email);
        $stmtRespostas->execute();

        // Deleta as perguntas do fórum criadas pelo aluno
        $sqlPerguntas = 'DELETE FROM PerguntasForum WHERE emailAluno = :email';
        $stmtPerguntas = $pdo->prepare($sqlPerguntas);
        $stmtPerguntas->bindParam(':email', $email);
        $stmtPerguntas->execute();

        // Deleta outros registros associados ao aluno
        $sqlOlimpiadas = 'DELETE FROM OlimpiadasSelecionadas WHERE emailAluno = :email';
        $stmtOlimpiadas = $pdo->prepare($sqlOlimpiadas);
        $stmtOlimpiadas->bindParam(':email', $email);
        $stmtOlimpiadas->execute();

        $sqlAcertos = 'DELETE FROM AcertosAluno WHERE emailAluno = :email';
        $stmtAcertos = $pdo->prepare($sqlAcertos);
        $stmtAcertos->bindParam(':email', $email);
        $stmtAcertos->execute();

        $sqlErros = 'DELETE FROM ErrosAluno WHERE emailAluno = :email';
        $stmtErros = $pdo->prepare($sqlErros);
        $stmtErros->bindParam(':email', $email);
        $stmtErros->execute();

        $sqlPontuacao = 'DELETE FROM PontuacaoAlunos WHERE emailAluno = :email';
        $stmtPontuacao = $pdo->prepare($sqlPontuacao);
        $stmtPontuacao->bindParam(':email', $email);
        $stmtPontuacao->execute();

        #adicionar quando estiver registrando o historico
        #DELETE FROM HistoricoAcessoAluno WHERE emailAluno = :email;

        // Deleta o registro do aluno
        $sqlAluno = 'DELETE FROM Aluno WHERE email = :email';
        $stmtAluno = $pdo->prepare($sqlAluno);
        $stmtAluno->bindParam(':email', $email);
        $stmtAluno->execute();

        $pdo->commit();

        $response = array('status' => 'success', 'message' => 'Conta excluída com sucesso!');
    } else {
        $response = array('status' => 'error', 'message' => 'Parâmetros incompletos.');
    }
} catch (Exception $e) {
    if ($pdo->inTransaction()) {
        $pdo->rollBack();
    }

    $response = array('status' => 'error', 'message' => 'Erro ao processar a solicitação: ' . $e->getMessage());
}

echo json_encode($response);
?>
