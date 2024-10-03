
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="TelaInicialProfessor.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap"> <!-- Link para a fonte Open Sans -->
</head>
<body>
    <div class="menu">
        <div class="menu-icon" onclick="toggleMenu()">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
        </div>
        <span class="helper-text" id="helperText">Helper in Olympics</span>
        <div class="menu-content" id="menuContent">
                        <a href="TelaPerfilProfessorHTML.php"><img src="Imagens_Mobile_HIO/iconeSeuPerfil.png" alt="Perfil" class="menu-icon-img"> Seu Perfil</a>

            <a href="#"><img src="Imagens_Mobile_HIO/iconeChats.png" alt="Fórum" class="menu-icon-img"> Fórum</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeManual.png" alt="Manual" class="menu-icon-img"> Manual</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeConfiguracoes.png" alt="Configurações" class="menu-icon-img"> Configurações</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeSair.png" alt="Sair" class="menu-icon-img"> Sair</a>
        </div>
    </div>


    <div class="message-box">
        <div class="message-text">
            <div class="message-line">Olá mestres!</div>
            <div class="message-line">Está procurando por alguma olimpíada?</div>
        </div>
        <img src="Imagens_Mobile_HIO/hipoPrincipalComMedalha.png" alt="Hipo com medalha" class="message-box-img">
    </div>


    
        <?php include 'TelaInicialProfessor.php'; ?>
          
    </div>
    

    <div class="previous-questionnaires">
        Seus questionários:
    </div>
    <div class="questionnaires-carousel-container">
        <button id="prevQuestionnaireButton" class="carousel-button" onclick="prevQuestionnaireSlide()">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar">
        </button>
        <div class="questionnaires-carousel">
            <div class="questionnaires-carousel-inner" id="questionnairesCarouselInner">
                <div class="questionnaires-carousel-item">
                    <div class="questionnaire-item">
                        <div class="questionnaire-info">
                            <p><strong>OBF</strong> - Mecânica Clássica</p>
                            <p><strong>Repouso</strong></p>
                            <p>200 respostas</p>
                        </div>
                        <a href="#" class="visualize-button">Visualizar</a>
                    </div>
                </div>
                <div class="questionnaires-carousel-item">
                    <div class="questionnaire-item">
                        <div class="questionnaire-info">
                            <p><strong>OBF</strong> - Mecânica Clássica</p>
                            <p><strong>Gravidade</strong></p>
                            <p>150 respostas</p>
                        </div>
                        <a href="#" class="visualize-button">Visualizar</a>
                    </div>
                </div>
                <div class="questionnaires-carousel-item">
                    <div class="questionnaire-item">
                        <div class="questionnaire-info">
                            <p><strong>OBI</strong> - Algoritmos</p>
                            <p><strong>Complexidade</strong></p>
                            <p>180 respostas</p>
                        </div>
                        <a href="#" class="visualize-button">Visualizar</a>
                    </div>
                </div>
            </div>
        </div>
       
        <button id="nextQuestionnaireButton" class="carousel-button" onclick="nextQuestionnaireSlide()">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir">
        </button>
    </div>

    <script src="TelaInicialProfessor.js"></script>
</body>
</html>


