<?php
header('Content-Type: application/json');

$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$username = 'root';
$password = 'root';

try {
    // Criar uma nova conexão PDO
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Preparar e executar a consulta
    $sql = "SELECT * FROM Olimpiada";
    $stmt = $pdo->prepare($sql);
    $stmt->execute();

    // Buscar todos os resultados
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

    if (count($result) > 0) {
        // Retornar os dados como JSON
        echo json_encode($result);
    } else {
        echo json_encode([]);
    }
} catch (PDOException $e) {
    echo json_encode(['error' => 'Erro: ' . $e->getMessage()]);
}

// Fechar a conexão
$pdo = null;
?>
