<!--Esse código só serve para simular o cadastro de livros para poder puxar no android studio-->

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Livro</title>
</head>
<body>
    <h1>Cadastro de Livro</h1>
    
    <form action="" method="POST" enctype="multipart/form-data">
        <label for="isbn">ISBN:</label>
        <input type="text" name="isbn" id="isbn" maxlength="13" required><br><br>

        <label for="capa">Capa do Livro:</label>
        <input type="file" name="capa" id="capa" accept="image/*" required><br><br>

        <label for="titulo">Título:</label>
        <input type="text" name="titulo" id="titulo" maxlength="300" required><br><br>

        <label for="autor">Autor:</label>
        <input type="text" name="autor" id="autor" maxlength="200" required><br><br>

        <label for="edicao">Edição:</label>
        <input type="number" name="edicao" id="edicao" required><br><br>

        <label for="dataPublicacao">Data de Publicação:</label>
        <input type="date" name="dataPublicacao" id="dataPublicacao" required><br><br>

        <label for="siglaOlimpiada">Sigla da Olimpíada Pertencente:</label>
        <input type="text" name="siglaOlimpiada" id="siglaOlimpiada" maxlength="10" required><br><br>

        <button type="submit" name="submit">Cadastrar Livro</button>
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
        $isbn = $_POST['isbn'];
        $titulo = $_POST['titulo'];
        $autor = $_POST['autor'];
        $edicao = (int)$_POST['edicao'];
        $dataPublicacao = $_POST['dataPublicacao'];
        $siglaOlimpiada = $_POST['siglaOlimpiada'];

        // Verificando se o arquivo de imagem foi enviado corretamente
        if (isset($_FILES['capa']) && $_FILES['capa']['error'] === UPLOAD_ERR_OK) {
            $capaTmp = $_FILES['capa']['tmp_name'];
            $capaConteudo = file_get_contents($capaTmp); // Conteúdo da imagem
        } else {
            echo "<p>Erro ao fazer upload da imagem da capa.</p>";
            exit;
        }

        // Inserir os dados no banco de dados
        $sql = "INSERT INTO Livro (isbn, capa, titulo, autor, edicao, dataPublicacao, siglaOlimpiadaPertencente) 
                VALUES (:isbn, :capa, :titulo, :autor, :edicao, :dataPublicacao, :siglaOlimpiada)";

        try {
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam(':isbn', $isbn);
            $stmt->bindParam(':capa', $capaConteudo, PDO::PARAM_LOB);
            $stmt->bindParam(':titulo', $titulo);
            $stmt->bindParam(':autor', $autor);
            $stmt->bindParam(':edicao', $edicao);
            $stmt->bindParam(':dataPublicacao', $dataPublicacao);
            $stmt->bindParam(':siglaOlimpiada', $siglaOlimpiada);

            $stmt->execute();
            echo "<p>Livro cadastrado com sucesso!</p>";
        } catch (PDOException $e) {
            echo "<p>Erro ao cadastrar o livro: " . $e->getMessage() . "</p>";
        }

        $pdo = null; // Fechar conexão
    }
    ?>
</body>
</html>
