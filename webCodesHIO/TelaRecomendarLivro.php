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

    // Validação de campos
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

        // Verifica se já existe um livro com o mesmo ISBN e olimpíada
        $sqlCheck = "SELECT COUNT(*) FROM Livro WHERE isbn = :isbn AND siglaOlimpiadaPertencente = :siglaOlimpiadaPertencente";
        $stmtCheck = $pdo->prepare($sqlCheck);
        $stmtCheck->bindParam(':isbn', $isbn);
        $stmtCheck->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);
        $stmtCheck->execute();
        $exists = $stmtCheck->fetchColumn();

        if ($exists > 0) {
            echo json_encode(['error' => 'Já existe um livro com este ISBN para a olimpíada selecionada.']);
            exit;
        }

        // Insere o livro no banco de dados
        $sqlInsert = "INSERT INTO Livro (isbn, titulo, autor, edicao, dataPublicacao, siglaOlimpiadaPertencente) 
                      VALUES (:isbn, :titulo, :autor, :edicao, :dataPublicacao, :siglaOlimpiadaPertencente)";
        $stmtInsert = $pdo->prepare($sqlInsert);
        $stmtInsert->bindParam(':isbn', $isbn);
        $stmtInsert->bindParam(':titulo', $titulo);
        $stmtInsert->bindParam(':autor', $autor);
        $stmtInsert->bindParam(':edicao', $edicao, PDO::PARAM_INT);
        $stmtInsert->bindParam(':dataPublicacao', $dataPublicacao);
        $stmtInsert->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);

        if ($stmtInsert->execute()) {
            // Busca o livro inserido para retornar
            $sql = "SELECT id, isbn, titulo, autor, edicao, dataPublicacao FROM Livro 
                    WHERE siglaOlimpiadaPertencente = :siglaOlimpiadaPertencente 
                    ORDER BY id DESC LIMIT 1";
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);
            $stmt->execute();

            $livro = $stmt->fetch(PDO::FETCH_ASSOC);

            if ($livro) {
                echo json_encode(['success' => true, 'livro' => $livro]);
            } else {
                echo json_encode(['error' => 'Erro ao buscar o livro inserido.']);
            }
        } else {
            echo json_encode(['error' => 'Erro ao adicionar o livro.']);
        }
    } catch (PDOException $e) {
        echo json_encode(['error' => 'Erro no banco de dados: ' . $e->getMessage()]);
    }
} else {
    echo json_encode(['error' => 'Método não permitido.']);
}
?>
