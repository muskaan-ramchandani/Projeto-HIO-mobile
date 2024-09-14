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


// Carrossel das Olimpíadas
let currentSlideIndex = 0;
const slidesToShow = 2; // Número de slides a serem exibidos


function updateCarousel() {
    const carouselInner = document.getElementById('carouselInner');
    const items = document.querySelectorAll('.carousel-item');
    const totalItems = items.length;


    // Atualiza a posição do carrossel
    carouselInner.style.transform = `translateX(${-currentSlideIndex * (100 / slidesToShow)}%)`;


    // Desativa/ativa os botões conforme necessário
    document.getElementById('prevButton').disabled = currentSlideIndex === 0;
    document.getElementById('nextButton').disabled = currentSlideIndex >= totalItems - slidesToShow;
}


function prevSlide() {
    if (currentSlideIndex > 0) {
        currentSlideIndex -= 1;
        updateCarousel();
    }
}


function nextSlide() {
    const totalItems = document.querySelectorAll('.carousel-item').length;
    if (currentSlideIndex < totalItems - slidesToShow) {
        currentSlideIndex += 1;
        updateCarousel();
    }
}


// Carrossel de questionários
let currentQuestionnaireIndex = 0;


function updateQuestionnairesCarousel() {
    const carouselInner = document.getElementById('questionnairesCarouselInner');
    const items = document.querySelectorAll('.questionnaires-carousel-item');
    const totalItems = items.length;


    // Atualiza a posição do carrossel
    carouselInner.style.transform = `translateX(${-currentQuestionnaireIndex * 100}%)`;


    // Desativa/ativa os botões conforme necessário
    document.getElementById('prevQuestionnaireButton').disabled = currentQuestionnaireIndex === 0;
    document.getElementById('nextQuestionnaireButton').disabled = currentQuestionnaireIndex >= totalItems - 1;
}


function prevQuestionnaireSlide() {
    if (currentQuestionnaireIndex > 0) {
        currentQuestionnaireIndex--;
        updateQuestionnairesCarousel();
    }
}


function nextQuestionnaireSlide() {
    const totalItems = document.querySelectorAll('.questionnaires-carousel-item').length;
    if (currentQuestionnaireIndex < totalItems - 1) {
        currentQuestionnaireIndex++;
        updateQuestionnairesCarousel();
    }
}


// Inicializa os carrosséis
updateCarousel();
updateQuestionnairesCarousel();

