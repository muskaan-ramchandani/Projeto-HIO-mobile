<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
     <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
    <link rel="stylesheet" href="TelaCadastroProfessor.css">
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&display=swap" rel="stylesheet">
    <script type="text/javascript" src="TelaCadastroProfessor.js" defer></script>
</head>
<body>
    <div class="container">
        <div class="purple-section">
            <img src="hipoProfessor.png" alt="Hipo professor" class="purple-image">
            <p class="helper-text">Helper in Olympics</p>
        </div>

        <div class="form-container">
            <h1 class="title">Cadastre-se</h1>
            <form id="orderForm" method="post" action="TelaCadastroProfessor.php">
                <div class="field">
                    <label class="label" for="name">Nome completo</label>
                    <div class="control">
                        <input class="input" type="text" id="name" name="name" required>
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="username">Nome de usuário</label>
                    <div class="control">
                        <input class="input" type="text" id="username" name="username" required>
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="email">Email</label>
                    <div class="control">
                        <input class="input" type="email" id="email" name="email" required>
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="password">Senha</label>
                    <div class="control">
                        <input class="input" type="password" id="password" name="password" required>
                    </div>
                </div>
                <div class="field">
                    <label class="label" for="confirmPassword">Confirme sua senha</label>
                    <div class="control">
                        <input class="input" type="password" id="confirmPassword" name="confirmPassword" required>
                    </div>
                </div>
                <div class="field">
                    <div class="control">
                        <button class="button is-primary" type="submit">Finalizar</button>
                    </div>
                </div>
                <?php if (isset($_GET['mensagem'])): ?>
                    <div id="responseMessage" class="message">
                        <p><?php echo htmlspecialchars($_GET['mensagem']); ?></p>
                    </div>
                <?php endif; ?>
            </form>
        </div>
    </div>
</body>
</html>
