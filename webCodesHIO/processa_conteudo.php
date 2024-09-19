<?php
$dsn = 'mysql:host=localhost;dbname=hio';
$username = 'root'; // Altere para o seu usuário do banco de dados
$password = 'root'; // Altere para a senha do seu banco de dados

try {
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $titulo = $_POST['titulo'];
        $subtitulo = $_POST['subtitulo'];
        $siglaOlimpiada = $_POST['siglaOlimpiada'];

        // Preparar a query de inserção
        $stmt = $pdo->prepare('INSERT INTO Conteudo (titulo, subtitulo, siglaOlimpiadaPertencente) VALUES (:titulo, :subtitulo, :siglaOlimpiadaPertencente)');
        
        // Executar a query com os parâmetros
        $stmt->execute([
            ':titulo' => $titulo,
            ':subtitulo' => $subtitulo,
            ':siglaOlimpiadaPertencente' => $siglaOlimpiada
        ]);
        
        // Mensagem de sucesso
        echo '<div class="message">Conteúdo inserido com sucesso!</div>';
    }
} catch (PDOException $e) {
    // Mensagem de erro
    echo '<div class="message error">Erro: ' . $e->getMessage() . '</div>';
}
?>
