<?php
// Configurações de conexão com o banco de dados
$host = '127.0.0.1:3306';
$dbname = 'u740411060_hio';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Obtém o email do professor da URL
    $professorEmail = isset($_GET['email']) ? $_GET['email'] : '';
    if (!empty($professorEmail)) {
        // Consulta para buscar os flashcards relacionados ao email
        $sql = "SELECT f.id, f.imagem, f.titulo, f.texto, f.idConteudoPertencente 
                FROM Flashcard f 
                WHERE f.profQuePostou = :email";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':email', $professorEmail, PDO::PARAM_STR);
        $stmt->execute();

        // Obtém os flashcards
        $flashcards = $stmt->fetchAll(PDO::FETCH_ASSOC);
    } else {
        echo '<p>Email do professor inválido.</p>';
        exit;
    }
} catch (PDOException $e) {
    echo "Erro ao conectar ao banco de dados: " . $e->getMessage();
    exit;
}
?>
