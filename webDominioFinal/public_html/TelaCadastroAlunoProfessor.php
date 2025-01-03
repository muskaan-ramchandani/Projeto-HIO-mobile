<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="TelaCadastroAlunoProfessor.css">
</head>
<body>
    <div class="container">
        <div class="left-half">
            <div class="text white-text">Professor</div>
            <div class="professor-text">
                Cadastra livros, vídeos e flashcards, datas de inscrição e fases de alguma olimpíada e cria questionários, além de poder auxiliar um aluno através dos chats.
            </div>
            <!-- Formulário para redirecionar para a página de cadastro do professor -->
            <form action="TelaCadastroProfessorHTML.php" method="post">
                <button type="submit" class="btn professor-btn">Sou Professor</button>
            </form>
        </div>
        <div class="image-container">
            <img src="hipoAlegreDeOlhosFechados.png"Imagem do hipo">
        </div>
        <div class="right-half">
            <div class="text purple-text">Aluno</div>
            <div class="aluno-text">
                Possui acesso aos livros, vídeos e flashcards, pode responder questionários, além de poder pedir auxílio a um professor através dos chats.
            </div>
            <button class="btn aluno-btn" onclick="openModal()">Sou Aluno</button>
        </div>
    </div>

    <!-- Imagem do botão à esquerda -->
    <div class="button-image-container-left">
        <button class="button-image">
            <img src="btnVoltarEAvancarExercicioEsquerda.png" alt="Botão voltar esquerda">
        </button>
    </div>
   
    <!-- Imagem do botão à direita -->
    <div class="button-image-container-right">
        <button class="button-image">
            <img src="btnVoltarEAvancarExercicioDireita.png" alt="Botão voltar direita">
        </button>
    </div>

    <!-- Modal -->
    <div id="studentModal" class="modal">
        <div class="modal-content">
            <h2 class="modal-title">Olá aluno!</h2>
            <p class="modal-text">Para proporcionar uma melhor experiência, optamos por criar diferentes ambientes que se
            adequem para cada tipo de usuário. Para alunos, criamos um <b>app</b>, levando em consideração as
            ações que vocês podem realizar.</p>
            <p class="modal-text">Acesse o app através do seu <b>celular</b> procurando por: <span class="highlight">Helper in Olympics</span></p>
            <button class="modal-btn" onclick="closeModal()">Entendido! Acessarei o app</button>
        </div>
    </div>

    <script>
        function openModal() {
            document.getElementById('studentModal').style.display = 'block';
        }

        function closeModal() {
            document.getElementById('studentModal').style.display = 'none';
        }

        // Fechar o modal quando o usuário clicar fora da modal
        window.onclick = function(event) {
            if (event.target == document.getElementById('studentModal')) {
                closeModal();
            }
        }
    </script>
</body>
</html>
