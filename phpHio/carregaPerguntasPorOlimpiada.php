<?php 
header('Content-Type: application/json; charset=utf-8');

$servername = "localhost"; 
$username = "u792730258_noemi";        
$password = "Masenfimacontece123";            
$dbname = "u792730258_hio";    

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
    exit;
}

// Receber os parâmetros
$siglaOlimpiada = $_GET['siglaOlimpiada'] ?? '';

if (empty($siglaOlimpiada)) {
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
JOIN 
    Aluno ON PerguntasForum.emailAluno = Aluno.email
WHERE
    PerguntasForum.siglaOlimpiadaRelacionada = :siglaOlimpiada
    
    ORDER BY PerguntasForum.dataPublicacao DESC;
";

$statement = $pdo->prepare($sql);
$statement->bindParam(':siglaOlimpiada', $siglaOlimpiada, PDO::PARAM_STR);
$statement->execute();


$listaPerguntasOlimpiada = [];

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

    $result['totalRespostas'] = $totalRespostas;

    $listaPerguntasOlimpiada[] = (object) $result;
}

echo json_encode([
    'listaPerguntasOlimpiada' => $listaPerguntasOlimpiada
]);

$pdo = null;
?>
