<?php
header('Content-Type: application/json; charset=utf-8');

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

$idConteudoPertencente = $_GET['idConteudoPertencente'] ?? '';

if (empty($idConteudoPertencente)) {
    echo json_encode(["message" => "Não foi possível detectar o id do conteúdo atual"]);
    exit;
}

$sql = "
SELECT v.id, v.capa, v.titulo, v.link, p.nomeUsuario AS profQuePostou 
FROM Video v
JOIN Professor p ON v.profQuePostou = p.email
WHERE v.idConteudoPertencente = :idConteudoPertencente";

$statement = $pdo->prepare($sql);
$statement->bindParam(':idConteudoPertencente', $idConteudoPertencente, PDO::PARAM_INT);
$statement->execute();

$videos = [];
while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
    if (isset($result['capa'])) {
        $result['capa'] = base64_encode($result['capa']);
    }
    $videos[] = (object) $result;
}

echo json_encode(['videos' => $videos]);

$pdo = null;
?>
