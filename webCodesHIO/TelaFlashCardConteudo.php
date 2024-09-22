<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');

$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";     

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
} catch (PDOException $e) {
    echo json_encode(["error" => "Erro na conexão com o banco de dados"]);
    exit;
}

// Verificando se o ID do conteúdo foi passado corretamente
$conteudoId = $_GET['id'] ?? '';

if (empty($conteudoId)) {
    echo json_encode(["error" => "Nenhum conteúdo foi fornecido."]);
    exit;
}

// Selecionando o conteúdo pelo ID
$sql = "
SELECT c.id, c.titulo, c.subtitulo, c.siglaOlimpiadaPertencente 
FROM Conteudo c
WHERE c.id = :conteudoId";

$statement = $pdo->prepare($sql);
$statement->bindParam(':conteudoId', $conteudoId);
$statement->execute();

$conteudo = $statement->fetch(PDO::FETCH_ASSOC);

if ($conteudo) {
    echo json_encode($conteudo);
} else {
    echo json_encode(["error" => "Conteúdo não encontrado."]);
}

$pdo = null;
?>
