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
    <link rel="stylesheet" href="TelaVideo.css">

        <?php
$id = isset($_GET['id']) ? htmlspecialchars($_GET['id']) : '';
$email = isset($_GET['email']) ? htmlspecialchars($_GET['email']) : '';
$sigla = isset($_GET['sigla']) ? htmlspecialchars($_GET['sigla']) : '';

?>

</head>
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
                <img src="iconeVideo.png" alt="Videos">
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
        <a href="TelaVideoHTML.php?sigla=<?php echo $sigla; ?>&id=<?php echo $id; ?>&email=<?php echo $email; ?>">
            <button class="custom-button">Tudo</button>
             </a>
             <a href="TelaSeusVideosHTML.php?sigla=<?php echo $sigla; ?>&id=<?php echo $id; ?>&email=<?php echo $email; ?>">
            <button class="custom-button">Suas recomendações</button>
             </a>
            <button class="custom-button" onclick="openModal()">Recomendar vídeo</button>
        </div>
    </div>
  
        <div class="info-box-container">
         
<?php include 'seus_videos.php'; ?>
            </div>





<!-- Modal para Recomendar Vídeo -->
<div class="modal" id="videoModal">
        <form action="simulandoCadastroVideo.php" method="post" class="modal-content" enctype="multipart/form-data">

        <span class="close" onclick="closeModal()">&times;</span>
        <h2 style="font-family: 'Open Sans', sans-serif; font-weight: bold; font-size: 20px; color: #835ad2; text-align: center;">
            Recomendar Vídeo
        </h2>
        
        <!-- Campo para o título do vídeo -->
        <label style="font-weight: bold; font-size: 15px; text-align: left; font-family: 'Open Sans';">Título</label>
        <input type="text" name="titulo" placeholder="Digite aqui o título do vídeo" class="input-field" style="margin-bottom: 20px;" required>

        <!-- Campo para o link do vídeo -->
        <label style="font-weight: bold; font-size: 15px; text-align: left; font-family: 'Open Sans';">Link do Vídeo</label>
        <input type="text" name="link" placeholder="Cole aqui o link do vídeo" class="input-field" required>
        <!-- Novo botão para adicionar capa -->
                <label style="font-weight: bold; font-size: 15px; text-align: left; font-family: 'Open Sans';">Adicionar Capa do Vídeo</label>
        <input type="file" name="capa" accept="image/*" class="input-field" style="margin-bottom: 20px;">


        <!-- Campos ocultos para idConteudoPertencente e profQuePostou -->
        <input type="hidden" name="idConteudoPertencente" value="<?php echo htmlspecialchars($_GET['id'] ?? ''); ?>">
        <input type="hidden" name="profQuePostou" value="<?php echo htmlspecialchars($_GET['email'] ?? ''); ?>">
                <input type="hidden" name="sigla" value="<?php echo htmlspecialchars($_GET['sigla'] ?? ''); ?>">

        <div class="button-group" style="margin-top: 20px;">
            <button type="submit" class="modal-button">Adicionar</button>
            <button type="button" class="modal-button cancel-button" onclick="closeModal()">Cancelar</button>
        </div>
    </form>
</div>







<script src="TelaVideo.js"></script>
       
</body>
</html>
