<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrossel de Olimpíadas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }

        .carrossel {
            display: flex;
            overflow-x: auto;
            scroll-behavior: smooth;
            padding: 20px;
            gap: 10px;
        }

        .olimpiada {
            min-width: 200px;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px;
            display: flex;
            flex-direction: column;
            align-items: center;
            background-color: #fff;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .olimpiada img {
            border-radius: 5px;
            width: 100px;
            height: 100px;
            object-fit: cover;
            margin-bottom: 10px;
        }

        .info h2 {
            font-size: 18px;
            margin: 10px 0;
        }

        .cor {
            font-weight: bold;
            margin-top: 5px;
        }

        .carrossel-controls {
            text-align: center;
            margin-top: 20px;
        }

        .carrossel-controls button {
            padding: 10px 20px;
            margin: 0 10px;
            background-color: #5271FF;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .carrossel-controls button:hover {
            background-color: #4159C8;
        }
    </style>
</head>
<body>

    <h1>Carrossel de Olimpíadas</h1>

    <!-- Carrossel de Olimpíadas -->
    <div class="carrossel" id="carrossel">
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
                    echo '<img src="http://192.168.0.81:8080/webCodesHIO/Imagens_Mobile_HIO/' . htmlspecialchars($row["icone"]) . '.png" alt="' . htmlspecialchars($row["nome"]) . '">';
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
    </div>

    <!-- Controles do carrossel -->
    <div class="carrossel-controls">
    <button onclick="scrollCarrossel(-200)">
        <img src="http://192.168.0.81:8080/webCodesHIO/Imagens_Mobile_HIO/btnVoltarEAvancarExercicioEsquerda.png" alt="Esquerda" style="width: 30px; height: 30px;">
    </button>
    <button onclick="scrollCarrossel(200)">
        <img src="http://192.168.0.81:8080/webCodesHIO/Imagens_Mobile_HIO/btnVoltarEAvancarExercicioDireita.png" alt="Direita" style="width: 30px; height: 30px;">
    </button>
</div>


    <script>
        const carrossel = document.getElementById('carrossel');

        function scrollCarrossel(distance) {
            carrossel.scrollBy({
                left: distance,
                behavior: 'smooth'
            });
        }
    </script>

</body>
</html>
