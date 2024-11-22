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
    echo '<button id="prevButton" class="carousel-button" onclick="prevSlide()">&#8592;</button>';
    echo '<div class="carousel-content" id="carouselInner">';

    // Loop para gerar os itens do carrossel
    $email = isset($_GET['email']) ? htmlspecialchars($_GET['email']) : '';

    foreach ($conteudos as $conteudo) {
        echo '<div class="carousel-item">';
        echo '<a href="TelaFlashCardHTML.php?id=' . htmlspecialchars($conteudo['id']) . '&sigla=' . htmlspecialchars($sigla) . '&email=' . $email . '">';
        echo '<h5>' . htmlspecialchars($conteudo['titulo']) . '</h5>';
        echo '<p>' . htmlspecialchars($conteudo['subtitulo']) . '</p>';
        echo '</a>';
        echo '</div>'; // .carousel-item
    }

    echo '</div>'; // .carousel-content
    echo '<button id="nextButton" class="carousel-button" onclick="nextSlide()">&#8594;</button>';
    echo '</div>'; // .carousel-container
}
?>

<!-- JavaScript para controlar o carrossel -->
<script>
    let currentIndex = 0;
    const itemsToShow = 1; // Número de itens a mostrar por vez

    function updateCarousel() {
        const carouselInner = document.getElementById('carouselInner');
        const totalItems = document.querySelectorAll('.carousel-item').length;
        const offset = currentIndex * (100 / itemsToShow);
        
        // Limitar o deslocamento
        if (currentIndex < 0) {
            currentIndex = 0;
        } else if (currentIndex > totalItems - itemsToShow) {
            currentIndex = totalItems - itemsToShow;
        }

        carouselInner.style.transform = 'translateX(-' + offset + '%)';
    }

    function nextSlide() {
        currentIndex++;
        updateCarousel();
    }

    function prevSlide() {
        currentIndex--;
        updateCarousel();
    }
</script>
