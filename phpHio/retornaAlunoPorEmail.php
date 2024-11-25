<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');

$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";     

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
    exit;
}

$email = $_GET['email'] ?? '';

if (empty($email)) {
    echo json_encode(["message" => "Email não recebido"]);
    exit;
}

$sql = "SELECT nomeCompleto, nomeUsuario, email, senha, fotoPerfil FROM Aluno WHERE email = :email";
$statement = $pdo->prepare($sql);
$statement->bindParam(':email', $email);
$statement->execute();

$aluno = $statement->fetch(PDO::FETCH_ASSOC);


if ($aluno) {
    if (isset($aluno['fotoPerfil'])) {
        $aluno['fotoPerfil'] = base64_encode($aluno['fotoPerfil']);
    }
    echo json_encode(["status" => "success", "aluno" => $aluno]);
} else {
    echo json_encode(["status" => "error", "message" => "Aluno não encontrado no banco de dados"]);
}

$pdo = null;
?>
