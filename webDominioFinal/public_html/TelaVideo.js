

function openModal() {
    document.getElementById('videoModal').style.display = 'block';
}


function closeModal() {
    document.getElementById('videoModal').style.display = 'none';
}



// para add capa de video
// Função para mostrar a imagem selecionada
document.getElementById('videoCoverInput').addEventListener('change', function(event) {
    const file = event.target.files[0];
   
    // Se não houver arquivo, não faz nada
    if (!file) return;


    // Cria uma URL para o arquivo da imagem
    const reader = new FileReader();
   
    reader.onload = function(e) {
        // Cria uma imagem para exibir
        const image = new Image();
        image.src = e.target.result;
        image.style.maxWidth = '200px'; // Limita a largura da imagem


        // Remove qualquer imagem anterior
        const existingImage = document.getElementById('videoCoverPreview');
        if (existingImage) {
            existingImage.remove();
        }


        // Adiciona a nova imagem à visualização
        image.id = 'videoCoverPreview';
        document.getElementById('videoModal').appendChild(image);
    };


    // Lê o arquivo da imagem
    reader.readAsDataURL(file);
});


// Função para adicionar o vídeo (aqui você pode expandir para processar o link e a capa)
function addVideo() {
    const title = document.querySelector('.input-field[type="text"]:nth-of-type(1)').value;
    const videoLink = document.querySelector('.input-field[type="text"]:nth-of-type(2)').value;
    const videoCover = document.getElementById('videoCoverInput').files[0];


    // Aqui você pode adicionar a lógica para processar o título, link e a imagem da capa
    console.log('Título:', title);
    console.log('Link do Vídeo:', videoLink);
    console.log('Imagem de Capa:', videoCover);


    // Fechar o modal (se necessário)
    closeModal();
}


// Função para fechar o modal
function closeModal() {
    document.getElementById('videoModal').style.display = 'none';
}




