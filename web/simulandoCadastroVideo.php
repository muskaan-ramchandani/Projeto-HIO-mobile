<?php
// Conectar ao banco de dados
$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "hio";

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "<p>Erro na conexão com o banco de dados: " . $e->getMessage() . "</p>";
    exit;
}

// Verificar se os dados foram enviados
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $titulo = $_POST['titulo'] ?? '';
    $link = $_POST['link'] ?? '';
    $idConteudoPertencente = $_POST['idConteudoPertencente'] ?? '';
    $profQuePostou = $_POST['profQuePostou'] ?? '';

    // Verificar se os campos obrigatórios estão preenchidos
    if (empty($titulo) || empty($link) || empty($idConteudoPertencente) || empty($profQuePostou)) {
        echo "<p>Erro: Todos os campos são obrigatórios.</p>";
        exit;
    }

    // Inserir os dados na tabela Video com capa como string vazia
    $sql = "INSERT INTO Video (titulo, link, profQuePostou, idConteudoPertencente, capa) 
            VALUES (:titulo, :link, :profQuePostou, :idConteudoPertencente, :capa)";
    $stmt = $pdo->prepare($sql);

    try {
        $stmt->execute([
            ':titulo' => $titulo,
            ':link' => $link,
            ':profQuePostou' => $profQuePostou,
            ':idConteudoPertencente' => $idConteudoPertencente,
            ':capa' => '' // Define capa como string vazia
        ]);
        echo "<p>Vídeo adicionado com sucesso!</p>";
    } catch (PDOException $e) {
        echo "<p>Erro ao adicionar vídeo: " . $e->getMessage() . "</p>";
    }
}

// Fechar conexão com o banco
$pdo = null;
?>
