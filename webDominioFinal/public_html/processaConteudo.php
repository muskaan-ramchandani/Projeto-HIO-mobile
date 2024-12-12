<?php
// processaConteudo.php


$dsn = 'mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';


try {
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Capturar o ID do conteúdo da URL
    if (isset($_GET['id'])) {
        $id = $_GET['id'];

        // Buscar o título do assunto pelo ID
        $sql = "SELECT titulo FROM Conteudo WHERE id = :id";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':id', $id);
        $stmt->execute();

        $conteudo = $stmt->fetch(PDO::FETCH_ASSOC);
        
        // Passar o título para o HTML
        $titulo = $conteudo ? htmlspecialchars($conteudo['titulo']) : 'Título não encontrado';
    } else {
        // Se não houver ID na URL, redirecionar para a página inicial
        header('Location: TelaInicialProfessorHTML.php');
        exit();
    }
} catch (PDOException $e) {
    echo "Erro: " . $e->getMessage();
}
