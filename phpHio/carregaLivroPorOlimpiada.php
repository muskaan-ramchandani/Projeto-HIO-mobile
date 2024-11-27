<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');

$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";      

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
    exit;
}

$siglaOlimpiadaPertencente = $_GET['siglaOlimpiadaPertencente'] ?? '';

if (empty($siglaOlimpiadaPertencente)) {
    echo json_encode(["message" => "Não foi possível detectar a olimpíada selecionada"]);
    exit;
}

$sql = "
SELECT l.id, l.isbn, l.capa, l.titulo, l.autor, l.edicao, l.dataPublicacao 
FROM Livro l
WHERE l.siglaOlimpiadaPertencente = :siglaOlimpiadaPertencente";

$statement = $pdo->prepare($sql);
$statement->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);
$statement->execute();

$livros = [];
while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
    if (isset($result['capa'])) {
        $result['capa'] = base64_encode($result['capa']);
    }
    $livros[] = (object) $result;
}

echo json_encode(['livros' => $livros]);

$pdo = null;
?>
