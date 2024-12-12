<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
    <link rel="stylesheet" href="TelaAdicionarQuestionarioCSS.css">
    <script>
        let perguntaIndex = 1; // Índice para rastrear perguntas

        function adicionarPergunta() {
            const form = document.querySelector('form'); // Seleciona o formulário
            const novaPergunta = document.createElement('div'); // Cria novo bloco de pergunta
            novaPergunta.classList.add('quadrado');

            novaPergunta.innerHTML = `
                <h3 class="titulo-quadrado">Pergunta ${perguntaIndex + 1}:</h3>
                <input type="text" name="perguntas[${perguntaIndex}][texto]" class="input-pergunta" placeholder="Digite sua pergunta aqui..." required>

                <div class="button-group-pergunta">
                    <div class="button-column">
                        <input type="text" name="perguntas[${perguntaIndex}][alternativas][0][texto]" class="input-alternativa correta" placeholder="Digite a alternativa correta" required>
                        <input type="checkbox" name="perguntas[${perguntaIndex}][alternativas][0][correta]" value="1" checked> Correta
                    </div>
                    <div class="button-column">
                        <input type="text" name="perguntas[${perguntaIndex}][alternativas][1][texto]" class="input-alternativa incorreta" placeholder="Digite a alternativa incorreta" required>
                        <input type="checkbox" name="perguntas[${perguntaIndex}][alternativas][1][correta]" value="0"> Incorreta
                    </div>
                    <div class="button-column">
                        <input type="text" name="perguntas[${perguntaIndex}][alternativas][2][texto]" class="input-alternativa incorreta" placeholder="Digite a alternativa incorreta" required>
                        <input type="checkbox" name="perguntas[${perguntaIndex}][alternativas][2][correta]" value="0"> Incorreta
                    </div>
                    <div class="button-column">
                        <input type="text" name="perguntas[${perguntaIndex}][alternativas][3][texto]" class="input-alternativa incorreta" placeholder="Digite a alternativa incorreta" required>
                        <input type="checkbox" name="perguntas[${perguntaIndex}][alternativas][3][correta]" value="0"> Incorreta
                    </div>
                </div>

                <h3 class="titulo-quadrado">Explicação:</h3>
                <input type="text" name="perguntas[${perguntaIndex}][explicacao]" class="input-explicacao" placeholder="Digite aqui a sua explicação para essa pergunta" required>
                
                <div class="linha-roxa"></div>

                <div class="action-buttons">
                    <button type="button" class="action-button" onclick="removerPergunta(this)">Excluir Pergunta</button>
                </div>
            `;

            form.insertBefore(novaPergunta, form.querySelector('.custom-button')); // Insere antes do botão "Publicar"
            perguntaIndex++;
        }

        function removerPergunta(button) {
            const pergunta = button.closest('.quadrado'); // Encontra o bloco da pergunta
            pergunta.remove(); // Remove o bloco
        }
    </script>
</head>
<body>
    <div class="barra">
        <div class="logo-container">
            <img src="iconeQuestionarios.png" alt="Imagem questionario">
             <?php include 'processaConteudo.php'; ?>
            <h1 class="titulo">Adicionar Questionário - </h1>
               <h1 class ="subtitulo">      <?php echo isset($titulo) ? $titulo : 'Título não encontrado'; ?></h1>
        </div>
    </div>

<br><br><br>

    <!-- Formulário -->
    <form method="POST" action="simulaCadastroQuestionario.php">
        <input type="hidden" name="profQuePostou" value="<?php echo htmlspecialchars($_GET['email'] ?? ''); ?>">
        <input type="hidden" name="idConteudoPertencente" value="<?php echo htmlspecialchars($_GET['id'] ?? ''); ?>">

        <div class="input-section">
            <h3 class="titulo-quadrado">Título do questionário:</h3>
            <input type="text" name="titulo" class="input-titulo" placeholder="Digite o título aqui..." required>
        </div>

        <!-- Primeira pergunta -->
        <div class="quadrado">
            <h3 class="titulo-quadrado">Pergunta 1:</h3>
            <input type="text" name="perguntas[0][texto]" class="input-pergunta" placeholder="Digite sua pergunta aqui..." required>

            <div class="button-group-pergunta">
                <div class="button-column">
                    <input type="text" name="perguntas[0][alternativas][0][texto]" class="input-alternativa correta" placeholder="Digite a alternativa correta" required>
                    <input type="checkbox" name="perguntas[0][alternativas][0][correta]" value="1" checked> Correta
                </div>
                <div class="button-column">
                    <input type="text" name="perguntas[0][alternativas][1][texto]" class="input-alternativa incorreta" placeholder="Digite a alternativa incorreta" required>
                    <input type="checkbox" name="perguntas[0][alternativas][1][correta]" value="0"> Incorreta
                </div>
                <div class="button-column">
                    <input type="text" name="perguntas[0][alternativas][2][texto]" class="input-alternativa incorreta" placeholder="Digite a alternativa incorreta" required>
                    <input type="checkbox" name="perguntas[0][alternativas][2][correta]" value="0"> Incorreta
                </div>
                <div class="button-column">
                    <input type="text" name="perguntas[0][alternativas][3][texto]" class="input-alternativa incorreta" placeholder="Digite a alternativa incorreta" required>
                    <input type="checkbox" name="perguntas[0][alternativas][3][correta]" value="0"> Incorreta
                </div>
            </div>

            <h3 class="titulo-quadrado">Explicação:</h3>
            <input type="text" name="perguntas[0][explicacao]" class="input-explicacao" placeholder="Digite aqui a sua explicação para essa pergunta" required>
            
            <div class="linha-roxa"></div>
        </div>

        <div class="action-buttons">
            <button type="button" class="action-button" onclick="adicionarPergunta()">Adicionar Pergunta</button>
        </div>

        <button type="submit" class="custom-button">Publicar</button>
          <button class="custom-button">Cancelar</button>
    </form>
</body>
</html>
