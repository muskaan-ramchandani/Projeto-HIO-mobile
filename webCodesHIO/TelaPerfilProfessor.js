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
document.addEventListener('DOMContentLoaded', function() {
    // Função para buscar os dados do professor
    function fetchProfessorData() {
        // Substitua esta URL pelo endpoint da sua API que retorna os dados do professor
        const apiUrl = 'http://seu-servidor.com/api/professor';

        fetch(apiUrl)
            .then(response => response.json())
            .then(data => {
                // Verifica se os dados foram recebidos corretamente
                if (data && data.nome && data.username && data.email) {
                    updateProfile(data);
                } else {
                    console.error('Dados do professor inválidos ou não encontrados.');
                }
            })
            .catch(error => {
                console.error('Erro ao buscar dados do professor:', error);
            });
    }

    // Função para atualizar o perfil na página
    function updateProfile(data) {
        // Atualiza os elementos da página com os dados do professor
        document.querySelector('.profile-name').textContent = data.nome;
        document.querySelector('.profile-username').textContent = data.username;
        document.querySelector('.profile-email').textContent = data.email;
    }

    // Chama a função para buscar os dados quando a página carrega
    fetchProfessorData();
});
