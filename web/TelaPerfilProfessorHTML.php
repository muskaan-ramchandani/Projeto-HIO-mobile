<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap"> <!-- Link para a fonte Open Sans -->
    <link rel="stylesheet" href="TelaPerfilProfessor.css"> <!-- Link para o CSS -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script> <!-- Link para Chart.js -->
</head>
<body>
    <!-- Menu -->
    <div class="menu">
        <div class="menu-icon" onclick="toggleMenu()">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
        </div>
        <span class="helper-text" id="helperText">Helper in Olympics</span>
        <div class="menu-content" id="menuContent">
            <a href="#"><img src="Imagens_Mobile_HIO/iconeSeuPerfil.png" alt="Perfil" class="menu-icon-img"> Seu Perfil</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeChats.png" alt="Fórum" class="menu-icon-img"> Fórum</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeManual.png" alt="Manual" class="menu-icon-img"> Manual</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeConfiguracoes.png" alt="Configurações" class="menu-icon-img"> Configurações</a>
            <a href="#"><img src="Imagens_Mobile_HIO/btnVoltarBRANCO.png" alt="Sair" class="menu-icon-img"> Sair</a>
        </div>
    </div>




    <!-- Conteúdo do Perfil -->
    <div class="content">
        <!-- Imagem no canto superior direito -->
        <div class="perfil-img">
            <img src="Imagens_Mobile_HIO/iconePerfilVazioRedonda.png" alt="Imagem">
        </div>




        <div class="profile-container">
            <h1 class="profile-name">Nome Completo do Professor</h1>
            <div class="profile-username">nomeUser1234</div>
            <div class="profile-email">emailDoProf@gmail.com</div>
            <hr class="profile-divider">
            <div class="contribution-title">Sua contribuição nos últimos meses:</div>
        </div>
       
        <!-- Gráfico Interativo -->
        <div class="chart-container">
            <canvas id="myChart"></canvas>
        </div>
    </div>




    <script src="TelaPerfilProfessor.js"></script>
</body>
</html>