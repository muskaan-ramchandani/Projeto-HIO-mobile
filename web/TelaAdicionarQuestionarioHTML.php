<!DOCTYPE html>  
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
    <link rel="stylesheet" href="TelaAdicionarQuestionario.css">
</head>
<body>
    <div class="barra">
        <div class="logo-container">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/iconeQuestionarios.png" alt="Imagem questionario">
            <h1 class="titulo">Adicionar questionário</h1>
            <h2 class="subtitulo">- Fundamentos da Cinemática do Ponto Material</h2>
        </div>
    </div>


    <div class="main-content">
        <h1>Fundamentos da Cinemática do Ponto Material</h1>
        <div class="button-group">
            <button class="custom-button">Publicar</button>
            <button class="custom-button">Salvar rascunho</button>
            <button class="custom-button">Cancelar</button>
        </div>
    </div>


    <!-- Título e campo de entrada fora do quadrado -->
 <!-- Título e campo de entrada fora do quadrado -->
<form method="POST" action="simulaCadastroQuestionario.php"> <!-- Ação do formulário para enviar dados ao PHP -->
    <!-- Campo oculto para o email do professor -->
    <input type="hidden" name="profQuePostou" value="<?php echo htmlspecialchars($_GET['email'] ?? ''); ?>">
    
    <!-- Campo oculto para o ID do conteúdo -->
    <input type="hidden" name="idConteudoPertencente" value="<?php echo htmlspecialchars($_GET['id'] ?? ''); ?>">

    <div class="input-section">
        <h3 class="titulo-quadrado">Título do questionário:</h3>
        <input type="text" name="titulo" class="input-titulo" placeholder="Digite o título aqui..." required>
    </div>

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

        <button type="button" class="add-alternativa-button">Adicionar alternativa</button> <!-- JavaScript para adicionar alternativas dinamicamente -->
        <h3 class="titulo-quadrado">Explicação:</h3>
        <input type="text" name="perguntas[0][explicacao]" class="input-explicacao" placeholder="Digite aqui a sua explicação para essa pergunta" required>  
        <div class="linha-roxa"></div>
        
        <div class="action-buttons">
            <button type="button" class="action-button">Excluir Pergunta</button> <!-- JavaScript para excluir perguntas -->
            <button type="button" class="action-button">Adicionar Pergunta</button> <!-- JavaScript para adicionar perguntas -->
        </div>

        </div>

        <button type="submit" class="custom-button">Publicar</button> <!-- Envia o formulário -->
    </form>

   
</body>
</html>
