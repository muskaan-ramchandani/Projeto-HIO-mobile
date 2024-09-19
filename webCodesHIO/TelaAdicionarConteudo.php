<?php
header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $titulo = $_POST['titulo'] ?? null;
    $subtitulo = $_POST['subtitulo'] ?? null;
    $siglaOlimpiadaPertencente = $_POST['siglaOlimpiadaPertencente'] ?? null;

    if (!$titulo || !$subtitulo || !$siglaOlimpiadaPertencente) {
        echo json_encode(['error' => 'Por favor, preencha todos os campos.']);
        exit;
    }

    $dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
    $username = 'root';
    $password = 'root';

    try {
        $pdo = new PDO($dsn, $username, $password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $sql = "INSERT INTO Conteudo (titulo, subtitulo, siglaOlimpiadaPertencente) VALUES (:titulo, :subtitulo, :siglaOlimpiadaPertencente)";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':titulo', $titulo);
        $stmt->bindParam(':subtitulo', $subtitulo);
        $stmt->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);
        
        if ($stmt->execute()) {
            echo json_encode(['success' => true]);
        } else {
            echo json_encode(['error' => 'Erro ao adicionar conteúdo.']);
        }
    } catch (PDOException $e) {
        echo json_encode(['error' => 'Erro: ' . $e->getMessage()]);
    }
} else {
    echo json_encode(['error' => 'Método não permitido.']);
}
?>
