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

        $sql = "SELECT * FROM Olimpiada WHERE sigla = :sigla";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':sigla', $sigla);
        $stmt->execute();

        $olimpiada = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($olimpiada) {
            echo json_encode([
                'nome' => $olimpiada['nome'],
                'icone' => $olimpiada['icone'],
                'cor' => $olimpiada['cor']
            ]);
        } else {
            echo json_encode(['error' => 'Olimpíada não encontrada.']);
        }
    } catch (PDOException $e) {
        echo json_encode(['error' => 'Erro: ' . $e->getMessage()]);
    }
} else {
    echo json_encode(['error' => 'Nenhuma olimpíada foi selecionada.']);
}
?>
