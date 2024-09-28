<?php
$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";     

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $idAlternativaMarcada = $_POST['idAlternativaMarcada'];
        $idQuestionarioPertencente = $_POST['idQuestionarioPertencente'];
        $idQuestaoPertencente = $_POST['idQuestaoPertencente'];
        $dataAcerto = $_POST['dataAcerto'];
        $emailAluno = $_POST['emailAluno'];


        $sql = "INSERT INTO AcertosAluno (idAlternativaMarcada, idQuestionarioPertencente, idQuestaoPertencente, dataAcerto, emailAluno) 
                VALUES (:idAlternativaMarcada, :idQuestionarioPertencente, :idQuestaoPertencente, :dataAcerto, :emailAluno)";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':idAlternativaMarcada', $idAlternativaMarcada);
        $stmt->bindParam(':idQuestionarioPertencente', $idQuestionarioPertencente);
        $stmt->bindParam(':idQuestaoPertencente', $idQuestaoPertencente);
        $stmt->bindParam(':dataAcerto', $dataAcerto);
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
