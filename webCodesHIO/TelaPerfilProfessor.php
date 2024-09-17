<?php
session_start(); // Se você estiver usando sessões para gerenciar o login

$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$username = 'root';
$password = 'root';

try {
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Supondo que o ID do professor esteja na sessão ou passado por GET
    $professorId = $_SESSION['professor_id'] ?? $_GET['id'] ?? null;

    if (!$professorId) {
        throw new Exception("ID do professor não fornecido.");
    }

    $sql = "SELECT nome, usuario, email FROM Professor WHERE id = :id";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':id', $professorId, PDO::PARAM_INT);
    $stmt->execute();

    $professor = $stmt->fetch(PDO::FETCH_ASSOC);

    if (!$professor) {
        throw new Exception("Professor não encontrado.");
    }

    echo json_encode($professor);

} catch (Exception $e) {
    echo json_encode(['error' => $e->getMessage()]);
}

$pdo = null;
?>
