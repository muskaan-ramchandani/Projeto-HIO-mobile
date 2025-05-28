<?php
$servername = "localhost"; 
$username = "u792730258_noemi";        
$password = "Masenfimacontece123";            
$dbname = "u792730258_hio";   

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $data = $_POST['data'];
        $idQuestionario = $_POST['idQuestionario'];
        $emailAluno = $_POST['emailAluno'];

        // Contar acertos do quest cancelado
        $sqlAcertos = "SELECT COUNT(*) as qntdAcertos FROM AcertosAluno WHERE idQuestionarioPertencente = :idQuestionario AND dataAcerto = :data AND emailAluno = :emailAluno";
        $stmtAcertos = $conn->prepare($sqlAcertos);
        $stmtAcertos->bindParam(':idQuestionario', $idQuestionario);
        $stmtAcertos->bindParam(':data', $data);
        $stmtAcertos->bindParam(':emailAluno', $emailAluno); 
        $stmtAcertos->execute();
        $qntdAcertos = $stmtAcertos->fetch(PDO::FETCH_ASSOC)['qntdAcertos'];

        // Contar erros do quest cancelado
        $sqlErros = "SELECT COUNT(*) as qntdErros FROM ErrosAluno WHERE idQuestionarioPertencente = :idQuestionario AND dataErro = :data AND emailAluno = :emailAluno";
        $stmtErros = $conn->prepare($sqlErros);
        $stmtErros->bindParam(':idQuestionario', $idQuestionario);
        $stmtErros->bindParam(':data', $data);
        $stmtErros->bindParam(':emailAluno', $emailAluno); 
        $stmtErros->execute();
        $qntdErros = $stmtErros->fetch(PDO::FETCH_ASSOC)['qntdErros'];

        // Apagar acertos do quest cancelado
        $sqlDeleteAcertos = "DELETE FROM AcertosAluno WHERE idQuestionarioPertencente = :idQuestionario AND dataAcerto = :data AND emailAluno = :emailAluno";
        $stmtDeleteAcertos = $conn->prepare($sqlDeleteAcertos);
        $stmtDeleteAcertos->bindParam(':idQuestionario', $idQuestionario);
        $stmtDeleteAcertos->bindParam(':data', $data);
        $stmtDeleteAcertos->bindParam(':emailAluno', $emailAluno);
        $stmtDeleteAcertos->execute();

        // Apagar erros do quest cancelado
        $sqlDeleteErros = "DELETE FROM ErrosAluno WHERE idQuestionarioPertencente = :idQuestionario AND dataErro = :data AND emailAluno = :emailAluno";
        $stmtDeleteErros = $conn->prepare($sqlDeleteErros);
        $stmtDeleteErros->bindParam(':idQuestionario', $idQuestionario);
        $stmtDeleteErros->bindParam(':data', $data);
        $stmtDeleteErros->bindParam(':emailAluno', $emailAluno); 
        $stmtDeleteErros->execute();

        echo json_encode(array(
            "status" => "success",
            "qntdErros" => $qntdErros,
            "qntdAcertos" => $qntdAcertos
        ));
    }
} catch(PDOException $e) {
    echo json_encode(array("status" => "error", "message" => "Erro: " . $e->getMessage()));
}
?>
