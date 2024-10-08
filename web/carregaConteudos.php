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
    echo '<div class="carousel-content" id="carouselInner">'; // Alterado para "carousel-content"

    // Loop para gerar os itens do carrossel, dois por vez
    for ($i = 0; $i < count($conteudos); $i += 2) {
        echo '<div class="carousel-item">';
        
        // Primeiro conteúdo
        if (isset($conteudos[$i])) {
            echo '<a href="TelaFlashCardHTML.php?id=' . htmlspecialchars($conteudos[$i]['id']) . '&sigla=' . htmlspecialchars($sigla) . '" class="button-content">';
            echo '<h5>' . htmlspecialchars($conteudos[$i]['titulo']) . '</h5>';
            echo '<p>' . htmlspecialchars($conteudos[$i]['subtitulo']) . '</p>';
            echo '</a>';
        }

        // Segundo conteúdo
        if (isset($conteudos[$i + 1])) {
            echo '<a href="TelaFlashCardHTML.php?id=' . htmlspecialchars($conteudos[$i + 1]['id']) . '&sigla=' . htmlspecialchars($sigla) . '" class="button-content">';
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
