<?php
$dsn = 'mysql:host=localhost;dbname=hio';
$username = 'root'; // Altere para o seu usuário do banco de dados
$password = 'root'; // Altere para a senha do seu banco de dados

try {
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
    $stmt = $pdo->query('SELECT sigla, nome FROM Olimpiada');
    $olimpiadas = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    echo json_encode($olimpiadas);
} catch (PDOException $e) {
    echo 'Erro: ' . $e->getMessage();
}
