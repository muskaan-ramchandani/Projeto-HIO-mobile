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

$emailAluno = $_POST['emailAluno'] ?? '';
$tipoMaterial = $_POST['tipoMaterial'] ?? '';
$idMaterial = $_POST['idMaterial'] ?? '';

if (empty($emailAluno) || empty($tipoMaterial) || empty($idMaterial)) {
    echo json_encode(["message" => "Parâmetros inválidos"]);
    exit;
}

try {
    $pdo->beginTransaction();

    // Contar quantos registros já existem para o aluno e tipo de material
    $sqlCount = "
        SELECT COUNT(*) AS total 
        FROM HistoricoAcessoAluno 
        WHERE emailAluno = :emailAluno AND tipoMaterial = :tipoMaterial";
    $stmtCount = $pdo->prepare($sqlCount);
    $stmtCount->bindParam(':emailAluno', $emailAluno);
    $stmtCount->bindParam(':tipoMaterial', $tipoMaterial);
    $stmtCount->execute();
    $resultCount = $stmtCount->fetch(PDO::FETCH_ASSOC);
    $total = $resultCount['total'] ?? 0;

    // Se houver mais de 9 registros, exclui o mais antigo
    if ($total >= 10) {
        $sqlDelete = "
            DELETE FROM HistoricoAcessoAluno 
            WHERE id = (
                SELECT id FROM (
                    SELECT id FROM HistoricoAcessoAluno
                    WHERE emailAluno = :emailAluno AND tipoMaterial = :tipoMaterial
                    ORDER BY id ASC LIMIT 1
                ) AS subquery
            )";
        $stmtDelete = $pdo->prepare($sqlDelete);
        $stmtDelete->bindParam(':emailAluno', $emailAluno);
        $stmtDelete->bindParam(':tipoMaterial', $tipoMaterial);
        $stmtDelete->execute();
    }

    $sqlInsert = "
        INSERT INTO HistoricoAcessoAluno (emailAluno, tipoMaterial, idMaterial)
        VALUES (:emailAluno, :tipoMaterial, :idMaterial)";
    $stmtInsert = $pdo->prepare($sqlInsert);
    $stmtInsert->bindParam(':emailAluno', $emailAluno);
    $stmtInsert->bindParam(':tipoMaterial', $tipoMaterial);
    $stmtInsert->bindParam(':idMaterial', $idMaterial);
    $stmtInsert->execute();

    $pdo->commit();

    echo json_encode(["message" => "Registro cadastrado com sucesso"]);
} catch (Exception $e) {
    $pdo->rollBack();
    echo json_encode(["message" => "Erro ao cadastrar o registro: " . $e->getMessage()]);
}

$pdo = null;
?>
