<?php
try {
    // Conexão com o banco de dados usando PDO
    $pdo = new PDO("mysql:host=localhost;dbname=hio", "root", "root"); // Ajuste as credenciais de acordo com seu ambiente
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Query para buscar as olimpíadas
    $sql = "SELECT nome, sigla FROM Olimpiada";
    $stmt = $pdo->query($sql);

    // Exibe cada olimpíada como uma opção no select
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        echo "<option value='" . htmlspecialchars($row['sigla']) . "'>" . htmlspecialchars($row['nome']) . "</option>";
    }

} catch (PDOException $e) {
    // Exibe a mensagem de erro se ocorrer um problema na conexão ou na consulta
    echo "Erro ao buscar olimpíadas: " . $e->getMessage();
}
?>
