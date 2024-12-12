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
    $sigla = isset($_GET['sigla']) ? $_GET['sigla'] : '';  // Pega a sigla da URL

    if ($idConteudoPertencente > 0) {
        // Consulta para buscar os textos e os e-mails dos professores
        $sql = "SELECT t.id, t.titulo, t.texto, p.email 
                FROM Texto t 
                INNER JOIN Professor p ON t.profQuePostou = p.email 
                WHERE t.idConteudoPertencente = :idConteudo";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':idConteudo', $idConteudoPertencente, PDO::PARAM_INT);
        $stmt->execute();

        // Obtém os textos
        $textos = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // Gera os info-boxes dinamicamente
        if (!empty($textos)) {
            foreach ($textos as $texto) {
                echo '<div class="info-box">
                        <p class="title">' . htmlspecialchars($texto['titulo']) . '</p>
                        <div class="info-box-content">
                            <div class="info-text">Postado por: ' . htmlspecialchars($texto['email']) . '</div>
                            <a href="TelaAcessoTextoHTML.php?id=' . htmlspecialchars($texto['id']) . '&email=' . urlencode($texto['email']) . '&sigla=' . urlencode($sigla) . '" 
                               class="info-button">Acessar</a>
                        </div>
                      </div>';
            }
        } else {
            echo '<p>Não há textos disponíveis para este conteúdo.</p>';
        }
    } else {
        echo '<p>ID do conteúdo inválido.</p>';
    }
} catch (PDOException $e) {
    // Mensagem de erro em caso de falha
    echo "Erro ao conectar ao banco de dados: " . $e->getMessage();
}
?>

