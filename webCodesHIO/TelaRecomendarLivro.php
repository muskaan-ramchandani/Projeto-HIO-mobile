<?php
session_start(); // Certifique-se de iniciar a sessão se estiver usando sessões

header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $isbn = $_POST['isbn'] ?? null;
    $titulo = $_POST['titulo'] ?? null;
    $autor = $_POST['autor'] ?? null;
    $edicao = $_POST['edicao'] ?? null;
    $dataPublicacao = $_POST['dataPublicacao'] ?? null;

    // Supondo que a sigla da olimpíada esteja disponível na sessão
    $siglaOlimpiadaPertencente = $_SESSION['siglaOlimpiada'] ?? null;

    if (!$isbn || !$titulo || !$autor || !$edicao || !$dataPublicacao || !$siglaOlimpiadaPertencente) {
        echo json_encode(['error' => 'Por favor, preencha todos os campos.']);
        exit;
    }

    $dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
    $username = 'root';
    $password = 'root';

    try {
        $pdo = new PDO($dsn, $username, $password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $sql = "INSERT INTO Livro (isbn, titulo, autor, edicao, dataPublicacao, siglaOlimpiadaPertencente) VALUES (:isbn, :titulo, :autor, :edicao, :dataPublicacao, :siglaOlimpiadaPertencente)";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':isbn', $isbn);
        $stmt->bindParam(':titulo', $titulo);
        $stmt->bindParam(':autor', $autor);
        $stmt->bindParam(':edicao', $edicao);
        $stmt->bindParam(':dataPublicacao', $dataPublicacao);
        $stmt->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);
        
        if ($stmt->execute()) {
            echo json_encode(['success' => true]);
        } else {
            echo json_encode(['error' => 'Erro ao recomendar livro.']);
        }
    } catch (PDOException $e) {
        echo json_encode(['error' => 'Erro: ' . $e->getMessage()]);
    }
} else {
    echo json_encode(['error' => 'Método não permitido.']);
}
?>
