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

$idQuestionarioPertencente = $_GET['idQuestionarioPertencente'] ?? '';

if (empty($idConteudoPertencente)) {
    echo json_encode(["message" => "Não foi possível detectar o id do questionário atual"]);
    exit;
}

/*	CREATE TABLE Questao(
	id INT AUTO_INCREMENT NOT NULL,
	txtPergunta TEXT NOT NULL,
    idQuestionarioPertencente INT NOT NULL, */

$sql = "
SELECT id, txtPergunta 
FROM Questao qt
WHERE idQuestionarioPertencente = :idQuestionarioPertencente";

$statement = $pdo->prepare($sql);
$statement->bindParam(':idQuestionarioPertencente', $idQuestionarioPertencente, PDO::PARAM_INT);
$statement->execute();

$questoesDoQuestionario = [];
    while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
        $questoesDoQuestionario[] = (object) $result;
    }

echo json_encode(['questoesDoQuestionario' => $questoesDoQuestionario]);

$pdo = null;
?>
