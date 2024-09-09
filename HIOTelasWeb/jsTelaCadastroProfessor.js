document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('orderForm');
    const responseMessage = document.getElementById('responseMessage');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const name = document.getElementById('name').value.trim();
        const username = document.getElementById('username').value.trim();
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value.trim();
        const confirmPassword = document.getElementById('confirmPassword').value.trim();

        
        if (name === '' || username === '' || email === '' || password === '' || confirmPassword === '') {
            showResponse('Todos os campos são obrigatórios.', false);
            return;
        }

        if (password !== confirmPassword) {
            showResponse('As senhas não coincidem.', false);
            return;
        }

      
        showResponse('Cadastro realizado com sucesso!', true);

       
    });

    function showResponse(message, success) {
        responseMessage.textContent = message;
        responseMessage.className = success ? 'success' : 'error';
        responseMessage.classList.remove('hidden');
    }
});
