let currentSlide = 0;
const items = document.querySelectorAll('.carousel-item');
const totalItems = items.length;

function updateCarousel() {
    // Oculta todos os itens
    items.forEach((item, index) => {
        item.style.display = 'none';
    });

    // Mostra o item atual
    items[currentSlide].style.display = 'block';
}

function nextSlide() {
    currentSlide = (currentSlide + 1) % totalItems; // Vai para o próximo slide, volta ao início no final
    updateCarousel();
}

function prevSlide() {
    currentSlide = (currentSlide - 1 + totalItems) % totalItems; // Vai para o slide anterior, volta ao final se estiver no início
    updateCarousel();
}

// Inicializa o carrossel mostrando o primeiro item
updateCarousel();
