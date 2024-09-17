<?php
// Parâmetros de conexão com o banco de dados
$host = 'localhost';
$dbname = 'hio';
$username = 'root';
$password = 'root';

try {
    // Conectando ao banco de dados usando PDO
    $pdo = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Query para selecionar os nomes e siglas das olimpíadas
    $query = "SELECT nome, sigla FROM Olimpiada";
    $stmt = $pdo->prepare($query);
    $stmt->execute();

    // Pegando os resultados
    $olimpiadas = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Retornando os dados em formato JSON
    echo json_encode($olimpiadas);

} catch (PDOException $e) {
    echo json_encode(['error' => 'Erro na conexão com o banco de dados: ' . $e->getMessage()]);
}
?>
