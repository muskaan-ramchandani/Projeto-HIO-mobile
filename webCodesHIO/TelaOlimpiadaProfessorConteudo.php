<?php
// dadosConteudoPDO.php

// Configurações de conexão
$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8mb4';
$usuario = 'root';
$senha = 'root';

try {
    // Conectando ao banco de dados usando PDO
    $pdo = new PDO($dsn, $usuario, $senha);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Query para buscar os conteúdos
    $sql = "SELECT titulo, subtitulo, siglaOlimpiadaPertencente FROM Conteudo";
    $stmt = $pdo->query($sql);

    // Verifica se há resultados e armazena em um array
    $conteudos = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
} catch (PDOException $e) {
    // Se houver erro, exibe a mensagem
    echo "Erro na conexão ou na consulta: " . $e->getMessage();
    $conteudos = [];
}

// Retorna os conteúdos para serem usados no HTML
return $conteudos;
?>
