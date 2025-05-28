<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');

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

$siglaOlimpiadaPertencente = $_GET['siglaOlimpiadaPertencente'] ?? '';

if (empty($siglaOlimpiadaPertencente)) {
    echo json_encode(["message" => "Não foi possível detectar a olimpíada selecionada"]);
    exit;
}

/*id INT AUTO_INCREMENT NOT NULL,
    anoDaProva YEAR NOT NULL,
    estado BOOLEAN NOT NULL,
    fase INT NOT NULL,
    profQuePostou VARCHAR(100) NOT NULL,
    arquivoPdf LONGBLOB NOT NULL,
	siglaOlimpiadaPertencente VARCHAR(10) NOT NULL,*/

 $sql = "
SELECT pa.id, pa.anoDaProva, pa.estado, pa.fase, p.nomeUsuario AS profQuePostou, pa.arquivoPdf
FROM ProvaAnterior pa
JOIN Professor p ON pa.profQuePostou = p.email
WHERE pa.siglaOlimpiadaPertencente = :siglaOlimpiadaPertencente";

$statement = $pdo->prepare($sql);
$statement->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);
$statement->execute();

$provas = [];
while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
    if (isset($result['arquivoPdf'])) {
        $result['arquivoPdf'] = base64_encode($result['arquivoPdf']);
    }
    $provas[] = (object) $result;
}

echo json_encode(['provas' => $provas]);

$pdo = null;
?>
