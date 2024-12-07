<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');

$servername = "localhost"; 
$username = "u740411060_user";        
$password = "OWYzZ?o2";            
$dbname = "u740411060_hio";     

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
    exit;
}

$email = $_GET['email'] ?? '';
$senha = $_GET['senha'] ?? '';

if (empty($email) || empty($senha)) {
    echo json_encode(["status" => "error", "message" => "Forneça todos os dados para prosseguir!"]);
    exit;
}

// Verificação da existência do email
$sql = "SELECT senha FROM Aluno WHERE email = :email";
$statement = $pdo->prepare($sql);
$statement->bindParam(':email', $email);
$statement->execute();

if ($statement->rowCount() == 0) {
    echo json_encode(["status" => "error", "message" => "O email inserido não foi cadastrado no aplicativo"]);
    exit;
}

// Verificação da compatibilidade da senha
$result = $statement->fetch(PDO::FETCH_ASSOC);
$senhaBanco = $result['senha'];

if ($senha !== $senhaBanco) {
    echo json_encode(["status" => "error", "message" => "Senha incorreta! Tente novamente."]);
    exit;
}

echo json_encode(["status" => "success", "message" => "Login realizado com sucesso!"]);

$pdo = null;
?>
