<?php
$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";     

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $nomeCompleto = $_POST['nomeCompleto'];
        $nomeUsuario = $_POST['nomeUsuario'];
        $email = $_POST['email'];
        $senha = $_POST['senha'];

        //Verificação do email
        $sqlCheckEmail = "SELECT COUNT(*) FROM Aluno WHERE email = :email";
        $stmtCheckEmail = $conn->prepare($sqlCheckEmail);
        $stmtCheckEmail->bindParam(':email', $email);
        $stmtCheckEmail->execute();
        $emailExists = $stmtCheckEmail->fetchColumn();

        if ($emailExists > 0) {
            echo json_encode(array("status" => "error", "message" => "O e-mail já está cadastrado. Tente com outro email ou faça login!"));
        } else {
            $sql = "INSERT INTO Aluno (nomeCompleto, nomeUsuario, email, senha) VALUES (:nomeCompleto, :nomeUsuario, :email, :senha)";
            $stmt = $conn->prepare($sql);
            $stmt->bindParam(':nomeCompleto', $nomeCompleto);
            $stmt->bindParam(':nomeUsuario', $nomeUsuario);
            $stmt->bindParam(':email', $email);
            $stmt->bindParam(':senha', $senha);

            if ($stmt->execute()) {
                echo json_encode(array("status" => "success", "message" => "Cadastro realizado com sucesso!"));
            } else {
                echo json_encode(array("status" => "error", "message" => "Erro ao cadastrar o aluno."));
            }
        }
    }
} catch(PDOException $e) {
    echo json_encode(array("status" => "error", "message" => "Erro: " . $e->getMessage()));
}
?>
