<?php
// Conexão com o banco de dados
$dsn = 'mysql:host=localhost;dbname=hio';
$user = 'root';
$password = 'root';

try {
    $pdo = new PDO($dsn, $user, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo 'Erro de conexão: ' . $e->getMessage();
    exit;
}

// Recebendo dados do formulário via POST
$nomeCompleto = $_POST['nomeCompleto'] ?? null;
$nomeUsuario = $_POST['nomeUsuario'] ?? null;
$emailAtual = $_POST['emailAtual'] ?? null;
$novoEmail = $_POST['novoEmail'] ?? null;

if ($nomeCompleto && $nomeUsuario && $emailAtual && $novoEmail) {
    // Preparando a query para atualizar os dados
    $query = "UPDATE usuarios SET nome_completo = :nomeCompleto, nome_usuario = :nomeUsuario, email = :novoEmail WHERE email = :emailAtual";
    
    $stmt = $pdo->prepare($query);
    $stmt->bindParam(':nomeCompleto', $nomeCompleto);
    $stmt->bindParam(':nomeUsuario', $nomeUsuario);
    $stmt->bindParam(':novoEmail', $novoEmail);
    $stmt->bindParam(':emailAtual', $emailAtual);
    
    // Executando a query
    if ($stmt->execute()) {
        echo json_encode(['status' => 'success', 'message' => 'Dados atualizados com sucesso!']);
    } else {
        echo json_encode(['status' => 'error', 'message' => 'Erro ao atualizar os dados.']);
    }
} else {
    echo json_encode(['status' => 'error', 'message' => 'Dados incompletos.']);
}
?>
