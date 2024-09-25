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

/*	id INT AUTO_INCREMENT NOT NULL,
	titulo VARCHAR(300) NOT NULL,
    profQuePostou VARCHAR(100) NOT NULL,
    idConteudoPertencente INT NOT NULL, */

$sql = "
SELECT q.id, q.titulo, p.nomeUsuario AS profQuePostou 
FROM Questionario q
JOIN Professor p ON q.profQuePostou = p.email
WHERE q.idConteudoPertencente = :idConteudoPertencente";

$statement = $pdo->prepare($sql);
$statement->bindParam(':idConteudoPertencente', $idConteudoPertencente, PDO::PARAM_INT);
$statement->execute();

$questionarios = [];
    while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
        $questionarios[] = (object) $result;
    }

echo json_encode(['questionarios' => $questionarios]);

$pdo = null;
?>
