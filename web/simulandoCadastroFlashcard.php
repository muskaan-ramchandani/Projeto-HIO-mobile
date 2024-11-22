<?php
// Conexão com o banco de dados
$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "hio";

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    die("Erro na conexão com o banco de dados: " . $e->getMessage());
}

// Verifica se o formulário foi enviado
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $idConteudoPertencente = $_POST['idConteudoPertencente'] ?? '';
    $profQuePostou = $_POST['profQuePostou'] ?? '';
    $titulo = $_POST['titulo'] ?? '';
    $texto = $_POST['texto'] ?? '';
    $imagem = null;

    // Processa a imagem se houver
    if (isset($_FILES['imagem']) && $_FILES['imagem']['error'] == 0) {
        $imagem = file_get_contents($_FILES['imagem']['tmp_name']);
    }

    // Validações básicas
    if (empty($idConteudoPertencente) || empty($profQuePostou) || empty($titulo) || empty($texto)) {
        die("Todos os campos são obrigatórios.");
    }

    // Prepara a inserção do flashcard no banco de dados
    $sql = "INSERT INTO Flashcard (idConteudoPertencente, profQuePostou, titulo, texto, imagem)
            VALUES (:idConteudoPertencente, :profQuePostou, :titulo, :texto, :imagem)";
    $stmt = $pdo->prepare($sql);

    $stmt->bindParam(':idConteudoPertencente', $idConteudoPertencente);
    $stmt->bindParam(':profQuePostou', $profQuePostou);
    $stmt->bindParam(':titulo', $titulo);
    $stmt->bindParam(':texto', $texto);
    $stmt->bindParam(':imagem', $imagem, PDO::PARAM_LOB);

    // Executa a inserção
    if ($stmt->execute()) {
        echo "<p>Flashcard adicionado com sucesso!</p>";
    } else {
        echo "<p>Erro ao adicionar o flashcard.</p>";
    }
} else {
    echo "<p>Erro: método de requisição inválido.</p>";
}

// Fecha a conexão
$pdo = null;
?>
