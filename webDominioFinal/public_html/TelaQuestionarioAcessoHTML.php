<?php
$dsn = 'mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';

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
    exit;
}

// Aqui você inclui o arquivo 'puxar_questionario.php' para buscar as questões
// Supondo que o código em 'puxar_questionario.php' retorne as variáveis $questionario e $alternativasPorPergunta
?>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
    <link href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css" rel="stylesheet">

    <link rel="stylesheet" href="TelaQuestionarioAcesso.css"> <!-- Link para o CSS -->
</head>
<body>
    <div class="barra">
        <div class="logo-container">
            <img src="https://hio.lat/iconeQuestionarios.png">
            
        </div>
        <div class="button-item" onclick="window.history.back();" style="cursor: pointer;">
    <img src="btnVoltarBRANCO.png" alt="Voltar">
    <div class="button-label">Voltar</div>
</div>

    </div>
   <?php
include 'puxar_questionario.php';
?>
    <!-- Informações do Questionário -->
    <div class="title"><?= htmlspecialchars($questionario['titulo']) ?></div>
    <div class="subtitle">Por: <?= htmlspecialchars($questionario['profQuePostou']) ?></div>

    <!-- Exibição das Questões -->
    <div id="question-container">
        <?php foreach ($questoes as $index => $questao): ?>
            <div class="question" id="question-<?= $index ?>" style="display: <?= $index === 0 ? 'block' : 'none'; ?>">
                <strong>Pergunta:</strong> <?= htmlspecialchars($questao['txtPergunta']) ?>

                <!-- Exibição das Alternativas -->
                <div class="options-container">
                    <?php 
                    // Verifica se existem alternativas para a questão
                    if (isset($alternativasPorPergunta[$questao['id']])): 
                    ?>
                        <div class="options">
                            <?php foreach ($alternativasPorPergunta[$questao['id']] as $alternativa): ?>
                                <label>
                                    <input type="radio" name="item<?= $questao['id'] ?>" value="<?= htmlspecialchars($alternativa['textoAlternativa']) ?>" 
                                    <?php 
                                    // Marca a alternativa correta
                                    if ($alternativa['corretaOuErrada'] == 1) echo 'checked'; 
                                    ?>>
                                    <?= htmlspecialchars($alternativa['textoAlternativa']) ?>
                                </label>
                            <?php endforeach; ?>
                        </div>
                    <?php else: ?>
                        <div class="options">
                            <p><em>Não há alternativas para esta questão.</em></p>
                        </div>
                    <?php endif; ?>
                </div>
<br>
                <!-- Resposta certa (marcada ou destacada) -->
                <div class="correct-answer">
                    <strong>Resposta certa:</strong> 
                    <?php 
                    $respostaCerta = '';
                    foreach ($alternativasPorPergunta[$questao['id']] as $alternativa) {
                        if ($alternativa['corretaOuErrada'] == 1) {
                            $respostaCerta = $alternativa['textoAlternativa'];
                            break;
                        }
                    }
                    echo htmlspecialchars($respostaCerta);
                    ?>
                </div>
            </div>
        <?php endforeach; ?>
    </div>

    <div class="button-container">
        <button class="back-button" id="back-button">
            <img src="btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar">
        </button>
        <button class="response-button">Resposta</button>
        <button class="next-button" id="next-button">
            <img src="btnVoltarEAvancarExercicioDireita.png" alt="Prosseguir">
        </button>
    </div>

    <script>
        let currentQuestionIndex = 0; // Índice da questão atual
        const questions = document.querySelectorAll('.question'); // Coleta todas as questões
        const nextButton = document.getElementById('next-button');
        const backButton = document.getElementById('back-button');

        // Função para mostrar a próxima questão
        nextButton.addEventListener('click', function() {
            if (currentQuestionIndex < questions.length - 1) {
                questions[currentQuestionIndex].style.display = 'none'; // Esconde a questão atual
                currentQuestionIndex++; // Avança para a próxima questão
                questions[currentQuestionIndex].style.display = 'block'; // Exibe a próxima questão
            } else {
                alert("Você completou todas as questões!");
            }
        });

        // Função para mostrar a questão anterior
        backButton.addEventListener('click', function() {
            if (currentQuestionIndex > 0) {
                questions[currentQuestionIndex].style.display = 'none'; // Esconde a questão atual
                currentQuestionIndex--; // Volta para a questão anterior
                questions[currentQuestionIndex].style.display = 'block'; // Exibe a questão anterior
            } else {
                alert("Você está na primeira questão!");

            }
        });
    </script>

    <style>
        /* Estilo para os botões de navegação */
        button {
            padding: 10px 20px; /* Reduzir o preenchimento do botão */
            font-size: 14px; /* Tamanho da fonte menor */
            width: auto; /* Deixa o tamanho do botão automático */
            background-color: #4CAF50; /* Cor de fundo do botão */
            border: none; /* Sem borda */
            color: white; /* Cor do texto */
            border-radius: 5px; /* Bordas arredondadas */
            cursor: pointer; /* Cursor de ponteiro ao passar sobre o botão */
        }

        button img {
            width: 60px; /* Define o tamanho das imagens dentro dos botões */
            height: 60px; /* Define o tamanho das imagens dentro dos botões */
            vertical-align: middle; /* Alinha a imagem verticalmente */
        }
    </style>

</body>
</html>
