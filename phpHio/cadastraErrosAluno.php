<?php
$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";      

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);


    /*CREATE TABLE ErrosAluno(
	id INT AUTO_INCREMENT NOT NULL,
    idAlternativaMarcada INT NOT NULL,
    idAlternativaCorreta INT NOT NULL,
    idQuestionarioPertencente INT NOT NULL,
    idQuestaoPertencente INT NOT NULL,
    FOREIGN KEY(idAlternativaMarcada) REFERENCES AlternativasQuestao(id),
	FOREIGN KEY(idQuestionarioPertencente) REFERENCES Questionario(id), 
	FOREIGN KEY(idQuestaoPertencente) REFERENCES Questao(id), 
	PRIMARY KEY(id)
);*/

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $idAlternativaMarcada = $_POST['idAlternativaMarcada'];
        $idAlternativaCorreta = $_POST['idAlternativaCorreta'];
        $idQuestionarioPertencente = $_POST['idQuestionarioPertencente'];
        $idQuestaoPertencente = $_POST['idQuestaoPertencente'];
        $dataErro = $_POST['dataErro'];
        $emailAluno = $_POST['emailAluno'];


        $sql = "INSERT INTO ErrosAluno (idAlternativaMarcada, idAlternativaCorreta, idQuestionarioPertencente, idQuestaoPertencente, dataErro, emailAluno) 
                VALUES (:idAlternativaMarcada, :idAlternativaCorreta, :idQuestionarioPertencente, :idQuestaoPertencente, :dataErro, :emailAluno)";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':idAlternativaMarcada', $idAlternativaMarcada);
        $stmt->bindParam(':idAlternativaCorreta', $idAlternativaCorreta);
        $stmt->bindParam(':idQuestionarioPertencente', $idQuestionarioPertencente);
        $stmt->bindParam(':idQuestaoPertencente', $idQuestaoPertencente);
        $stmt->bindParam(':dataErro', $dataErro);
        $stmt->bindParam(':emailAluno', $emailAluno);

        if ($stmt->execute()) {
            echo json_encode(array("status" => "success", "message" => "Registro de acertos cadastrado com sucesso!"));
        } else {
            echo json_encode(array("status" => "error", "message" => "Erro ao cadastrar o registro de acertos."));
        }
    }
} catch(PDOException $e) {
    echo json_encode(array("status" => "error", "message" => "Erro: " . $e->getMessage()));
}
?>
