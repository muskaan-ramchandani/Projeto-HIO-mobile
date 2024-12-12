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
        // Consulta para buscar os questionários e os e-mails dos professores
        $sql = "SELECT q.id, q.titulo, q.profQuePostou 
                FROM Questionario q 
                WHERE q.idConteudoPertencente = :idConteudo";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':idConteudo', $idConteudoPertencente, PDO::PARAM_INT);
        $stmt->execute();

        // Obtém os questionários
        $questionarios = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // Gera os info-boxes dinamicamente
        if (!empty($questionarios)) {
            foreach ($questionarios as $questionario) {
                // Link dinâmico para redirecionar com o id e o email
               $link = "TelaQuestionarioAcessoHTML.php?id=" . htmlspecialchars($questionario['id']) 
        . "&email=" . urlencode($email) 
        . "&sigla=" . urlencode($sigla);


                echo '<div class="info-box">
                        <p class="title">' . htmlspecialchars($questionario['titulo']) . '</p>
                        <div class="info-box-content">
                            <img src="iconePerfilVazioRedonda.png" alt="Imagem do Professor">
                            <div class="info-text">' . htmlspecialchars($questionario['profQuePostou']) . '</div>
                            <button class="info-button" onclick="location.href=\'' . $link . '\'">Acessar</button>
                        </div>
                      </div>';
            }
        } else {
            echo '<p>Não há questionários disponíveis para este conteúdo.</p>';
        }
    } else {
        echo '<p>ID do conteúdo inválido.</p>';
    }
} catch (PDOException $e) {
    // Mensagem de erro em caso de falha
    echo "Erro ao conectar ao banco de dados: " . $e->getMessage();
}
?>
