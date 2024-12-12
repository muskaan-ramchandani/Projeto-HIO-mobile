<?php include 'conexao.php'; 

try {
    // Capturar a sigla da URL
    if (!isset($_GET['sigla'])) {
        throw new Exception('Sigla não fornecida.');
    }

    $sigla = $_GET['sigla'];

    // Preparar a consulta para buscar os livros
    $sql = "SELECT titulo, autor, isbn, edicao, dataPublicacao, capa 
            FROM Livro 
            WHERE siglaOlimpiadaPertencente = :sigla";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':sigla', $sigla);
    $stmt->execute();

    if ($stmt->rowCount() > 0) {
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $titulo = htmlspecialchars($row['titulo']);
            $autor = htmlspecialchars($row['autor']);
            $isbn = htmlspecialchars($row['isbn']);
            $edicao = htmlspecialchars($row['edicao']);
            $dataPublicacao = htmlspecialchars($row['dataPublicacao']);
            $capa = htmlspecialchars($row['capa']); // URL da capa do livro


        echo '<div class="info-box">
        <div class="info-box-content">
          
            <div class="info-box-text">
                <p class="subtitle">' . $titulo . '</p>
                <p class="title">Autor: ' . $autor . '</p>
                <p>Editora: ' . $isbn . '</p>
                <p>Edição: ' . $edicao . '</p>
                <p>Publicação: ' . $dataPublicacao . '</p>
            </div>
            <!-- Link para o Google com o título do livro -->
            <a href="https://www.google.com/search?q=' . urlencode($titulo) . '" 
               target="_blank" 
               class="info-button">
               Onde comprar?
            </a>
        </div>
      </div>';


        }
    } else {
        echo '<p>Nenhuma recomendação de livros até agora. Cadastre!.</p>';
    }
} catch (Exception $e) {
    echo '<p>Erro ao carregar os livros: ' . htmlspecialchars($e->getMessage()) . '</p>';
}
?>

<style>
/* Estilo para a imagem dentro da div */
.info-box-img {
    width: 120px; /* Aumenta a largura da imagem */
    height: 10px; /* Mantém a proporção da imagem */
    max-width: 100%; /* A imagem nunca ultrapassará os limites da div */
    object-fit: contain; /* Garante que a imagem se ajusta sem distorcer */
    margin-bottom: 15px; /* Espaçamento entre a imagem e o texto */
}
.info-box-content {
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>

