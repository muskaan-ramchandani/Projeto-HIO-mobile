<?php
// Conexão com o banco de dados
$host = 'localhost';
$dbname = 'hio';
$username = 'root';
$password = '';

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "Erro ao conectar ao banco de dados: " . $e->getMessage();
}

// Função para buscar dados de flashcards, vídeos, etc.
function buscarDados($email) {
    global $pdo;
    
    // Consultas para buscar flashcards, vídeos e conteúdos
    $stmt = $pdo->prepare("SELECT * FROM flashcards WHERE email = :email");
    $stmt->bindParam(':email', $email);
    $stmt->execute();
    $flashcards = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    // Similarmente, podemos adicionar consultas para vídeos e conteúdos
    // Retorna os dados em um array
    return ['flashcards' => $flashcards];
}
?>
