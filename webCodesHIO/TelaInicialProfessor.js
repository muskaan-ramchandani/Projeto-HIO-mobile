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

function prevSlide() {
    const carouselInner = document.getElementById('carouselInner');
    const firstItem = carouselInner.querySelector('.carousel-item');
    carouselInner.appendChild(firstItem); // Move o primeiro item para o final
}

function nextSlide() {
    const carouselInner = document.getElementById('carouselInner');
    const lastItem = carouselInner.querySelector('.carousel-item:last-child');
    carouselInner.insertBefore(lastItem, carouselInner.firstChild); // Move o último item para o início
}

function prevQuestionnaireSlide() {
    const questionnairesCarouselInner = document.getElementById('questionnairesCarouselInner');
    const firstItem = questionnairesCarouselInner.querySelector('.questionnaires-carousel-item');
    questionnairesCarouselInner.appendChild(firstItem); // Move o primeiro item para o final
}

function nextQuestionnaireSlide() {
    const questionnairesCarouselInner = document.getElementById('questionnairesCarouselInner');
    const lastItem = questionnairesCarouselInner.querySelector('.questionnaires-carousel-item:last-child');
    questionnairesCarouselInner.insertBefore(lastItem, questionnairesCarouselInner.firstChild); // Move o último item para o início
}
