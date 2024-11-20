<?php
include('conexao.php'); // Inclua a conexão com o banco de dados

session_start(); // Inicie a sessão se não estiver iniciada

// Verifique se o email foi enviado ou está na sessão
if (isset($_POST['email']) && !empty($_POST['email'])) {
    $email = $_POST['email'];
} elseif (isset($_SESSION['email']) && !empty($_SESSION['email'])) {
    $email = $_SESSION['email'];
} else {
    die("Erro: Email não definido.");
}

// Verifique se o ano da prova foi enviado
if (isset($_POST['anoDaProva']) && !empty($_POST['anoDaProva'])) {
    $anoDaProva = $_POST['anoDaProva'];
} else {
    die("Erro: Ano da Prova não está definido.");
}

// Verifique se a fase foi enviada
if (isset($_POST['fase']) && !empty($_POST['fase'])) {
    $fase = $_POST['fase'];
} else {
    die("Erro: Fase não está definida.");
}

// Verifique se o estado foi enviado
$estado = isset($_POST['estado']) ? 1 : 0; // Convertendo checkbox para 1 (checked) ou 0 (unchecked)

// Verifique se a sigla foi enviada
if (isset($_POST['sigla']) && !empty($_POST['sigla'])) {
    $siglaOlimpiadaPertencente = $_POST['sigla'];
} else {
    die("Erro: Sigla não está definida.");
}

// Processar o arquivo PDF
if (isset($_FILES['arquivoPdf']) && $_FILES['arquivoPdf']['error'] == 0) {
    // Ler o conteúdo do arquivo
    $arquivoPdf = file_get_contents($_FILES['arquivoPdf']['tmp_name']);
} else {
    die("Erro: Arquivo PDF não foi enviado corretamente.");
}

// Inserir no banco de dados
try {
    $stmt = $pdo->prepare("INSERT INTO ProvaAnterior (anoDaProva, estado, fase, profQuePostou, arquivoPdf, siglaOlimpiadaPertencente) VALUES (:anoDaProva, :estado, :fase, :profQuePostou, :arquivoPdf, :siglaOlimpiadaPertencente)");

    $stmt->execute([
        ':anoDaProva' => $anoDaProva,
        ':estado' => $estado,
        ':fase' => $fase,
        ':profQuePostou' => $email,
        ':arquivoPdf' => $arquivoPdf,
        ':siglaOlimpiadaPertencente' => $siglaOlimpiadaPertencente
    ]);

    echo "Prova anterior adicionada com sucesso!";
} catch (PDOException $e) {
    die("Erro ao inserir dados: " . $e->getMessage());
}
?>
