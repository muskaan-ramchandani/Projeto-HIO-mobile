<?php
// Conexão com o banco de dados
$servername = "127.0.0.1:3306";
$username = "u740411060_user";
$password = "OWYzZ?o2";
$dbname = "u740411060_hio";

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

    // Verifica se uma imagem foi enviada e processa
    if (isset($_FILES['imagem']) && $_FILES['imagem']['error'] == 0) {
        // Lê o conteúdo da imagem
        $imagem = file_get_contents($_FILES['imagem']['tmp_name']);
    } else {
        // Se não houver imagem, usa uma imagem padrão (por exemplo, um placeholder)
        $imagem = file_get_contents('caminho/para/imagem/placeholder.jpg'); // Substitua pelo caminho da sua imagem padrão
    }

    // Validações básicas
    if (empty($idConteudoPertencente) || empty($profQuePostou) || empty($titulo) || empty($texto)) {
        echo "<script>alert('Todos os campos são obrigatórios!');</script>";
        exit;
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
        echo "<script>alert('Flashcard adicionado com sucesso!');</script>";
        // Defina a variável $sigla (supondo que a sigla seja uma variável que você obtém ou define)
        // Exemplo:
        $sigla = "SUA_SIGLA_AQUI"; // Alterar conforme necessário

        // Redireciona para TelaFlashCardHTML.php passando sigla, email e id
        echo "<script>window.location.href = 'TelaFlashCardHTML.php?sigla=" . urlencode($sigla) . "&email=" . urlencode($profQuePostou) . "&id=" . $idConteudoPertencente . "';</script>";
        exit;
    } else {
        echo "<script>alert('Erro ao adicionar o flashcard.');</script>";
    }
} else {
    echo "<script>alert('Erro: método de requisição inválido.');</script>";
}

// Fecha a conexão
$pdo = null;
?>
