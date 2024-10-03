<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Prova Anterior</title>
</head>
<body>
    <h1>Cadastrar Prova Anterior</h1>

    <?php
    session_start();

    $servername = "localhost";
    $username = "root";
    $password = "root";
    $dbname = "hio";

    try {
        $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    } catch (PDOException $e) {
        die("Erro na conexão com o banco de dados: " . $e->getMessage());
    }
  
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $anoDaProva = $_POST['anoDaProva'];
        $estado = isset($_POST['estado']) ? 1 : 0;
        $fase = $_POST['fase'];
        $profQuePostou = $_POST['profQuePostou'];
        $siglaOlimpiadaPertencente = $_POST['sigla'];

   
        if (isset($_FILES['arquivoPdf']) && $_FILES['arquivoPdf']['error'] === UPLOAD_ERR_OK) {
            $arquivoPdf = file_get_contents($_FILES['arquivoPdf']['tmp_name']);

            // Inserir os dados no banco de dados
            $sql = "INSERT INTO ProvaAnterior (anoDaProva, estado, fase, profQuePostou, arquivoPdf, siglaOlimpiadaPertencente) 
                    VALUES (:anoDaProva, :estado, :fase, :profQuePostou, :arquivoPdf, :siglaOlimpiadaPertencente)";
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam(':anoDaProva', $anoDaProva);
            $stmt->bindParam(':estado', $estado);
            $stmt->bindParam(':fase', $fase);
            $stmt->bindParam(':profQuePostou', $profQuePostou);
            $stmt->bindParam(':arquivoPdf', $arquivoPdf, PDO::PARAM_LOB);
            $stmt->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);

            if ($stmt->execute()) {
                echo "<p>Prova cadastrada com sucesso!</p>";
            } else {
                echo "<p>Erro ao cadastrar prova.</p>";
            }
        } else {
            echo "<p>Erro ao enviar o arquivo PDF.</p>";
        }
    }

   
    $sql = "SELECT nome, sigla FROM Olimpiada";
    $stmt = $pdo->prepare($sql);
    $stmt->execute();
    $olimpiadas = $stmt->fetchAll(PDO::FETCH_ASSOC);
    ?>
