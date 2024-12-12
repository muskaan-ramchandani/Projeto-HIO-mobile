<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bem Vindo</title>
    <link rel="stylesheet" href="TelaBemVindo.css">
    <script>
      
      document.addEventListener('DOMContentLoaded', function () {
            // Redireciona ao clicar no botão à esquerda
            document.getElementById('left-button').addEventListener('click', function () {
                window.location.href = 'index.php';
            });

            // Redireciona ao clicar no botão à direita
            document.getElementById('right-button').addEventListener('click', function () {
                window.location.href = 'TelaCadastroAlunoProfessor.php';
            });
        });
    </script>
</head>
<body>
    <div class="container">
        <!-- Container para o texto -->
        <div class="text-container">
            <h1 class="welcome">Bem vindo(a)</h1>
            <h1 class="hio">ao HIO!</h1>
            <p class="intro-text">
                Olá mestres e medalhistas! Meu nome é Hipo e sou o mascote do HIO (Helper in Olympics).
            </p>
            <p class="intro-text">
                Caso você não saiba, o site tem como objetivo permitir que professores voluntários ajudem
                alunos a estudarem para olimpíadas científicas, reunindo materiais para facilitar o estudo.
            </p>
        </div>

        <!-- Container para a imagem -->
        <div class="image-container">
            <img src="hipoMostrandoAlgoDaEsquerda.png" alt="hipo apontando para esquerda">
        </div>
    </div>

    <!-- Botão de imagem à esquerda -->
    <div class="button-image-container-left">
        <button id= "left-button" class="button-image">
            <img src="btnVoltarEAvancarExercicioEsquerda.png" alt="Botão voltar esquerda">
        </button>
    </div>
   
    <!-- Botão de imagem à direita -->
    <div class="button-image-container-right">
        <button id="right-button" class="button-image">
            <img src="btnVoltarEAvancarExercicioDireita.png" alt="Botão voltar direita">
        </button>
    </div>
</body>
</html>
