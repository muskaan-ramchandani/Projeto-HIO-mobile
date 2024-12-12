<?php
header('Content-Type: application/json; charset=utf-8');
header('Character-Encoding: utf-8'); 

$servername = "localhost"; 
$username = "u740411060_user";        
$password = "OWYzZ?o2";            
$dbname = "u740411060_hio";         

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
    exit;
}

$sql= "SELECT 
    A.fotoPerfil, COALESCE(P.pontuacao, 0) AS pontuacao, A.nomeUsuario, A.email
    FROM Aluno A
    LEFT JOIN PontuacaoAlunos P ON A.email = P.emailAluno
    ORDER BY P.pontuacao DESC;";

$statement = $pdo->prepare($sql);
$statement->execute();

$posicoesRanking = [];
while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
    if (isset($result['fotoPerfil']) && !empty($result['fotoPerfil'])) {
        $result['fotoPerfil'] = base64_encode($result['fotoPerfil']);
    } else {
        // Atribuir a imagem padrão
        $defaultImagePath = "C:\\HIOMobilePCCT\\Imagens Mobile HIO\\iconePerfilVazioRedonda.png";
        $result['fotoPerfil'] = base64_encode(file_get_contents($defaultImagePath));
    }
    $posicoesRanking[] = (object) $result;
}

echo json_encode(['posicoesRanking' => $posicoesRanking]);

$pdo = null;

?>