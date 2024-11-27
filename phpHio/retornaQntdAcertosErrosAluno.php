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

if (empty($emailAluno)) {
    echo json_encode(["message" => "O email não foi enviado"]);
    exit;
}

//retorna qntd erros
$sqlTotalErros = "
    SELECT COUNT(*) AS totalErros
    FROM ErrosAluno
    WHERE emailAluno = :emailAluno
";
$statementTotalErros = $pdo->prepare($sqlTotalErros);
$statementTotalErros->bindParam(':emailAluno', $emailAluno, PDO::PARAM_STR);
$statementTotalErros->execute();
$totalErros = $statementTotalErros->fetch(PDO::FETCH_ASSOC)['totalErros'];

//retorna qntd acertos
$sqlTotalAcertos = "
    SELECT COUNT(*) AS totalAcertos
    FROM AcertosAluno
    WHERE emailAluno = :emailAluno
";
$statementTotalAcertos = $pdo->prepare($sqlTotalAcertos);
$statementTotalAcertos->bindParam(':emailAluno', $emailAluno, PDO::PARAM_STR);
$statementTotalAcertos->execute();
$totalAcertos = $statementTotalAcertos->fetch(PDO::FETCH_ASSOC)['totalAcertos'];

echo json_encode([
    'totalAcertos' => $totalAcertos,
    'totalErros' => $totalErros
]);

$pdo = null;
?>
