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
function contarErros($pdo, $emailAluno, $dataInicial, $dataFinal) {
    $sql = "
        SELECT COUNT(*) AS totalErros
        FROM ErrosAluno e
        WHERE e.emailAluno = :emailAluno 
        AND e.dataErro BETWEEN :dataInicial AND :dataFinal
    ";
    $statement = $pdo->prepare($sql);
    $statement->bindParam(':emailAluno', $emailAluno, PDO::PARAM_STR);
    $statement->bindParam(':dataInicial', $dataInicial, PDO::PARAM_STR);
    $statement->bindParam(':dataFinal', $dataFinal, PDO::PARAM_STR);
    $statement->execute();
    return $statement->fetch(PDO::FETCH_ASSOC)['totalErros'];
}

$totalErrosSemana1 = contarErros($pdo, $emailAluno, $dataInicialSemana1, $dataFinalSemana1);
$totalErrosSemana2 = contarErros($pdo, $emailAluno, $dataInicialSemana2, $dataFinalSemana2);
$totalErrosSemana3 = contarErros($pdo, $emailAluno, $dataInicialSemana3, $dataFinalSemana3);

$sqlErros = "
    SELECT 
        o.sigla AS siglaOlimpiada,
        c.titulo AS tituloConteudo,
        q.titulo AS tituloQuestionario,
        p.nomeUsuario AS usuarioProfessor,
        quest.txtPergunta,
        altMarcada.textoAlternativa AS alternativaMarcada,
        altCorreta.textoAlternativa AS alternativaCorreta
    FROM 
        ErrosAluno e
    JOIN 
        Questao quest ON e.idQuestaoPertencente = quest.id
    JOIN 
        AlternativasQuestao altMarcada ON e.idAlternativaMarcada = altMarcada.id
    JOIN 
        AlternativasQuestao altCorreta ON e.idAlternativaCorreta = altCorreta.id
    JOIN 
        Questionario q ON quest.idQuestionarioPertencente = q.id
    JOIN 
        Conteudo c ON q.idConteudoPertencente = c.id
    JOIN 
        Professor p ON q.profQuePostou = p.email
    JOIN 
        Olimpiada o ON c.siglaOlimpiadaPertencente = o.sigla
    WHERE 
        e.emailAluno = :emailAluno
";

$statementErros = $pdo->prepare($sqlErros);
$statementErros->bindParam(':emailAluno', $emailAluno, PDO::PARAM_STR);
$statementErros->execute();

$listaErros = [];
while ($result = $statementErros->fetch(PDO::FETCH_ASSOC)) {
    $listaErros[] = (object) $result;
}

echo json_encode([
    'totalErrosSemana1' => $totalErrosSemana1,
    'totalErrosSemana2' => $totalErrosSemana2,
    'totalErrosSemana3' => $totalErrosSemana3,
    'listaErros' => $listaErros
]);

$pdo = null;
?>
