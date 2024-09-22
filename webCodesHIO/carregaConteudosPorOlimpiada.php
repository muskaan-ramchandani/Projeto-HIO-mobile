<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');

$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "hio";

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados: " . $e->getMessage()]);
    exit;
}

// Verificar se a sigla foi passada via GET
$siglaOlimpiadaPertencente = $_GET['sigla'] ?? '';

if (empty($siglaOlimpiadaPertencente)) {
    echo json_encode(["message" => "Nenhuma sigla de olimpíada foi fornecida."]);
    exit;
}

// Log para verificar se a sigla está sendo capturada corretamente
// Você pode remover isso depois de verificar
error_log("Sigla recebida: " . $siglaOlimpiadaPertencente);

$sql = "
    SELECT c.id, c.titulo, c.subtitulo 
    FROM Conteudo c
    WHERE c.siglaOlimpiadaPertencente = :sigla
";

try {
    $statement = $pdo->prepare($sql);
    $statement->bindParam(':sigla', $siglaOlimpiadaPertencente, PDO::PARAM_STR);
    $statement->execute();

    $conteudos = [];
    while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
        $conteudos[] = (object) $result;
    }

    // Verifica se há conteúdos e retorna
    if (empty($conteudos)) {
        echo json_encode(["message" => "Nenhum conteúdo encontrado para a olimpíada fornecida."]);
    } else {
        echo json_encode($conteudos);
    }
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro ao buscar conteúdos: " . $e->getMessage()]);
}

$pdo = null;
?>
