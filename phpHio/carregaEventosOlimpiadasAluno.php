<?php
header('Content-Type: application/json; charset=utf-8');

$servername = "sql207.infinityfree.com";
$username = "if0_37755624";
$password = "1k31AyGMaqyz7";
$dbname = "if0_37755624_hio";     

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
    exit;
}

$emailAluno = $_GET['emailAluno'] ?? '';
$dataAtual = $_GET['dataAtual'] ?? '';

if (empty($emailAluno) || empty($dataAtual)) {
    echo json_encode(["message" => "Não foram enviados todos os parâmetros"]);
    exit;
}

// Deleta eventos anteriores à data atual
$sqlDelete = "
    DELETE FROM 
        Eventos 
    WHERE 
        dataOcorrencia < :dataAtual
";
$statementDelete = $pdo->prepare($sqlDelete);
$statementDelete->bindParam(':dataAtual', $dataAtual, PDO::PARAM_STR);
$statementDelete->execute();

// Seleciona eventos que começam na data atual ou depois
$sql = "
    SELECT 
        Eventos.id,
        Eventos.tipo,
        Eventos.dataOcorrencia,
        Eventos.horarioComeco,
        Eventos.horarioFim,
        Eventos.link,
        Eventos.siglaOlimpiadaPertencente,
        Olimpiada.cor
    FROM 
        Eventos
    INNER JOIN 
        OlimpiadasSelecionadas ON Eventos.siglaOlimpiadaPertencente = OlimpiadasSelecionadas.sigla
    INNER JOIN 
        Olimpiada ON Eventos.siglaOlimpiadaPertencente = Olimpiada.sigla
    WHERE 
        OlimpiadasSelecionadas.emailAluno = :emailAluno
        AND Eventos.dataOcorrencia >= :dataAtual
    ORDER BY 
        Eventos.dataOcorrencia ASC
";

$statement = $pdo->prepare($sql);
$statement->bindParam(':emailAluno', $emailAluno, PDO::PARAM_STR);
$statement->bindParam(':dataAtual', $dataAtual, PDO::PARAM_STR);
$statement->execute();

$eventos = $statement->fetchAll(PDO::FETCH_ASSOC);

echo json_encode([
    'eventos' => $eventos
]);

$pdo = null;
?>
