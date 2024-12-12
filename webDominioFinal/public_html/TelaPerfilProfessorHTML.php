<?php
session_start();

$servername = "127.0.0.1:3306";
$username = "u740411060_user";
$password = "OWYzZ?o2";
$dbname = "u740411060_hio";

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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
    <link rel="stylesheet" href="TelaPerfilProfessorCSS.css"> <!-- Link para o CSS -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script> <!-- Link para Chart.js -->
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



    <!-- Conteúdo do Perfil -->
    <div class="content">
        <!-- Imagem no canto superior direito -->
        <div class="perfil-img">
            <img src="iconePerfilVazioRedonda.png" alt="Imagem">
        </div>



<!-- 
        <div class="profile-container">
        <?php if ($fotoPerfil): ?>
            <img class="profile-photo" src="" alt="Foto de Perfil">
        <?php else: ?>
            <img class="profile-photo" src="" alt="Foto de Perfil Padrão">
        <?php endif; ?>
         -->
        
        <h1 class="profile-name"><?php echo $nomeCompleto; ?></h1>
        <div class="profile-username"><?php echo $nomeUsuario; ?></div>
        <div class="profile-email"><?php echo $emailProfessor; ?></div>
        <hr class="profile-divider">
        <div class="contribution-title">Sua contribuição:</div>
       
    </div>

       
        
        <div class="chart-container">
          <canvas id="myChart" width="400" height="200"></canvas>
        </div>
    </div>




    <script src="TelaPerfilProfessorJS.js"></script>
</body>
</html>
