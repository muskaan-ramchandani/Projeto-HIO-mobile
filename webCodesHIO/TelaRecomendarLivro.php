<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $isbn = $_POST['isbn'] ?? null;
    $titulo = $_POST['titulo'] ?? null;
    $autor = $_POST['autor'] ?? null;
    $edicao = $_POST['edicao'] ?? null;
    $dataPublicacao = $_POST['dataPublicacao'] ?? null;
    $siglaOlimpiadaPertencente = $_POST['siglaOlimpiadaPertencente'] ?? null;

    // Verifica se a capa foi enviada
    if (isset($_FILES['cover']) && $_FILES['cover']['error'] === UPLOAD_ERR_OK) {
        $capa = file_get_contents($_FILES['cover']['tmp_name']);
    } else {
        echo json_encode(['error' => 'Erro ao fazer upload da capa do livro.']);
        exit;
    }

    if (!$isbn || !$titulo || !$autor || !$edicao || !$dataPublicacao || !$siglaOlimpiadaPertencente || !$capa) {
        echo json_encode(['error' => 'Por favor, preencha todos os campos.']);
        exit;
    }

    $dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
    $username = 'root';
    $password = 'root';

    try {
        $pdo = new PDO($dsn, $username, $password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Insere o livro no banco de dados
        $sqlInsert = "INSERT INTO Livro (isbn, capa, titulo, autor, edicao, dataPublicacao, siglaOlimpiadaPertencente) 
                      VALUES (:isbn, :capa, :titulo, :autor, :edicao, :dataPublicacao, :siglaOlimpiadaPertencente)";
        $stmtInsert = $pdo->prepare($sqlInsert);
        $stmtInsert->bindParam(':isbn', $isbn);
        $stmtInsert->bindParam(':capa', $capa, PDO::PARAM_LOB);
        $stmtInsert->bindParam(':titulo', $titulo);
        $stmtInsert->bindParam(':autor', $autor);
        $stmtInsert->bindParam(':edicao', $edicao);
        $stmtInsert->bindParam(':dataPublicacao', $dataPublicacao);
        $stmtInsert->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);

        if ($stmtInsert->execute()) {
            echo json_encode(['success' => true]);
        } else {
            echo json_encode(['error' => 'Erro ao adicionar livro.']);
        }
    } catch (PDOException $e) {
        echo json_encode(['error' => 'Erro no banco de dados: ' . $e->getMessage()]);
    }
}
