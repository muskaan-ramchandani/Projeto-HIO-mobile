<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
    <link rel="stylesheet" href="AdicionarFlashCard.css">
</head>
<body>
    <div class="barra">
        <div class="logo-container">
            <img src="Imagens_Mobile_HIO/iconeFlashcard.png" alt="Imagem flashcard">
            <h1 class="titulo">Adicionar flashcard</h1>
            <h2 class="subtitulo">- Fundamentos da Cinemática do Ponto Material</h2>
        </div>
    </div>
<br><br><br>

    <div class="main-content">
        <h1>Fundamentos da Cinemática do Ponto Material</h1>
        <div class="button-group">
            <button class="custom-button">Publicar</button>
            <button class="custom-button">Salvar rascunho</button>
            <button class="custom-button">Cancelar</button>
        </div>
    </div>


    <div class="quadrado">
        <div class="titulo-input" contenteditable="true">Insira aqui o título</div>
        <hr class="linha">
        <div class="texto-input" contenteditable="true">Insira seu texto aqui</div>


        <input type="file" id="fileInput" accept="image/*" style="display: none;">
        <button class="botao-adicionar">Adicionar Imagem</button>
        <div id="imagePreview" style="margin-top: 20px;"></div>
    </div>


    <script>
        const fileInput = document.getElementById('fileInput');
        const imagePreview = document.getElementById('imagePreview');
        const addButton = document.querySelector('.botao-adicionar');


        addButton.addEventListener('click', function() {
            fileInput.click();
        });


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