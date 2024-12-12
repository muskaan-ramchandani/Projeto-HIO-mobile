<?php
$host = '127.0.0.1:3306';        // Host do banco de dados
$dbname = 'u740411060_hio';      // Nome do banco de dados
$user = 'u740411060_user';       // Usuário do banco de dados
$password = 'OWYzZ?o2';          // Senha do banco de dados

try {
    // Conexão com o banco de dados usando PDO
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $user, $password);
    // Configura o PDO para lançar exceções em caso de erro
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    // Exibe uma mensagem de erro caso a conexão falhe
    die("Erro na conexão com o banco de dados: " . $e->getMessage());
}

session_start(); // Inicie a sessão se não estiver iniciada

// Verifique se o email foi enviado ou está na sessão
if (isset($_POST['email']) && !empty($_POST['email'])) {
    $email = $_POST['email'];
} elseif (isset($_SESSION['email']) && !empty($_SESSION['email'])) {
    $email = $_SESSION['email'];
} else {
    die("<script>alert('Erro: Email não definido.'); window.history.back();</script>");
}

// Verifique se o ano da prova foi enviado
if (isset($_POST['anoDaProva']) && !empty($_POST['anoDaProva'])) {
    $anoDaProva = $_POST['anoDaProva'];
} else {
    die("<script>alert('Erro: Ano da Prova não está definido.'); window.history.back();</script>");
}

// Verifique se a fase foi enviada
if (isset($_POST['fase']) && !empty($_POST['fase'])) {
    $fase = $_POST['fase'];
} else {
    die("<script>alert('Erro: Fase não está definida.'); window.history.back();</script>");
}

// Verifique se o estado foi enviado
$estado = isset($_POST['estado']) ? 1 : 0; // Convertendo checkbox para 1 (checked) ou 0 (unchecked)

// Verifique se a sigla foi enviada
if (isset($_POST['sigla']) && !empty($_POST['sigla'])) {
    $siglaOlimpiadaPertencente = $_POST['sigla'];
} else {
    die("<script>alert('Erro: Sigla não está definida.'); window.history.back();</script>");
}

// Processar o arquivo PDF
if (isset($_FILES['arquivoPdf']) && $_FILES['arquivoPdf']['error'] == 0) {
    // Ler o conteúdo do arquivo
    $arquivoPdf = file_get_contents($_FILES['arquivoPdf']['tmp_name']);
} else {
    die("<script>alert('Erro: Arquivo PDF não foi enviado corretamente.'); window.history.back();</script>");
}

// Inserir no banco de dados
try {
    // Preparando a query para inserção
    $stmt = $pdo->prepare("INSERT INTO ProvaAnterior (anoDaProva, estado, fase, profQuePostou, arquivoPdf, siglaOlimpiadaPertencente) VALUES (:anoDaProva, :estado, :fase, :profQuePostou, :arquivoPdf, :siglaOlimpiadaPertencente)");

    // Executando a inserção com os parâmetros
    $stmt->execute([
        ':anoDaProva' => $anoDaProva,
        ':estado' => $estado,
        ':fase' => $fase,
        ':profQuePostou' => $email,
        ':arquivoPdf' => $arquivoPdf,
        ':siglaOlimpiadaPertencente' => $siglaOlimpiadaPertencente
    ]);

    // Mensagem de sucesso e redirecionamento
    echo "<script>alert('Prova anterior adicionada com sucesso!'); window.location.href = 'TelaOlimpiadaProfessorHTML.php?email=" . urlencode($email) . "&sigla=" . urlencode($siglaOlimpiadaPertencente) . "';</script>";
} catch (PDOException $e) {
    // Exibindo erro se ocorrer durante a inserção
    echo "<script>alert('Erro ao inserir dados: " . $e->getMessage() . "'); window.history.back();</script>";
}
?>
