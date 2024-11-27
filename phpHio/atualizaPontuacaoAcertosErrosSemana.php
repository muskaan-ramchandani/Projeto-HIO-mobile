<?php
$servername = "sql207.infinityfree.com";
$username = "if0_37755624";
$password = "1k31AyGMaqyz7";
$dbname = "if0_37755624_hio";

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    //Zerando a pontuação de todos os alunos
    $sqlResetPontuacao = "UPDATE PontuacaoAlunos SET pontuacao = 0";
    $pdo->exec($sqlResetPontuacao);

    //Apagando acertos da semana que passou
    $sqlDeleteAcertos = "DELETE FROM AcertosAluno";
    $pdo->exec($sqlDeleteAcertos);

    //Apagando erros da semana que passou
    $sqlDeleteErros = "DELETE FROM ErrosAluno";
    $pdo->exec($sqlDeleteErros);

    echo "Pontuações zeradas e acertos/erros deletados com sucesso!";
} catch (PDOException $e) {
    echo "Erro ao executar: " . $e->getMessage();
}

$pdo = null;
?>
