<?php 
header('Content-Type: application/json; charset=utf-8');

$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";     

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
    exit;
}

// Receber os parâmetros
$emailAluno = $_GET['emailAluno'] ?? '';
$dataInicialSemana1 = $_GET['dataInicialSemana1'] ?? ''; 
$dataFinalSemana1 = $_GET['dataFinalSemana1'] ?? '';
$dataInicialSemana2 = $_GET['dataInicialSemana2'] ?? '';
$dataFinalSemana2 = $_GET['dataFinalSemana2'] ?? '';
$dataInicialSemana3 = $_GET['dataInicialSemana3'] ?? ''; 
$dataFinalSemana3 = $_GET['dataFinalSemana3'] ?? '';

if (empty($emailAluno) || empty($dataInicialSemana1) || empty($dataFinalSemana1) || empty($dataInicialSemana2) || empty($dataFinalSemana2) || empty($dataInicialSemana3) || empty($dataFinalSemana3)) {
    echo json_encode(["message" => "Não foram enviados todos os parâmetros"]);
    exit;
}

// Contar acertos para cada semana
function contarAcertos($pdo, $emailAluno, $dataInicial, $dataFinal) {
    $sql = "
        SELECT COUNT(*) AS totalAcertos
        FROM AcertosAluno a
        WHERE a.emailAluno = :emailAluno 
        AND a.dataAcerto BETWEEN :dataInicial AND :dataFinal
    ";
    $statement = $pdo->prepare($sql);
    $statement->bindParam(':emailAluno', $emailAluno, PDO::PARAM_STR);
    $statement->bindParam(':dataInicial', $dataInicial, PDO::PARAM_STR);
    $statement->bindParam(':dataFinal', $dataFinal, PDO::PARAM_STR);
    $statement->execute();
    return $statement->fetch(PDO::FETCH_ASSOC)['totalAcertos'];
}

$totalAcertosSemana1 = contarAcertos($pdo, $emailAluno, $dataInicialSemana1, $dataFinalSemana1);
$totalAcertosSemana2 = contarAcertos($pdo, $emailAluno, $dataInicialSemana2, $dataFinalSemana2);
$totalAcertosSemana3 = contarAcertos($pdo, $emailAluno, $dataInicialSemana3, $dataFinalSemana3);

$sqlAcertos = "
    SELECT 
        o.sigla AS siglaOlimpiada,
        c.titulo AS tituloConteudo,
        q.titulo AS tituloQuestionario,
        p.nomeUsuario AS usuarioProfessor,
        quest.txtPergunta,
        alt.textoAlternativa AS alternativaMarcada
    FROM 
        AcertosAluno a
    JOIN 
        Questao quest ON a.idQuestaoPertencente = quest.id
    JOIN 
        AlternativasQuestao alt ON a.idAlternativaMarcada = alt.id
    JOIN 
        Questionario q ON quest.idQuestionarioPertencente = q.id
    JOIN 
        Conteudo c ON q.idConteudoPertencente = c.id
    JOIN 
        Professor p ON q.profQuePostou = p.email
    JOIN 
        Olimpiada o ON c.siglaOlimpiadaPertencente = o.sigla
    WHERE 
        a.emailAluno = :emailAluno
";

$statementAcertos = $pdo->prepare($sqlAcertos);
$statementAcertos->bindParam(':emailAluno', $emailAluno, PDO::PARAM_STR);
$statementAcertos->execute();

$listaAcertos = [];
while ($result = $statementAcertos->fetch(PDO::FETCH_ASSOC)) {
    $listaAcertos[] = (object) $result;
}

echo json_encode([
    'totalAcertosSemana1' => $totalAcertosSemana1,
    'totalAcertosSemana2' => $totalAcertosSemana2,
    'totalAcertosSemana3' => $totalAcertosSemana3,
    'listaAcertos' => $listaAcertos
]);

$pdo = null;
?>
