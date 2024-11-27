<?php
header('Content-Type: application/json; charset=utf-8');
$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";     

$pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);

if (isset($_POST['fotoPerfil'])&&isset($_POST['email'])) {

    $fotoPerfil = $_POST['fotoPerfil'];
    $imagemBinario = base64_decode($fotoPerfil);

    $email = $_POST['email'];

    $sql = 'UPDATE Aluno SET fotoPerfil = :imagemBinario WHERE email = :email';

    $statement = $pdo->prepare($sql);
    $statement->bindParam(':imagemBinario', $imagemBinario);
    $statement->bindParam(':email', $email);


    if ($statement->execute()) {
        $response = array('status' => 'success', 'message' => 'Alterações feitas com sucesso!');
    } else {
        $response = array('status' => 'error', 'message' => 'Erro ao alterar sua foto.');
    }
    
} else {
    $response = array('status' => 'error', 'message' => 'Parâmetros incompletos.');
}

echo json_encode($response);
?>