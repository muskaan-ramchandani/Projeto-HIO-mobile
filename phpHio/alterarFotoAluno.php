<?php
header('Content-Type: application/json; charset=utf-8');

$servername = "localhost"; 
$username = "u740411060_user";        
$password = "OWYzZ?o2";            
$dbname = "u740411060_hio";    

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if (isset($_POST['fotoPerfil']) && isset($_POST['email'])) {
        $fotoPerfil = $_POST['fotoPerfil'];
        $email = $_POST['email'];

        $imagemBinario = base64_decode($fotoPerfil, true);
        if ($imagemBinario === false) {
            throw new Exception('Imagem inválida.');
        }

        $sql = 'UPDATE Aluno SET fotoPerfil = :imagemBinario WHERE email = :email';
        $statement = $pdo->prepare($sql);
        $statement->bindParam(':imagemBinario', $imagemBinario, PDO::PARAM_LOB);
        $statement->bindParam(':email', $email);

        if ($statement->execute()) {
            $response = array('status' => 'success', 'message' => 'Alterações feitas com sucesso!');
        } else {
            $response = array('status' => 'error', 'message' => 'Erro ao alterar sua foto.');
        }
    } else {
        $response = array('status' => 'error', 'message' => 'Parâmetros incompletos.');
    }
} catch (Exception $e) {
    $response = array('status' => 'error', 'message' => 'Erro: ' . $e->getMessage());
}

echo json_encode($response);
?>
