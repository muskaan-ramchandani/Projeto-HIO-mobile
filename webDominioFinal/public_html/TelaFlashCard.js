
// Fecha o modal se o usuário clicar fora do conteúdo do modal
window.onclick = function(event) {
    if (event.target.classList.contains('modal')) {
        event.target.style.display = "none";
    }
}
function openModal(modalId, title, text, image) {
    // Define o título, texto e imagem no modal
    document.getElementById("modal-title").innerText = title;
    document.getElementById("modal-text").innerText = text;
    document.getElementById("modal-image").src = image;

    // Exibe o modal
    const modal = document.getElementById("flashcard-modal");
    modal.style.display = "block";
}
function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'block';
    } else {
        console.error(`Modal com ID ${modalId} não encontrado.`);
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'none';
    } else {
        console.error(`Modal com ID ${modalId} não encontrado.`);
    }
}

// Evento para fechar modal ao clicar fora dele (opcional)
window.onclick = function(event) {
    const modals = document.getElementsByClassName('modal');
    Array.from(modals).forEach((modal) => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
};

