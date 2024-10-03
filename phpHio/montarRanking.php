<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');

$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";     

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
    exit;
}


$email = $_GET['email'] ?? '';

if (empty($email)) {
    echo json_encode(["message" => "Não foi possível detectar o email do aluno"]);
    exit;
}

$sql= "SELECT "

?>