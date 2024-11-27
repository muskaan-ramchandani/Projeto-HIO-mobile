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
$idPergunta = $_GET['idPergunta'] ?? '';

if (empty($idPergunta)) {
    echo json_encode(["message" => "Não foram enviados todos os parâmetros"]);
    exit;
}

/*CREATE TABLE RespostasForum(
	id INT AUTO_INCREMENT NOT NULL,
	emailProf VARCHAR(100) NOT NULL,
    resposta TEXT NOT NULL,
    dataResposta DATE NOT NULL,
    idPergunta INT NOT NUll,
	FOREIGN KEY(idPergunta) REFERENCES PerguntasForum(id),
	FOREIGN KEY(emailProf) REFERENCES Professor(email),
	PRIMARY KEY(id)
);*/

$sql = "
    SELECT 
    RespostasForum.id,
    Professor.nomeUsuario,
    Professor.fotoPerfil,
    RespostasForum.resposta
FROM 
    RespostasForum
JOIN 
    Professor ON RespostasForum.emailProf = Professor.email
WHERE
    RespostasForum.idPergunta = :idPergunta
ORDER BY RespostasForum.dataResposta DESC;
";

$statement = $pdo->prepare($sql);
$statement->bindParam(':idPergunta', $idPergunta, PDO::PARAM_INT);
$statement->execute();


$listaRespostas = [];

while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {

    if (isset($result['fotoPerfil'])) {
        $result['fotoPerfil'] = base64_encode($result['fotoPerfil']);
    }

    $listaRespostas[] = (object) $result;
}

echo json_encode([
    'listaRespostas' => $listaRespostas
]);

$pdo = null;
?>
