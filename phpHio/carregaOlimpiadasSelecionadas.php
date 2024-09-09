<?php
    header('Content-Type: application/json');
    header('Character-Encoding: utf-8');

    try {
        $pdo = new PDO('mysql:host=localhost;dbname=hio;port=3306;charset=utf8', 'root', 'root');
    } catch (PDOException $e) {
        echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
        exit;
    }

    $emailAluno = $_GET['emailAluno'] ?? '';

    if (empty($emailAluno)) {
        echo json_encode(["message" => "Email não fornecido"]);
        exit;
    }

    $sql = "
        SELECT o.nome, o.sigla, o.icone, o.cor 
        FROM Olimpiada o
        INNER JOIN OlimpiadasSelecionadas os ON o.sigla = os.sigla
        WHERE os.emailAluno = :emailAluno
    ";

    $statement = $pdo->prepare($sql);
    $statement->bindParam(':emailAluno', $emailAluno);
    $statement->execute();

    $olimpiadas = [];
    while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
        $olimpiadas[] = (object) $result;
    }

    echo json_encode(['olimpiadas' => $olimpiadas]);

    $pdo = null;
?>
