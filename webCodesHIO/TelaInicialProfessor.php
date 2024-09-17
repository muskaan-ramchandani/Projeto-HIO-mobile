<?php
$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$username = 'root';
$password = 'root';

try {
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $sql = "SELECT * FROM Olimpiada";
    $stmt = $pdo->prepare($sql);
    $stmt->execute();

    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

    foreach ($result as $row) {
        $cor = htmlspecialchars($row["cor"]);
        $corHex = '#ffffff'; // Cor padrão (branco) caso a cor não seja reconhecida
        switch (strtolower($cor)) {
            case 'rosa':
                $corHex = '#CB6CE6';
                break;
            case 'ciano':
                $corHex = '#18B9CD';
                break;
            case 'azul':
                $corHex = '#5271FF';
                break;
            case 'laranja':
                $corHex = '#FF914D';
                break;
        }

        echo '<div class="carousel-item" style="background-color: ' . $corHex . ';">';
        echo '<a href="#" class="olympics-button" style="background-color: ' . $corHex . ';">';
        echo '<img src="http://192.168.0.81:8080/webCodesHIO/Imagens_Mobile_HIO/' . htmlspecialchars($row["icone"]) . '.png" alt="' . htmlspecialchars($row["nome"]) . '" class="button-icon">';
        echo htmlspecialchars($row["nome"]);
        echo '</a>';
        echo '</div>';
    }

} catch (PDOException $e) {
    echo "<p>Erro: " . $e->getMessage() . "</p>";
}

$pdo = null;
?>
