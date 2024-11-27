<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');

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

$idConteudoPertencente = $_GET['idConteudoPertencente'] ?? '';

if (empty($idConteudoPertencente)) {
    echo json_encode(["message" => "Não foi possível detectar o conteúdo selecionado"]);
    exit;
}

/*CREATE TABLE Texto(
	id INT AUTO_INCREMENT NOT NULL,
    titulo VARCHAR(300) NOT NULL,
    texto TEXT NOT NULL,
    profQuePostou VARCHAR(100) NOT NULL,
    idConteudoPertencente INT NOT NULL,
    FOREIGN KEY(idConteudoPertencente) REFERENCES Conteudo(id), 
	FOREIGN KEY(profQuePostou) REFERENCES Professor(email),
    PRIMARY KEY(id)
);*/

$sql = "
SELECT t.id, t.titulo, t.texto, p.nomeUsuario AS profQuePostou
FROM Texto t
JOIN Professor p ON t.profQuePostou = p.email
WHERE t.idConteudoPertencente = :idConteudoPertencente";


$statement = $pdo->prepare($sql);
$statement->bindParam(':idConteudoPertencente', $idConteudoPertencente);
$statement->execute();

$textos = [];
    while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
        $textos[] = (object) $result;
    }

echo json_encode(['textos' => $textos]);

$pdo = null;
?>