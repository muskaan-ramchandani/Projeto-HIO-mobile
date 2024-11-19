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

if (empty($emailAluno)) {
    echo json_encode(["message" => "Não foram enviados todos os parâmetros"]);
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
WHERE
    PerguntasForum.emailAluno = :emailAluno
JOIN 
    Aluno ON PerguntasForum.emailAluno = Aluno.email
    
    ORDER BY PerguntasForum.dataPublicacao DESC;
";

$statement = $pdo->prepare($sql);
$statement->bindParam(':emailAluno', $emailAluno, PDO::PARAM_STR);
$statement->execute();


$listaPerguntasRespondidas = [];
$listaPerguntasSemResposta = [];

while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
    $idPergunta = $result['id'];
    
    // contando respostas
    $sqlTotalRespostas = "
        SELECT COUNT(*) AS totalRespostas
        FROM RespostasForum
        WHERE idPergunta = :idPergunta
    ";
    $sqlTotalRespostas = $pdo->prepare($sqlTotalRespostas);
    $sqlTotalRespostas->bindParam(':idPergunta', $idPergunta, PDO::PARAM_INT);
    $sqlTotalRespostas->execute();
    
    $totalRespostas = $sqlTotalRespostas->fetch(PDO::FETCH_ASSOC)['totalRespostas'];
    $result['totalRespostas'] = $totalRespostas;

    if (isset($result['fotoPerfil'])) {
        $result['fotoPerfil'] = base64_encode($result['fotoPerfil']);
    }

    //para retornar olimpiadas com resposta
    if ($totalRespostas > 0) {
        $result['totalRespostas'] = $totalRespostas;

        $listaPerguntasRespondidas[] = (object) $result;
    }else{
        //perguntas sem respostas
        $result['totalRespostas'] = $totalRespostas;

        $listaPerguntasSemResposta[] = (object) $result;
    }
}

echo json_encode([
    'listaPerguntasRespondidas' => $listaPerguntasRespondidas,
    'listaPerguntasSemResposta' => $listaPerguntasSemResposta
]);

$pdo = null;
?>
