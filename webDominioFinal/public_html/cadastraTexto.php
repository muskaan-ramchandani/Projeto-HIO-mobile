<?php
// Conexão com o banco de dados
$servername = "127.0.0.1:3306";  // Servidor
$username = "u740411060_user";   // Usuário do banco
$password = "OWYzZ?o2";          // Senha
$dbname = "u740411060_hio";      // Nome do banco

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    die("Erro ao conectar ao banco de dados: " . $e->getMessage());
}

// Verifica se o formulário foi enviado
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['publicar'])) {
    // Sanitiza e valida os dados
    $titulo = trim($_POST['titulo']);
    $texto = trim($_POST['texto']);
    $email = trim($_POST['email']);
    $idConteudo = intval($_POST['idConteudo']);
    $sigla = trim($_POST['sigla']); // Assumindo que a sigla vem no POST também

    if (empty($titulo) || empty($texto) || empty($email)) {
        echo "<script>alert('Todos os campos são obrigatórios!');</script>";
        exit;
    }

    try {
        // Insere os dados na tabela `Texto`
        $sql = "INSERT INTO Texto (titulo, texto, profQuePostou, idConteudoPertencente) 
                VALUES (:titulo, :texto, :email, :idConteudo)";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([
            ':titulo' => $titulo,
            ':texto' => $texto,
            ':email' => $email,
            ':idConteudo' => $idConteudo,
        ]);

        // Redireciona para a TelaTextoHTML.php com os parâmetros sigla, id e email
        header("Location: TelaTextoHTML.php?sigla=" . urlencode($sigla) . "&id=" . urlencode($idConteudo) . "&email=" . urlencode($email));
        exit;

    } catch (PDOException $e) {
        echo "<script>alert('Erro ao cadastrar o texto: " . addslashes($e->getMessage()) . "');</script>";
    }
}
?>
