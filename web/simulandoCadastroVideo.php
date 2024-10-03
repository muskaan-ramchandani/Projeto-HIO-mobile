<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Vídeo</title>
</head>
<body>
    <h1>Cadastro de Vídeo</h1>
    
    <form action="" method="POST" enctype="multipart/form-data">
        <label for="titulo">Título:</label>
        <input type="text" name="titulo" id="titulo" maxlength="300" required><br><br>

        <label for="capa">Capa do Vídeo:</label>
        <input type="file" name="capa" id="capa" accept="image/*" required><br><br>

        <label for="link">Link do Vídeo:</label>
        <input type="url" name="link" id="link" required><br><br>

        <label for="profQuePostou">Email do Professor:</label>
        <input type="email" name="profQuePostou" id="profQuePostou" required><br><br>

        <label for="idConteudoPertencente">ID do Conteúdo Pertencente:</label>
        <input type="number" name="idConteudoPertencente" id="idConteudoPertencente" required><br><br>

        <button type="submit" name="submit">Cadastrar Vídeo</button>
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
        $link = $_POST['link'];
        $profQuePostou = $_POST['profQuePostou'];
        $idConteudoPertencente = (int)$_POST['idConteudoPertencente'];

        // Verificando se o arquivo de imagem foi enviado corretamente
        if (isset($_FILES['capa']) && $_FILES['capa']['error'] === UPLOAD_ERR_OK) {
            $capaTmp = $_FILES['capa']['tmp_name'];
            $capaConteudo = file_get_contents($capaTmp); // Conteúdo da imagem
        } else {
            echo "<p>Erro ao fazer upload da capa do vídeo.</p>";
            exit;
        }

        // Inserir os dados no banco de dados
        $sql = "INSERT INTO Video (capa, titulo, link, profQuePostou, idConteudoPertencente) 
                VALUES (:capa, :titulo, :link, :profQuePostou, :idConteudoPertencente)";

        try {
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam(':capa', $capaConteudo, PDO::PARAM_LOB);
            $stmt->bindParam(':titulo', $titulo);
            $stmt->bindParam(':link', $link);
            $stmt->bindParam(':profQuePostou', $profQuePostou);
            $stmt->bindParam(':idConteudoPertencente', $idConteudoPertencente);

            $stmt->execute();
            echo "<p>Vídeo cadastrado com sucesso!</p>";
        } catch (PDOException $e) {
            echo "<p>Erro ao cadastrar o vídeo: " . $e->getMessage() . "</p>";
        }

        $pdo = null; // Fechar conexão
    }
    ?>
</body>
</html>
