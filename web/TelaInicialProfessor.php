<?php
// Configurações do banco de dados
$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$username = 'root';
$password = 'root';

try {
    // Conectar ao banco de dados
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Preparar a consulta para buscar todas as olimpíadas
    $sql = "SELECT nome, sigla, icone, cor FROM Olimpiada";
    $stmt = $pdo->query($sql);

    // Verificar se há resultados
    if ($stmt->rowCount() > 0) {
        echo '<div class="previous-olympics">Olimpíadas disponíveis:</div>';
        echo '<div class="carousel-container">';
        echo '<button id="prevButton" class="carousel-button" onclick="prevSlide()">
                <img src="Imagens_Mobile_HIO/btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar">
              </button>';
        echo '<div class="carousel">';
        echo '<div class="carousel-inner" id="carouselInner">';

        $emailProfessor = htmlspecialchars($_GET['email']);

        
        // Loop para gerar cada item do carrossel com base nos dados do banco de dados
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $nome = htmlspecialchars($row['nome']);
            $sigla = htmlspecialchars($row['sigla']);
            $icone = htmlspecialchars($row['icone']);
            $cor = htmlspecialchars($row['cor']);

            // Gerar um item do carrossel
            echo '<div class="carousel-item">';
            echo '<a href="TelaOlimpiadaProfessorHTML.php?sigla=' . $sigla .'&email=' . $emailProfessor . '" class="olympics-button" style="background-color: ' . getCorHexa($cor) . ';">';
            echo '<img src="Imagens_Mobile_HIO/' . $icone . '.png" alt="' . $sigla . '" class="button-icon">';
            echo $nome . ' - ' . $sigla;
            echo '</a>';
            echo '</div>';
        }

        echo '</div>'; 
        echo '</div>'; 
        echo '<button id="nextButton" class="carousel-button" onclick="nextSlide()">
                <img src="Imagens_Mobile_HIO/btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir">
              </button>';
        echo '</div>'; 
    } else {
        echo 'Nenhuma olimpíada disponível no momento.';
    }
} catch (PDOException $e) {
    echo 'Erro ao conectar ao banco de dados: ' . $e->getMessage();
}

function getCorHexa($cor) {
    switch ($cor) {
        case 'Rosa':
            return '#CB6CE6';
        case 'Azul':
            return '#5271FF';
        case 'Laranja':
            return '#FF914D';
        case 'Ciano':
            return '#18B9CD';
        default:
            return '#CCCCCC'; 
    }
}
?>
