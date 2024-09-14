<?php
// Conexão com o banco de dados usando PDO
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "hio";

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "Erro de conexão: " . $e->getMessage();
    die();
}

// Se houver um termo de pesquisa, execute a consulta
$searchTerm = isset($_GET['search']) ? $_GET['search'] : '';

// Preparar a consulta SQL com filtro
$sql = "SELECT * FROM Olimpiada WHERE nome LIKE :searchTerm OR sigla LIKE :searchTerm";
$stmt = $pdo->prepare($sql);
$stmt->bindValue(':searchTerm', '%' . $searchTerm . '%');
$stmt->execute();

$olympiads = $stmt->fetchAll(PDO::FETCH_ASSOC);

// Fechar conexão
$pdo = null;

// Se for uma solicitação AJAX, retornar JSON e sair
if (isset($_GET['search'])) {
    header('Content-Type: application/json');
    echo json_encode($olympiads);
    exit;
}
?>
