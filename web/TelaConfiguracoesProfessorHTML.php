
<?php
session_start();

$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "hio";

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
} catch (PDOException $e) {
    die("Erro na conexão com o banco de dados: " . $e->getMessage());
}

// Recuperar o email da URL
if (isset($_GET['email'])) {
    $email = $_GET['email'];
    
    // Consultar os dados do professor
    $sql = "SELECT nomeCompleto, nomeUsuario, email, fotoPerfil FROM Professor WHERE email = :email";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':email', $email);
    $stmt->execute();
    $professor = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($professor) {
        // Exibir os dados do professor
        $nomeCompleto = htmlspecialchars($professor['nomeCompleto']);
        $nomeUsuario = htmlspecialchars($professor['nomeUsuario']);
        $emailProfessor = htmlspecialchars($professor['email']);
        
        // Verificar se existe uma foto de perfil
        if ($professor['fotoPerfil']) {
            $fotoPerfil = base64_encode($professor['fotoPerfil']);
        } else {
            $fotoPerfil = null; // Caso não haja foto, você pode colocar uma imagem padrão
        }
    } else {
        die("Professor não encontrado.");
    }
} else {
    die("Email não fornecido na URL.");
}
?>


<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap"> <!-- Link para a fonte Open Sans -->
    <link rel="stylesheet" href="TelaConfiguracoesProfessor.css">
</head>
<body>
    <!-- Menu -->
    <div class="menu">
        <div class="menu-icon" onclick="toggleMenu()">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
        </div>
        <span class="helper-text" id="helperText">Helper in Olympics</span>
        <div class="menu-content" id="menuContent">
            <a href="#"><img src="Imagens_Mobile_HIO/iconeSeuPerfil.png" alt="Perfil" class="menu-icon-img"> Seu Perfil</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeChats.png" alt="Fórum" class="menu-icon-img"> Fórum</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeManual.png" alt="Manual" class="menu-icon-img"> Manual</a>
            <a href="#"><img src="Imagens_Mobile_HIO/iconeConfiguracoes.png" alt="Configurações" class="menu-icon-img"> Configurações</a>
            <a href="#"><img src="Imagens_Mobile_HIO/btnVoltarBRANCO.png" alt="Sair" class="menu-icon-img"> Sair</a>
        </div>
    </div>




    <!-- Conteúdo do Perfil -->
    <div class="content">
        <!-- Imagem no canto superior direito -->
        <div class="perfil-img">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/iconePerfilVazioRedonda.png" alt="Imagem">
        </div>
        <button class="dados-button">Seus Dados</button>




        <div class="profile-container">
               <h1 class="profile-name"><?php echo $nomeCompleto; ?></h1>
        <div class="profile-username"><?php echo $nomeUsuario; ?></div>
        <div class="profile-email"><?php echo $emailProfessor; ?></div>
            <hr class="profile-divider">
        </div>


   <!-- Modal para alterar dados -->
   <div class="modal" id="modalAlterarDados">
    <form action="alterarDados.php" method="POST">
        <div class="modal-content">
            <h2 class="modal-title">Alterar dados</h2>
            <div class="image-container">
                <img src="Imagens/iconePerfilVazioRedonda.png" alt="Imagem no modal" class="modal-image">
                <img src="Imagens/btnEditarFoto.png" alt="Imagem sobreposta" class="overlay-image">
            </div>

            <label class="modal-label">Nome completo:</label>
            <input type="text" name="nomeCompleto" class="modal-input" placeholder="Digite seu nome completo" required>

            <label class="modal-label">Nome de usuário:</label>
            <input type="text" name="nomeUsuario" class="modal-input" placeholder="Digite seu nome de usuário" required>

            <label class="modal-label">Email:</label>
            <input type="email" name="email" class="modal-input" placeholder="Digite seu email" value="<?php echo $email; ?>" readonly>


            <button type="submit" class="modal-button">Salvar</button>
        </div>
    </form>
</div>


    <!-- Modal para confirmação da alteração de dados -->
<div class="modal" id="modalConfirmacaoAlteracao">
    <div class="modal-content">
        <h2 class="modal-title">Digite sua senha para confirmar a alteração de dados</h2>
       
        <label class="modal-label">Senha:</label>
        <input type="password" class="modal-input" placeholder="Digite sua senha">


        <div class="button-group">
            <button class="modal-button confirmar">Confirmar</button>
            <button class="modal-button cancelar" onclick="closeModal('modalConfirmacaoAlteracao')">Cancelar</button>
        </div>
    </div>
</div>




<div class="button-grid">
    <button class="action-button" onclick="openModal('modalAlterarDados')">
        <img src="path/to/imagem1.png" class="button-icon">
        <strong>Alterar dados</strong>
    </button>




    <button class="action-button" onclick="abrirModalCodigoVerificacao()">
        <img src="path/to/imagem2.png" class="button-icon">
        <strong>Alterar senha</strong>
    </button>
   
    <!-- Modal para alterar senha-->
<div class="modal modal-senha" id="modalAlterarSenha">
    <div class="modal-content-senha">
        <h2 class="modal-title-senha">Foi enviado um código de verificação para seu email para permitir alteração de senha</h2>
        <input type="text" class="modal-input-senha" placeholder="Digite o código aqui">


        <button class="modal-button-senha" onclick="verificarCodigo()">Verificar</button>
        <button class="modal-button-senha" onclick="reenviarCodigo()">Enviar novamente</button>
        <button class="modal-button-senha" onclick="closeModal('modalAlterarSenha')">Cancelar</button>
    </div>
</div>


     <!-- Modal para informar nova senha -->
 <div class="modal-senha-nova" id="modalNovaSenha">
    <div class="modal-content-nova">
        <h2 class="modal-title-nova">Informe sua nova senha</h2>
        <label class="modal-label">Nova senha:</label>
        <input type="password" class="modal-input" placeholder="Digite sua nova senha">
       
        <label class="modal-label">Confirme nova senha:</label>
        <input type="password" class="modal-input" placeholder="Confirme sua nova senha">


        <div class="button-group">
            <button class="modal-button" onclick="salvarNovaSenha()">Alterar senha</button>
            <button class="modal-button" onclick="closeModal('modalNovaSenha')">Cancelar</button>
        </div>
    </div>
</div>




<!-- Botão para abrir o modal de histórico de cadastro -->
<button class="action-button" onclick="abrirModalHistoricoCadastro()">
    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/imagem3.png" class="button-icon">
    <strong>Histórico de cadastro</strong>
</button>


<!-- Modal de histórico de cadastro -->
<div id="modalHistoricoCadastro" class="modal-historico" style="display: none;">
    <div class="modal-content-historico">
        <span class="close-button-historico" onclick="fecharModalHistoricoCadastro()">&times;</span>
        <h2 class="modal-title">Histórico de cadastro</h2>
        <p class="modal-texto">Texto:</p>
       
        <!-- Carrossel -->
        <div class="carousel">
            <div class="carousel-content" id="carouselContent">
                <div class="info-box-container">


                   
                    <div class="info-box">
                        <div class="info-conteudo-texto">Entenda o que é velocidade</div>
                        <div class="circle-container">
                            <div class="circle blue"></div>
                            <span class="olympiad-name">OBF</span>
                        </div>
                    </div>


                    <div class="info-box">
                        <div class="info-conteudo-texto">ArrayList</div>
                        <div class="circle-container">
                            <div class="circle orange"></div>
                            <span class="olympiad-name">OBI</span>
                        </div>
                    </div>


                    <div class="info-box">
                        <div class="info-conteudo-texto">Regra de 3</div>
                        <div class="circle-container">
                            <div class="circle green"></div>
                            <span class="olympiad-name">OBMEP</span>
                        </div>
                    </div>


                    <div class="info-box">
                        <div class="info-conteudo-texto">Origem da vida</div>
                        <div class="circle-container">
                            <div class="circle green2"></div>
                            <span class="olympiad-name">OBG</span>
                        </div>
                    </div>
                     </div>
            </div>
            <div class="carousel-buttons">
                <button class="button-voltar" onclick="voltarCarrossel()">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar" class="button-icon">
                </button>
                <button class="button-prosseguir" onclick="prosseguirCarrossel()">
                    <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir" class="button-icon">
                </button>
            </div>
        </div>
        <p class="modal-video">Vídeo:</p>
<!-- Carrossel de Vídeo -->
<div class="carousel">
    <div class="carousel-content" id="videoCarouselContent">
        <div class="info-box-container">
            <div class="info-box">
                <div class="info-conteudo-texto">Entenda o que é velocidade</div>
                <img src="https://i.ytimg.com/vi/U5vvZQjiUwQ/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLBllQlWH59ybZmXUw5Rh4QSTM_fiQ" alt="Imagem 1" class="info-image">
                <div class="circle-container">
                    <div class="circle blue"></div>
                    <span class="olympiad-name">OBF</span>
                </div>
            </div>


            <div class="info-box">
                <div class="info-conteudo-texto">ArrayList</div>
                <img src="https://i.ytimg.com/vi/gmm7062i-tI/maxresdefault.jpg" alt="Imagem 1" class="info-image">
                <div class="circle-container">
                    <div class="circle orange"></div>
                    <span class="olympiad-name">OBI</span>
                </div>
            </div>


            <div class="info-box">
                <div class="info-conteudo-texto">Regra de 3</div>
                <img src="https://i.ytimg.com/vi/MccFSQeCS1M/maxresdefault.jpg" alt="Imagem 1" class="info-image">
                <div class="circle-container">
                    <div class="circle green"></div>
                    <span class="olympiad-name">OBMEP</span>
                </div>
            </div>


            <div class="info-box">
                <div class="info-conteudo-texto">Origem da vida</div>
                <img src="https://i.ytimg.com/vi/O5wz1CCPQeI/maxresdefault.jpg" alt="Imagem 1" class="info-image">
                <div class="circle-container">
                    <div class="circle green2"></div>
                    <span class="olympiad-name">OBG</span>
                </div>
            </div>
        </div>
    </div>
   
    <div class="carousel-buttons">
        <button class="button-voltar" onclick="voltarVideoCarousel()">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar" class="button-icon">
        </button>
        <button class="button-prosseguir" onclick="prosseguirVideoCarousel()">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir" class="button-icon">
        </button>
    </div>
</div>


<!-- Modal para Flashcard -->
<p class="modal-flashcard">Flashcard:</p>
<div class="carousel-flashcard">
    <div class="carousel-content-flashcard" id="carouselContentFlashcard">
        <div class="info-box-container">
            <div class="info-box">
                <div class="info-conteudo-texto">Entenda o que é velocidade</div>
                <div class="circle-container">
                    <div class="circle blue"></div>
                    <span class="olympiad-name">OBF</span>
                </div>
            </div>


            <div class="info-box">
                <div class="info-conteudo-texto">ArrayList</div>
                <div class="circle-container">
                    <div class="circle orange"></div>
                    <span class="olympiad-name">OBI</span>
                </div>
            </div>


            <div class="info-box">
                <div class="info-conteudo-texto">Regra de 3</div>
                <div class="circle-container">
                    <div class="circle green"></div>
                    <span class="olympiad-name">OBMEP</span>
                </div>
            </div>


            <div class="info-box">
                <div class="info-conteudo-texto">Origem da vida</div>
                <div class="circle-container">
                    <div class="circle green2"></div>
                    <span class="olympiad-name">OBG</span>
                </div>
            </div>
        </div>
    </div>
    <div class="carousel-buttons">
        <button class="button-voltar" onclick="voltarCarrosselFlashcard()">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar" class="button-icon">
        </button>
        <button class="button-prosseguir" onclick="prosseguirCarrosselFlashcard()">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir" class="button-icon">
        </button>
    </div>
</div>


    </div>
</div>





<!-- DELETAR CONTA -->
<button class="action-button" onclick="abrirModalDeletarConta()">
    <img src="path/to/imagem4.png" class="button-icon">
    <strong>Deletar conta</strong>
</button>

<!-- Modal para deletar conta -->
<div class="modal" id="modalDeletarConta">
    <div class="modal-content-deletar">
        <h2 class="modal-titleDeletar">Foi enviado um código de verificação para seu email para permitir a ação de deletar sua conta</h2>
        <input type="text" class="modal-inputDeletar" placeholder="Digite o código de verificação" id="codigoVerificacao">
        <button class="modal-buttonDeletar" onclick="verificarCodigoDeletar()">Verificar</button>
        <button class="modal-buttonDeletar" onclick="reenviarCodigoDeletar()">Enviar Novamente</button>
        <button class="modal-buttonDeletar" onclick="closeModal('modalDeletarConta')">Cancelar</button>
    </div>
</div>

<!-- Modal de confirmação para deletar conta -->
<div class="modal" id="modalConfirmacaoDeletar">
    <div class="modal-content-deletar">
        <h2 class="modal-titleDeletar">Tem certeza que quer deletar sua conta? Esta ação é irreversível.</h2>
        <div class="button-group">
            <button class="modal-buttonConfirmacao" onclick="deletarConta()">Sim</button>
            <button class="modal-buttonNao" onclick="closeModal('modalConfirmacaoDeletar')">Não</button>
        </div>
    </div>
</div>




       
        <script src="TelaConfiguracoesProfessor.js"></script>
    </body>
    </html>
