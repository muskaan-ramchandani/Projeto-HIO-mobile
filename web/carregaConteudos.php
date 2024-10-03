<?php
// Conectar ao banco de dados
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

// Obter a sigla da olimpíada a partir da URL
$sigla = $_GET['sigla'] ?? '';

if (empty($sigla)) {
    echo "<p>Olimpíada não detectada!</p>";
    exit;
}

// Consulta SQL para buscar os conteúdos
$sql = "
SELECT id, titulo, subtitulo 
FROM Conteudo 
WHERE siglaOlimpiadaPertencente = :sigla";
$statement = $pdo->prepare($sql);
$statement->bindParam(':sigla', $sigla);
$statement->execute();

// Coletar os conteúdos em um array
$conteudos = [];
while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
    $conteudos[] = $result;
}

// Fechar conexão com o banco
$pdo = null;

// Gerar HTML do carrossel com os dados obtidos
if (count($conteudos) === 0) {
    echo "<p>Nenhum conteúdo encontrado para esta olimpíada.</p>";
} else {
    echo '<div class="carousel-container">';
    echo '<button id="prevButton" class="carousel-button" onclick="prevSlide()">Anterior</button>';
    echo '<div class="carousel">';
    echo '<div class="carousel-inner" id="carouselInner">';

    // Loop para gerar os itens do carrossel como botões
    foreach ($conteudos as $index => $conteudo) {
        $activeClass = ($index === 0) ? 'active' : ''; // O primeiro item é ativo
        echo '<div class="carousel-item ' . $activeClass . '">';
        echo '<a href="TelaFlashCardHTML.php?id=' . htmlspecialchars($conteudo['id']) . '&sigla=' . htmlspecialchars($sigla) . '" class="button-content">';
        echo '<h5>' . htmlspecialchars($conteudo['titulo']) . '</h5>';
        echo '<p>' . htmlspecialchars($conteudo['subtitulo']) . '</p>';
        echo '</a>';
        echo '</div>'; // .carousel-item
    }

    echo '</div>'; // .carousel-inner
    echo '</div>'; // .carousel
    echo '<button id="nextButton" class="carousel-button" onclick="nextSlide()">Próximo</button>';
    echo '</div>'; // .carousel-container
}
?>
