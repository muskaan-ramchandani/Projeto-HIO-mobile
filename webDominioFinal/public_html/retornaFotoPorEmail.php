<?php
header('Content-Type: application/json; charset=utf-8');

$servername = "localhost"; 
$username = "u740411060_user";        
$password = "OWYzZ?o2";            
$dbname = "u740411060_hio";         

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados: " . $e->getMessage()]);
    exit;
}

$email = $_GET['email'] ?? '';

if (empty($email)) {
    echo json_encode(["message" => "Email não recebido"]);
    exit;
}

$sql = "SELECT fotoPerfil FROM Aluno WHERE email = :email LIMIT 1"; 
$statement = $pdo->prepare($sql);
$statement->bindParam(':email', $email);
$statement->execute();

$foto = $statement->fetch(PDO::FETCH_ASSOC);

if ($foto && !empty($foto['fotoPerfil'])) {
    $fotoBase64 = base64_encode($foto['fotoPerfil']);
    echo json_encode(["status" => "success", "fotoPerfil" => $fotoBase64]);
} else {
    echo json_encode(["status" => "error", "message" => "Foto de perfil não encontrada"]);
}

$pdo = null;
?>
