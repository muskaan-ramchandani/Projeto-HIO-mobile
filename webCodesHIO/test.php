<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Olimpíadas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .olimpiada {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            color: #fff; /* Texto em branco para contraste com as cores de fundo */
        }
        .olimpiada img {
            border-radius: 5px;
            margin-right: 20px;
        }
        .olimpiada .info {
            flex: 1;
        }
        .cor {
            font-weight: bold;
            margin-top: 5px;
        }
    </style>
</head>
<body>

<?php
$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$username = 'root';
$password = 'root';

try {
    // Criar uma nova conexão PDO
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Preparar e executar a consulta
    $sql = "SELECT * FROM Olimpiada";
    $stmt = $pdo->prepare($sql);
    $stmt->execute();

    // Buscar todos os resultados
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

    if (count($result) > 0) {
        // Saída dos dados de cada linha
        foreach ($result as $row) {
            // Definir a cor de fundo com base na cor do banco de dados
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

            // Exibir dados e imagem
            echo '<div class="olimpiada" style="background-color: ' . $corHex . ';">';
            echo '<img src="http://192.168.0.81:8080/webCodesHIO/Imagens_Mobile_HIO/' . htmlspecialchars($row["icone"]) . '.png" alt="' . htmlspecialchars($row["nome"]) . '" style="width:100px;height:100px;">';
            echo '<div class="info">';
            echo '<h2>' . htmlspecialchars($row["nome"]) . '</h2>';
            echo '<p>Sigla: ' . htmlspecialchars($row["sigla"]) . '</p>';
            echo '<p class="cor">Cor: ' . htmlspecialchars($row["cor"]) . '</p>';
            echo '</div>';
            echo '</div>';
        }
    } else {
        echo "<p>0 resultados</p>";
    }
} catch (PDOException $e) {
    echo "<p>Erro: " . $e->getMessage() . "</p>";
}

// Fechar a conexão
$pdo = null;
?>

</body>
</html>
