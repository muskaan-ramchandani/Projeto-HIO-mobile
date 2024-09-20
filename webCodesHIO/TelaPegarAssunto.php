<?php
header('Content-Type: application/json');

$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8'; // Ajuste conforme necessário
$username = 'root'; // Seu usuário do banco de dados
$password = 'root'; // Sua senha do banco de dados

// Obtém a sigla da olimpíada a partir da query string
$siglaOlimpiada = isset($_GET['sigla']) ? $_GET['sigla'] : '';

try {
    // Cria uma nova conexão PDO
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Consulta os conteúdos para a olimpíada específica
    $sql = "SELECT titulo FROM Conteudo WHERE siglaOlimpiadaPertencente = :siglaOlimpiadaPertencente";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);
    $stmt->execute();

    $contents = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Retorna os dados em formato JSON
    echo json_encode($contents);

} catch (PDOException $e) {
    echo json_encode(['error' => 'Erro na conexão: ' . $e->getMessage()]);
}
?>
