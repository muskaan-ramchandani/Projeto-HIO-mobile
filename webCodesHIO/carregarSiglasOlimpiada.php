<?php
header('Content-Type: application/json');

$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$username = 'root';
$password = 'root';

try {
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Consulta para buscar as siglas das olimpíadas
    $sql = "SELECT sigla FROM Olimpiada";
    $stmt = $pdo->prepare($sql);
    $stmt->execute();

    $siglas = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($siglas);

} catch (PDOException $e) {
    echo json_encode(['error' => 'Erro ao buscar siglas: ' . $e->getMessage()]);
}
?>
