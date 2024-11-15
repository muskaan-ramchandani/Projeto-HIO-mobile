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

$sql = "
    SELECT 
    PerguntasForum.id,
    Aluno.nomeUsuario,
    Aluno.fotoPerfil,
    PerguntasForum.titulo,
    PerguntasForum.pergunta,
    PerguntasForum.dataPublicacao,
    PerguntasForum.siglaOlimpiadaRelacionada
FROM 
    PerguntasForum
JOIN 
    Aluno ON PerguntasForum.emailAluno = Aluno.email;

";

$statement = $pdo->query($sql);

$listaPerguntas = [];
while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
    $listaPerguntas[] = (object) $result;
}

//contando respostas para a pergunta
$sqlTotalRespostas = "
    SELECT COUNT(*) AS totalRespostas
    FROM RespostasForum
    WHERE idPergunta = :idPergunta
";
$sqlTotalRespostas = $pdo->prepare($sqlTotalAcertos);
$sqlTotalRespostas->bindParam(':idPergunta', $emailAluno, PDO::PARAM_STR);
$sqlTotalRespostas->execute();
$totalRespostas = $sqlTotalRespostas->fetch(PDO::FETCH_ASSOC)['idPergunta'];


echo json_encode([
    'totalRespostas' => $totalRespostas,
    'listaPerguntas' => $listaPerguntas
]);

$pdo = null;
?>
