<?php
// Conexão ao banco de dados (utilize seu arquivo de conexão)
require 'conexao.php';

$sql = "SELECT nome, sigla, icone, cor FROM Olimpiada";
$stmt = $pdo->prepare($sql);
$stmt->execute();
$olimpiadas = $stmt->fetchAll(PDO::FETCH_ASSOC);
?>

<div class="carousel-container">
    <button id="prevButton" class="carousel-button" onclick="prevSlide()">
        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar">
    </button>
    <div class="carousel">
        <div class="carousel-inner" id="carouselInner">
            <?php foreach ($olimpiadas as $olimpiada): ?>
                <div class="carousel-item">
                    <a href="visualizarConteudos.php?sigla=<?php echo $olimpiada['sigla']; ?>" class="olympics-button" style="background-color: <?php echo $olimpiada['cor']; ?>;">
                        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/<?php echo $olimpiada['icone']; ?>.png" alt="<?php echo $olimpiada['sigla']; ?>" class="button-icon">
                        <?php echo $olimpiada['nome'] . ' - ' . $olimpiada['sigla']; ?>
                    </a>
                </div>
            <?php endforeach; ?>
        </div>
    </div>
    <button id="nextButton" class="carousel-button" onclick="nextSlide()">
        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir">
    </button>
</div>
