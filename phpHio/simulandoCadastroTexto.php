<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar Texto</title>
</head>
<body>
    <h2>Cadastro de Texto</h2>
    <form method="POST" action="">
        <label for="titulo">Título:</label><br>
        <input type="text" id="titulo" name="titulo" required><br><br>

        <label for="texto">Texto:</label><br>
        <textarea id="texto" name="texto" required></textarea><br><br>

        <label for="profQuePostou">E-mail do Professor que Postou:</label><br>
        <input type="email" id="profQuePostou" name="profQuePostou" required><br><br>

        <label for="idConteudoPertencente">ID do Conteúdo Pertencente:</label><br>
        <input type="number" id="idConteudoPertencente" name="idConteudoPertencente" required><br><br>

        <input type="submit" name="submit" value="Cadastrar Texto">
    </form>

    <?php

    // Verifica se o formulário foi enviado
    if (isset($_POST['submit'])) {
        // Dados recebidos do formulário
        $titulo = $_POST['titulo'];
        $texto = $_POST['texto'];
        $profQuePostou = $_POST['profQuePostou'];
        $idConteudoPertencente = $_POST['idConteudoPertencente'];

        // Conexão com o banco de dados MySQL
        $servername = "localhost"; // Defina conforme necessário
        $username = "root"; // Defina conforme necessário
        $password = "root"; // Defina conforme necessário
        $dbname = "hio"; // Defina o nome do seu banco de dados

        try {
            $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
            exit;
        }

        // Preparando a instrução SQL para inserção
        $sql = "INSERT INTO Texto (titulo, texto, profQuePostou, idConteudoPertencente) VALUES (:titulo, :texto, :profQuePostou, :idConteudoPertencente)";

        // Preparando a query
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':titulo', $titulo);
        $stmt->bindParam(':texto', $texto);
        $stmt->bindParam(':profQuePostou', $profQuePostou);
        $stmt->bindParam(':idConteudoPertencente', $idConteudoPertencente);

        // Executa a query
        if ($stmt->execute()) {
            echo "<p>Texto cadastrado com sucesso!</p>";
        } else {
            echo "<p>Erro ao cadastrar o texto: " . $stmt->errorInfo()[2] . "</p>";
        }

        // Fecha a conexão
        $pdo = null;
    }
    ?>
</body>
</html>
