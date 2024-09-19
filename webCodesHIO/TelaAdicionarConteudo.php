<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');

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

        // Verifica se já existe um conteúdo com o mesmo título e olimpíada
        $sqlCheck = "SELECT COUNT(*) FROM Conteudo WHERE titulo = :titulo AND siglaOlimpiadaPertencente = :siglaOlimpiadaPertencente";
        $stmtCheck = $pdo->prepare($sqlCheck);
        $stmtCheck->bindParam(':titulo', $titulo);
        $stmtCheck->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);
        $stmtCheck->execute();
        $exists = $stmtCheck->fetchColumn();

        if ($exists > 0) {
            echo json_encode(['error' => 'Já existe um conteúdo com este título para a olimpíada selecionada.']);
            exit;
        }

        // Insere o conteúdo no banco de dados
        $sqlInsert = "INSERT INTO Conteudo (titulo, subtitulo, siglaOlimpiadaPertencente) VALUES (:titulo, :subtitulo, :siglaOlimpiadaPertencente)";
        $stmtInsert = $pdo->prepare($sqlInsert);
        $stmtInsert->bindParam(':titulo', $titulo);
        $stmtInsert->bindParam(':subtitulo', $subtitulo);
        $stmtInsert->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);

        if ($stmtInsert->execute()) {
            // Busca o conteúdo inserido para retornar
            $sql = "SELECT id, titulo, subtitulo FROM Conteudo WHERE siglaOlimpiadaPertencente = :siglaOlimpiadaPertencente ORDER BY id DESC LIMIT 1";
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);
            $stmt->execute();

            $conteudo = $stmt->fetch(PDO::FETCH_ASSOC);

            if ($conteudo) {
                echo json_encode(['success' => true, 'conteudo' => $conteudo]);
            } else {
                echo json_encode(['error' => 'Erro ao buscar o conteúdo inserido.']);
            }
        } else {
            echo json_encode(['error' => 'Erro ao adicionar conteúdo.']);
        }
    } catch (PDOException $e) {
        echo json_encode(['error' => 'Erro no banco de dados: ' . $e->getMessage()]);
    }
} else {
    echo json_encode(['error' => 'Método não permitido.']);
}
?>
