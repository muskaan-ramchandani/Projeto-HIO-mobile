<?php
// Conectar ao banco de dados

$dsn = 'mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';


try {
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Capturar a sigla da URL
    if (isset($_GET['sigla'])) {
        $sigla = $_GET['sigla'];

        // Buscar os dados da olimpíada correspondente
        $sql = "SELECT nome, icone, cor FROM Olimpiada WHERE sigla = :sigla";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':sigla', $sigla);
        $stmt->execute();

        $olimpiada = $stmt->fetch(PDO::FETCH_ASSOC);
    } else {
        // Se não houver sigla na URL, redirecionar para a página inicial
        header('Location: TelaInicialProfessorHTML.php');
        exit();
    }
} catch (PDOException $e) {
    echo "Erro: " . $e->getMessage();
}
?>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
    <link rel="stylesheet" href="TelaFlashCard.css"> <!-- Link para o CSS -->
</head>
 <?php
$id = isset($_GET['id']) ? htmlspecialchars($_GET['id']) : '';
$email = isset($_GET['email']) ? htmlspecialchars($_GET['email']) : '';

?>

<body>
<div class="barra">
    <div class="logo-container">
        <img src="https://hio.lat/<?php echo htmlspecialchars($olimpiada['icone']); ?>.png" alt="<?php echo htmlspecialchars($olimpiada['nome']); ?>">
        <div class="text"><?php echo htmlspecialchars($olimpiada['nome']); ?> - <?php echo htmlspecialchars($sigla); ?></div>
    </div>
    <div class="button-container">
        <div class="button-item">
            <a href="TelaTextoHTML.php?sigla=<?php echo $sigla; ?>&id=<?php echo $id; ?>&email=<?php echo $email; ?>"">
            <img src="iconeTexto.png" alt="Textos">
            <div class="button-label">Textos</div>
            </a>
        </div>

        <div class="button-item">
           <a href="TelaVideoHTML.php?sigla=<?php echo $sigla; ?>&id=<?php echo $id; ?>&email=<?php echo $email; ?>">
            <img src="iconeVideo.png" alt="Vídeo">
            <div class="button-label">Vídeos</div>
            </a>
        </div>

        <div class="button-item">
            <a href="TelaFlashCardHTML.php?sigla=<?php echo $sigla; ?>&id=<?php echo $id; ?>&email=<?php echo $email; ?>">
            <img src="iconeFlashcard.png" alt="Flashcards">
            <div class="button-label">Flashcards</div>
            </a>
        </div>
        <div class="button-item">
        <a href="TelaQuestionarioHTML.php?sigla=<?php echo $sigla; ?>&id=<?php echo $id; ?>&email=<?php echo $email; ?>">
            <img src="iconeQuestionarios.png" alt="Questionários">
            <div class="button-label">Questionários</div>
            </a>
        </div>
        <div class="button-item">
        <a href="TelaOlimpiadaProfessorHTML.php?sigla=<?php echo $sigla; ?>&id=<?php echo $id; ?>&email=<?php echo $email; ?>">
            <img src="imgInicioWeb.png" alt="Voltar">
            <div class="button-label">Voltar</div>
             </a>
        </div>
    </div>
</div>
<?php include 'processaConteudo.php'; ?>
<div class="main-content">
    <h1><?php echo isset($titulo) ? $titulo : 'Título não encontrado'; ?></h1>
    <div class="button-group">
    <a href="TelaFlashCardHTML.php?sigla=<?php echo $sigla; ?>&id=<?php echo $id; ?>&email=<?php echo $email; ?>">
        <button class="custom-button">Tudo</button>
         </a>
          <a href="TelaSeusFlashCardHTML.php?sigla=<?php echo $sigla; ?>&id=<?php echo $id; ?>&email=<?php echo $email; ?>">
        <button class="custom-button">Seus flashcards</button>
 </a>
       
<a href="TelaAdicionarFlashCardHTML.php?id=<?php echo $id; ?>&email=<?php echo $email; ?>">
    <button class="custom-button">Adicionar flashcard</button>
</a>

    </div>
</div>






 <div class="info-box-container">
        
            <!-- Primeira linha de retângulos -->
   <?php include 'flashcards_conteudos.php'; ?>

  
    <?php if (!empty($flashcards)): ?>
        <?php foreach ($flashcards as $flashcard): ?>
            <!-- Info Box -->
            <div class="info-box">
                <p class="title"><?php echo htmlspecialchars($flashcard['titulo']); ?></p>
                <div class="info-box-content">
                    <img src="iconePerfilVazioRedonda.png">
                    <div class="info-text">Professor: <?php echo htmlspecialchars($flashcard['email']); ?></div>
                    <button class="info-button" onclick="openModal('modal-<?php echo $flashcard['id']; ?>')">Acessar</button>
                </div>
            </div>
        

            <!-- Modal do Flashcard -->
            <div id="modal-<?php echo htmlspecialchars($flashcard['id']); ?>" class="modal" style="display: none;">
                <div class="modal-content">
                    <span class="close" onclick="closeModal('modal-<?php echo htmlspecialchars($flashcard['id']); ?>')">&times;</span>
                    <h2><?php echo htmlspecialchars($flashcard['titulo']); ?></h2>
                    <p><?php echo htmlspecialchars($flashcard['texto']); ?></p>
                                    <?php if (!empty($flashcard['imagem'])): ?>
                    <img src="data:image/jpeg;base64,<?php echo base64_encode($flashcard['imagem']); ?>" alt="Imagem do Flashcard">
                <?php else: ?>
                    <p>Imagem não disponível.</p>
                <?php endif; ?>

                </div>
            </div>
        <?php endforeach; ?>
    <?php else: ?>
        <p>Não há flashcards disponíveis para este conteúdo.</p>
    <?php endif; ?>
</div>


</div>


    




    <script src="TelaFlashCard.js"></script>
</body>
</html>






