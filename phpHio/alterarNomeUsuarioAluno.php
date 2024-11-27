<?php
header('Content-Type: application/json; charset=utf-8');
$servername = "sql207.infinityfree.com";
$username = "if0_37755624";
$password = "1k31AyGMaqyz7";
$dbname = "if0_37755624_hio";  

$pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);

if (isset($_POST['nomeUsuario']) && isset($_POST['email'])) {

    $nomeUsuario = $_POST['nomeUsuario'];
    $email = $_POST['email'];

    $sql = 'UPDATE Aluno SET nomeUsuario = :nomeUsuario WHERE email = :email';

    $statement = $pdo->prepare($sql);
    $statement->bindParam(':nomeUsuario', $nomeUsuario);
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