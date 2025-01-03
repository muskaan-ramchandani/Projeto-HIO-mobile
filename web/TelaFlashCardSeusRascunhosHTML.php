<!DOCTYPE html>  
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
    <link rel="stylesheet" href="TelaFlashcardSeusRascunhos.css">
</head>
<body>
    <div class="barra">
        <div class="logo-container">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/iconeFlashcard.png" alt="Imagem flashcard">
            <h1 class="titulo">Continuar Rascunho</h1>
            <h2 class="subtitulo">- Fundamentos da Cinemática do Ponto Material</h2>
        </div>
    </div>




    <div class="main-content">
        <h1>Fundamentos da Cinemática do Ponto Material</h1>
        <div class="button-group">
            <button class="custom-button">Publicar</button>
            <button class="custom-button">Salvar rascunho</button>
            <button class="custom-button">Cancelar</button>
        </div>
    </div>




    <div class="quadrado">
        <h3 class="titulo-quadrado"> Meu Título</h3>
        <hr class="linha">
        <p>Texto</p>
        <p>Texto</p>
        <p>Texto</p>
        <p>Texto</p>
        <p>Texto</p>
        <p>Texto</p>
        <p>Texto</p>
        <p>Texto</p>
        <p>Texto</p>




        <!-- Input para o arquivo -->
        <input type="file" id="fileInput" accept="image/*" style="display: none;">
        <button class="botao-adicionar">Adicionar Imagem</button>
        <div id="imagePreview" style="margin-top: 20px;"></div>
    </div>




    <!-- Botão Add Imagem JS -->
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


