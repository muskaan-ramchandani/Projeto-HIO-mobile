<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.9.4/css/bulma.min.css">
    <link rel="stylesheet" href="TelaForum.css">
</head>
<body>
    <div class="menu">
        <div class="menu-icon" onclick="toggleMenu()">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
        </div>
         <span class="forum-text">Fórum</span>
        <div class="menu-content" id="menuContent">
       
        <a href="TelaPerfilProfessorHTML.php?email=<?php echo isset($_GET['email']) ? htmlspecialchars($_GET['email']) : ''; ?>">
        <img src="iconeSeuPerfil.png" alt="Perfil" class="menu-icon-img"> Seu Perfil
    </a>
             <a href="TelaForumHTML.php?email=<?php echo isset($_GET['email']) ? htmlspecialchars($_GET['email']) : ''; ?>">
        <img src="iconeChats.png" alt="Fórum" class="menu-icon-img"> Fórum
    </a>
        <a href="Manual_do_usuário_HIO.pdf?email=<?php echo isset($_GET['email']) ? htmlspecialchars($_GET['email']) : ''; ?>">
        <img src="iconeManual.png" alt="Fórum" class="menu-icon-img"> Manual
    </a>  
        <a href="TelaConfiguracoesProfessorHTML.php?email=<?php echo isset($_GET['email']) ? htmlspecialchars($_GET['email']) : ''; ?>">
        <img src="iconeConfiguracoes.png" alt="Configurações" class="menu-icon-img"> Configurações
    </a>
            <a href="index.php"><img src="iconeSair.png" alt="Sair" class="menu-icon-img"> Sair</a>
        </div>
    </div>

    
    <img src="iconePerfilVazioRedonda.png" alt="Imagem perfil" class="top-right-image">
    <div class="barra-roxa"></div>
    <h2 class="perguntas-olimpiada">Perguntas por olimpíada:</h2>
    <div class="squares-container">
    </div>
    <div class="squares-container">
        
           <?php include 'perguntas_olimpiada.php'; ?>

    </div>
        <!-- Modal-equação reduzida -->




    <h2 class="perguntas-recentes">Perguntas recentes:</h2>

           <?php include 'TelaForum.php'; ?>





    <script src="TelaForum.js"></script>
</body>
</html>


