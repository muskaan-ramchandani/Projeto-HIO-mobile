<?php
// Configurações de conexão com o banco de dados
$host = '127.0.0.1:3306';
$dbname = 'u740411060_hio';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';

try {
    // Conectando ao banco de dados
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Obtém o idConteudoPertencente da URL
    $idConteudoPertencente = isset($_GET['id']) ? (int)$_GET['id'] : 0;

    if ($idConteudoPertencente > 0) {
        // Consulta SQL para pegar o título, texto e o autor (e-mail do professor)
        $sql = "SELECT t.titulo, t.texto, p.email 
                FROM Texto t 
                INNER JOIN Professor p ON t.profQuePostou = p.email 
                WHERE t.id = :idConteudo";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':idConteudo', $idConteudoPertencente, PDO::PARAM_INT);
        $stmt->execute();

        // Recupera os dados do banco
        $texto = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($texto) {
            $titulo = htmlspecialchars($texto['titulo']);
            $textoConteudo = nl2br(htmlspecialchars($texto['texto']));  // Usamos nl2br para formatar quebras de linha
            $autor = htmlspecialchars($texto['email']);  // Ou você pode substituir o e-mail pelo nome do professor se preferir
        } else {
            echo "Texto não encontrado!";
            exit;
        }
    } else {
        echo "ID do conteúdo inválido!";
        exit;
    }

} catch (PDOException $e) {
    echo "Erro ao conectar ao banco de dados: " . $e->getMessage();
}
?>
