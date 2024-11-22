<?php
// Configurações do banco de dados
$host = 'localhost';        // Host do banco de dados
$dbname = 'hio';  // Nome do banco de dados
$user = 'root';          // Usuário do banco de dados
$password = 'root';        // Senha do banco de dados

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
