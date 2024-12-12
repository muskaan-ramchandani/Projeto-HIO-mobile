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
        // Consulta para buscar os flashcards relacionados ao idConteudoPertencente e ao e-mail
        $sql = "SELECT f.id, f.titulo, f.texto, f.imagem, p.email 
                FROM Flashcard f 
                INNER JOIN Professor p ON f.profQuePostou = p.email 
                WHERE f.idConteudoPertencente = :idConteudo AND p.email = :email";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':idConteudo', $idConteudoPertencente, PDO::PARAM_INT);
        $stmt->bindParam(':email', $email, PDO::PARAM_STR);
        $stmt->execute();

        // Obtém os flashcards
        $flashcards = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // Verifica se há flashcards disponíveis
        $flashcardDisponivel = !empty($flashcards);
    } else {
        $flashcards = [];
        $flashcardDisponivel = false;
    }
} catch (PDOException $e) {
    echo "Erro ao conectar ao banco de dados: " . $e->getMessage();
    exit;
}
?>
<style>
/* Classe para redimensionar a imagem do flashcard no modal */
.modal img {
    max-width: 100%; /* Ajusta a largura da imagem para 100% da largura do modal */
    max-height: 400px; /* Ajusta a altura máxima da imagem para 400px */
    object-fit: contain; /* Mantém a proporção da imagem ao redimensioná-la */
}

/* Ajustes para o container dos info-box */
.info-box-container {
    display: flex; /* Alinha os info-box horizontalmente */
    justify-content: space-between; /* Espaça igualmente os info-box */
    gap: 15px; /* Espaço entre os info-box */
    flex-wrap: wrap; /* Permite quebra de linha se não houver espaço suficiente */
    margin-top: 60px; /* Espaço acima do contêiner para separar das seções anteriores */
}
</style>
