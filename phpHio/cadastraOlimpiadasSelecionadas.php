<?php
$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";       

//sigla VARCHAR(10) NOT NULL,
//emailAluno VARCHAR(100) NOT NULL,

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $sigla = $_POST['sigla'];
        $emailAluno = $_POST['emailAluno'];
    
		$sql = "INSERT INTO OlimpiadasSelecionadas (sigla, emailAluno) VALUES (:sigla, :emailAluno)";
		$stmt = $conn->prepare($sql);
		$stmt->bindParam(':sigla', $sigla);
		$stmt->bindParam(':emailAluno', $emailAluno);

		if ($stmt->execute()) {
			echo json_encode(array("status" => "success", "message" => "Olimpíadas selecionadas com sucesso!"));
		} else {
			echo json_encode(array("status" => "error", "message" => "Erro ao cadastrar olimpíadas selecionadas."));
		}
    
    }
} catch(PDOException $e) {
    echo json_encode(array("status" => "error", "message" => "Erro: " . $e->getMessage()));
}
?>