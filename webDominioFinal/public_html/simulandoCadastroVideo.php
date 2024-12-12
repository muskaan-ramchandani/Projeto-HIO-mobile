<?php
// Conectar ao banco de dados
$servername = "127.0.0.1:3306";
$username = "u740411060_user";
$password = "OWYzZ?o2";
$dbname = "u740411060_hio";

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "<script>alert('Erro na conexão com o banco de dados: " . $e->getMessage() . "');</script>";
    exit;
}

// Verificar se os dados foram enviados
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $titulo = $_POST['titulo'] ?? '';
    $link = $_POST['link'] ?? '';
    $idConteudoPertencente = $_POST['idConteudoPertencente'] ?? '';
    $profQuePostou = $_POST['profQuePostou'] ?? '';
    $sigla = $_POST['sigla'] ?? '';

    // Verificar se os campos obrigatórios estão preenchidos
    if (empty($titulo) || empty($link) || empty($idConteudoPertencente) || empty($profQuePostou)) {
        echo "<script>alert('Erro: Todos os campos são obrigatórios.');</script>";
        exit;
    }

    // Processar a capa do vídeo
    $capa = '';
    if (isset($_FILES['capa']) && $_FILES['capa']['error'] === 0) {
        $capa = file_get_contents($_FILES['capa']['tmp_name']);
    }

    // Inserir os dados no banco de dados
    $sql = "INSERT INTO Video (titulo, link, profQuePostou, idConteudoPertencente, capa) 
            VALUES (:titulo, :link, :profQuePostou, :idConteudoPertencente, :capa)";
    $stmt = $pdo->prepare($sql);

    try {
        $stmt->execute([
            ':titulo' => $titulo,
            ':link' => $link,
            ':profQuePostou' => $profQuePostou,
            ':idConteudoPertencente' => $idConteudoPertencente,
            ':capa' => $capa
        ]);

        // Redirecionar para a TelaVideoHTML.php com email, sigla e id
        echo "<script>
                alert('Vídeo adicionado com sucesso!');
                window.location.href = 'TelaVideoHTML.php?email=" . urlencode($profQuePostou) . "&sigla=" . urlencode($sigla) . "&id=" . urlencode($idConteudoPertencente) . "';
              </script>";
    } catch (PDOException $e) {
        echo "<script>alert('Erro ao adicionar vídeo: " . addslashes($e->getMessage()) . "');</script>";
    }
}

// Fechar conexão com o banco
$pdo = null;
?>
