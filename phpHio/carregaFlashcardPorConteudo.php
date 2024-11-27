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
SELECT f.id, f.imagem, f.titulo, f.texto, p.nomeUsuario AS profQuePostou 
FROM Flashcard f
JOIN Professor p ON f.profQuePostou = p.email
WHERE f.idConteudoPertencente = :idConteudoPertencente";

$statement = $pdo->prepare($sql);
$statement->bindParam(':idConteudoPertencente', $idConteudoPertencente, PDO::PARAM_INT);
$statement->execute();

$flashcards = [];
while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
    if (isset($result['imagem'])) {
        $result['imagem'] = base64_encode($result['imagem']);
    }
    $flashcards[] = (object) $result;
}

echo json_encode(['flashcards' => $flashcards]);

$pdo = null;
?>
