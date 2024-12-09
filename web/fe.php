<?php
// Configuração do banco de dados
$host = 'sql207.infinityfree.com'; // Alterar conforme necessário
$dbname = 'if0_37755624_hio'; // Alterar conforme necessário
$username = 'if0_37755624'; // Alterar conforme necessário
$password = '1k31AyGMaqyz7'; // Alterar conforme necessário

try {
    // Conexão com o banco de dados usando PDO
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Consulta para buscar todas as Olimpíadas
    $sql = "SELECT nome, sigla, icone, cor FROM Olimpiada";
    $stmt = $pdo->prepare($sql);
    $stmt->execute();

    // Obter os resultados
    $olimpiadas = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Retornar os dados em formato JSON
    header('Content-Type: application/json');
    echo json_encode($olimpiadas);

} catch (PDOException $e) {
    // Tratar erros de conexão ou execução
    http_response_code(500); // Código de erro de servidor
    echo json_encode(["error" => $e->getMessage()]);
}
