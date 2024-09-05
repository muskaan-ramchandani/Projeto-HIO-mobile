<?php
$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";     

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Falha na conexão: " . $conn->connect_error);
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $nomeCompleto = $_POST['nomeCompleto'];
    $nomeUsuario = $_POST['nomeUsuario'];
    $email = $_POST['email'];
    $senha = $_POST['senha'];

    $sql = "INSERT INTO Aluno (nomeCompleto, nomeUsuario, email, senha) VALUES ('$nomeCompleto', '$nomeUsuario', '$email', '$senha')";

    if ($conn->query($sql) === TRUE) {
        echo "Cadastro realizado com sucesso!";
    } else {
        echo "Erro: " . $sql . "<br>" . $conn->error;
    }
}

$conn->close();
?>
