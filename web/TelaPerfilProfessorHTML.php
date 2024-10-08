<?php
session_start();

$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "hio";

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
} catch (PDOException $e) {
    die("Erro na conexão com o banco de dados: " . $e->getMessage());
}

// Recuperar o email da URL
if (isset($_GET['email'])) {
    $email = $_GET['email'];
    
    // Consultar os dados do professor
    $sql = "SELECT nomeCompleto, nomeUsuario, email, fotoPerfil FROM Professor WHERE email = :email";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':email', $email);
    $stmt->execute();
    $professor = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($professor) {
        // Exibir os dados do professor
        $nomeCompleto = htmlspecialchars($professor['nomeCompleto']);
        $nomeUsuario = htmlspecialchars($professor['nomeUsuario']);
        $emailProfessor = htmlspecialchars($professor['email']);
        
        // Verificar se existe uma foto de perfil
        if ($professor['fotoPerfil']) {
            $fotoPerfil = base64_encode($professor['fotoPerfil']);
        } else {
            $fotoPerfil = null; // Caso não haja foto, você pode colocar uma imagem padrão
        }
    } else {
        die("Professor não encontrado.");
    }
} else {
    die("Email não fornecido na URL.");
}
?>


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
        <?php if ($fotoPerfil): ?>
            <img class="profile-photo" src="data:image/jpeg;base64,<?php echo $fotoPerfil; ?>" alt="Foto de Perfil">
        <?php else: ?>
            <img class="profile-photo" src="Imagens_Mobile_HIO/defaultProfile.png" alt="Foto de Perfil Padrão">
        <?php endif; ?>
        
        <h1 class="profile-name"><?php echo $nomeCompleto; ?></h1>
        <div class="profile-username"><?php echo $nomeUsuario; ?></div>
        <div class="profile-email"><?php echo $emailProfessor; ?></div>
        <hr class="profile-divider">
        <div class="contribution-title">Sua contribuição nos últimos meses:</div>
        <!-- Aqui você pode colocar mais informações de contribuições, se necessário -->
    </div>

       
        <!-- Gráfico Interativo -->
        <div class="chart-container">
            <canvas id="myChart"></canvas>
        </div>
    </div>




    <script src="TelaPerfilProfessor.js"></script>
</body>
</html>
