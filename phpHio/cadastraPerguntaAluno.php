<?php
$servername = "sql207.infinityfree.com";
$username = "if0_37755624";
$password = "1k31AyGMaqyz7";
$dbname = "if0_37755624_hio";   

/*CREATE TABLE PerguntasForum(
	id INT AUTO_INCREMENT NOT NULL,
	emailAluno VARCHAR(100) NOT NULL,
    titulo TEXT NOT NULL,
    pergunta TEXT NOT NULL,
	dataPublicacao DATE NOT NULL,
    siglaOlimpiadaRelacionada VARCHAR(10) NOT NULL,
	FOREIGN KEY(siglaOlimpiadaRelacionada) REFERENCES Olimpiada(sigla),
	FOREIGN KEY(emailAluno) REFERENCES Aluno(email),
	PRIMARY KEY(id)
);*/

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $emailAluno = $_POST['emailAluno'];
        $titulo = $_POST['titulo'];
        $pergunta = $_POST['pergunta'];
        $siglaOlimpiadaRelacionada = $_POST['siglaOlimpiadaRelacionada'];
		$dataPublicacao = $_POST['dataPublicacao'];
    
		$sql = "INSERT INTO PerguntasForum (emailAluno, titulo, pergunta, siglaOlimpiadaRelacionada, dataPublicacao) 
        VALUES (:emailAluno, :titulo, :pergunta, :siglaOlimpiadaRelacionada, :dataPublicacao)";

		$stmt = $conn->prepare($sql);
		$stmt->bindParam(':emailAluno', $emailAluno);
		$stmt->bindParam(':titulo', $titulo);
        $stmt->bindParam(':pergunta', $pergunta);
		$stmt->bindParam(':siglaOlimpiadaRelacionada', $siglaOlimpiadaRelacionada);
		$stmt->bindParam(':dataPublicacao', $dataPublicacao);

		if ($stmt->execute()) {
			echo json_encode(array("status" => "success", "message" => "Pergunta publicada com sucesso!"));
		} else {
			echo json_encode(array("status" => "error", "message" => "Erro ao publicar pergunta."));
		}
    
    }
} catch(PDOException $e) {
    echo json_encode(array("status" => "error", "message" => "Erro: " . $e->getMessage()));
}
?>