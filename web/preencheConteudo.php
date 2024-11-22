<?php
// Configuração da conexão com o banco de dados
$host = 'localhost';
$db = 'nome_do_banco';
$user = 'usuario';
$password = 'senha';

try {
    $pdo = new PDO("mysql:host=$host;dbname=$db", $user, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    die("Erro na conexão com o banco de dados: " . $e->getMessage());
}

// Obter o e-mail da URL
$email = $_GET['email'] ?? null;

if ($email) {
    // Consulta para buscar os conteúdos associados ao e-mail
    $stmt = $pdo->prepare("
        SELECT titulo, subtitulo, tipo, cor, olimpiada
        FROM conteudos
        WHERE email = :email
        ORDER BY tipo, titulo
    ");
    $stmt->bindParam(':email', $email, PDO::PARAM_STR);
    $stmt->execute();

    $conteudos = $stmt->fetchAll(PDO::FETCH_ASSOC);
} else {
    die("E-mail não fornecido na URL.");
}

// Agrupar os conteúdos por tipo
$carrosselTextos = [];
$carrosselVideos = [];
$carrosselFlashcards = [];

foreach ($conteudos as $conteudo) {
    switch ($conteudo['tipo']) {
        case 'texto':
            $carrosselTextos[] = $conteudo;
            break;
        case 'video':
            $carrosselVideos[] = $conteudo;
            break;
        case 'flashcard':
            $carrosselFlashcards[] = $conteudo;
            break;
    }
}
?>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Histórico de Cadastro</title>
    <link rel="stylesheet" href="styles.css"> <!-- Inclua seu CSS -->
</head>
<body>
    <!-- Modal de Histórico de Cadastro -->
    <div id="modalHistoricoCadastro">
        <h2>Histórico de Cadastro</h2>
        
        <!-- Carrossel de Textos -->
        <p>Texto:</p>
        <div class="carousel">
            <?php foreach ($carrosselTextos as $texto): ?>
                <div class="info-box">
                    <div class="info-conteudo-texto"><?= htmlspecialchars($texto['titulo']) ?></div>
                    <div class="circle-container">
                        <div class="circle <?= htmlspecialchars($texto['cor']) ?>"></div>
                        <span class="olympiad-name"><?= htmlspecialchars($texto['olimpiada']) ?></span>
                    </div>
                </div>
            <?php endforeach; ?>
        </div>

        <!-- Carrossel de Vídeos -->
        <p>Vídeo:</p>
        <div class="carousel">
            <?php foreach ($carrosselVideos as $video): ?>
                <div class="info-box">
                    <div class="info-conteudo-texto"><?= htmlspecialchars($video['titulo']) ?></div>
                    <img src="path/to/video-thumbnail.jpg" alt="<?= htmlspecialchars($video['titulo']) ?>" class="info-image">
                    <div class="circle-container">
                        <div class="circle <?= htmlspecialchars($video['cor']) ?>"></div>
                        <span class="olympiad-name"><?= htmlspecialchars($video['olimpiada']) ?></span>
                    </div>
                </div>
            <?php endforeach; ?>
        </div>

        <!-- Carrossel de Flashcards -->
        <p>Flashcard:</p>
        <div class="carousel">
            <?php foreach ($carrosselFlashcards as $flashcard): ?>
                <div class="info-box">
                    <div class="info-conteudo-texto"><?= htmlspecialchars($flashcard['titulo']) ?></div>
                    <div class="circle-container">
                        <div class="circle <?= htmlspecialchars($flashcard['cor']) ?>"></div>
                        <span class="olympiad-name"><?= htmlspecialchars($flashcard['olimpiada']) ?></span>
                    </div>
                </div>
            <?php endforeach; ?>
        </div>
    </div>
</body>
</html>
