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
    echo '<div class="carousel-content" id="carouselInner">';

    // Loop para gerar os itens do carrossel, dois por vez
    $email = isset($_GET['email']) ? htmlspecialchars($_GET['email']) : '';

    for ($i = 0; $i < count($conteudos); $i += 2) {
        echo '<div class="carousel-item">';
    
        // Primeiro conteúdo
        if (isset($conteudos[$i])) {
            echo '<a href="TelaFlashCardHTML.php?id=' . htmlspecialchars($conteudos[$i]['id']) . '&sigla=' . htmlspecialchars($sigla) . '&email=' . $email . '" class="button-content">';
            echo '<h5>' . htmlspecialchars($conteudos[$i]['titulo']) . '</h5>';
            echo '<p>' . htmlspecialchars($conteudos[$i]['subtitulo']) . '</p>';
            echo '</a>';
        }
    
        // Segundo conteúdo
        if (isset($conteudos[$i + 1])) {
            echo '<a href="TelaFlashCardHTML.php?id=' . htmlspecialchars($conteudos[$i + 1]['id']) . '&sigla=' . htmlspecialchars($sigla) . '&email=' . $email . '" class="button-content">';
            echo '<h5>' . htmlspecialchars($conteudos[$i + 1]['titulo']) . '</h5>';
            echo '<p>' . htmlspecialchars($conteudos[$i + 1]['subtitulo']) . '</p>';
            echo '</a>';
        }
        echo '</div>'; // .carousel-item
    }

    echo '</div>'; // .carousel-content
    echo '<button id="nextButton" class="carousel-button" onclick="nextSlide()">Próximo</button>';
    echo '</div>'; // .carousel-container
}
?>

<!-- Estilos CSS para o carrossel -->
<style>
    .carousel-container {
        display: flex;
        align-items: center;
        justify-content: center;
        position: relative;
        width: 100%;
        overflow: hidden;
    }

    .carousel-content {
        display: flex;
        transition: transform 0.5s ease;
        width: 100%;
    }

    .carousel-item {
        display: flex;
        justify-content: space-between;
        width: 100%; /* ajuste conforme necessário */
        margin: 0 10px; /* espaço entre os itens */
    }

    .button-content {
        flex: 1;
        margin: 0 5px;
        padding: 10px;
        background-color: #f9f9f9;
        border: 1px solid #ddd;
        border-radius: 5px;
        text-align: center;
        text-decoration: none;
        color: #333;
        transition: background-color 0.3s;
    }

    .button-content:hover {
        background-color: #e2e2e2;
    }

    .carousel-button {
        background-color: #007bff;
        color: white;
        border: none;
        padding: 10px;
        cursor: pointer;
        margin: 10px;
        border-radius: 5px;
        transition: background-color 0.3s;
    }

    .carousel-button:hover {
        background-color: #0056b3;
    }
</style>

<!-- JavaScript para controlar o carrossel -->
<script>
    let currentIndex = 0;
    const itemsToShow = 2; // Número de itens a mostrar por vez

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
