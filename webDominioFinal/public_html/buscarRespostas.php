<?php
header('Content-Type: application/json');

// Conexão com o banco de dados
$servername = "127.0.0.1:3306";
$username = "u740411060_user";
$password = "OWYzZ?o2";
$dbname = "u740411060_hio";

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode([]);
    exit;
}

// Verificar se o ID da pergunta foi enviado
$idPergunta = $_GET['idPergunta'] ?? null;

if ($idPergunta) {
    $sql = "SELECT emailProf, resposta, dataResposta FROM RespostasForum WHERE idPergunta = :idPergunta ORDER BY dataResposta DESC";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':idPergunta', $idPergunta);
    $stmt->execute();
    $respostas = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($respostas);
} else {
    echo json_encode([]);
}
?>
