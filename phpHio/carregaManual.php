<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type, Authorization, X-Requested-With');

$servername = "localhost"; 
$username = "u792730258_noemi";        
$password = "Masenfimacontece123";            
$dbname = "u792730258_hio";        

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados", "error" => $e->getMessage()]);
    exit;
}

$sql = "SELECT arquivoPdf FROM Manual WHERE id = 1";

try {
    $statement = $pdo->prepare($sql);
    $statement->execute();

    $result = $statement->fetch(PDO::FETCH_ASSOC);

    if ($result && isset($result['arquivoPdf'])) {
        $temp_pdf = tempnam(sys_get_temp_dir(), 'pdf');
        file_put_contents($temp_pdf, $result['arquivoPdf']);

        header('Content-Type: application/pdf');
        header('Content-Disposition: attachment; filename="manual.pdf"');
        header('Content-Length: ' . filesize($temp_pdf));

        readfile($temp_pdf);

        unlink($temp_pdf);
    } else {
        echo json_encode(['message' => 'Nenhum arquivo PDF encontrado para o ID informado']);
    }
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro ao executar a consulta", "error" => $e->getMessage()]);
}

$pdo = null;
?>
