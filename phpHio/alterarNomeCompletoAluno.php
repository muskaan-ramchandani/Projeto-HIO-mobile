<?php
header('Content-Type: application/json; charset=utf-8');
$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";     

$pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);

if (isset($_POST['nomeCompleto'])&&isset($_POST['email'])) {

    $nomeCompleto = $_POST['nomeCompleto'];
    $email = $_POST['email'];


    $sql = 'UPDATE Aluno SET nomeCompleto = :nomeCompleto WHERE email = :email';

    $statement = $pdo->prepare($sql);
    $statement->bindParam(':nomeCompleto', $nomeCompleto);
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