<?php
// Conexão com o banco de dados
$servername = "127.0.0.1:3306";
$username = "u740411060_user";
$password = "OWYzZ?o2";
$dbname = "u740411060_hio";

// Conecta ao banco de dados
try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    die("Erro na conexão com o banco de dados: " . $e->getMessage());
}

// Verifica se o email está presente na URL
if (isset($_GET['email']) && !empty($_GET['email'])) {
    $email = $_GET['email'];

    // Prepara a consulta SQL para buscar os textos associados ao email
    $sql = "SELECT id, titulo, texto, profQuePostou FROM Texto WHERE profQuePostou = :email";  
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':email', $email, PDO::PARAM_STR);
    $stmt->execute();

    // Verifica se encontrou resultados
    if ($stmt->rowCount() > 0) {
        // Armazena os resultados em um array
        $textos = $stmt->fetchAll(PDO::FETCH_ASSOC);
    } else {
        $textos = [];
    }
} else {
    $textos = [];
}

// Fecha a conexão com o banco
$pdo = null;
?>
