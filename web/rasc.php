
<?php
// Conectar ao banco de dados
$dsn = 'mysql:host=sql207.infinityfree.com;dbname=if0_37755624_hio;charset=utf8';
$username = 'if0_37755624';
$password = '1k31AyGMaqyz7';

session_start();  // Iniciar sessão

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
    <link rel="stylesheet" href="rasc.css">
</head>
<body>
  
<div class="barra">
    <div class="logo-container">
        <img src="http://hio.ct.ws/web/<?php echo htmlspecialchars($olimpiada['icone']); ?>.png" alt="<?php echo htmlspecialchars($olimpiada['nome']); ?>">
        <div class="text"><?php echo htmlspecialchars($olimpiada['nome']); ?> - <?php echo htmlspecialchars($sigla); ?></div>
    </div>
</div>




        <div class="container">
            <a href="file:///C:/Users/Muskaan%20Ramchandani/Projeto-HIO-mobile/webCodesHIO/TelaEventosOlimpiada.html">
                <button class="botao-adicionais">Acessar eventos</button>
            </a>
            <button class="botao-adicionais" onclick="toggleMenu()">Adicionar itens</button>
            <div id="menu" class="menu">
                <a href="#" class="menu-item" onclick="showModal()">Adicionar conteúdo</a>
                <a href="#" class="menu-item">Recomendar livro</a>
                <a href="#" class="menu-item">Adicionar prova anterior</a>
            </div>
        </div>
       
       
       
       
   
        <!-- Modal para adicionar conteúdo -->
    <div id="modalAddContent" class="modal">
    <div class="modal-content">
        <span class="close" onclick="hideModal('modalAddContent')">&times;</span>
        <h2>Adicionar conteúdo</h2>
        <div class="modal-body">
            <label for="titleAddContent">Título:</label>
            <input type="text" id="titleAddContent" class="modal-input">
            <label for="subtitleAddContent">Subtítulo:</label>
            <input type="text" id="subtitleAddContent" class="modal-input">
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" onclick="addContent()">Adicionar</button>
            <button class="btn btn-secondary" onclick="hideModal('modalAddContent')">Cancelar</button>
        </div>
    </div>
</div>




        <!-- Modal para recomendar livros-->
    <div id="bookModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="hideBookModal()">&times;</span>
        <h2>Recomendar Livro</h2>
        <div class="modal-body">
            <label for="isbn">ISBN:</label>
            <input type="text" id="isbn" class="modal-input">




            <label for="title">Título:</label>
            <input type="text" id="title" class="modal-input">




            <label for="author">Autor:</label>
            <input type="text" id="author" class="modal-input">




            <label for="edition">Número da Edição:</label>
            <input type="text" id="edition" class="modal-input">




            <label for="publication-date">Data de Publicação:</label>
            <input type="text" id="publication-date" class="modal-input">




            <!-- Botão para adicionar a capa do livro -->
            <div class="cover-upload">
                <button class="btn btn-primary" onclick="document.getElementById('cover').click()">Adicionar Capa do Livro</button>
                <input type="file" id="cover" class="modal-input" accept="image/*" style="display: none;">
                <span id="cover-file-name"></span>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" onclick="addBook()">Adicionar</button>
            <button class="btn btn-secondary" onclick="hideBookModal()">Cancelar</button>
        </div>
    </div>
</div>




        <!-- Modal para adicionar prova anterior -->
    <div id="modalAddExam" class="modal">
    <div class="modal-content">
        <span class="close" onclick="hideModal('modalAddExam')">&times;</span>
        <h2>Adicionar prova anterior</h2>
        <div class="modal-body">
            <div class="form-group">
                <label for="examYear">Ano da prova:</label>
                <input type="text" id="examYear" class="modal-input">
            </div>
            <div class="form-group">
                <label for="examPhase">Fase:</label>
                <input type="text" id="examPhase" class="modal-input">
            </div>
            <div class="form-group">
                <label>Estado:</label>
                <div class="radio-group">
                    <label>
                        <input type="radio" name="examState" value="Respondida">
                        Respondida
                    </label>
                    <label>
                        <input type="radio" name="examState" value="Não Respondida">
                        Não Respondida
                    </label>
                </div>
            </div>
            <div class="file-upload">
                <input type="file" id="uploadPdf" style="display: none;" />
                <button id="uploadPdfBtn">Selecionar PDF</button>
                <span id="pdf-file-name">Nenhum arquivo selecionado</span>
            </div>
            <!-- Botão para abrir o PDF -->
            <div id="pdf-viewer" style="display: none;">
                <button id="viewPdfBtn">Visualizar PDF</button>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" onclick="addExam()">Adicionar</button>
            <button class="btn btn-secondary" onclick="hideModal('modalAddExam')">Cancelar</button>
        </div>
    </div>
</div>






        <div class="botao-voltar-container">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarBRANCO.png" alt="Botão Casinha" class="botao-voltar">
            <div class="botao-voltar-texto">Voltar</div>
        </div>
    </div>
    <div class="conteudos-titulo">Conteúdos:</div>




    <!-- Carrossel de Conteúdos -->
    <div class="carousel-container">
        <button id="prevContent" class="carousel-button" onclick="prevContentSlide()">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar" class="button-icon">
        </button>
        <div class="carousel" id="contentCarousel">
            <div class="carousel-inner" id="contentCarouselInner">
                <div class="carousel-item">
                    <a href="#" class="olympics-button" style="background-color: #cb6ce6;">
                        Mecânica Clássica: Fundamentos da cinemática do ponto material
                    </a>
                </div>
                <div class="carousel-item">
                    <a href="#" class="olympics-button" style="background-color: #5271ff;">
                        Dilatação superficial: Conceito e fórmulas
                    </a>
                </div>
                <div class="carousel-item">
                    <a href="#" class="olympics-button" style="background-color: #ff9900;">
                        Mecânica Clássica: Fundamentos da cinemática do ponto material
                    </a>
                </div>
                <div class="carousel-item">
                    <a href="#" class="olympics-button" style="background-color: #18b9cd;">
                        Dilatação superficial: Conceito e fórmulas
                    </a>
                </div>
            </div>
        </div>
        <button id="nextContent" class="carousel-button" onclick="nextContentSlide()">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir" class="button-icon">
        </button>
    </div>




    <div class="recomendacoes-titulo">Recomendações de livros:</div>




    <!-- Carrossel de Recomendações -->
    <div class="carousel-container">
        <button id="prevBooks" class="carousel-button" onclick="prevBooksSlide()">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar" class="button-icon">
        </button>
        <div class="carousel" id="booksCarousel">
            <div class="carousel-inner" id="booksCarouselInner">
                <div class="carousel-item">
                    <div class="info-card">
                        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/livroFisica.jpg" alt="Imagem do Livro" class="info-card-img">
                        <div class="info-card-content">
                            <div class="info-card-title">Física básica para provas - 13 exercícios</div>
                            <div class="info-card-author">Autor: Maria Souza</div>
                            <div class="info-card-publisher">Editora: EdiCase</div>
                            <div class="info-card-edition">Edição: 1</div>
                            <div class="info-card-publication">Publicação: 24/12/2006</div>
                            <button class="info-card-button">Onde comprar?</button>
                        </div>
                    </div>
                </div>
                <div class="carousel-item">
                    <div class="info-card">
                        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/livroFisica.jpg" alt="Imagem do Livro" class="info-card-img">
                        <div class="info-card-content">
                            <div class="info-card-title">Física para Universitários</div>
                            <div class="info-card-author">Autor: João Pereira</div>
                            <div class="info-card-publisher">Editora: LivroEdit</div>
                            <div class="info-card-edition">Edição: 2</div>
                            <div class="info-card-publication">Publicação: 15/03/2015</div>
                            <button class="info-card-button">Onde comprar?</button>
                        </div>
                    </div>
                </div>
                <div class="carousel-item">
                    <div class="info-card">
                        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/livroFisica.jpg" alt="Imagem do Livro" class="info-card-img">
                        <div class="info-card-content">
                            <div class="info-card-title">Fundamentos da Física Moderna</div>
                            <div class="info-card-author">Autor: Ana Silva</div>
                            <div class="info-card-publisher">Editora: TechBooks</div>
                            <div class="info-card-edition">Edição: 3</div>
                            <div class="info-card-publication">Publicação: 05/08/2020</div>
                            <button class="info-card-button">Onde comprar?</button>
                        </div>
                    </div>
                </div>
                <div class="carousel-item">
                    <div class="info-card">
                        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/livroFisica.jpg" alt="Imagem do Livro" class="info-card-img">
                        <div class="info-card-content">
                            <div class="info-card-title">Teoria e Exercícios de Física</div>
                            <div class="info-card-author">Autor: Carlos Almeida</div>
                            <div class="info-card-publisher">Editora: EduBooks</div>
                            <div class="info-card-edition">Edição: 1</div>
                            <div class="info-card-publication">Publicação: 10/11/2018</div>
                            <button class="info-card-button">Onde comprar?</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <button id="nextBooks" class="carousel-button" onclick="nextBooksSlide()">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir" class="button-icon">
        </button>
    </div>




    <div class="provas-anteriores-titulo">Provas anteriores:</div>




    <!-- Carrossel de Provas anteriores -->
    <div class="carousel-container">
    <button id="prevExams" class="carousel-button" onclick="prevExamsSlide()">
        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar" class="button-icon">
    </button>
    <div class="carousel" id="examsCarousel">
        <div class="carousel-inner" id="examsCarouselInner">
            <!--  carrossel de provas anteriores -->
            <div class="carousel-item">
                <div class="provas-card">
                    <b>Prova 2 - 2022</b>
                    <p>Estado: Respondida</p>
                    <p>Fase: 1</p>
                    <div class="additional-info">
                        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem">
                        Demi Lovato
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="provas-card">
                    <b>Prova 2 - 2021</b>
                    <p>Estado: Não Respondida</p>
                    <p>Fase: 2</p>
                    <div class="additional-info">
                        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem">
                        John Doe
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="provas-card">
                    <b>Prova 3 - 2020</b>
                    <p>Estado: Respondida</p>
                    <p>Fase: 3</p>
                    <div class="additional-info">
                        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem">
                        Jane Smith
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="provas-card">
                    <b>Prova 4 - 2019</b>
                    <p>Estado: Não Respondida</p>
                    <p>Fase: 4</p>
                    <div class="additional-info">
                        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem">
                        Mark Johnson
                    </div>
                </div>
            </div>
        </div>
    </div>
    <button id="nextExams" class="carousel-button" onclick="nextExamsSlide()">
        <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir" class="button-icon">
    </button>
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




    </script>
</body>
</html>



