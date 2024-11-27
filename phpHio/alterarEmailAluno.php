<?php
header('Content-Type: application/json; charset=utf-8');
$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";     


try{
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);

if (isset($_POST['email'])&&isset($_POST['novoEmail'])) {

    $email = $_POST['email'];
    $novoEmail = $_POST['novoEmail'];

    $pdo->beginTransaction();

    $sqlAluno = 'UPDATE Aluno SET email = :novoEmail WHERE email = :email';
        $stmtAluno = $pdo->prepare($sqlAluno);
        $stmtAluno->bindParam(':novoEmail', $novoEmail);
        $stmtAluno->bindParam(':email', $email);
        $stmtAluno->execute();

        $tabelasRelacionadas = [
            'OlimpiadasSelecionadas',
            'AcertosAluno',
            'ErrosAluno',
            'PontuacaoAlunos',
            'PerguntasForum'
        ];

        foreach ($tabelasRelacionadas as $tabela) {
            $sqlRelacionada = "UPDATE $tabela SET emailAluno = :novoEmail WHERE emailAluno = :email";
            $stmtRelacionada = $pdo->prepare($sqlRelacionada);
            $stmtRelacionada->bindParam(':novoEmail', $novoEmail);
            $stmtRelacionada->bindParam(':email', $email);
            $stmtRelacionada->execute();
        }

        $pdo->commit();

        $response = ['status' => 'success', 'message' => 'Email atualizado com sucesso em todas as tabelas!'];
    } else {
        $response = ['status' => 'error', 'message' => 'Parâmetros incompletos.'];
    }

} catch (Exception $e) {
    if ($pdo->inTransaction()) {
        $pdo->rollBack();
    }
    $response = ['status' => 'error', 'message' => 'Erro: ' . $e->getMessage()];
}

echo json_encode($response);
?>