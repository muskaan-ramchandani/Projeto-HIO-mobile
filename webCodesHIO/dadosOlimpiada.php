<?php
// dadosOlimpiadas.php

// Configurações de conexão ao banco de dados
$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8mb4';
$usuario = 'root';
$senha = 'root';

try {
    // Conexão usando PDO
    $pdo = new PDO($dsn, $usuario, $senha);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Query para buscar todas as Olimpíadas
    $sql = "SELECT nome, sigla, icone, cor FROM Olimpiada";
    $stmt = $pdo->query($sql);

    // Pega todas as Olimpíadas em formato de array associativo
    $olimpiadas = $stmt->fetchAll(PDO::FETCH_ASSOC);

} catch (PDOException $e) {
    echo "Erro na conexão ou na consulta: " . $e->getMessage();
    $olimpiadas = [];
}

// Retorna o array de Olimpíadas para uso no HTML
return $olimpiadas;
?>
