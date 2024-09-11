document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('login-form');

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const usernameOrEmail = document.getElementById('username-email').value;
        const password = document.getElementById('password').value;
    });
});
