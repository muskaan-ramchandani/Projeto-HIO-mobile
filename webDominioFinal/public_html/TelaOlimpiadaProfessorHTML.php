<?php
// Conectar ao banco de dados
$dsn = 'mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';

// Definir estado inicial do menu (fechado)
if (!isset($_SESSION['menu_aberto'])) {
    $_SESSION['menu_aberto'] = false; // Menu começa fechado
}

// Alternar o estado do menu quando o parâmetro 'toggle_menu' for passado na URL
if (isset($_GET['toggle_menu'])) {
    $_SESSION['menu_aberto'] = !$_SESSION['menu_aberto']; // Alterna entre aberto/fechado
}

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
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
    <link rel="stylesheet" href="TelaOlimpiadaProfessor.css">

<!-- Bulma Carousel CSS -->

</head>
<body>
  <?php
 
  $email = isset($_GET['email']) ? $_GET['email'] : '';
?>
<div class="barra">
    <div class="logo-container">
       <img src="<?php echo htmlspecialchars($olimpiada['icone']); ?>.png" alt="<?php echo htmlspecialchars($olimpiada['nome']); ?>">
        <div class="text"><?php echo htmlspecialchars($olimpiada['nome']); ?> - <?php echo htmlspecialchars($sigla); ?></div>
    </div>
         <div class="container">
<a href="TelaEventoOlimpiadaHTML.php?sigla=<?= urlencode($sigla) ?>&email=<?= urlencode($email) ?>">
    <button class="botao-adicionais">Acessar eventos</button>
</a>

            <button class="botao-adicionais" onclick="toggleMenu()">Adicionar itens</button>
            <div id="menu" class="menu">
                <a href="#" class="menu-item" onclick="showModal()">Adicionar conteúdo</a>
                <a href="#" class="menu-item">Recomendar livro</a>
                <a href="#" class="menu-item">Adicionar prova anterior</a>
            </div>
            
            <a href="TelaInicialProfessorHTML.php?email=<?= urlencode($email) ?>">
    <button class="botao-adicionais"img src="imgInicioWeb.png">Voltar</button>
</a>

        </div>
       
       
</div>

<BR><BR><br><br>


   
       
<!-- Modal para adicionar conteúdo -->
<div id="modalAddContent" class="modal">
    <div class="modal-content">
        <span class="close" onclick="hideModal('modalAddContent')" aria-label="Fechar">&times;</span>
        <h2>Adicionar conteúdo</h2>
        <div class="modal-body">
            <form action="TelaAdicionarConteudo.php" method="POST">
                <label for="titulo">Título:</label>
                <input type="text" id="titulo" name="titulo" class="modal-input" required>

                <label for="subtitulo">Subtítulo:</label>
                <input type="text" id="subtitulo" name="subtitulo" class="modal-input" required>

                <input type="hidden" id="sigla" name="sigla" value="<?php echo htmlspecialchars($_GET['sigla'] ?? ''); ?>">
                <input type="hidden" id="email" name="email" value="<?php echo htmlspecialchars($_GET['email'] ?? ''); ?>">

                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Adicionar</button>
                    <button type="button" class="btn btn-secondary" onclick="hideModal('modalAddContent')">Cancelar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="bookModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="hideBookModal()">&times;</span>
        <h2>Recomendar Livro</h2>
        <div class="modal-body">
            <!-- Formulário para enviar os dados -->
            <form action="simulandoCadastroLivro.php" method="POST" enctype="multipart/form-data">
                <label for="isbn">ISBN:</label>
                <input type="text" id="isbn" name="isbn" class="modal-input" required>

                <label for="title">Título:</label>
                <input type="text" id="title" name="titulo" class="modal-input" required>

                <label for="author">Autor:</label>
                <input type="text" id="author" name="autor" class="modal-input" required>

                <label for="edition">Número da Edição:</label>
                <input type="text" id="edition" name="edicao" class="modal-input" required>

                <label for="publication-date">Data de Publicação:</label>
                <input type="date" id="publication-date" name="dataPublicacao" class="modal-input" required>

                <input type="hidden" id="sigla" name="sigla" value="<?php echo htmlspecialchars($_GET['sigla'] ?? ''); ?>">


                <!-- Botão para adicionar a capa do livro -->
                <div class="cover-upload">
                    <button type="button" class="btn btn-primary" onclick="document.getElementById('cover').click()">Adicionar Capa do Livro</button>
                    <input type="file" id="cover" name="capa" class="modal-input" accept="image/*" style="display: none;" required>
                    <span id="cover-file-name"></span>
                </div>
                <!-- Fim do formulário -->
        </div>
        <div class="modal-footer">
            <!-- Botões de ação do modal -->
            <button type="submit" class="btn btn-primary">Adicionar</button>
            <button type="button" class="btn btn-secondary" onclick="hideBookModal()">Cancelar</button>
        </div>
        </form> <!-- Fechar formulário -->
    </div>
</div>



        <!-- Modal para adicionar prova anterior -->
<!-- Modal para adicionar prova anterior -->
<div id="modalAddExam" class="modal">
    <div class="modal-content">
        <span class="close" onclick="hideModal('modalAddExam')">&times;</span>
     <form action="simulandoCadastroProvaAnterior.php" method="POST" enctype="multipart/form-data">
        <h2>Adicionar prova anterior</h2>
        <div class="modal-body">
            <div class="form-group">
                <label for="examYear">Ano da prova:</label>
                <input type="text" id="anoDaProva" name="anoDaProva" class="modal-input" required>
            </div>
            <div class="form-group">
                <label for="examPhase">Fase:</label>
                <input type="text" id="fase" name="fase" class="modal-input" required>
            </div>
            <div class="form-group">
                <label>Estado:</label>
                <div class="radio-group">
                    <label>
                        <input type="radio" name="estado" value="Respondida" required>
                        Respondida
                    </label>
                    <label>
                        <input type="radio" name="estado" value="Não Respondida" required>
                        Não Respondida
                    </label>
                </div>
            </div>
            <div class="file-upload">
                <input type="file" id="uploadPdf" name="arquivoPdf" style="display: none;" required />
                <button type="button" id="uploadPdfBtn">Selecionar PDF</button>
                <span id="pdf-file-name">Nenhum arquivo selecionado</span>
            </div>
            <!-- Botão para abrir o PDF -->
            <div id="pdf-viewer" style="display: none;">
                <button type="button" id="viewPdfBtn">Visualizar PDF</button>
            </div>
        </div>
        
        <input type="hidden" id="sigla" name="sigla" value="<?php echo htmlspecialchars($_GET['sigla'] ?? ''); ?>">
         <input type="hidden" id="email" name="email" value="<?php echo htmlspecialchars($_GET['email'] ?? ''); ?>">
        <div class="modal-footer">
                <button type="submit" class="btn btn-primary">Adicionar</button>
                <button type="button" class="btn btn-secondary" onclick="hideModal('modalAddExam')">Cancelar</button>
            </form>
        </div>
    </div>
</div>



 <h1 class="title has-text-centered">Conteúdos:</h1>

<?php include 'obter_conteudos.php'; 
    ?>

<br><br>
 <h1 class="title has-text-centered">Livros:</h1>
 <div class="info-box-container">
<?php include 'obter_livros.php'; ?>
</div>
<br><br>
 <h1 class="title has-text-centered">Provas:</h1>
 <div class="info-box-container">
<?php include 'obter_provas.php'; ?>

</div>
    <!--JavaScript-->
    <script>
        let currentContentIndex = 0;
        let currentBooksIndex = 0;
        let currentExamsIndex = 0;




        // Conteudos




    function prevContentSlide() {
    const items = document.querySelectorAll('#contentCarouselInner .carousel-item');
    const totalItems = items.length;
    const itemsToShow = 2; // Número de itens a serem exibidos por vez




    // Atualiza o índice do conteúdo atual
    if (currentContentIndex > 0) {
        currentContentIndex -= itemsToShow;
    } else {
        currentContentIndex = Math.max(totalItems - itemsToShow, 0);
    }
   
    // Atualiza a visualização do carrossel
    updateContentCarousel();
}




    function nextContentSlide() {
    const items = document.querySelectorAll('#contentCarouselInner .carousel-item');
    const totalItems = items.length;
    const itemsToShow = 2; // Número de itens a serem exibidos por vez




    // Atualiza o índice do conteúdo atual
    if (currentContentIndex < totalItems - itemsToShow) {
        currentContentIndex += itemsToShow;
    } else {
        currentContentIndex = 0;
    }
   
    // Atualiza a visualização do carrossel
    updateContentCarousel();
}




    function updateContentCarousel() {
    const carouselInner = document.getElementById('contentCarouselInner');
    const items = document.querySelectorAll('#contentCarouselInner .carousel-item');
    const itemWidth = items[0].offsetWidth;
    const gap = 10; // Espaço entre os itens
    const itemsToShow = 2; // Número de itens a serem exibidos por vez




    // Calcula o deslocamento para a posição correta
    const offset = -currentContentIndex * (itemWidth + gap);




    // Aplica o deslocamento ao carrossel
    carouselInner.style.transform = `translateX(${offset}px)`;
    carouselInner.style.transition = 'transform 0.3s ease'; // Transição suave
}








        //Recomendação de livros
        function prevBooksSlide() {
            const items = document.querySelectorAll('#booksCarousel .carousel-item');
            const totalItems = items.length;
            if (currentBooksIndex > 0) {
                currentBooksIndex -= 2;
            } else {
                currentBooksIndex = Math.max(totalItems - 2, 0);
            }
            updateBooksCarousel();
        }




        function nextBooksSlide() {
            const items = document.querySelectorAll('#booksCarousel .carousel-item');
            const totalItems = items.length;
            if (currentBooksIndex < totalItems - 2) {
                currentBooksIndex += 2;
            } else {
                currentBooksIndex = 0;
            }
            updateBooksCarousel();
        }




        function updateBooksCarousel() {
            const carouselInner = document.getElementById('booksCarouselInner');
            const itemWidth = document.querySelector('#booksCarousel .carousel-item').offsetWidth;
            const gap = 10; // Espaço entre os itens
            const offset = -currentBooksIndex * (itemWidth + gap);
            carouselInner.style.transform = `translateX(${offset}px)`;
        }




        //Provas anteriores




        function prevExamsSlide() {
            const items = document.querySelectorAll('#examsCarousel .carousel-item');
            const totalItems = items.length;
            if (currentExamsIndex > 0) {
                currentExamsIndex -= 2;
            } else {
                currentExamsIndex = Math.max(totalItems - 2, 0);
            }
            updateExamsCarousel();
        }




        function nextExamsSlide() {
            const items = document.querySelectorAll('#examsCarousel .carousel-item');
            const totalItems = items.length;
            if (currentExamsIndex < totalItems - 2) {
                currentExamsIndex += 2;
            } else {
                currentExamsIndex = 0;
            }
            updateExamsCarousel();
        }




        function updateExamsCarousel() {
            const carouselInner = document.getElementById('examsCarouselInner');
            const itemWidth = document.querySelector('#examsCarousel .carousel-item').offsetWidth;
            const gap = 10; // Espaço entre os itens
            const offset = -currentExamsIndex * (itemWidth + gap);
            carouselInner.style.transform = `translateX(${offset}px)`;
        }




        window.onload = () => {
            updateContentCarousel(); // Atualiza a posição inicial do carrossel de conteúdos
            updateBooksCarousel(); // Atualiza a posição inicial do carrossel de livros
            updateExamsCarousel(); // Atualiza a posição inicial do carrossel de provas
        };




       
        //Menu
    function toggleMenu() {
            var menu = document.getElementById('menu');
            menu.style.display = (menu.style.display === 'block') ? 'none' : 'block';
        }




    // Função para exibir o modal específico
    function showModal(modalId) {
    var modal = document.getElementById(modalId);
    modal.style.display = 'block';
}




    // Função para ocultar o modal específico
    function hideModal(modalId) {
    var modal = document.getElementById(modalId);
    modal.style.display = 'none';
}




    document.querySelectorAll('.menu-item').forEach(item => {
    item.addEventListener('click', function(event) {
        event.preventDefault();
        const action = event.target.textContent.trim();




        if (action === 'Adicionar conteúdo') {
            showModal('modalAddContent');
        } else if (action === 'Recomendar Livro') {
            showModal('bookModal');  
        } else if (action === 'Adicionar prova anterior') {
            showModal('modalAddExam');  
        }
    });
});




    // Fecha o modal se o usuário clicar fora do conteúdo
    window.onclick = function(event) {
    const modals = document.querySelectorAll('.modal');
    modals.forEach(modal => {
        if (event.target === modal) {
            hideModal(modal.id);
        }
    });
}
    // Função para mostrar o modal de recomendar livros
    function showBookModal() {
    var bookModal = document.getElementById('bookModal');
    bookModal.style.display = 'block';
}




    // Função para esconder o modal de recomendar livros
    function hideBookModal() {
    var bookModal = document.getElementById('bookModal');
    bookModal.style.display = 'none';
}




    // Função para atualizar o nome do arquivo selecionado
    document.getElementById('cover').addEventListener('change', function() {
    var fileName = document.getElementById('cover').files[0] ? document.getElementById('cover').files[0].name : 'Nenhum arquivo selecionado';
    document.getElementById('cover-file-name').textContent = fileName;
});




// Atualiza o evento de clique para "Recomendar Livro"
document.querySelectorAll('.menu-item')[1].onclick = showBookModal;








// Adicionar prova anterior
// Função para mostrar o modal
function showModal(modalId) {
    var modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'block';
    }
}




// Função para esconder o modal
function hideModal(modalId) {
    var modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'none';
    }
}




// Função para adicionar prova anterior
function addExam() {
    var year = document.getElementById('examYear').value;
    var phase = document.getElementById('examPhase').value;
    var state = document.querySelector('input[name="examState"]:checked').value;
    var pdfFile = document.getElementById('uploadPdf').files[0];
   
    // Lógica para adicionar prova anterior
    console.log('Adicionar prova anterior:', {year, phase, state, pdfFile});
    hideModal('modalAddExam');
}
// Atualiza o nome do arquivo PDF selecionado e exiba o botão de visualização
document.getElementById('uploadPdf').addEventListener('change', function() {
    var fileInput = document.getElementById('uploadPdf');
    var fileName = fileInput.files[0] ? fileInput.files[0].name : 'Nenhum arquivo selecionado';
    document.getElementById('pdf-file-name').textContent = fileName;




    // Exibir o botão de visualização se um arquivo foi selecionado
    var pdfViewer = document.getElementById('pdf-viewer');
    if (fileInput.files.length > 0) {
        pdfViewer.style.display = 'block';
    } else {
        pdfViewer.style.display = 'none';
    }
});




// Adicionar evento de clique para abrir o PDF
document.getElementById('viewPdfBtn').addEventListener('click', function() {
    var fileInput = document.getElementById('uploadPdf');
    if (fileInput.files.length > 0) {
        var fileUrl = URL.createObjectURL(fileInput.files[0]);
        window.open(fileUrl, '_blank'); // Abre o PDF em uma nova aba
    } else {
        alert('Nenhum arquivo PDF selecionado.');
    }
});




// Adicionar evento de clique para o botão de upload de PDF
document.getElementById('uploadPdfBtn').addEventListener('click', function() {
    document.getElementById('uploadPdf').click();
});
// Função para obter parâmetros da URL

 function scrollCarousel(direction) {
    const container = document.querySelector('.carousel-container');
    const scrollAmount = 300; // Quantidade de rolagem em pixels

    if (direction === 'left') {
        container.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
    } else {
        container.scrollBy({ left: scrollAmount, behavior: 'smooth' });
    }
}


    </script>
</body>
</html>



