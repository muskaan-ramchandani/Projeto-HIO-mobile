<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');

$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "hio";

$emailAluno = $_GET['email'];

$pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);

$sql = "
    SELECT O.nome, O.sigla, O.icone, O.cor
    FROM Olimpiada O
    WHERE O.sigla NOT IN (
        SELECT OS.sigla
        FROM OlimpiadasSelecionadas OS
        WHERE OS.emailAluno = :email
    )
";
$statement = $pdo->prepare($sql);
$statement->bindParam(':email', $emailAluno, PDO::PARAM_STR);
$statement->execute();

$olimpiadas = [];
while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
    $olimpiadas[] = (object) $result;
}

$ordem_cores = ['Rosa', 'Azul', 'Laranja', 'Ciano'];

$grupos = [];
foreach ($ordem_cores as $cor) {
    $grupos[$cor] = array_filter($olimpiadas, function ($item) use ($cor) {
        return $item->cor === $cor;
    });
}

$resultado = [];
while (true) {
    $adicionou = false;
    foreach ($ordem_cores as $cor) {
        if (!empty($grupos[$cor])) {
            $resultado[] = array_shift($grupos[$cor]);
            $adicionou = true;
        }
    }
    if (!$adicionou) break; 
}

$json_resultado = ['olimpiadas' => $resultado];

echo json_encode($json_resultado);
?>
