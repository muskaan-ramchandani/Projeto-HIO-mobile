<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Flashcard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
        }
        h1 {
            text-align: center;
            font-size: 24px;
        }
        label {
            font-weight: bold;
            margin-top: 10px;
            display: block;
        }
        input[type="text"],
        input[type="file"],
        textarea,
        input[type="email"],
        input[type="number"] {
            width: 100%;
            padding: 8px;
            margin: 8px 0;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Cadastro de Flashcard</h1>
        <form action="" method="POST" enctype="multipart/form-data">
            <label for="titulo">Título:</label>
            <input type="text" name="titulo" id="titulo" maxlength="300" required>

            <label for="imagem">Imagem:</label>
            <input type="file" name="imagem" id="imagem" accept="image/*" required>

            <label for="texto">Texto do Flashcard:</label>
            <textarea name="texto" id="texto" rows="4" required></textarea>

            <label for="profQuePostou">Email do Professor:</label>
            <input type="email" name="profQuePostou" id="profQuePostou" required>

            <label for="idConteudoPertencente">ID do Conteúdo Pertencente:</label>
            <input type="number" name="idConteudoPertencente" id="idConteudoPertencente" required>

            <button type="submit" name="submit">Cadastrar Flashcard</button>
        </form>

        <?php
        if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submit'])) {
            // Configurações do banco de dados
            $servername = "localhost"; 
            $username = "root";        
            $password = "root";            
            $dbname = "hio";     

            try {
                $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
                $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            } catch (PDOException $e) {
                echo "<p>Erro na conexão com o banco de dados: " . $e->getMessage() . "</p>";
                exit;
            }

            // Recebendo os dados do formulário
            $titulo = $_POST['titulo'];
            $texto = $_POST['texto'];
            $profQuePostou = $_POST['profQuePostou'];
            $idConteudoPertencente = (int)$_POST['idConteudoPertencente'];

            // Verificando se o arquivo de imagem foi enviado corretamente
            if (isset($_FILES['imagem']) && $_FILES['imagem']['error'] === UPLOAD_ERR_OK) {
                $imagemTmp = $_FILES['imagem']['tmp_name'];
                $imagemConteudo = file_get_contents($imagemTmp); // Conteúdo da imagem
            } else {
                echo "<p>Erro ao fazer upload da imagem.</p>";
                exit;
            }

            // Inserir os dados no banco de dados
            $sql = "INSERT INTO Flashcard (imagem, titulo, texto, profQuePostou, idConteudoPertencente) 
                    VALUES (:imagem, :titulo, :texto, :profQuePostou, :idConteudoPertencente)";

            try {
                $stmt = $pdo->prepare($sql);
                $stmt->bindParam(':imagem', $imagemConteudo, PDO::PARAM_LOB);
                $stmt->bindParam(':titulo', $titulo);
                $stmt->bindParam(':texto', $texto);
                $stmt->bindParam(':profQuePostou', $profQuePostou);
                $stmt->bindParam(':idConteudoPertencente', $idConteudoPertencente);

                $stmt->execute();
                echo "<p>Flashcard cadastrado com sucesso!</p>";
            } catch (PDOException $e) {
                echo "<p>Erro ao cadastrar o flashcard: " . $e->getMessage() . "</p>";
            }

            $pdo = null; // Fechar conexão
        }
        ?>
    </div>
</body>
</html>
