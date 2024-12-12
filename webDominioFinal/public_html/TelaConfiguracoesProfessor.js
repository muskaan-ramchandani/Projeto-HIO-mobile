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


// js para alterar dados
function openModal(modalId) {
    document.getElementById(modalId).style.display = 'flex'; // Mostra o modal
}


function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none'; // Esconde o modal
}


function abrirModalConfirmacao() {
    // Obtém os valores dos campos
    const nomeCompleto = document.querySelector('.modal-input[placeholder="Digite seu nome completo"]').value;
    const nomeUsuario = document.querySelector('.modal-input[placeholder="Digite seu nome de usuário"]').value;
    const email = document.querySelector('.modal-input[placeholder="Digite seu email"]').value;


    // Verifica se todos os campos estão preenchidos
    const todosPreenchidos = nomeCompleto && nomeUsuario && email;
    // Verifica se o email é um Gmail
    const emailGmail = /^[^\s@]+@gmail\.com$/.test(email);


    if (todosPreenchidos && emailGmail) {
        closeModal('modalAlterarDados'); // Fecha o modal de alterar dados
        openModal('modalConfirmacaoAlteracao'); // Abre o modal de confirmação
    } else {
        if (!todosPreenchidos) {
            alert("Por favor, preencha todos os campos."); // Alerta se algum campo estiver vazio
        } else {
            alert("Por favor, insira um email válido (somente Gmail)."); // Alerta se o email não for um Gmail
        }
    }
}


// js para alterar senha
function abrirModalCodigoVerificacao() {
    document.getElementById('modalAlterarSenha').style.display = 'flex'; // Abre o modal de alteração de senha
}


function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none'; // Fecha o modal
}


function verificarCodigo() {
    // Lógica para verificar o código
    alert("Código verificado!");
}


function reenviarCodigo() {
    // Lógica para reenviar o código
    alert("Código reenviado!");
}


  function verificarCodigo() {
            // Lógica para verificar o código
            // Se o código estiver correto, abrir o modal para nova senha
            document.getElementById('modalNovaSenha').style.display = 'block';
        }


        function closeModal(modalId) {
            document.getElementById(modalId).style.display = 'none';
        }


        function salvarNovaSenha() {
            // Lógica para salvar a nova senha
            alert("Nova senha salva com sucesso!");
            closeModal('modalNovaSenha');
        }


    // js para verificar e alterar senha
function verificarCodigo() {
    // Lógica para verificar o código
   
    // Se o código estiver correto, esconder o modal antigo e abrir o novo
    closeModal('modalAlterarSenha');
    document.getElementById('modalNovaSenha').style.display = 'block';
}


function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}


function salvarNovaSenha() {
    // Lógica para salvar a nova senha
    alert("Nova senha salva com sucesso!");
    closeModal('modalNovaSenha');
}


// js deletar conta
function abrirModalDeletarConta() {
    document.getElementById('modalDeletarConta').style.display = 'block';
}


function verificarCodigoDeletar() {
    const codigo = document.getElementById('codigoVerificacao').value;
   
}


function reenviarCodigoDeletar() {
    alert("Código reenviado para seu email!");
}


    // logica para confirmar o deletar conta
    function abrirModalDeletarConta() {
        document.getElementById('modalDeletarConta').style.display = 'block';
    }
   
    function verificarCodigoDeletar() {
        const codigo = document.getElementById('codigoVerificacao').value;
        // Lógica para verificar o código. Aqui vamos assumir que o código é sempre aceito.
        // Se o código estiver correto, esconder o modal antigo e abrir o novo.
        closeModal('modalDeletarConta');
        document.getElementById('modalConfirmacaoDeletar').style.display = 'block';
    }
   
    function closeModal(modalId) {
        document.getElementById(modalId).style.display = 'none';
    }
   
    function deletarConta() {
        // Lógica para deletar a conta
        alert("Conta deletada com sucesso!");
        closeModal('modalConfirmacaoDeletar');
    }
   


// Função para abrir o modal de histórico de cadastro
function abrirModalHistoricoCadastro() {
    document.getElementById("modalHistoricoCadastro").style.display = "block";
}


function fecharModalHistoricoCadastro() {
    document.getElementById("modalHistoricoCadastro").style.display = "none";
}


// Para fechar o modal quando clicar fora do conteúdo do modal
window.onclick = function(event) {
    const modal = document.getElementById("modalHistoricoCadastro");
    if (event.target === modal) {
        fecharModalHistoricoCadastro();
    }
};


// carrossel texto
let currentIndex = 0;


function prosseguirCarrossel() {
    const carouselContent = document.getElementById("carouselContent");
    currentIndex += 3; // Avança 3 retângulos
    if (currentIndex >= 5) { // Se ultrapassar o total de retângulos, volta para o início
        currentIndex = 0;
    }
    updateCarousel(carouselContent);
}


function voltarCarrossel() {
    const carouselContent = document.getElementById("carouselContent");
    currentIndex -= 3; // Volta 3 retângulos
    if (currentIndex < 0) { // Se ficar negativo, volta para o primeiro retângulo
        currentIndex = 0; // Ajustado para garantir que não fique negativo
    }
    updateCarousel(carouselContent);
}


function updateCarousel(carouselContent) {
    const translateX = -currentIndex * (300 + 20); // Ajuste para o tamanho do retângulo (largura + margem)
    carouselContent.style.transform = `translateX(${translateX}px)`;
}


// carrossel para video (historico de cadastro)
let currentVideoIndex = 0;


function prosseguirVideoCarousel() {
    const carouselContent = document.getElementById("videoCarouselContent");
    currentVideoIndex += 1; // Avança 1 retângulo
    const totalItems = document.querySelectorAll('.info-box').length; // Total de retângulos
    if (currentVideoIndex >= totalItems) { // Se ultrapassar o total de retângulos, volta para o início
        currentVideoIndex = 0;
    }
    updateVideoCarousel(carouselContent);
}


function voltarVideoCarousel() {
    const carouselContent = document.getElementById("videoCarouselContent");
    currentVideoIndex -= 1; // Volta 1 retângulo
    const totalItems = document.querySelectorAll('.info-box').length; // Total de retângulos
    if (currentVideoIndex < 0) { // Se ficar negativo, vai para o final
        currentVideoIndex = totalItems - 1; // Começa do último retângulo
    }
    updateVideoCarousel(carouselContent);
}


function updateVideoCarousel(carouselContent) {
    const translateX = -currentVideoIndex * (300 + 20); // Ajuste para o tamanho do retângulo (largura + margem)
    carouselContent.style.transform = `translateX(${translateX}px)`;
}




// modal para flashcards
// Carrossel para Flashcards
let currentFlashcardIndex = 0;


function prosseguirCarrosselFlashcard() {
    const carouselContent = document.getElementById("carouselContentFlashcard");
    currentFlashcardIndex += 1; // Avança 1 retângulo
    const totalItems = document.querySelectorAll('.info-box').length; // Total de retângulos
    if (currentFlashcardIndex >= totalItems) { // Se ultrapassar o total de retângulos, volta para o início
        currentFlashcardIndex = 0;
    }
    updateFlashcardCarousel(carouselContent);
}


function voltarCarrosselFlashcard() {
    const carouselContent = document.getElementById("carouselContentFlashcard");
    currentFlashcardIndex -= 1; // Volta 1 retângulo
    const totalItems = document.querySelectorAll('.info-box').length; // Total de retângulos
    if (currentFlashcardIndex < 0) { // Se ficar negativo, vai para o final
        currentFlashcardIndex = totalItems - 1; // Começa do último retângulo
    }
    updateFlashcardCarousel(carouselContent);
}


function updateFlashcardCarousel(carouselContent) {
    const translateX = -currentFlashcardIndex * (200 + 20); // Ajuste para o tamanho do retângulo (largura + margem)
    carouselContent.style.transform = `translateX(${translateX}px)`;
}



    function abrirModalAlterarSenha() {
        // Exibe o modal alterando o estilo para 'flex'
        document.getElementById('modalAlterarSenha').style.display = 'flex';
    }

    function closeModal(modalId) {
        // Oculta o modal alterando o estilo para 'none'
        document.getElementById(modalId).style.display = 'none';
    }


