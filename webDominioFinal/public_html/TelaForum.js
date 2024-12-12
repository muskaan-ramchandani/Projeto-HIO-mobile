// js para menu
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


//js para os botoes-quadrados
document.querySelectorAll('.square').forEach(button => {
    button.addEventListener('click', () => {
    });
});


// js modal equação reduzida
    // Selecionando o botão "Responder pergunta" e o modal
var responderBtn = document.getElementById("responderBtn");
var modal = document.getElementById("responderModal");
var closeBtn = document.getElementsByClassName("close-btn")[0];


    // Quando o usuário clica no botão "Responder pergunta", o modal é exibido
responderBtn.onclick = function() {
    modal.style.display = "block";
}


    // Quando o usuário clica no "X" (botão de fechar) no modal, ele fecha
closeBtn.onclick = function() {
    modal.style.display = "none";
}


    // Quando o usuário clica fora do modal, ele também fecha
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}


    // Fechar o modal quando o botão "Cancelar" for clicado
cancelarBtnModal.addEventListener('click', function() {
    modal.style.display = 'none'; // Esconde o modal
});


   