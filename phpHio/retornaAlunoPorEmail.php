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

$sql = "SELECT nomeCompleto, nomeUsuario, email, senha, fotoPerfil FROM Aluno WHERE email = :email";
$statement = $pdo->prepare($sql);
$statement->bindParam(':email', $email);
$statement->execute();

$aluno = $statement->fetch(PDO::FETCH_ASSOC);

if ($aluno) {
    if (!empty($aluno['fotoPerfil'])) {
        $aluno['fotoPerfil'] = base64_encode($aluno['fotoPerfil']);
        error_log("Foto de perfil encontrada e codificada para base64.");
    } else {
        $aluno['fotoPerfil'] = null;
        error_log("Foto de perfil não encontrada ou está vazia.");
    }
    echo json_encode(["status" => "success", "aluno" => $aluno]);
} else {
    echo json_encode(["status" => "error", "message" => "Aluno não encontrado no banco de dados"]);
    error_log("Aluno não encontrado no banco de dados para o email: " . $email);
}

$pdo = null;
?>
