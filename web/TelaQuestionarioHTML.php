<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
    <link rel="stylesheet" href="TelaQuestionario.css"> <!-- Link para o CSS -->
    <?php
$id = isset($_GET['id']) ? htmlspecialchars($_GET['id']) : '';
$email = isset($_GET['email']) ? htmlspecialchars($_GET['email']) : '';
$sigla = isset($_GET['sigla']) ? htmlspecialchars($_GET['sigla']) : '';

?>

</head>
<body>
    <div class="barra">
        <div class="logo-container">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/imgMacaCaindo.png" alt="Maça caindo">
            <div class="text">OBF 2024 - Olimpíada Brasileira de Física</div>
        </div>
        <div class="button-container">
            <div class="button-item">
                <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/iconeTexto.png" alt="Textos">
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
            <button class="custom-button">Seus questionários</button>
            <a href="file:///C:/Users/Muskaan%20Ramchandani/Projeto-HIO-mobile/webCodesHIO/TelaQuestionarioRascunhos.html">
                <button class="custom-button">Seus rascunhos</button>
            </a>
            <a href="TelaAdicionarQuestionarioHTML.php?sigla=<?php echo $sigla; ?>&id=<?php echo $id; ?>&email=<?php echo $email; ?>">
                <button class="custom-button">Adicionar questionário</button>
            </a>
        </div>
    </div>
   
   


        <div class="info-box-container">
            <!-- Primeira linha de retângulos -->
            <div class="info-box">
                <p class="title">Conceitos fundamentais da Cinemática Escalar</p>
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">profAnaCastela</div>
                    <button class="info-button" onclick="openModal('modal1')">Acessar</button>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Ponto material</p>
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">zezeDiCamargo</div>
                    <button class="info-button" onclick="openModal('modal2')">Acessar</button>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Entenda o que é velocidade</p>
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">bruninhoMars</div>
                    <button class="info-button" onclick="openModal('modal3')">Acessar</button>
                </div>
            </div>
        </div>




        <div class="info-box-container">
            <!-- Segunda linha de retângulos -->
            <div class="info-box">
                <p class="title">Equação de Torricelli</p>
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">mariliaProf</div>
                    <button class="info-button" onclick="openModal('modal4')">Acessar</button>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Movimento Uniformemente Variado (MUV)</p>
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">professora_Tay</div>
                    <button class="info-button" onclick="openModal('modal5')">Acessar</button>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Trajetória, móvel e espaço percorrido</p>
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">anittaProf</div>
                    <button class="info-button" onclick="openModal('modal6')">Acessar</button>
                </div>
            </div>
        </div>




        <div class="info-box-container">
            <!-- Terceira linha de retângulos -->
            <div class="info-box">
                <p class="title">Lei de Newton</p>
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">fabiolaProf</div>
                    <button class="info-button" onclick="openModal('modal7')">Acessar</button>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Gravitação Universal</p>
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">rogerioProf</div>
                    <button class="info-button" onclick="openModal('modal8')">Acessar</button>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Lançamento Vertical</p>
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">luanaProf</div>
                    <button class="info-button" onclick="openModal('modal9')">Acessar</button>
                </div>
            </div>
        </div>




        <div class="info-box-container">
            <!-- Quarta linha de retângulos -->
            <div class="info-box">
                <p class="title">Do que se trata o ponto material</p>
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">demiLov</div>
                    <button class="info-button" onclick="openModal('modal10')">Acessar</button>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Cinemática: conceitos e fórmulas</p>
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">profShawnMend</div>
                    <button class="info-button" onclick="openModal('modal11')">Acessar</button>
                </div>
            </div>




            <div class="info-box">
                <p class="title">Principais fórmulas da cinemática</p>
                <div class="info-box-content">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/circuloRoxo.png" alt="Imagem do Professor">
                    <div class="info-text">jjorgeMatProf</div>
                    <button class="info-button" onclick="openModal('modal12')">Acessar</button>
                </div>
            </div>
        </div>
    </div>




    <!-- Modais -->
    <div id="modal1" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('modal1')">&times;</span>
            <p>Conteúdo do modal 1</p>
        </div>
    </div>




    <div id="modal2" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('modal2')">&times;</span>
            <p>Conteúdo do modal 2</p>
        </div>
    </div>




    <div id="modal3" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('modal3')">&times;</span>
            <p>Conteúdo do modal 3</p>
        </div>
    </div>




    <div id="modal4" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('modal4')">&times;</span>
            <p>Conteúdo do modal 4</p>
        </div>
    </div>




    <div id="modal5" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('modal5')">&times;</span>
            <p>Conteúdo do modal 5</p>
        </div>
    </div>




    <div id="modal6" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('modal6')">&times;</span>
            <p>Conteúdo do modal 6</p>
        </div>
    </div>




    <div id="modal7" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('modal7')">&times;</span>
            <p>Conteúdo do modal 7</p>
        </div>
    </div>




    <div id="modal8" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('modal8')">&times;</span>
            <p>Conteúdo do modal 8</p>
        </div>
    </div>




    <div id="modal9" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('modal9')">&times;</span>
            <p>Conteúdo do modal 9</p>
        </div>
    </div>




    <div id="modal10" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('modal10')">&times;</span>
            <p>Conteúdo do modal 10</p>
        </div>
    </div>




    <div id="modal11" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('modal11')">&times;</span>
            <p>Conteúdo do modal 11</p>
        </div>
    </div>




    <div id="modal12" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('modal12')">&times;</span>
            <p>Conteúdo do modal 12</p>
        </div>
    </div>




    <script src="TelaQuestionario.js"></script>
</body>
</html>







