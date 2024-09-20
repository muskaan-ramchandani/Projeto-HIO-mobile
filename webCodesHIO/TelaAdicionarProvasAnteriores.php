<?php
header('Content-Type: application/json'); // Define o tipo de retorno como JSON

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    try {
        // Conexão com o banco de dados
        $pdo = new PDO('mysql:host=localhost;dbname=hio;charset=utf8', 'root', 'root');
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Captura os dados do formulário
        $anoDaProva = $_POST['anoDaProva'] ?? null;
        $fase = $_POST['fase'] ?? null;
        $estado = isset($_POST['estado']) ? (int)$_POST['estado'] : null;  // Converte para inteiro
        $emailProfessor = $_POST['emailProfessor'] ?? null;
        $siglaOlimpiadaPertencente = $_POST['siglaOlimpiadaPertencente'] ?? null;

        // Verifica se todos os campos obrigatórios estão preenchidos
        if (!$anoDaProva || !$fase || !$estado || !$emailProfessor || !$siglaOlimpiadaPertencente) {
            echo json_encode(['success' => false, 'error' => 'Campos obrigatórios não preenchidos.']);
            exit();
        }

        // Verifica se o arquivo PDF foi enviado
        if (isset($_FILES['arquivoPdf']) && $_FILES['arquivoPdf']['error'] === UPLOAD_ERR_OK) {
            $arquivoPdf = file_get_contents($_FILES['arquivoPdf']['tmp_name']);
        } else {
            echo json_encode(['success' => false, 'error' => 'Erro no upload do PDF.']);
            exit();
        }

        // Insere no banco de dados
        $sql = "INSERT INTO ProvaAnterior (anoDaProva, fase, estado, arquivoPdf, profQuePostou, siglaOlimpiadaPertencente)
                VALUES (:anoDaProva, :fase, :estado, :arquivoPdf, :emailProfessor, :siglaOlimpiadaPertencente)";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':anoDaProva', $anoDaProva);
        $stmt->bindParam(':fase', $fase);
        $stmt->bindParam(':estado', $estado, PDO::PARAM_INT);  
        $stmt->bindParam(':arquivoPdf', $arquivoPdf, PDO::PARAM_LOB);
        $stmt->bindParam(':emailProfessor', $emailProfessor);
        $stmt->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);

        // Executa a inserção
        if ($stmt->execute()) {
            echo json_encode(['success' => true, 'message' => 'Prova anterior cadastrada com sucesso!']);
        } else {
            echo json_encode(['success' => false, 'error' => 'Erro ao cadastrar a prova anterior.']);
        }
    } catch (Exception $e) {
        // Em caso de erro, captura a exceção e retorna a mensagem de erro
        echo json_encode(['success' => false, 'error' => 'Erro no servidor: ' . $e->getMessage()]);
    }
} else {
    echo json_encode(['success' => false, 'error' => 'Método de requisição inválido.']);
}
?>
