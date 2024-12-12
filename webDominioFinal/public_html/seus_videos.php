<?php
// Configurações de conexão com o banco de dados
$host = '127.0.0.1:3306';
$dbname = 'u740411060_hio';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';

try {
    // Conexão com o banco usando PDO
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Obtém o idConteudoPertencente e o e-mail da URL
    $idConteudoPertencente = isset($_GET['id']) ? (int)$_GET['id'] : 0;
    $email = isset($_GET['email']) ? $_GET['email'] : ''; // Pega o e-mail da URL

    if ($idConteudoPertencente > 0 && !empty($email)) {
        // Consulta para buscar os vídeos relacionados ao e-mail e ao idConteudoPertencente
        $sql = "SELECT v.titulo, p.email, v.link, v.capa 
                FROM Video v 
                INNER JOIN Professor p ON v.profQuePostou = p.email 
                WHERE v.idConteudoPertencente = :idConteudo AND p.email = :email";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':idConteudo', $idConteudoPertencente, PDO::PARAM_INT);
        $stmt->bindParam(':email', $email, PDO::PARAM_STR);
        $stmt->execute();

        // Obtém os vídeos
        $videos = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // Gera os info-boxes dinamicamente
        if (!empty($videos)) {
            foreach ($videos as $video) {
                echo '<div class="info-box">
                        <p class="title">' . htmlspecialchars($video['titulo']) . '</p>
                        <div class="info-box-content">';

                // Verificar se a capa está disponível
                if (!empty($video['capa'])) {
                    echo '<img src="data:image/jpeg;base64,' . base64_encode($video['capa']) . '" alt="Capa do Vídeo" style="width: 200px; height: auto; border-radius: 10px; margin-bottom: 10px;">';
                } else {
                    echo '<p>Capa não disponível.</p>';
                }

                echo '      <div class="info-text">Postado por: ' . htmlspecialchars($video['email']) . '</div>
                            <button class="info-button" onclick="location.href=\'' . htmlspecialchars($video['link']) . '\'">Acessar</button>
                        </div>
                      </div>';
            }
        } else {
            echo '<p>Não há vídeos disponíveis para este conteúdo e e-mail.</p>';
        }
    } else {
        echo '<p>ID do conteúdo ou e-mail inválido.</p>';
    }
} catch (PDOException $e) {
    // Mensagem de erro em caso de falha
    echo "Erro ao conectar ao banco de dados: " . $e->getMessage();
}
?>
