<?php
// Configuração do banco de dados
$servername = "127.0.0.1:3306"; 
$username = "u740411060_user";        
$password = "OWYzZ?o2";            
$dbname = "u740411060_hio";     


// Conexão com o banco de dados
try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "<p>Erro de conexão: " . $e->getMessage() . "</p>";
    exit;
}

// Variável de busca
$searchTerm = isset($_GET['search']) ? $_GET['search'] : ''; // Obtém o termo de busca se presente

// Consulta SQL para buscar as olimpíadas
$olimpiadas = [];
if (!empty($searchTerm)) {
    $searchTerm = "%" . htmlspecialchars($searchTerm) . "%"; // Sanitiza o termo de busca
    $sql = "SELECT * FROM Olimpiada WHERE nome LIKE :searchTerm OR sigla LIKE :searchTerm LIMIT 5";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([':searchTerm' => $searchTerm]);
    $olimpiadas = $stmt->fetchAll(PDO::FETCH_ASSOC);
}
?>


<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
         <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
    <link rel="stylesheet" href="TelaInicialProfessorCSS.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap"> <!-- Link para a fonte Open Sans -->

</head>
<body>
<style>/* Estilizando o container de resultados */
#searchResults {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    padding: 20px;
    gap: 15px; /* Espaço entre os itens */
}

.info-box {
    background-color: #f9f9f9;
    border-radius: 8px;
    padding: 15px;
    width: calc(33% - 20px); /* Exibe três itens por linha */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

/* Estilo do texto do nome da olimpíada */
.info-conteudo-texto {
    font-size: 18px;
    font-weight: bold;
    color: #333;
    margin-bottom: 10px;
}

/* Estilizando o container do círculo */
.circle-container {
    display: flex;
    align-items: center;
    gap: 10px;
}

/* Estilo do círculo */
.circle {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background-color: #1e90ff; /* Cor azul */
}

/* Estilo do nome da olimpíada */
.olympiad-name {
    font-size: 16px;
    color: #333;
}

/* Efeito de hover no box */
.info-box:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

/* Responsividade: ajusta os itens em telas menores */
@media (max-width: 768px) {
    .info-box {
        width: calc(50% - 20px); /* Exibe dois itens por linha */
    }
}

@media (max-width: 480px) {
    .info-box {
        width: 100%; /* Exibe um item por linha */
    }
}
</style>
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
    
<div class="message-box">
    <div class="message-text">
        <div class="title">Olá mestres!</div>
        <div class="subtitle">Está procurando por alguma olimpíada?</div>
    </div>
    <img src="hipoPrincipalComMedalha.png" alt="Hipo com medalha" class="message-box-img">
    <form method="GET" action="">
    <?php
// Verifica se o parâmetro 'email' está presente na URL
$email = isset($_GET['email']) ? $_GET['email'] : ''; // 
?>
        <input type="text" name="search" class="search-input" placeholder="Procure pelo nome ou pela sigla:" value="<?php echo htmlspecialchars($searchTerm); ?>">
           <input type="hidden" name="email" value="<?php echo htmlspecialchars($email); ?>">

  <button type="submit" class="btn-buscar">Buscar
</button>


    </form>
</div>

<!-- Div onde os resultados serão exibidos -->
<!-- Div onde os resultados serão exibidos -->
<div id="searchResults">
<?php
// Exibe os resultados da pesquisa
if (!empty($olimpiadas)) {
    foreach ($olimpiadas as $olimpiada) {
        
        $sigla = htmlspecialchars($olimpiada['sigla']);
        $email = htmlspecialchars($email);
        $link = "TelaOlimpiadaProfessorHTML.php?sigla=" . urlencode($sigla) . "&email=" . urlencode($email);

        echo '<a href="' . $link . '" class="info-box" style="text-decoration: none; color: inherit;">';
        echo '  <div class="info-conteudo-texto">' . htmlspecialchars($olimpiada['nome']) . '</div>';
        echo '  <div class="circle-container">';
        echo '    <div class="circle blue"></div>';
        echo '    <span class="olympiad-name">' . $sigla . '</span>';
        echo '  </div>';
        echo '</a>';
    }
} else {
    echo '<p>Nenhuma olimpíada encontrada.</p>';
}
?>

</div>




    
        <?php include 'TelaInicialProfessor.php'; ?>


    

    
    <script src="TelaInicialProfessorJS.js"></script>
    
    <style>
    .title {
    color: white;
}

.subtitle {
    color: white;
}

    </style>
</body>
</html>


