<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrossel de Olimpíadas</title>
    <style>
      .carousel-container {
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    width: 100%;
    max-width: 1200px; /* Defina um limite máximo de largura */
    margin: auto;
}

.carousel {
    width: 100%; /* Certifique-se de que o carrossel ocupe 100% do container */
    overflow: hidden;
}

.carousel-inner {
    display: flex;
    transition: transform 0.5s ease;
}

.carousel-item {
    min-width: 33.33%; /* Definir a largura relativa de cada item */
    box-sizing: border-box;
    padding: 10px;
    text-align: center; /* Centraliza o conteúdo */
}

.olympics-button {
    display: block;
    text-align: center;
    padding: 20px;
    border-radius: 8px;
    color: white;
    text-decoration: none;
    font-weight: bold;
    font-size: 18px;
}

.carousel-item img.button-icon {
    max-width: 100px; /* Limite o tamanho máximo da imagem */
    height: auto;
    margin-bottom: 10px; /* Espaçamento entre a imagem e o texto */
}

.carousel-button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background-color: transparent;
    border: none;
    cursor: pointer;
    z-index: 10; /* Garante que os botões estejam acima do conteúdo */
}

#prevButton {
    left: 10px;
}

#nextButton {
    right: 10px;
}

    </style>
   
</head>
<body>

<div class="carousel-container">
    <button id="prevButton" class="carousel-button" onclick="prevSlide()">
        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar">
    </button>
    
    <div class="carousel">
        <div class="carousel-inner" id="carouselInner">
            <?php
            // Inclui o arquivo que busca as Olimpíadas
            $olimpiadas = include('dadosOlimpiadas.php');

            // Verifica se existem Olimpíadas a serem exibidas
            if (!empty($olimpiadas)) {
                foreach ($olimpiadas as $olimpiada) {
                    // Gera o HTML de cada item do carrossel
                    echo '<div class="carousel-item">';
                    echo '<a href="#" class="olympics-button" style="background-color: ' . getColorHex($olimpiada['cor']) . ';">';
                    echo '<img src="http://192.168.0.81:8080/webCodesHIO/Imagens_Mobile_HIO/' . $olimpiada['icone'] . '.png" alt="' . $olimpiada['sigla'] . '" class="button-icon">';
                    echo $olimpiada['nome'] . ' - ' . $olimpiada['sigla'];
                    echo '</a>';
                    echo '</div>';
                }
            } else {
                echo "Nenhuma Olimpíada encontrada.";
            }

            // Função para converter o nome da cor para o valor hexadecimal
            function getColorHex($colorName) {
                switch ($colorName) {
                    case 'Rosa':
                        return '#CB6CE6';
                    case 'Azul':
                        return '#5271FF';
                    case 'Laranja':
                        return '#FF914D';
                    case 'Ciano':
                        return '#18B9CD';
                    default:
                        return '#CCCCCC'; // Cor padrão
                }
            }
            ?>
        </div>
    </div>
    
    <button id="nextButton" class="carousel-button" onclick="nextSlide()">
        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir">
    </button>
</div>

<script>
    let currentSlide = 0;

    function updateCarousel() {
        const carouselInner = document.getElementById('carouselInner');
        const totalItems = carouselInner.children.length;
        const itemWidth = carouselInner.children[0].offsetWidth;
        carouselInner.style.transform = 'translateX(' + (-currentSlide * itemWidth) + 'px)';
    }

    function prevSlide() {
        const carouselInner = document.getElementById('carouselInner');
        const totalItems = carouselInner.children.length;
        currentSlide = (currentSlide - 1 + totalItems) % totalItems;
        updateCarousel();
    }

    function nextSlide() {
        const carouselInner = document.getElementById('carouselInner');
        const totalItems = carouselInner.children.length;
        currentSlide = (currentSlide + 1) % totalItems;
        updateCarousel();
    }

    window.onload = updateCarousel;
</script>

</body>
</html>
