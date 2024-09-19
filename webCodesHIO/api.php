<?php
header("Content-Type: application/json");

// Configurações do banco de dados
$servername = "localhost"; // ou o endereço do seu servidor de banco de dados
$username = "root";
$password = "root";
$dbname = "hio";

try {
    // Criar conexão com PDO
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(['status' => 'error', 'message' => $e->getMessage()]);
    exit();
}

$method = $_SERVER['REQUEST_METHOD'];

switch ($method) {
    case 'GET':
        if (isset($_GET['email'])) {
            $email = $_GET['email'];
            $stmt = $pdo->prepare("SELECT * FROM Professor WHERE email = :email");
            $stmt->bindParam(':email', $email);
            $stmt->execute();
            $professor = $stmt->fetch(PDO::FETCH_ASSOC);
            echo json_encode($professor);
        } else {
            $stmt = $pdo->query("SELECT * FROM Professor");
            $professores = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($professores);
        }
        break;

    case 'POST':
        $data = json_decode(file_get_contents('php://input'), true);
        $stmt = $pdo->prepare("INSERT INTO Professor (nomeCompleto, nomeUsuario, email, senha, fotoPerfil) VALUES (:nomeCompleto, :nomeUsuario, :email, :senha, :fotoPerfil)");
        $stmt->bindParam(':nomeCompleto', $data['nomeCompleto']);
        $stmt->bindParam(':nomeUsuario', $data['nomeUsuario']);
        $stmt->bindParam(':email', $data['email']);
        $stmt->bindParam(':senha', $data['senha']);
        $stmt->bindParam(':fotoPerfil', $data['fotoPerfil']);
        if ($stmt->execute()) {
            echo json_encode(['status' => 'success']);
        } else {
            echo json_encode(['status' => 'error', 'message' => $stmt->errorInfo()[2]]);
        }
        break;

    case 'PUT':
        $data = json_decode(file_get_contents('php://input'), true);
        $stmt = $pdo->prepare("UPDATE Professor SET nomeCompleto = :nomeCompleto, nomeUsuario = :nomeUsuario, senha = :senha, fotoPerfil = :fotoPerfil WHERE email = :email");
        $stmt->bindParam(':nomeCompleto', $data['nomeCompleto']);
        $stmt->bindParam(':nomeUsuario', $data['nomeUsuario']);
        $stmt->bindParam(':senha', $data['senha']);
        $stmt->bindParam(':fotoPerfil', $data['fotoPerfil']);
        $stmt->bindParam(':email', $data['email']);
        if ($stmt->execute()) {
            echo json_encode(['status' => 'success']);
        } else {
            echo json_encode(['status' => 'error', 'message' => $stmt->errorInfo()[2]]);
        }
        break;

    case 'DELETE':
        if (isset($_GET['email'])) {
            $email = $_GET['email'];
            $stmt = $pdo->prepare("DELETE FROM Professor WHERE email = :email");
            $stmt->bindParam(':email', $email);
            if ($stmt->execute()) {
                echo json_encode(['status' => 'success']);
            } else {
                echo json_encode(['status' => 'error', 'message' => $stmt->errorInfo()[2]]);
            }
        } else {
            echo json_encode(['status' => 'error', 'message' => 'Email não fornecido']);
        }
        break;

    case 'POST_LOGIN':
        $data = json_decode(file_get_contents('php://input'), true);
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
        break;

    default:
        echo json_encode(['status' => 'error', 'message' => 'Método não suportado']);
        break;
}

$pdo = null;
?>
