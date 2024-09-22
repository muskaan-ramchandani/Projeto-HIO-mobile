<?php 
header('Content-Type: application/json');
if (isset($_GET['sigla'])) {
    $sigla = $_GET['sigla'];
    $dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
    $username = 'root';
    $password = 'root';
    try {
       
        $pdo = new PDO($dsn, $username, $password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        
        $sql = "SELECT titulo, subtitulo FROM Conteudo WHERE siglaOlimpiadaPertencente = :sigla";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':sigla', $sigla);
        $stmt->execute();
       
        $conteudos = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if ($conteudos) {
            echo json_encode($conteudos);
        } else {
            echo json_encode(['error' => 'Nenhum conteúdo encontrado para esta Olimpíada.']);
        }
    } catch (PDOException $e) {
        echo json_encode(['error' => 'Erro: ' . $e->getMessage()]);
    }
} else {
    echo json_encode(['error' => 'Nenhuma Olimpíada foi selecionada.']);
}
?>
