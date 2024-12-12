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

    // Obtém o idConteudoPertencente da URL
    $idConteudoPertencente = isset($_GET['id']) ? (int)$_GET['id'] : 0;

    if ($idConteudoPertencente > 0) {
        // Consulta para buscar os vídeos, e-mails dos professores, links dos vídeos e capas
        $sql = "SELECT v.titulo, p.email, v.link, v.capa 
                FROM Video v 
                INNER JOIN Professor p ON v.profQuePostou = p.email 
                WHERE v.idConteudoPertencente = :idConteudo";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':idConteudo', $idConteudoPertencente, PDO::PARAM_INT);
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

                echo '      <div class="info-text">' . htmlspecialchars($video['email']) . '</div>
                            <button class="info-button" onclick="location.href=\'' . htmlspecialchars($video['link']) . '\'">Acessar</button>
                        </div>
                      </div>';
            }
        } else {
            echo '<p>Não há vídeos disponíveis para este conteúdo.</p>';
        }
    } else {
        echo '<p>ID do conteúdo inválido.</p>';
    }
} catch (PDOException $e) {
    // Mensagem de erro em caso de falha
    echo "Erro ao conectar ao banco de dados: " . $e->getMessage();
}
?>
