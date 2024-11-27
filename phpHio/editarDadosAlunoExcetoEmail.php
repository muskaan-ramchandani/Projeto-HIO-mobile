<?php
header('Content-Type: application/json; charset=utf-8');

$servername = "sql207.infinityfree.com";
$username = "if0_37755624";
$password = "1k31AyGMaqyz7";
$dbname = "if0_37755624_hio";      

$pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);

if (isset($_POST['nomeCompleto']) && isset($_POST['nomeUsuario']) && isset($_POST['email']) && isset($_POST['senha'])) {

    $nomeCompleto = $_POST['nomeCompleto'];
    $nomeUsuario = $_POST['nomeUsuario'];
    $email = $_POST['email'];
    $senha = $_POST['senha'];

    $sql = 'UPDATE Aluno SET nomeCompleto = :nomeCompleto, nomeUsuario = :nomeUsuario, senha = :senha WHERE email = :email';

    $statement = $pdo->prepare($sql);
    $statement->bindParam(':nomeCompleto', $nomeCompleto);
    $statement->bindParam(':nomeUsuario', $nomeUsuario);
    $statement->bindParam(':senha', $senha);
    $statement->bindParam(':email', $email);

    if ($statement->execute()) {
        $response = array('status' => 'success', 'message' => 'Alterações feitas com sucesso!');
    } else {
        $response = array('status' => 'error', 'message' => 'Erro ao alterar seus dados.');
    }
    
} else {
    $response = array('status' => 'error', 'message' => 'Parâmetros incompletos.');
}

echo json_encode($response);
?>
