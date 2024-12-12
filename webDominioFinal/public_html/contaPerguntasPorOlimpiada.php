<?php 
header('Content-Type: application/json; charset=utf-8');

$servername = "localhost"; 
$username = "u740411060_user";        
$password = "OWYzZ?o2";            
$dbname = "u740411060_hio";       

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
    exit;
}

$sql = "
    SELECT siglaOlimpiadaRelacionada, COUNT(*) as totalPerguntas
    FROM PerguntasForum
    GROUP BY siglaOlimpiadaRelacionada;
";

$statement = $pdo->query($sql);

$contagemPerguntas = [];
while ($row = $statement->fetch(PDO::FETCH_ASSOC)) {
    $contagemPerguntas[$row['siglaOlimpiadaRelacionada']] = $row['totalPerguntas'];
}

echo json_encode(['contagemPerguntas' => $contagemPerguntas]);

$pdo = null;
?>