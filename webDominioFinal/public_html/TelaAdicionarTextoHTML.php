<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
           <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
    <link rel="stylesheet" href="TelaAdicionarTexto.css">
</head>
<body>
    
   <div class="barra">
        <div class="logo-container">
            <img src="iconeTexto.png" alt="Imagem questionario">
             <?php include 'processaConteudo.php'; ?>
            <h1 class="titulo">Adicionar Texto -     </h1>
               <h1 class ="subtitulo"><?php echo isset($titulo) ?   $titulo : 'Título não encontrado'; ?></h1>
              
</div>

        </div>
    </div>


    <div class="main-content">
        <form action="cadastraTexto.php" method="POST">
            <div class="button-group">
                <button type="submit" name="publicar" class="custom-button">Publicar</button>
                <button type="button" class="custom-button" 
        onclick="window.history.back();">
    Cancelar
</button>

            </div>

            <div class="quadrado">
                <input type="text" name="titulo" class="titulo-input" placeholder="Insira aqui o título" required>
                <div class="linha"></div>
                <textarea name="texto" class="texto-input" placeholder="Digite seu texto aqui" required></textarea>
            </div>

            <!-- Campos ocultos para a sigla e o email -->
            <input type="hidden" name="sigla" value="<?php echo htmlspecialchars($_GET['sigla'] ?? ''); ?>">
            <input type="hidden" name="email" value="<?php echo htmlspecialchars($_GET['email'] ?? ''); ?>">
            <input type="hidden" name="idConteudo" value="<?php echo htmlspecialchars($_GET['id'] ?? ''); ?>">
        </form>
    </div>
</body>
</html>
