



        function obterCorHex(cor) {
            switch (cor.toLowerCase()) {
                case 'rosa': return '#CB6CE6';
                case 'ciano': return '#18B9CD';
                case 'azul': return '#5271FF';
                case 'laranja': return '#FF914D';
                default: return '#ffffff';  
            }
        }

        async function carregarOlimpiada() {
            const params = new URLSearchParams(window.location.search);
            const sigla = params.get('sigla');
            if (!sigla) {
                console.error('Nenhuma sigla fornecida.');
                return;
            }
            try {
                const response = await fetch(`TelaOlimpiadaProfessor.php?sigla=${sigla}`);
                const data = await response.json();
                if (data.error) {
                    console.error(data.error);
                    return;
                }
                // Preencher os dados no HTML
                document.getElementById('logoContainer').innerHTML = `
                    <img src="http://192.168.0.81:8080/webCodesHIO/Imagens_Mobile_HIO/${data.icone}.png" alt="${data.nome}">
                    <div class="text">${data.nome}</div>
                `;
                document.getElementById('content').innerHTML = `
                    <h1>Bem-vindo à ${data.nome}</h1>
                    <p>Detalhes da Olimpíada aparecerão aqui...</p>
                `;
               
                const corHex = obterCorHex(data.cor.trim()); 
                document.getElementById('logoContainer').style.backgroundColor = corHex;
                document.getElementById('barra').style.backgroundColor = corHex;
                // Log para depuração
                console.log('Cor aplicada:', corHex);
                document.getElementById('olympiadName').innerText = data.nome;  
                document.getElementById('siglaOlimpiada').value = data.sigla;   
            } catch (error) {
                console.error('Erro ao buscar dados:', error);
            }
        }

        async function carregarOlimpiadaConteudo() {
            
            const params = new URLSearchParams(window.location.search);
            const sigla = params.get('sigla');
        
            if (!sigla) {
                console.error('Nenhuma sigla fornecida.');
                return;
            }
        
            try {
                // Buscar conteúdos da Olimpíada
                const responseConteudos = await fetch(`TelaOlimpiadaProfessorConteudo.php?sigla=${sigla}`);
                
                // Verificar se a requisição foi bem-sucedida
                if (!responseConteudos.ok) {
                    throw new Error(`Erro na requisição: ${responseConteudos.status}`);
                }
        
                const conteudos = await responseConteudos.json();
        
                if (conteudos.error) {
                    console.error(conteudos.error);
                    return;
                }
        
                // Verificar se há conteúdos
                if (conteudos.length === 0) {
                    console.warn('Nenhum conteúdo encontrado.');
                    document.getElementById('contentSpinner').innerHTML = '<p>Nenhum conteúdo disponível.</p>';
                    return;
                }
        
                // Processar e exibir os conteúdos
                const contentContainer = document.getElementById('contentSpinner');
                let spinnerContent = '<div class="spinner-horizontal">';
        
                conteudos.forEach(conteudo => {
                    spinnerContent += `
                        <div class="spinner-item" data-id="${conteudo.id}">
                            <h3>${conteudo.titulo}</h3>
                            <p>${conteudo.subtitulo}</p>
                        </div>
                    `;
                });
        
                spinnerContent += '</div>';
                contentContainer.innerHTML = spinnerContent;
        
                
                document.querySelectorAll('.spinner-item').forEach(item => {
                    item.addEventListener('click', function() {
                        const conteudoId = this.getAttribute('data-id');
                        window.location.href = `TelaFlashCard.html?id=${conteudoId}`;
                    });
                });
        
            } catch (error) {
                console.error('Erro ao carregar conteúdos:', error);
            }
        }
        
        // Chamar função no carregamento da página

        
        window.onload = function() {
            carregarOlimpiada();
            carregarOlimpiadaConteudo();
        };
        
       


    function prevContentSlide() {
    const items = document.querySelectorAll('#contentCarouselInner .carousel-item');
    const totalItems = items.length;
    const itemsToShow = 2; // Número de itens a serem exibidos por vez


  
    if (currentContentIndex > 0) {
        currentContentIndex -= itemsToShow;
    } else {
        currentContentIndex = Math.max(totalItems - itemsToShow, 0);
    }
   
    
    updateContentCarousel();
}


    function nextContentSlide() {
    const items = document.querySelectorAll('#contentCarouselInner .carousel-item');
    const totalItems = items.length;
    const itemsToShow = 2; 


    if (currentContentIndex < totalItems - itemsToShow) {
        currentContentIndex += itemsToShow;
    } else {
        currentContentIndex = 0;
    }
   

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
document.addEventListener('DOMContentLoaded', function() {
    const emailProfessor = localStorage.getItem('emailProfessor');

    if (!emailProfessor) {
        window.location.href = 'TelaRecepcao.html'; // Redireciona se o email não estiver disponível
    } else {
        console.log('Email do professor encontrado:', emailProfessor);
        // Aqui você pode utilizar o email para cadastrar a prova anterior
    }
});

// Função para adicionar prova anterior
async function addExam() {

    function showModalAddExam(modalId, siglaOlimpiada, emailProfessor) {
        carregarOlimpiada();
         document.getElementById('siglaOlimpiada').value = siglaOlimpiada; // Preenche a sigla da olimpíada
         document.getElementById('emailProfessor').value = emailProfessor; // Preenche o email do professor
         document.getElementById(modalId).style.display = 'block';
     }
     

    const anoDaProva = document.getElementById('examYear').value;
    const fase = document.getElementById('examPhase').value;
    const estado = document.querySelector('input[name="examState"]:checked').value;
    const arquivoPdf = document.getElementById('uploadPdf').files[0];
    const siglaOlimpiadaPertencente = document.getElementById('siglaOlimpiada').value;
    const emailProfessor = localStorage.getItem('emailProfessor');
  

    if (!anoDaProva || !fase || !arquivoPdf || !emailProfessor || !siglaOlimpiadaPertencente) {
        alert('Por favor, preencha todos os campos e selecione um arquivo PDF.');
        return;
    }

    const formData = new FormData();
    formData.append('anoDaProva', anoDaProva);
    formData.append('fase', fase);
    formData.append('estado', estado);
    formData.append('arquivoPdf', arquivoPdf);
    formData.append('emailProfessor', emailProfessor); // Adicionando o email do professor
    formData.append('siglaOlimpiadaPertencente', siglaOlimpiadaPertencente); 

    try {
        const response = await fetch('TelaAdicionarProvasAnteriores.php', {
            method: 'POST',
            body: formData
        });

        const result = await response.json();

        if (result.success) {
            alert('Prova anterior adicionada com sucesso!');
            hideModal('modalAddExam');
            
        } else {
            alert('Erro ao adicionar prova: ' + result.error);
        }
    } catch (error) {
        console.error('Erro:', error);
    }
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


        window.onload = carregarOlimpiada;


        function hideModal(modalId) {
        document.getElementById(modalId).style.display = 'none';
    }

    function showModal(modalId) {
        document.getElementById(modalId).style.display = 'block';
    }




    async function addContent() {
    const titulo = document.getElementById('titleAddContent').value;
    const subtitulo = document.getElementById('subtitleAddContent').value;
    const siglaOlimpiadaPertencente = document.getElementById('siglaOlimpiada').value;

    if (!titulo || !subtitulo || !siglaOlimpiadaPertencente) {
        alert('Por favor, preencha todos os campos.');
        return;
    }  

    console.log(`Adicionando conteúdo: ${titulo}, ${subtitulo} para a Olimpíada: ${siglaOlimpiadaPertencente}`);


    try {
        const response = await fetch('TelaAdicionarConteudo.php', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: new URLSearchParams({
                titulo: titulo,
            subtitulo: subtitulo,
            siglaOlimpiadaPertencente: siglaOlimpiadaPertencente
            })
        });

        const result = await response.json();

        if (result.success) {
            alert('Conteúdo adicionado com sucesso!');
            hideModal('modalAddContent');
            // Atualize a lista de conteúdos ou faça outras ações conforme necessário
        } else {
            alert('Erro ao adicionar conteúdo: ' + result.error);
        }
    } catch (error) {
        console.error('Erro:', error);
    }
}

async function addBook() {
    const isbn = document.getElementById('isbn').value;
    const titulo = document.getElementById('title').value;
    const autor = document.getElementById('author').value;
    const edicao = document.getElementById('edition').value;
    const dataPublicacao = document.getElementById('publication-date').value;
    const siglaOlimpiadaPertencente = document.getElementById('siglaOlimpiada').value;
    const coverInput = document.getElementById('cover');


    // Tratamento do arquivo da capa
    const coverFile = document.getElementById('cover').files[0];
    if (!coverFile) {
        alert('Por favor, selecione uma capa do livro.');
        return;
    }

    if (!isbn || !titulo || !autor || !edicao || !dataPublicacao || !siglaOlimpiadaPertencente) {
        alert('Por favor, preencha todos os campos.');
        return;
    }

    const formData = new FormData();
    formData.append('isbn', isbn);
    formData.append('titulo', titulo);
    formData.append('autor', autor);
    formData.append('edicao', edicao);
    formData.append('dataPublicacao', dataPublicacao);
    formData.append('siglaOlimpiadaPertencente', siglaOlimpiadaPertencente);
    formData.append('cover', coverInput.files[0]); // Adiciona o arquivo da capa
   

    try {
        const response = await fetch('TelaRecomendarLivro.php', {
            method: 'POST',
            body: formData
        });

        const result = await response.json();

        if (result.success) {
            alert('Livro adicionado com sucesso!');
            hideBookModal();
            // Atualize a lista de livros ou faça outras ações conforme necessário
        } else {
            alert('Erro ao adicionar livro: ' + result.error);
        }
    } catch (error) {
        console.error('Erro:', error);
    }
}

// Chame a função carregarOlimpiadaParaLivro quando abrir o modal
function showAddBookModal() {
    carregarOlimpiada();
    document.getElementById('bookModal').style.display = 'block';
}

