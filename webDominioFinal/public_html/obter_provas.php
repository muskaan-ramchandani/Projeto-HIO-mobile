<?php 
// Ativar exibição de erros para depuração
ini_set('display_errors', 1);
error_reporting(E_ALL);

include 'conexao.php';

try {
    // Capturar a sigla da URL
    if (!isset($_GET['sigla'])) {
        throw new Exception('Sigla não fornecida.');
    }

    $sigla = $_GET['sigla'];

    // Preparar a consulta para buscar as provas
    $sql = "SELECT id, anoDaProva, estado, fase, profQuePostou, arquivoPdf 
            FROM ProvaAnterior 
            WHERE siglaOlimpiadaPertencente = :sigla";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':sigla', $sigla);
    $stmt->execute();
    
   
    // Chama a função e armazena a cor aleatória
    $cor = getCorAleatoria();

    if ($stmt->rowCount() > 0) {
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $id = htmlspecialchars($row['id']);
            $anoDaProva = htmlspecialchars($row['anoDaProva']);
            $estado = htmlspecialchars($row['estado']);
            $fase = htmlspecialchars($row['fase']);
            $profQuePostou = htmlspecialchars($row['profQuePostou']);
            $arquivoPdf = htmlspecialchars($row['arquivoPdf']); // Caminho do arquivo PDF

            // Exibe as informações sobre a prova
            echo '<div class="info-box" style="border-left: 3px solid ' . $cor . ';">
                    <div class="info-box-content">
                        <p class="title">Ano da Prova: ' . $anoDaProva . '</p>
                        <p class="subtitle">Estado: ' . $estado . '</p>
                        <p>Fase: ' . $fase . '</p>
                        <p>Professor: ' . $profQuePostou . '</p>
                        
                        <!-- Link para download do PDF -->
                        <a href="baixar_prova.php?id=' . $id . '" class="info-button">
                            Acessar Prova
                        </a>
                    </div>
                </div>';
        }
    } else {
        echo '<p>Nenhuma prova disponível até agora. Cadastre!</p>';
    }
} catch (Exception $e) {
    echo '<p>Erro ao carregar as provas: ' . htmlspecialchars($e->getMessage()) . '</p>';
}
?>
