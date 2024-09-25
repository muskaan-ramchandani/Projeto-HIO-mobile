<?php
$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";     

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    /*CREATE TABLE QntdAcertosSemanais(
	id INT AUTO_INCREMENT NOT NULL,
    emailAluno VARCHAR(100) NOT NULL,
	qntdAcertosSemana INT DEFAULT 0,
	dataInicialSemana DATE NOT NULL,
    dataFinalSemana DATE NOT NULL,
    FOREIGN KEY(emailAluno) REFERENCES Aluno(email),
    PRIMARY KEY(id)
);*/

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $emailAluno = $_POST['emailAluno'];
        $qntdAcertosSemana = $_POST['qntdAcertosSemana'];
        $dataInicialSemana = $_POST['dataInicialSemana'];
        $dataFinalSemana = $_POST['dataFinalSemana'];

        $sql = "INSERT INTO QntdAcertosSemanais (emailAluno, qntdAcertosSemana, dataInicialSemana, dataFinalSemana) 
                VALUES (:emailAluno, :qntdAcertosSemana, :dataInicialSemana, :dataFinalSemana)";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':emailAluno', $emailAluno);
        $stmt->bindParam(':qntdErrosqntdAcertosSemanaSemana', $qntdAcertosSemana);
        $stmt->bindParam(':dataInicialSemana', $dataInicialSemana);
        $stmt->bindParam(':dataFinalSemana', $dataFinalSemana);

        if ($stmt->execute()) {
            echo json_encode(array("status" => "success", "message" => "Registro de erros cadastrado com sucesso!"));
        } else {
            echo json_encode(array("status" => "error", "message" => "Erro ao cadastrar o registro de erros."));
        }
    }
} catch(PDOException $e) {
    echo json_encode(array("status" => "error", "message" => "Erro: " . $e->getMessage()));
}
?>
