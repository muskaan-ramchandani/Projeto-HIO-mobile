<?php
// Configurações do banco de dados
$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$username = 'root';
$password = 'root';

try {
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Verifica se o formulário foi enviado
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $titulo = $_POST['titulo'] ?? '';
        $subtitulo = $_POST['subtitulo'] ?? '';
        $siglaOlimpiadaPertencente = $_POST['sigla'] ?? '';

        // Preparar a consulta para inserir conteúdo
        $sql = "INSERT INTO Conteudo (titulo, subtitulo, siglaOlimpiadaPertencente) VALUES (:titulo, :subtitulo, :sigla)";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':titulo', $titulo);
        $stmt->bindParam(':subtitulo', $subtitulo);
        $stmt->bindParam(':sigla', $siglaOlimpiadaPertencente);

        if ($stmt->execute()) {
            echo '<p>Conteúdo adicionado com sucesso!</p>';
        } else {
            echo '<p>Erro ao adicionar conteúdo.</p>';
        }
    }

    // Resto do código para carregar as olimpíadas
    $sql = "SELECT nome, sigla, icone, cor FROM Olimpiada";
    $stmt = $pdo->query($sql);
    // ...
} catch (PDOException $e) {
    echo 'Erro ao conectar ao banco de dados: ' . $e->getMessage();
}
?>
