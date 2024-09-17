function toggleMenu() {
    var menuContent = document.getElementById('menuContent');
    var menuIcon = document.querySelector('.menu-icon');
    var helperText = document.getElementById('helperText');


    if (menuContent.style.display === 'block') {
        menuContent.style.display = 'none';
        menuIcon.classList.remove('active');
        helperText.style.display = 'none';
    } else {
        menuContent.style.display = 'block';
        menuIcon.classList.add('active');
        helperText.style.display = 'inline';
    }
}
let currentSlide = 0;
let totalSlides = 0;
let olimpiadas = [];

// Função para carregar as olimpíadas usando AJAX e preencher o carousel
function loadOlimpiadas() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'TelaInicialProfessor.php', true);

    xhr.onload = function () {
        if (this.status === 200) {
            olimpiadas = JSON.parse(this.responseText);

            if (olimpiadas.error) {
                console.error('Erro ao carregar as olimpíadas:', olimpiadas.error);
            } else {
                // Criando os itens do carousel dinamicamente
                totalSlides = olimpiadas.length;
                updateCarousel();
            }
        }
    };

    xhr.send();
}

// Função para atualizar o carousel com os itens atuais
function updateCarousel() {
    const carouselInner = document.getElementById('carouselInner');
    carouselInner.innerHTML = ''; // Limpa o conteúdo anterior

    // Adiciona o item da olimpíada atual ao carousel
    const currentOlimpiada = olimpiadas[currentSlide];
    const itemDiv = document.createElement('div');
    itemDiv.className = 'carousel-item';

    const olympicsButton = document.createElement('a');
    olympicsButton.href = '#';
    olympicsButton.className = 'olympics-button';
    olympicsButton.style.backgroundColor = '#cb6ce6'; // Ajuste a cor conforme desejado

    const icon = document.createElement('img');
    icon.src = 'Imagens_Mobile_HIO/imgTelescopio.png'; // Altere para o ícone desejado
    icon.className = 'button-icon';

    const span = document.createElement('span');
    span.textContent = currentOlimpiada.nome + ' (' + currentOlimpiada.sigla + ')';

    olympicsButton.appendChild(icon);
    olympicsButton.appendChild(span);
    itemDiv.appendChild(olympicsButton);
    carouselInner.appendChild(itemDiv);
}

// Função para mostrar o slide anterior
function prevSlide() {
    currentSlide = (currentSlide - 1 + totalSlides) % totalSlides;
    updateCarousel();
}

// Função para mostrar o próximo slide
function nextSlide() {
    currentSlide = (currentSlide + 1) % totalSlides;
    updateCarousel();
}

// Chama a função ao carregar a página
window.onload = loadOlimpiadas;