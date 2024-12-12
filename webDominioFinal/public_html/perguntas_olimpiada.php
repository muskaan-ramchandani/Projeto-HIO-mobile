<?php
// Configurações do banco de dados
$servername = "127.0.0.1:3306";
$username = "u740411060_user";
$password = "OWYzZ?o2";
$dbname = "u740411060_hio";

// Conexão com o banco de dados
try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "<p>Erro ao conectar ao banco de dados: " . htmlspecialchars($e->getMessage()) . "</p>";
    exit;
}

// Função para gerar cor aleatória
function getCorAleatoria() {
    $cores = ['Rosa' => '#CB6CE6', 'Azul' => '#5271FF', 'Laranja' => '#FF914D', 'Ciano' => '#18B9CD'];
    return $cores[array_rand($cores)];
}

// Consulta para contar o número de perguntas por olimpíada
$sql = "SELECT siglaOlimpiadaRelacionada, COUNT(*) as numPerguntas 
        FROM PerguntasForum 
        GROUP BY siglaOlimpiadaRelacionada";
$stmt = $pdo->query($sql);

// HTML para a estrutura das olimpíadas e perguntas
echo '<div class="squares-container">';

// Verifica se existem olimpíadas com perguntas
if ($stmt->rowCount() > 0) {
    // Exibe cada olimpíada com o número de perguntas relacionadas
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        $siglaOlimpiada = htmlspecialchars($row['siglaOlimpiadaRelacionada']);
        $numPerguntas = $row['numPerguntas'];

        // Chama a função para obter a cor aleatória
        $corAleatoria = getCorAleatoria();

        // Gera as divs para cada olimpíada com a cor aleatória
        echo '
        <div class="square" style="background-color: ' . $corAleatoria . ';">
            <div class="square-text">' . $siglaOlimpiada . '</div>
            <div class="square-subtext">' . $numPerguntas . ' perguntas relacionadas</div>
        </div>';
    }
} else {
    echo "<p>Nenhuma pergunta relacionada a olimpíadas encontrada.</p>";
}

echo '</div>';
?>
