
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
    <link rel="stylesheet" href="TelaAdicionarFlashCard.css">
</head>
<body>
    <div class="barra">
        <div class="logo-container">
            <img src="iconeFlashcard.png" alt="Imagem flashcard">
            <?php include 'processaConteudo.php'; ?>
            <h1 class="titulo">Adicionar flashcard -   </h1>
               <h1 class ="subtitulo"><?php echo isset($titulo) ?          $titulo : 'Título não encontrado'; ?></h1>
        </div>
    </div>


<BR> <BR>

    <div class="main-content">
        <h1><?php echo isset($titulo) ? $titulo : 'Título não encontrado'; ?></h1>
        <div class="button-group">
            <button form="flashcardForm" type="submit" class="custom-button">Publicar</button>
            <button type="button" class="custom-button" onclick="window.history.back()">Cancelar</button>
        </div>
    </div>

    <div class="quadrado">
        <form id="flashcardForm" action="simulandoCadastroFlashcard.php" method="POST" enctype="multipart/form-data">
            <!-- Campos ocultos para id e email -->
            <input type="hidden" name="idConteudoPertencente" value="<?php echo isset($_GET['id']) ? htmlspecialchars($_GET['id']) : ''; ?>">
            <input type="hidden" name="profQuePostou" value="<?php echo isset($_GET['email']) ? htmlspecialchars($_GET['email']) : ''; ?>">
             <input type="hidden" name="sigla" value="<?php echo isset($_GET['sigla']) ? htmlspecialchars($_GET['sigla']) : ''; ?>">


            <!-- Título -->
            <div class="titulo-input" contenteditable="true" onblur="document.getElementById('titulo').value = this.innerText">Insira aqui o título</div>
            <input type="hidden" name="titulo" id="titulo">

            <hr class="linha">
            <!-- Texto -->
            <div class="texto-input" contenteditable="true" onblur="document.getElementById('texto').value = this.innerText">Insira seu texto aqui</div>
            <input type="hidden" name="texto" id="texto">

            <!-- Upload de Imagem -->
            <input type="file" name="imagem" id="fileInput" accept="image/*" style="display: none;">
            <button type="button" class="botao-adicionar" onclick="document.getElementById('fileInput').click()">Adicionar Imagem</button>
            <div id="imagePreview" style="margin-top: 20px;"></div>

                    <!-- Preview da Imagem -->
        <div id="imagePreview" style="margin-top: 20px; text-align: center;">
            <img id="previewImage" src="#" alt="Pré-visualização da Imagem" style="display: none; max-width: 100%; height: auto;">
        </div>

        </form>
    </div>

    <script>
        const fileInput = document.getElementById('fileInput');
        const imagePreview = document.getElementById('imagePreview');

        fileInput.addEventListener('change', function() {
            const file = fileInput.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(event) {
                    imagePreview.innerHTML = `<img src="${event.target.result}" alt="Imagem selecionada" style="max-width: 100%; border: 1px solid #898989; border-radius: 5px;">`;
                }
                reader.readAsDataURL(file);
            }
        });
    </script>
</body>
</html>
