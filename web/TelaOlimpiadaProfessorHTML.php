<?php
// Conectar ao banco de dados
$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$username = 'root';
$password = 'root';

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
    <link rel="stylesheet" href="TelaOlimpiadaProfessor.css">
</head>
<body>
<div class="barra">
    <div class="logo-container">
        <img src="Imagens_Mobile_HIO/<?php echo htmlspecialchars($olimpiada['icone']); ?>.png" alt="<?php echo htmlspecialchars($olimpiada['nome']); ?>">
        <div class="text"><?php echo htmlspecialchars($olimpiada['nome']); ?> - <?php echo htmlspecialchars($sigla); ?></div>
    </div>
</div>


<br><br><br>
<br><br><br>



<div class="container">
    <a href="TelaEventoOlimpiadaHTML.php">
        <button class="botao-adicionais">Acessar eventos</button>
    </a>
    <button class="botao-adicionais" onclick="toggleMenu()">Adicionar itens</button>
    <div id="menu" class="menu" style="display: none;">
        <a href="#" class="menu-item" onclick="showAddContentForm()">Adicionar conteúdo</a>
        <a href="#" class="menu-item" onclick="showAddBookForm()">Recomendar livro</a>
        <a href="#" class="menu-item" onclick="showModalAddExam()">Adicionar prova anterior</a>
    </div>
</div>

       
   
<div id="addContentForm" class="add-content-form" style="display: none;">
    <form action="TelaAdicionarConteudo.php" method="post">
        <div class="modal-content">
            <h2>Adicionar conteúdo</h2>
            <div class="modal-body">
                <label for="titleAddContent">Título:</label>
                <input type="text" id="titleAddContent" name="titulo" class="modal-input" required>
                <label for="subtitleAddContent">Subtítulo:</label>
                <input type="text" id="subtitleAddContent" name="subtitulo" class="modal-input" required>
                <input type="hidden" name="sigla" value="<?php echo htmlspecialchars($sigla); ?>">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary">Adicionar</button>
                <button type="button" class="btn btn-secondary" onclick="hideAddContentForm()">Cancelar</button>
            </div>
        </div>
    </form>
</div>

        <!-- Modal para recomendar livros-->
        <div id="addBookForm" class="add-book-form" style="display: none;">
    <form action="simulandoCadastroLivro.php" method="post" enctype="multipart/form-data">
        <div class="modal-content">
            <h2>Recomendar Livro</h2>
            <div class="modal-body">
                <label for="isbnAddBook">ISBN:</label>
                <input type="text" id="isbnAddBook" name="isbn" class="modal-input" maxlength="13" required>

                <label for="coverAddBook">Capa do Livro:</label>
                <input type="file" id="coverAddBook" name="capa" class="modal-input" accept="image/*" required>

                <label for="titleAddBook">Título:</label>
                <input type="text" id="titleAddBook" name="titulo" class="modal-input" maxlength="300" required>

                <label for="authorAddBook">Autor:</label>
                <input type="text" id="authorAddBook" name="autor" class="modal-input" maxlength="200" required>

                <label for="editionAddBook">Edição:</label>
                <input type="number" id="editionAddBook" name="edicao" class="modal-input" required>

                <label for="pubDateAddBook">Data de Publicação:</label>
                <input type="date" id="pubDateAddBook" name="dataPublicacao" class="modal-input" required>

                <input type="hidden" name="sigla" value="<?php echo htmlspecialchars($sigla); ?>">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" name="submit">Adicionar Livro</button>
                <button type="button" class="btn btn-secondary" onclick="hideAddBookForm()">Cancelar</button>
            </div>
        </div>
    </form>
</div>



        <!-- Modal para adicionar prova anterior -->
        <div id="AddExamForm" class="add-exam-form" style="display: none;">
    <form action="simulandoCadastroProvaAnterior.php" method="post" enctype="multipart/form-data">
        <div class="modal-content">
            <span class="close" onclick="hideModal('modalAddExam')">&times;</span>
            <h2>Adicionar prova anterior</h2>
            <div class="modal-body">
                
                <!-- Ano da Prova -->
                <div class="form-group">
                    <label for="anoDaProva">Ano da Prova:</label>
                    <input type="number" id="anoDaProva" name="anoDaProva" class="modal-input" required>
                </div>

                <!-- Fase -->
                <div class="form-group">
                    <label for="fase">Fase:</label>
                    <input type="number" id="fase" name="fase" class="modal-input" required>
                </div>

                <!-- Estado (checkbox) -->
                <div class="form-group">
                    <label for="estado">Estado:</label>
                    <input type="checkbox" id="estado" name="estado">
                </div>

                <!-- Professor que Postou -->
                <div class="form-group">
                    <input type="hidden" name="profQuePostou" value="<?php echo htmlspecialchars($email); ?>">
                </div>

                <!-- Upload de Arquivo PDF -->
                <div class="file-upload">
                    <label for="arquivoPdf">Arquivo PDF:</label>
                    <input type="file" id="arquivoPdf" name="arquivoPdf" accept=".pdf" class="modal-input" required>
                </div>

                <!-- Visualizador de PDF -->
                <div id="pdf-viewer" style="display: none;">
                    <button id="viewPdfBtn">Visualizar PDF</button>
                </div>

                <!-- Olimpíada Pertencente (Select Dropdown) -->
                <div class="form-group">
                <input type="hidden" name="sigla" value="<?php echo htmlspecialchars($sigla); ?>">
            </div>

            <!-- Botões de ação -->
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary">Adicionar</button>
                <button type="button" class="btn btn-secondary" onclick="hideModal('modalAddExam')">Cancelar</button>
            </div>
        </div>
    </form>
</div>





        <div class="botao-voltar-container">
            <img src="Imagens_Mobile_HIO/btnVoltarBRANCO.png" alt="Botão Casinha" class="botao-voltar">
            <div class="botao-voltar-texto">Voltar</div>
        </div>
    </div>
    <div class="conteudos-titulo">Conteúdos:</div>


    <?php include 'carregaConteudos.php'; ?>



<script> 
function toggleMenu() {
    const menu = document.getElementById('menu');
    menu.style.display = menu.style.display === 'none' ? 'block' : 'none';
}

function showAddContentForm() {
    document.getElementById('addContentForm').style.display = 'block';
    hideMenu();
}

function hideAddContentForm() {
    document.getElementById('addContentForm').style.display = 'none';
}

function hideMenu() {
    document.getElementById('menu').style.display = 'none';
}

function showAddBookForm() {
        document.getElementById("addBookForm").style.display = "flex";
    }

    function hideAddBookForm() {
        document.getElementById("addBookForm").style.display = "none";
    }



    // Função para exibir o modal de Adicionar Prova
function showModalAddExam() {
    document.getElementById('AddExamForm').style.display = 'block';
}

// Função para esconder o modal de Adicionar Prova
function hideModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}

// Função para visualizar o PDF após o upload
document.getElementById('arquivoPdf').addEventListener('change', function() {
    const fileInput = this;
    const file = fileInput.files[0];
    if (file && file.type === 'application/pdf') {
        const reader = new FileReader();
        reader.onload = function(e) {
            const pdfViewer = document.getElementById('pdf-viewer');
            const viewPdfBtn = document.getElementById('viewPdfBtn');
            
            // Mostra o botão de visualização do PDF
            pdfViewer.style.display = 'block';
            
            // Adiciona a funcionalidade ao botão de visualização
            viewPdfBtn.onclick = function() {
                const pdfDataUrl = e.target.result;
                const pdfWindow = window.open("", "_blank");
                pdfWindow.document.write(`<iframe width='100%' height='100%' src='${pdfDataUrl}'></iframe>`);
            };
        };
        reader.readAsDataURL(file); // Converte o PDF para uma URL e permite a visualização
    }
});
    let index = 0;
    const items = document.querySelectorAll('.carousel-item');
    const totalItems = items.length;

    function mostrarItem() {
        items.forEach((item, i) => {
            item.style.display = (i === index) ? 'block' : 'none';
        });
    }

    function prevSlide() {
        index = (index > 0) ? index - 1 : totalItems - 1; // Volta para o último item se estiver no primeiro
        mostrarItem();
    }

    function nextSlide() {
        index = (index < totalItems - 1) ? index + 1 : 0; // Volta para o primeiro item se estiver no último
        mostrarItem();
    }

    // Exibe o primeiro item ao carregar
    mostrarItem();
</script>
</body>
</html>





