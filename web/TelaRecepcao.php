<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="TelaRecepcao.css">
</head>
<body>
    <div class="container">
        <!-- Parte do quadrado roxo -->
        <div class="square">
            <h1>Helper in Olympics</h1>
            <div class="image-container">
                <img src="Imagens_Mobile_HIO/hipoPrincipalComMedalha.png" alt="Hipo com medalha">
            </div>
        </div>
        
        <!-- Parte branca da tela -->
        <div class="login-section">
            <h1 class="login-title">Entrar</h1>
            <form id="login-form" method="post" action="verificar_login.php">
                <input type="text" id="username-email" name="email" placeholder="Email" required>
                <input type="password" id="password" name="password" placeholder="Senha" required>
                <button type="submit"><b>Entrar</b></button>
                <p class="forgot-password">Esqueceu sua senha? <a href="#" class="forgot-link">Clique aqui</a></p>
                <button type="button" id="register-button" class="register-button">Cadastre-se</button>
            </form>
            <?php if (isset($_GET['mensagem'])): ?>
                <div id="responseMessage" class="message">
                    <p><?php echo htmlspecialchars($_GET['mensagem']); ?></p>
                </div>
            <?php endif; ?>
        </div>
    </div>
    
    <script></script>
    <script>
        // Adiciona um ouvinte de evento ao botão "Cadastre-se"
        document.getElementById('register-button').addEventListener('click', function() {
            // Redireciona para a página de cadastro
            window.location.href = 'TelaBemVindo.html'; // Atualizado para URL relativa
        });
    </script>
</body>
</html>