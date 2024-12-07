<?php
header('Content-Type: application/json; charset=utf-8');

$servername = "localhost"; 
$username = "u740411060_user";        
$password = "OWYzZ?o2";            
$dbname = "u740411060_hio";    

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