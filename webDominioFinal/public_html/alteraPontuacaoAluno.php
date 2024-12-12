<?php
$servername = "localhost"; 
$username = "u740411060_user";        
$password = "OWYzZ?o2";            
$dbname = "u740411060_hio";

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    /*CREATE TABLE PontuacaoAlunos(
	id INT AUTO_INCREMENT NOT NULL,
	emailAluno VARCHAR(100) NOT NULL,
    pontuacao INT NOT NULL,
	FOREIGN KEY(emailAluno) REFERENCES Aluno(email),
	PRIMARY KEY(id)
);*/

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $emailAluno = $_POST['emailAluno'];  
        $pontuacao = $_POST['pontuacao'];  

        $sql = "SELECT * FROM PontuacaoAlunos WHERE emailAluno = :emailAluno";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':emailAluno', $emailAluno);
        $stmt->execute();
        $resultado = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($resultado) {
            // Se já existir uma linha, atualizar a pontuação
            $novaPontuacao = $resultado['pontuacao'] + $pontuacao;
            $sql = "UPDATE PontuacaoAlunos SET pontuacao = :novaPontuacao WHERE emailAluno = :emailAluno";
            $stmt = $conn->prepare($sql);
            $stmt->bindParam(':novaPontuacao', $novaPontuacao);
            $stmt->bindParam(':emailAluno', $emailAluno);

            if ($stmt->execute()) {
                echo json_encode(array("status" => "success", "message" => "Pontuação atualizada com sucesso!"));
            } else {
                echo json_encode(array("status" => "error", "message" => "Erro ao atualizar a pontuação."));
            }
        } else {
            // Se não existir, inserir uma nova linha com o email e pontuação
            $sql = "INSERT INTO PontuacaoAlunos (emailAluno, pontuacao) VALUES (:emailAluno, :pontuacao)";
            $stmt = $conn->prepare($sql);
            $stmt->bindParam(':emailAluno', $emailAluno);
            $stmt->bindParam(':pontuacao', $pontuacao);

            if ($stmt->execute()) {
                echo json_encode(array("status" => "success", "message" => "Registro de pontuação cadastrado com sucesso!"));
            } else {
                echo json_encode(array("status" => "error", "message" => "Erro ao cadastrar o registro de pontuação."));
            }
        }
    }
} catch(PDOException $e) {
    echo json_encode(array("status" => "error", "message" => "Erro: " . $e->getMessage()));
}
?>
