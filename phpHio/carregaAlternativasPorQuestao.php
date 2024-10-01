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
$idQuestaoPertencente = $_GET['idQuestaoPertencente'] ?? '';

if (empty($idQuestionarioPertencente) || empty($idQuestaoPertencente)) {
    echo json_encode(["message" => "Está faltando um dos parâmetros", "idQuestionarioPertencente" => $idQuestionarioPertencente, "idQuestaoPertencente" => $idQuestaoPertencente]);
    exit;
}

/*CREATE TABLE AlternativasQuestao(
	id INT AUTO_INCREMENT NOT NULL,
	textoAlternativa TEXT NOT NULL,
    corretaOuErrada BOOLEAN NOT NULL,
    idQuestionarioPertencente INT NOT NULL,
    idQuestaoPertencente INT NOT NULL,
	FOREIGN KEY(idQuestionarioPertencente) REFERENCES Questionario(id), 
	FOREIGN KEY(idQuestaoPertencente) REFERENCES Questao(id), 
	PRIMARY KEY(id)
);*/

$sql = "
    SELECT id, textoAlternativa, corretaOuErrada 
    FROM AlternativasQuestao 
    WHERE idQuestionarioPertencente = :idQuestionarioPertencente 
    AND idQuestaoPertencente = :idQuestaoPertencente
";

$statement = $pdo->prepare($sql);
$statement->bindParam(':idQuestionarioPertencente', $idQuestionarioPertencente, PDO::PARAM_INT);
$statement->bindParam(':idQuestaoPertencente', $idQuestaoPertencente, PDO::PARAM_INT);
$statement->execute();

$alternativasDaQuestao = [];
    while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
        $alternativasDaQuestao[] = (object) $result;
    }

echo json_encode(['alternativasDaQuestao' => $alternativasDaQuestao]);

$pdo = null;
?>
