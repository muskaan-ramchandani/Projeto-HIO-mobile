<?php
include 'conexao.php';

try {
    // Verifica se o ID da prova foi fornecido
    if (!isset($_GET['id'])) {
        throw new Exception('ID da prova não fornecido.');
    }

    $id = $_GET['id'];

    // Prepara a consulta para buscar o arquivo PDF
    $sql = "SELECT arquivoPdf FROM ProvaAnterior WHERE id = :id";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':id', $id, PDO::PARAM_INT);
    $stmt->execute();

    // Verifica se o arquivo foi encontrado
    if ($stmt->rowCount() > 0) {
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        $arquivoPdf = $row['arquivoPdf'];

        // Configura o cabeçalho para forçar o download do arquivo
        header('Content-Type: application/pdf');
        header('Content-Disposition: attachment; filename="prova_' . $id . '.pdf"');
        echo $arquivoPdf;
    } else {
        echo 'Arquivo não encontrado.';
    }
} catch (Exception $e) {
    echo 'Erro: ' . htmlspecialchars($e->getMessage());
}
?>
