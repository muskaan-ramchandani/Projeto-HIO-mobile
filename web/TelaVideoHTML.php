<?php
// Conectar ao banco de dados
$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$username = 'root';
$password = 'root';

try {
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Capturar a sigla da URL
    if (isset($_GET['sigla'])) {
        $sigla = $_GET['sigla'];

        // Buscar os dados da olimpíada correspondente
        $sql = "SELECT nome, icone, cor FROM Olimpiada WHERE sigla = :sigla";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':sigla', $sigla);
        $stmt->execute();

        $olimpiada = $stmt->fetch(PDO::FETCH_ASSOC);
    } else {
        // Se não houver sigla na URL, redirecionar para a página inicial
        header('Location: TelaInicialProfessorHTML.php');
        exit();
    }
} catch (PDOException $e) {
    echo "Erro: " . $e->getMessage();
}
?>
?><!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
    <link rel="stylesheet" href="TelaVideo.css">
</head>
<body>
<div class="barra">
    <div class="logo-container">
        <img src="Imagens_Mobile_HIO/<?php echo htmlspecialchars($olimpiada['icone']); ?>.png" alt="<?php echo htmlspecialchars($olimpiada['nome']); ?>">
        <div class="text"><?php echo htmlspecialchars($olimpiada['nome']); ?> - <?php echo htmlspecialchars($sigla); ?></div>
    </div>
        <div class="button-container">
            <div class="button-item">
                <img src="Imagens_Mobile_HIO/iconeTexto.png" alt="Textos">
                <div class="button-label">Textos</div>
            </div>
            <div class="button-item">
                <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/iconeVideo.png" alt="Videos">
                <div class="button-label">Vídeos</div>
            </div>
            <div class="button-item">
                <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/iconeFlashcard.png" alt="Flashcards">
                <div class="button-label">Flashcards</div>
            </div>
            <div class="button-item">
                <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/iconeQuestionarios.png" alt="Questionários">
                <div class="button-label">Questionários</div>
            </div>
            <div class="button-item">
                <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarBRANCO.png" alt="Voltar">
                <div class="button-label">Voltar</div>
            </div>
        </div>
    </div>
   
    <!-- Novo conteúdo abaixo da barra -->
    <div class="main-content">
    <h1>Fundamentos da Cinemática do Ponto Material</h1>
    <div class="button-group">
        <button class="custom-button">Tudo</button>
        <button class="custom-button">Suas recomendações</button>
        <a href="simulandoCadastroVideo.php">
            <button class="custom-button">Recomendar vídeo</button>
        </a>
    </div>
</div>   
        <div class="info-box-container">
            <!-- Primeira linha de retângulos -->
            <div class="info-box">
                <p class="title">Equação de Torricelli</p>
                <img src="https://i.ytimg.com/vi/qurkbU5Wjk4/maxresdefault.jpg" alt="Nova Imagem 1" class="info-image">
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">mariliaProf</div>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Movimento Uniformemente Variado (MUV)</p>
                <img src="https://i.ytimg.com/vi/cHm4MGcYbcw/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLBaJcFGawhjVBysEZMKPgfWWS2DbA" alt="Nova Imagem 1" class="info-image">
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">professora_Tay</div>


                </div>
            </div>




            <div class="info-box">
                <p class="title">Trajetória, móvel e espaço percorrido</p>
                <img src="https://i.ytimg.com/vi/pu7BeHjlmkY/maxresdefault.jpg" alt="Nova Imagem 1" class="info-image">
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">anittaProf</div>
                </div>
            </div>
        </div>




        <div class="info-box-container">
            <!-- Segunda linha de retângulos -->
            <div class="info-box">
                <p class="title">Lei de Newton</p>
                <img src="https://i.ytimg.com/vi/UM_RzhJakEQ/maxresdefault.jpg" alt="Nova Imagem 1" class="info-image">
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">fabiolaProf</div>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Gravitação Universal</p>
                <img src="https://i.ytimg.com/vi/YbDSfRLF23I/maxresdefault.jpg" alt="Nova Imagem 1" class="info-image">
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">rogerioProf</div>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Lançamento Vertical</p>
                <img src="https://i.ytimg.com/vi/ZQQurGymVKU/maxresdefault.jpg" alt="Nova Imagem 1" class="info-image">
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">luanaProf</div>
                </div>
            </div>
        </div>




        <div class="info-box-container">
            <!-- Terceira linha de retângulos -->
            <div class="info-box">
                <p class="title">Do que se trata o ponto material</p>
                <img src="https://i.ytimg.com/vi/HW9rK4uV3bA/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLBrkLs0EGno3XeGeN16-Fb550z01w" alt="Nova Imagem 1" class="info-image">
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">demiLov</div>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Cinemática: conceitos e fórmulas</p>
                <img src="https://i.ytimg.com/vi/_6ILoTeChCE/maxresdefault.jpg" alt="Nova Imagem 1" class="info-image">
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">profShawnMend</div>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Principais fórmulas da cinemática</p>
                <img src="https://i.ytimg.com/vi/D6gB4jnx9Wc/maxresdefault.jpg" alt="Nova Imagem 1" class="info-image">
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">jjorgeMatProf</div>
                </div>
            </div>
        </div>


        <!--Quarta linha de retangulos-->
        <div class="info-box-container">
        <div class="info-box">
            <p class="title">Conceitos fundamentais da Cinemática Escalar</p>
            <img src="https://i.ytimg.com/vi/f02hvGb5gmM/maxresdefault.jpg" alt="Nova Imagem 1" class="info-image">
            <div class="info-box-content">
                <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor" class="prof-image">
                <div class="info-text">profAnaCastela</div>
            </div>
        </div>    
               
   
   
        <div class="info-box">
            <p class="title">Ponto material</p>
            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRN5t9q_gDOKlRs0TXBEeCBiNrDZb6iIX-xIA&s" alt="Nova Imagem 1" class="info-image">
            <div class="info-box-content">
                <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor" class="prof-image">
                <div class="info-text">ZezeDiCamargo</div>
            </div>
        </div>    
   
   
        <div class="info-box">
            <p class="title">Entenda o que é velocidade</p>
            <img src="https://i.ytimg.com/vi/LkMFn9XvK_g/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLDDiGePV_gUvU2R6sYmUs_s9fbDMg" alt="Nova Imagem 1" class="info-image">
            <div class="info-box-content">
                <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor" class="prof-image">
                <div class="info-text">bruninhoMars</div>
            </div>
        </div>  
    </div>
     <!-- Modal para Recomendar Vídeo -->
<div class="modal" id="videoModal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2 style="font-family: 'Open Sans', sans-serif; font-weight: bold; font-size: 20px; color: #835ad2; text-align: center;">Recomendar Vídeo</h2>
       
        <label style="font-weight: bold; font-size: 15px; text-align: left; font-family: 'Open Sans';">Título</label>
        <input type="text" placeholder="Digite aqui o título do vídeo" class="input-field" style="margin-bottom: 20px;"> <!-- Aumenta a margem inferior -->
       
        <label style="font-weight: bold; font-size: 15px; text-align: left; font-family: 'Open Sans';">Link do Vídeo</label>
        <input type="text" placeholder="Cole aqui o link do vídeo" class="input-field">


        <div class="button-group" style="margin-top: 20px;">
            <button class="modal-button" onclick="addVideo()">Adicionar</button>
            <button class="modal-button cancel-button" onclick="closeModal()">Cancelar</button>
        </div>
    </div>
</div>






<script src="TelaVideo.js"></script>
       
</body>
</html>
