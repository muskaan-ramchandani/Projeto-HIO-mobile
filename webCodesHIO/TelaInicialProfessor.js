function toggleMenu() {
    var menuContent = document.getElementById('menuContent');
    var menuIcon = document.querySelector('.menu-icon');
    var helperText = document.getElementById('helperText');

    if (menuContent.style.display === 'block') {
        menuContent.style.display = 'none';
        menuIcon.classList.remove('active');
        helperText.style.display = 'none';
    } else {
        menuContent.style.display = 'block';
        menuIcon.classList.add('active');
        helperText.style.display = 'inline';
    }
}

function scrollCarrossel(distance) {
    const carrossel = document.getElementById('carrossel');
    carrossel.scrollBy({
        left: distance,
        behavior: 'smooth'
    });
}



async function carregarOlimpiadas() {
    try {
        const response = await fetch('TelaInicialProfessor.php');
        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        const data = await response.json();

        if (data.error) {
            console.error(data.error);
            return;
        }

        const carrossel = document.getElementById('carrossel');
        carrossel.innerHTML = '';  // Limpa o conteúdo anterior

        data.forEach(olimpiada => {
            const div = document.createElement('div');
            div.classList.add('olimpiada');
            div.style.backgroundColor = obterCorHex(olimpiada.cor);

            div.innerHTML = `
                <a href="TelaOlimpiadaProfessor.html?sigla=${olimpiada.sigla}">
                    <img src="http://192.168.0.81:8080/webCodesHIO/Imagens_Mobile_HIO/${olimpiada.icone}.png" alt="${olimpiada.nome}">
                    <div class="info">
                        <h2>${olimpiada.nome}</h2>
                        <p>Sigla: ${olimpiada.sigla}</p>
                        <p class="cor">Cor: ${olimpiada.cor}</p>
                    </div>
                </a>
            `;
            carrossel.appendChild(div);
        });
    } catch (error) {
        console.error('Erro ao buscar dados:', error);
    }
}

function obterCorHex(cor) {
    switch (cor.toLowerCase()) {
        case 'rosa': return '#CB6CE6';
        case 'ciano': return '#18B9CD';
        case 'azul': return '#5271FF';
        case 'laranja': return '#FF914D';
        default: return '#ffffff';  // Branco por padrão
    }
}


window.onload = carregarOlimpiadas;

document.addEventListener('DOMContentLoaded', function() {
    const params = new URLSearchParams(window.location.search);
    const emailProfessor = params.get('email');

    // Verifica se o email foi capturado corretamente
    if (!emailProfessor || emailProfessor.trim() === '') {
        window.location.href = 'TelaRecepcao.html'; // Redireciona se não houver email
    } else {
        console.log('Email capturado:', emailProfessor);
        localStorage.setItem('emailProfessor', emailProfessor); // Armazena no LocalStorage
    }
});

