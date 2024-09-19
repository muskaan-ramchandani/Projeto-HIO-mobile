<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type");

$servername = "localhost"; // ou o endereço do seu servidor de banco de dados
$username = "root";
$password = "root";
$dbname = "hio";

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(['status' => 'error', 'message' => 'Erro de conexão: ' . $e->getMessage()]);
    exit();
}

$method = $_SERVER['REQUEST_METHOD'];

if ($method === 'POST') {
    $data = json_decode(file_get_contents('php://input'), true);

    if (isset($data['method']) && $data['method'] === 'POST_LOGIN') {
        $email = $data['email'];
        $senha = $data['senha'];

        // Verificar se o email e a senha correspondem
        $stmt = $pdo->prepare("SELECT * FROM Professor WHERE email = :email AND senha = :senha");
        $stmt->bindParam(':email', $email);
        $stmt->bindParam(':senha', $senha);
        $stmt->execute();
        $professor = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($professor) {
            echo json_encode(['status' => 'success', 'message' => 'Login bem-sucedido']);
        } else {
            echo json_encode(['status' => 'error', 'message' => 'Email ou senha incorretos']);
        }
    } else {
        echo json_encode(['status' => 'error', 'message' => 'Método não suportado']);
    }
} else {
    echo json_encode(['status' => 'error', 'message' => 'Método não suportado']);
}

$pdo = null;
?>
