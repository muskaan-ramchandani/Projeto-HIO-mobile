<?php
$id = 1; // Defina o ID do professor a ser exibido
$apiUrl = 'http://192.168.0.81:8080/webCodesHIO/api.php' . $email;
$response = file_get_contents($apiUrl);
$professor = json_decode($response, true);
?><!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Perfil do Professor</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
    <link rel="stylesheet" href="TelaPerfilProfessor.css">
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
            <a href="#"><img src="Imagens_Mobile_HIO/iconeSeuPerfil.png" alt="Perfil" class="menu-icon-img"> Seu Perfil</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeChats.png" alt="Fórum" class="menu-icon-img"> Fórum</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeManual.png" alt="Manual" class="menu-icon-img"> Manual</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeConfiguracoes.png" alt="Configurações" class="menu-icon-img"> Configurações</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeSair.png" alt="Sair" class="menu-icon-img"> Sair</a>
        </div>
    </div>

    <div class="content">
        <div class="perfil-img">
            <img src="Imagens_Mobile_HIO/iconePerfilVazioRedonda.png" alt="Imagem">
        </div>

        <div class="profile-container">
            <h1 class="profile-name"><?php echo htmlspecialchars($professor['nome']); ?></h1>
            <div class="profile-username"><?php echo htmlspecialchars($professor['username']); ?></div>
            <div class="profile-email"><?php echo htmlspecialchars($professor['email']); ?></div>
            <hr class="profile-divider">
        </div>
    </div>

    <script src="TelaPerfilProfessor.js"></script>
</body>
</html>