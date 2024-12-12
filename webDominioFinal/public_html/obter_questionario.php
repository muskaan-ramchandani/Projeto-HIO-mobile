<?php
// Função para gerar uma cor hexadecimal aleatória
function getCorAleatoria() {
    $cores = ['Rosa' => '#CB6CE6', 'Azul' => '#5271FF', 'Laranja' => '#FF914D', 'Ciano' => '#18B9CD'];
    $chaveAleatoria = array_rand($cores); // Obtém uma chave aleatória
    return $cores[$chaveAleatoria]; // Retorna a cor correspondente à chave aleatória
}


// Configurações de conexão com o banco de dados
$host = '127.0.0.1:3306';
$dbname = 'u740411060_hio';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';

try {
    // Conexão com o banco usando PDO
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Obtém o email da URL
    $email = isset($_GET['email']) ? $_GET['email'] : '';

    if (!empty($email)) {
        // Consulta para buscar os questionários baseados no email do professor
        $sql = "SELECT q.id, q.titulo, q.profQuePostou 
                FROM Questionario q 
                WHERE q.profQuePostou = :email";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':email', $email, PDO::PARAM_STR);
        $stmt->execute();

        // Obtém os questionários
        $questionarios = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // Gera os info-boxes dinamicamente
        if (!empty($questionarios)) {
            echo '<div class="info-box-container">';
            foreach ($questionarios as $questionario) {
                // Gera uma cor aleatória para cada questionário
                $corAleatoria = gerarCorAleatoria();
                // Link dinâmico para redirecionar para a tela de acesso ao questionário
                $link = "TelaQuestionarioAcessoHTML.php?id=" . htmlspecialchars($questionario['id']) 
                    . "&email=" . urlencode($email);
                
                echo '<div class="info-box" style="background-color: ' . $corAleatoria . ';">
                        <p class="title">' . htmlspecialchars($questionario['titulo']) . '</p>
                        <div class="info-box-content">
                            <img src="path/to/default/image.png" alt="Imagem do Professor">
                            <div class="info-text">' . htmlspecialchars($questionario['profQuePostou']) . '</div>
                            <button class="info-button" onclick="location.href=\'' . $link . '\'">Acessar</button>
                        </div>
                      </div>';
            }
            echo '</div>';
        } else {
            echo '<p>Não há questionários disponíveis para este professor.</p>';
        }
    } else {
        echo '<p>Email não fornecido ou inválido.</p>';
    }
} catch (PDOException $e) {
    // Mensagem de erro em caso de falha
    echo "Erro ao conectar ao banco de dados: " . $e->getMessage();
}
?>
<style>
    
    
    /* Container para os info-boxes */
.info-box-container {
    display: flex;
    overflow-x: auto; /* Permite rolar horizontalmente */
    gap: 20px; /* Espaçamento entre os boxes */
    padding: 20px 0; /* Espaçamento nas extremidades */
    align-items: center;
}

/* Estilo de cada info-box */
.info-box {
    width: 250px; /* Tamanho fixo para os info-boxes */
    border: 1px solid #ccc; /* Borda */
    border-radius: 10px; /* Cantos arredondados */
    padding: 15px;
    display: flex;
    flex-direction: column;
    align-items: center;
    box-sizing: border-box; /* Inclui padding e borda no cálculo do tamanho */
}

/* Estilo do título dentro de cada info-box */
.info-box .title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
    text-align: center;
}

/* Estilo da imagem do professor */
.info-box img {
    width: 80px;
    height: 80px;
    border-radius: 50%; /* Imagem circular */
    margin-bottom: 10px;
}

/* Estilo do texto do professor */
.info-box .info-text {
    font-size: 14px;
    color: #333;
    margin-bottom: 15px;
    text-align: center;
}

/* Estilo do botão de acessar */
.info-box .info-button {
    padding: 10px 20px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    text-align: center;
}

.info-box .info-button:hover {
    background-color: #45a049;
}

</style>