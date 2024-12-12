<?php
// Conectar ao banco de dados
$dsn = 'mysql:host=sql207.infinityfree.com;dbname=if0_37755624_hio;charset=utf8';
$username = 'if0_37755624';
$password = '1k31AyGMaqyz7';

try {
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Capturar a sigla da URL
    if (isset($_GET['sigla'])) {
        $sigla = $_GET['sigla'];

        // Buscar os dados da olimpíada correspondente
        $sql = "SELECT nome, icone, cor FROM Olimpiada WHERE sigla = :sigla";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':sigla', $sigla);
        $stmt->execute();

        $olimpiada = $stmt->fetch(PDO::FETCH_ASSOC);
    } else {
        // Se não houver sigla na URL, redirecionar para a página inicial
        header('Location: TelaInicialProfessorHTML.php');
        exit();
    }
} catch (PDOException $e) {
    echo "Erro: " . $e->getMessage();
}
?>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
    <link rel="stylesheet" href="TelaAcessoTexto.css">
</head>
<body>
    <?php
// Capturando o email e a sigla da URL usando GET
$email = isset($_GET['email']) ? $_GET['email'] : '';
$sigla = isset($_GET['sigla']) ? $_GET['sigla'] : '';

// Verifique se ambos os valores estão definidos
if (empty($email) || empty($sigla)) {
    die("Erro: Email ou sigla não definidos.");
}
?>

  <div class="barra">
    <div class="logo-container">
        <img src="iconeTexto.png">
        <div class="text"><?php echo htmlspecialchars($olimpiada['nome']); ?> - <?php echo htmlspecialchars($sigla); ?></div>
    </div>


        <div class="button-item"onclick="window.history.back();" style="cursor: pointer;">
            <img src="btnVoltarBRANCO.png" alt="Voltar">
            <div class="button-label">Voltar</div>
        </div>
    </div>

 <?php include 'TelaAcessoTexto.php'; ?>


    <div class="conceitos">
    <?php echo $titulo; ?>  <!-- Exibe o título do conteúdo -->

    </div>


    <div class="autor">
            Por: <?php echo $autor; ?>  <!-- Exibe o e-mail ou nome do professor que postou -->

    </div>


    <div class="texto-principal">
        <?php echo $textoConteudo; ?>  <!-- Exibe o conteúdo do texto -->

</div>


</body>
</html>
