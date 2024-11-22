// Função para abrir o modal
function openModal(modalId) {
    document.getElementById(modalId).style.display = "block";
}


// Função para fechar o modal
function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
}


// Fecha o modal se o usuário clicar fora do conteúdo do modal
window.onclick = function(event) {
    if (event.target.classList.contains('modal')) {
        event.target.style.display = "none";
    }
}


