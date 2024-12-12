<?php
// Configurações do banco de dados
$host = '127.0.0.1:3306';        // Host do banco de dados
$dbname = 'u740411060_hio';  // Nome do banco de dados
$user = 'u740411060_user';          // Usuário do banco de dados
$password = 'OWYzZ?o2';        // Senha do banco de dados

try {
    // Conexão com o banco de dados usando PDO
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $user, $password);
    // Configura o PDO para lançar exceções em caso de erro
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    // Exibe uma mensagem de erro caso a conexão falhe
    die("Erro na conexão com o banco de dados: " . $e->getMessage());
}
?>
