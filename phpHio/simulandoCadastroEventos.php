<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar Evento</title>
</head>
<body>

<h2>Cadastrar Novo Evento</h2>

<form action="" method="post">
    <label for="tipo">Tipo de Evento:</label>
    <input type="text" id="tipo" name="tipo" required><br><br>
    
    <label for="dataOcorrencia">Data de Ocorrência:</label>
    <input type="date" id="dataOcorrencia" name="dataOcorrencia" required><br><br>
    
    <label for="horario">Horário de Início e Fim:</label>
    <input type="text" id="horario" name="horario" placeholder="HH:MM - HH:MM" required><br><br>
    
    <label for="link">Link (opcional):</label>
    <input type="url" id="link" name="link"><br><br>
    
    <label for="siglaOlimpiadaPertencente">Sigla da Olimpíada:</label>
    <input type="text" id="siglaOlimpiadaPertencente" name="siglaOlimpiadaPertencente" required><br><br>
    
    <button type="submit" name="submit">Cadastrar Evento</button>
</form>

<?php
if (isset($_POST['submit'])) {
    $tipo = $_POST['tipo'];
    $dataOcorrencia = $_POST['dataOcorrencia'];
    $horario = $_POST['horario'];
    $link = isset($_POST['link']) ? $_POST['link'] : '';
    $siglaOlimpiadaPertencente = $_POST['siglaOlimpiadaPertencente'];

    // Dividir o horário em começo e fim
    list($horarioComeco, $horarioFim) = explode(' - ', $horario);

    $servername = "localhost"; 
    $username = "root";        
    $password = "root";            
    $dbname = "hio";     

    try {
        $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $sql = "INSERT INTO Eventos (tipo, dataOcorrencia, horarioComeco, horarioFim, link, siglaOlimpiadaPertencente) 
                VALUES (:tipo, :dataOcorrencia, :horarioComeco, :horarioFim, :link, :siglaOlimpiadaPertencente)";
        $stmt = $pdo->prepare($sql);
        
        $stmt->bindParam(':tipo', $tipo, PDO::PARAM_STR);
        $stmt->bindParam(':dataOcorrencia', $dataOcorrencia, PDO::PARAM_STR);
        $stmt->bindParam(':horarioComeco', $horarioComeco, PDO::PARAM_STR);
        $stmt->bindParam(':horarioFim', $horarioFim, PDO::PARAM_STR);
        $stmt->bindParam(':link', $link, PDO::PARAM_STR);
        $stmt->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente, PDO::PARAM_STR);
        
        if ($stmt->execute()) {
            echo "<p>Evento cadastrado com sucesso!</p>";
        } else {
            echo "<p>Erro ao cadastrar evento.</p>";
        }

    } catch (PDOException $e) {
        echo "<p>Erro na conexão com o banco de dados: " . $e->getMessage() . "</p>";
    }
}
?>
</body>
</html>
