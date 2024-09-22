
function obterCorHex(corNome) {
    const cores = {
        'Rosa': '#CB6CE6',
        'Ciano': '#18B9CD',
        'Azul': '#5271FF',
        'Laranja': '#FF914D'
    };
    return cores[corNome] || '#FFFFFF';  // Valor padrão caso a cor não exista
}


async function carregarFlashCard() {
    const params = new URLSearchParams(window.location.search);
    const conteudoId = params.get('id');  // Pegando o ID do conteúdo da URL

    if (!conteudoId) {
        console.error('Nenhum conteúdo fornecido.');
        return;
    }

    try {
        // 1. Buscar os dados do conteúdo
        const responseConteudo = await fetch(`TelaFlashCardConteudo.php?id=${conteudoId}`);
        const dataConteudo = await responseConteudo.json();

        if (dataConteudo.error) {
            console.error(dataConteudo.error);
            return;
        }

        // Exibir o conteúdo no HTML
        document.getElementById('content').innerHTML = `
            <h1>${dataConteudo.titulo}</h1>
            <p>${dataConteudo.subtitulo}</p>
        `;

        // 2. Buscar os dados da Olimpíada com base na siglaOlimpiadaPertencente do conteúdo
        const responseOlimpiada = await fetch(`TelaOlimpiadaProfessor.php?sigla=${dataConteudo.siglaOlimpiadaPertencente}`);
        const dataOlimpiada = await responseOlimpiada.json();

        if (dataOlimpiada.error) {
            console.error(dataOlimpiada.error);
            return;
        }

        // Preencher o logo e nome da Olimpíada
        document.getElementById('logoContainer').innerHTML = `
            <img src="http://192.168.0.81:8080/webCodesHIO/Imagens_Mobile_HIO/${dataOlimpiada.icone}.png" alt="${dataOlimpiada.nome}">
            <div class="text">${dataOlimpiada.nome}</div>
        `;

        // Aplicar a cor da Olimpíada ao background da barra e do logoContainer
        const corHex = obterCorHex(dataOlimpiada.cor.trim());
        document.getElementById('logoContainer').style.backgroundColor = corHex;
        document.getElementById('barra').style.backgroundColor = corHex;

    } catch (error) {
        console.error('Erro ao carregar dados:', error);
    }
}

window.onload = carregarFlashCard;
