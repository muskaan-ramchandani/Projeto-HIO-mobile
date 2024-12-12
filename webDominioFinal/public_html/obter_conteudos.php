<?php
// Configuração do banco de dados
$dsn = 'mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';
try {
    // Conectar ao banco de dados
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Verificar se o parâmetro "sigla" foi passado na URL
    $sigla = isset($_GET['sigla']) ? $_GET['sigla'] : '';
    $email = isset($_GET['email']) ? $_GET['email'] : '';

    if (!$sigla) {
        die('Sigla da olimpíada não fornecida!');
    }

    // Preparar a consulta para buscar conteúdos relacionados à olimpíada
    $sql = "SELECT id, titulo, subtitulo FROM Conteudo WHERE siglaOlimpiadaPertencente = :sigla";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':sigla', $sigla);
    $stmt->execute();

    // Função para determinar a cor com base na escolha aleatória
    function getCorAleatoria() {
        $cores = ['Rosa' => '#CB6CE6', 'Azul' => '#5271FF', 'Laranja' => '#FF914D', 'Ciano' => '#18B9CD'];
        return $cores[array_rand($cores)];
    }

    // Gerar o carrossel dinamicamente
    if ($stmt->rowCount() > 0) {
        echo '<div class="info-box-container">';
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $titulo = htmlspecialchars($row['titulo']);
            $subtitulo = htmlspecialchars($row['subtitulo']);
            $cor = getCorAleatoria();
            $id = $row['id'];

            echo '<div class="info-box-content" style="border-left: 3px solid ' . $cor . '; padding: 20px;">
                        <div class="info-box-text">
                    <p class="subtitle"><strong>' . $titulo . '</strong></p>
                    <p class="subtitle">' . $subtitulo . '</p>
                    <button class="button is-link" onclick="location.href=\'TelaTextoHTML.php?id=' . $id . '&email=' . urlencode($email) . '&sigla=' . urlencode($sigla) . '\'">Acessar</button>
                    </div>
                  </div>';
        }
        echo '</div>';
    } else {
        echo '<p>Nenhum conteúdo disponível até agora. Cadastre!.</p>';
    }

} catch (PDOException $e) {
    echo '<p>Erro ao conectar ao banco de dados: ' . htmlspecialchars($e->getMessage()) . '</p>';
}
?>
