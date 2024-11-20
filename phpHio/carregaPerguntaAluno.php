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
    Aluno ON PerguntasForum.emailAluno = Aluno.email

    ORDER BY PerguntasForum.dataPublicacao DESC;
";

$statement = $pdo->query($sql);

$listaPerguntas = [];
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

    $listaPerguntas[] = (object) $result;
}

//contando grupos de pergunta por olimpiada
$sqlContagemPerguntas = "
    SELECT 
        siglaOlimpiadaRelacionada, 
        COUNT(*) AS totalPerguntasPorOlimpiada
    FROM 
        PerguntasForum
    GROUP BY 
        siglaOlimpiadaRelacionada;
";

$statementContagem = $pdo->query($sqlContagemPerguntas);

$contagemPerguntasPorOlimpiada = [];
while ($contagem = $statementContagem->fetch(PDO::FETCH_ASSOC)) {
    $contagemPerguntasPorOlimpiada[$contagem['siglaOlimpiadaRelacionada']] = $contagem['totalPerguntasPorOlimpiada'];
}

echo json_encode([
    'listaPerguntas' => $listaPerguntas,
    'contagemPerguntasPorOlimpiada' => $contagemPerguntasPorOlimpiada
]);

$pdo = null;
?>
