<?php
session_start();

$servername = "127.0.0.1:3306";
$username = "u740411060_user";
$password = "OWYzZ?o2";
$dbname = "u740411060_hio";

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
        $email = htmlspecialchars($professor['email']);
        
        // Verificar se existe uma foto de perfil
        if ($professor['fotoPerfil']) {
            $fotoPerfil = base64_encode($professor['fotoPerfil']);
        } else {
            $fotoPerfil = null; // Caso não haja foto, você pode colocar uma imagem padrão
        }
    } else {
        // Se o professor não for encontrado, redireciona para a TelaRecepcao.php
        header("Location: index.php");
        exit; // Certifique-se de chamar o exit para impedir a execução do código abaixo
    }
} else {
    // Se o email não for fornecido na URL, redireciona para a TelaRecepcao.php
    header("Location: index.php");
    exit;
}
?>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap"> <!-- Link para a fonte Open Sans -->
         <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
    <link rel="stylesheet" href="TelaConfiguracoesProfessor.css">
</head>
<body>
        <div class="menu">
        <div class="menu-icon" onclick="toggleMenu()">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
        </div>
        <span class="helper-text" id="helperText">Helper in Olympics</span>
        <div class="menu-content" id="menuContent">

        <a href="TelaPerfilProfessorHTML.php?email=<?php echo isset($_GET['email']) ? htmlspecialchars($_GET['email']) : ''; ?>">
        <img src="iconeSeuPerfil.png" alt="Perfil" class="menu-icon-img"> Seu Perfil
    </a>
             <a href="TelaForumHTML.php?email=<?php echo isset($_GET['email']) ? htmlspecialchars($_GET['email']) : ''; ?>">
        <img src="iconeChats.png" alt="Fórum" class="menu-icon-img"> Fórum
    </a>
        <a href="Manual_do_usuário_HIO.pdf?email=<?php echo isset($_GET['email']) ? htmlspecialchars($_GET['email']) : ''; ?>">
        <img src="iconeManual.png" alt="Fórum" class="menu-icon-img"> Manual
    </a>  
        <a href="TelaConfiguracoesProfessorHTML.php?email=<?php echo isset($_GET['email']) ? htmlspecialchars($_GET['email']) : ''; ?>">
        <img src="iconeConfiguracoes.png" alt="Configurações" class="menu-icon-img"> Configurações
    </a>
            <a href="index.php"><img src="iconeSair.png" alt="Sair" class="menu-icon-img"> Sair</a>
        </div>
    </div>





    <!-- Conteúdo do Perfil -->
    <div class="content">
        <!-- Imagem no canto superior direito -->
        <div class="perfil-img">
            <img src="iconePerfilVazioRedonda.png" alt="Imagem">
        </div>
        <button class="dados-button">Seus Dados</button>




        <div class="profile-container">
               <h1 class="profile-name"><?php echo $nomeCompleto; ?></h1>
        <div class="profile-username"><?php echo $nomeUsuario; ?></div>
        <div class="profile-email"><?php echo $email; ?></div>
            <hr class="profile-divider">
        </div>


   <!-- Modal para alterar dados -->
   <div class="modal" id="modalAlterarDados">
    <form action="alterarDadosPHP.php" method="POST">
        <div class="modal-content">
            <h2 class="modal-title">Alterar dados</h2>
            <div class="image-container">
                <img src="iconePerfilVazioRedonda.png" alt="Imagem no modal" class="modal-image">
                <img src="btnEditarFoto.png" alt="Imagem sobreposta" class="overlay-image">
            </div>

            <label class="modal-label">Nome completo:</label>
            <input type="text" name="nomeCompleto" class="modal-input" placeholder="Digite seu nome completo" required>

            <label class="modal-label">Nome de usuário:</label>
            <input type="text" name="nomeUsuario" class="modal-input" placeholder="Digite seu nome de usuário" required>

                        <input type="hidden" name="email" value="<?php echo htmlspecialchars($email); ?>">
            
         


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
        <img src="iconeAlterarDados.png" class="button-icon">
        <strong>Alterar dados</strong>
    </button>





    <button class="action-button" onclick="abrirModal('modalAlterarSenha')"">
        <img src="iconeAlterarSenha.png" class="button-icon">
        <strong>Alterar senha</strong>
    </button>
  
<?php
// index.php

// Inclui o arquivo que contém a lógica de verificação e exclusão
include('alterar_senha.php');
?>

    <!-- Modal para validação da senha atual -->
    <div class="modal" id="modalAlterarSenha">
        <div class="modal-content">
            <h2>Informe sua senha atual</h2>
            <form method="POST">
                <input type="hidden" name="email" value="<?php echo htmlspecialchars($email); ?>">
                <input type="password" name="senha_atual" placeholder="Senha atual" required>
                <button class="button button-primary" type="submit">Verificar</button>
                <button class="button button-secondary" type="button" onclick="fecharModal('modalAlterarSenha')">Cancelar</button>
            </form>
            <?php if (isset($erro)) echo "<div class='error'>$erro</div>"; ?>
        </div>
    </div>

    <!-- Modal para entrada da nova senha -->
    <?php if (isset($verificacao) && $verificacao): ?>
    <div class="modal" id="modalNovaSenha" style="display: flex;">
        <div class="modal-content">
            <h2>Informe sua nova senha</h2>
            <form method="POST">
                <input type="hidden" name="email" value="<?php echo htmlspecialchars($email); ?>">
                <input type="password" name="nova_senha" placeholder="Nova senha" required>
                <input type="password" name="confirmar_nova_senha" placeholder="Confirme nova senha" required>
                <button class="button button-primary" type="submit">Alterar senha</button>
                <button class="button button-secondary" type="button" onclick="fecharModal('modalNovaSenha')">Cancelar</button>
            </form>
            <?php if (isset($erro)) echo "<div class='error'>$erro</div>"; ?>
        </div>
    </div>
    <?php endif; ?>

    <!-- Mensagem de sucesso -->
    <?php if (isset($sucesso)) echo "<div class='success'>$sucesso</div>"; ?>




<!-- Botão para abrir o modal de histórico de cadastro -->
<button class="action-button" onclick="abrirModalHistoricoCadastro()">
    <img src="iconeHistoricoDeAcesso.png" class="button-icon">
    <strong>Histórico de cadastro</strong>
</button>


<!-- Modal de histórico de cadastro -->
<div id="modalHistoricoCadastro" class="modal-historico" style="display: none;">
    <div class="modal-content-historico">
        <span class="close-button-historico" onclick="fecharModalHistoricoCadastro()">&times;</span>
        <h2 class="modal-title">Histórico de cadastro</h2>
        <p class="modal-texto">Texto:</p>
       <?php
// index.php

// Inclui o arquivo que contém a lógica de verificação e exclusão
include('historico_texto.php');
?>
        <!-- Carrossel -->
<div class="carousel">
        <div class="carousel-content" id="carouselContent">
            <div class="info-box-container">
                <?php if (!empty($textos)): ?>
                    <?php foreach ($textos as $texto): ?>

                        <div class="info-box">
                            <div class="info-conteudo-texto"><?php echo htmlspecialchars($texto['titulo']); ?></div>
                            <div class="circle-container">
                                <div class="circle blue"></div>
                                <span class="olympiad-name"><?php echo htmlspecialchars($texto['profQuePostou']); ?></span>
                           
                            </div>
                        </div>
                    <?php endforeach; ?>
                <?php else: ?>
                    <p>Não há textos cadastrados por você.</p>
                <?php endif; ?>
            </div>
        </div>

    <!-- Controles do Carrossel -->
    <div class="carousel-buttons">
        <button class="button-voltar" onclick="voltarCarousel()">
            <img src="btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar" class="button-icon">
        </button>
        <button class="button-prosseguir" onclick="prosseguirCarrossel()">
            <img src="btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir" class="button-icon">
        </button>
    </div>
</div>

<?php
// index.php

// Inclui o arquivo que contém a lógica de verificação e exclusão
include('historico_video.php');
?>
        <p class="modal-video">Vídeo:</p>
<!-- Carrossel de Vídeo -->
<div class="carousel">
    <div class="carousel-content" id="videoCarouselContent">
        <?php if (!empty($videos)): ?>
            <?php foreach ($videos as $video): ?>
                <div class="info-box-container">
                    <div class="info-box">
                        <!-- Título do vídeo -->
                        <div class="info-conteudo-texto">
                            <?php echo htmlspecialchars($video['titulo']); ?>
                        </div>
                        <!-- Imagem de capa -->
                        <!-- Conteúdo pertencente -->
                        <div class="circle-container">
                            <div class="circle blue"></div>
            
                        </div>
                        <!-- Link do vídeo -->
                        <a href="<?php echo htmlspecialchars($video['link']); ?>" target="_blank" class="info-button">Assistir</a>
                    </div>
                </div>
            <?php endforeach; ?>
        <?php else: ?>
            <p>Não há vídeos cadastrados por você.</p>
        <?php endif; ?>
    </div>


   

    <div class="carousel-buttons">
        <button class="button-voltar" onclick="voltarVideoCarousel()">
            <img src="btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar" class="button-icon">
        </button>
        <button class="button-prosseguir" onclick="prosseguirVideoCarousel()">
            <img src="btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir" class="button-icon">
        </button>
    </div>
</div>



<?php
// index.php

// Inclui o arquivo que contém a lógica de verificação e exclusão
include('historico_flashcard.php');
?>
<!-- Modal para Flashcard -->
<p class="modal-flashcard">Flashcard:</p>
<div class="carousel-flashcard">
    <div class="carousel-content-flashcard" id="carouselContentFlashcard">
        <?php if (!empty($flashcards)): ?>
            <?php foreach ($flashcards as $flashcard): ?>
                <div class="info-box-container">
                    <div class="info-box">
                        <!-- Exibe o título do flashcard -->
                        <div class="info-conteudo-texto">
                            <?php echo htmlspecialchars($flashcard['titulo']); ?>
                        </div>
                        <!-- Exibe o conteúdo pertencente -->
                        <div class="circle-container">
                            <div class="circle blue"></div>
                            
                        </div>
                        <!-- Botão para abrir o modal do flashcard -->
                        <button class="info-button" onclick="openModal('modal-<?php echo $flashcard['id']; ?>')">
                            Ver Detalhes
                        </button>
                    </div>
                </div>

                <!-- Modal do Flashcard -->
                <div id="modal-<?php echo $flashcard['id']; ?>" class="modal" style="display: none;">
                    <div class="modal-content">
                        <span class="close" onclick="closeModal('modal-<?php echo $flashcard['id']; ?>')">&times;</span>
                        <h2><?php echo htmlspecialchars($flashcard['titulo']); ?></h2>
                        <p><?php echo htmlspecialchars($flashcard['texto']); ?></p>
                            <img src="flashcard_imagem.php?id=<?php echo $id; ?>" alt="Imagem do Flashcard">


                    </div>
                </div>
            <?php endforeach; ?>
        <?php else: ?>
            <p>Não há flashcards cadastrados por você.</p>
        <?php endif; ?>

                </div>
                

<div class="carousel-buttons">
        <button class="button-voltar" onclick="voltarCarrosselFlashcard()">
            <img src="btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar" class="button-icon">
        </button>
        <button class="button-prosseguir" onclick="prosseguirCarrosselFlashcard()">
            <img src="btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir" class="button-icon">
        </button>
    </div>
</div>
 </div>
  </div>
           

 <div id="modal-<?php echo $flashcard['id']; ?>" class="modal" style="display: none;">
                    <div class="modal-content">
                        <span class="close" onclick="closeModal('modal-<?php echo $flashcard['id']; ?>')">&times;</span>
                        <h2><?php echo htmlspecialchars($flashcard['titulo']); ?></h2>
                        <p><?php echo htmlspecialchars($flashcard['texto']); ?></p>
                     <img src="data:image/jpeg;base64,<?php echo base64_encode($flashcard['imagem']); ?>" alt="Imagem do Flashcard">

                    </div>
                </div>
           

<?php
// index.php

// Inclui o arquivo que contém a lógica de verificação e exclusão
include('deletar_conta.php');
?>


<!-- DELETAR CONTA -->
<button class="action-button" onclick="abrirModalDeletarConta()">
    <img src="iconeDeletarConta.png" class="button-icon">
    <strong>Deletar conta</strong>
</button>
<!-- Modal para deletar conta -->
<?php if (isset($contaDeletada) && $contaDeletada): ?>
    <p>Conta deletada com sucesso!</p>
<?php elseif (isset($erro)): ?>
    <p>Erro: <?php echo $erro; ?></p>
<?php elseif (isset($senhaValida) && $senhaValida): ?>
    <p>A senha está correta. Agora, por favor, confirme a exclusão da conta.</p>
<?php endif; ?>

<!-- Modal para deletar conta -->
<div class="modal" id="modalDeletarConta" style="display: <?php echo isset($senhaValida) && !$senhaValida ? 'block' : 'none'; ?>;">
    <div class="modal-content-deletar">
        <h2 class="modal-titleDeletar">Informe sua senha para deletar a conta</h2>
        <form method="POST" action="">
            <input type="hidden" name="email" value="<?php echo htmlspecialchars($email); ?>">
            <input type="password" class="modal-inputDeletar" placeholder="Digite a sua senha" id="codigoVerificacao" name="senha" required>
            <button class="modal-buttonDeletar" type="submit">Verificar</button>
        </form>
        <br>
        <button class="modal-buttonDeletar" onclick="closeModal('modalDeletarConta')">Cancelar</button>
    </div>
</div>

<!-- Modal de confirmação para deletar conta -->
<div class="modal" id="modalConfirmacaoDeletar" style="display: <?php echo isset($senhaValida) && $senhaValida ? 'block' : 'none'; ?>;">
    <div class="modal-content-deletar">
        <h2 class="modal-titleDeletar">Tem certeza que quer deletar sua conta? Esta ação é irreversível.</h2>
        <form method="POST" action="">
            <input type="hidden" name="email" value="<?php echo htmlspecialchars($email); ?>">
            <input type="hidden" name="confirmarExclusao" value="true">
            <button class="modal-buttonConfirmacao" type="submit">Sim</button>
        </form>
        <button class="modal-buttonNao" onclick="closeModal('modalConfirmacaoDeletar')">Não</button>
    </div>
</div>






       
        <script src="TelaConfiguracoesProfessor.js"></script>
    </body>
    </html>
