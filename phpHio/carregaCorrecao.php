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
$dataErro = $_GET['dataErro'] ?? '';
$idQuestionarioPertencente = $_GET['idQuestionarioPertencente'] ?? '';

if (empty($emailAluno) || empty($dataErro) || empty($idQuestionarioPertencente)) {
    echo json_encode(["message" => "Não foram enviados todos os parâmetros"]);
    exit;
}

$sql = "
    SELECT 
        q.txtPergunta, 
        a.textoAlternativa AS alternativaCorreta, 
        q.explicacaoResposta
    FROM 
        ErrosAluno e
    JOIN 
        Questao q ON e.idQuestaoPertencente = q.id
    JOIN 
        AlternativasQuestao a ON e.idAlternativaCorreta = a.id
    WHERE 
        e.emailAluno = :emailAluno 
        AND e.dataErro = :dataErro
        AND e.idQuestionarioPertencente = :idQuestionarioPertencente
        AND a.corretaOuErrada = 1
";


$statement = $pdo->prepare($sql);
$statement->bindParam(':emailAluno', $emailAluno, PDO::PARAM_STR);
$statement->bindParam(':dataErro', $dataErro, PDO::PARAM_STR);
$statement->bindParam(':idQuestionarioPertencente', $idQuestionarioPertencente, PDO::PARAM_INT);
$statement->execute();

$questoesComErros = [];
while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
    $questoesComErros[] = (object) $result;
}

$sqlTotalQuestoes = "
    SELECT COUNT(*) AS totalQuestoes
    FROM Questao
    WHERE idQuestionarioPertencente = :idQuestionarioPertencente
";
$statementTotalQuestoes = $pdo->prepare($sqlTotalQuestoes);
$statementTotalQuestoes->bindParam(':idQuestionarioPertencente', $idQuestionarioPertencente, PDO::PARAM_INT);
$statementTotalQuestoes->execute();
$totalQuestoes = $statementTotalQuestoes->fetch(PDO::FETCH_ASSOC)['totalQuestoes'];

$sqlTotalErros = "
    SELECT COUNT(*) AS totalErros
    FROM ErrosAluno
    WHERE emailAluno = :emailAluno 
    AND dataErro = :dataErro
    AND idQuestionarioPertencente = :idQuestionarioPertencente
";
$statementTotalErros = $pdo->prepare($sqlTotalErros);
$statementTotalErros->bindParam(':emailAluno', $emailAluno, PDO::PARAM_STR);
$statementTotalErros->bindParam(':dataErro', $dataErro, PDO::PARAM_STR);
$statementTotalErros->bindParam(':idQuestionarioPertencente', $idQuestionarioPertencente, PDO::PARAM_INT);
$statementTotalErros->execute();
$totalErros = $statementTotalErros->fetch(PDO::FETCH_ASSOC)['totalErros'];

if (empty($questoesComErros)) {
    echo json_encode(["message" => "Nenhuma questão encontrada para os parâmetros fornecidos."]);
} else {
    echo json_encode([
        'questoesComErros' => $questoesComErros,
        'totalQuestoes' => $totalQuestoes,
        'totalErros' => $totalErros
    ]);
}

$pdo = null;
?>
