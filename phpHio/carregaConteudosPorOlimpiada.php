<?php
    header('Content-Type: application/json');
    header('Character-Encoding: utf-8');
    $servername = "localhost"; 
    $username = "u792730258_noemi";        
    $password = "Masenfimacontece123";            
    $dbname = "u792730258_hio";      

    try {
        $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    } catch (PDOException $e) {
        echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
        exit;
    }

        /*CREATE TABLE Conteudo(
	id INT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    subtitulo VARCHAR(200) NOT NULL,
    siglaOlimpiadaPertencente VARCHAR(10) NOT NULL,
	FOREIGN KEY(siglaOlimpiadaPertencente) REFERENCES Olimpiada(sigla),
    PRIMARY KEY(id)
); */

    $siglaOlimpiadaPertencente = $_GET['siglaOlimpiadaPertencente'] ?? '';

    if (empty($siglaOlimpiadaPertencente)) {
        echo json_encode(["message" => "Não foi possível detectar a olimpíada selecionada"]);
        exit;
    }

    $sql = "
    SELECT c.id, c.titulo, c.subtitulo 
    FROM Conteudo c
    WHERE c.siglaOlimpiadaPertencente = :siglaOlimpiadaPertencente";

    $statement = $pdo->prepare($sql);
    $statement->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);
    $statement->execute();

    $conteudos = [];
    while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
        $conteudos[] = (object) $result;
    }

    echo json_encode(['conteudos' => $conteudos]);

    $pdo = null;
?>
