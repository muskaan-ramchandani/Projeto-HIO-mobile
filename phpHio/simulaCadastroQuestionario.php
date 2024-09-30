<?php 
$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "hio";

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        // Inserir questionário
        $titulo = $_POST['titulo'];
        $profQuePostou = $_POST['profQuePostou'];
        $idConteudoPertencente = $_POST['idConteudoPertencente'];

        $sqlQuestionario = "INSERT INTO Questionario (titulo, profQuePostou, idConteudoPertencente) VALUES (:titulo, :profQuePostou, :idConteudoPertencente)";
        $stmtQuestionario = $conn->prepare($sqlQuestionario);
        $stmtQuestionario->bindParam(':titulo', $titulo);
        $stmtQuestionario->bindParam(':profQuePostou', $profQuePostou);
        $stmtQuestionario->bindParam(':idConteudoPertencente', $idConteudoPertencente);
        $stmtQuestionario->execute();
        $idQuestionario = $conn->lastInsertId();

        // Inserir questões e alternativas
        foreach ($_POST['perguntas'] as $index => $pergunta) {
            $txtPergunta = $pergunta['txtPergunta'];
            $explicacaoResposta = $pergunta['explicacaoResposta'];

            // Inserir questão
            $sqlQuestao = "INSERT INTO Questao (txtPergunta, explicacaoResposta, idQuestionarioPertencente) VALUES (:txtPergunta, :explicacaoResposta, :idQuestionario)";
            $stmtQuestao = $conn->prepare($sqlQuestao);
            $stmtQuestao->bindParam(':txtPergunta', $txtPergunta);
            $stmtQuestao->bindParam(':explicacaoResposta', $explicacaoResposta);
            $stmtQuestao->bindParam(':idQuestionario', $idQuestionario);
            $stmtQuestao->execute();
            $idQuestao = $conn->lastInsertId();

            // Inserir alternativas
            foreach ($pergunta['alternativas'] as $alternativa) {
                $textoAlternativa = $alternativa['textoAlternativa'];
                $corretaOuErrada = $alternativa['corretaOuErrada'];

                $sqlAlternativa = "INSERT INTO AlternativasQuestao (textoAlternativa, corretaOuErrada, idQuestionarioPertencente, idQuestaoPertencente) 
                                   VALUES (:textoAlternativa, :corretaOuErrada, :idQuestionario, :idQuestao)";
                $stmtAlternativa = $conn->prepare($sqlAlternativa);
                $stmtAlternativa->bindParam(':textoAlternativa', $textoAlternativa);
                $stmtAlternativa->bindParam(':corretaOuErrada', $corretaOuErrada);
                $stmtAlternativa->bindParam(':idQuestionario', $idQuestionario);
                $stmtAlternativa->bindParam(':idQuestao', $idQuestao);
                $stmtAlternativa->execute();
            }
        }

        echo "<p>Questionário cadastrado com sucesso!</p>";
    }
} catch(PDOException $e) {
    echo "Erro: " . $e->getMessage();
}
?>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Adicionar Questionário</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: auto;
            background-color: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 30px;
        }
        h1 {
            color: #6C63FF;
        }
        label, input, textarea, button {
            display: block;
            width: 100%;
            margin-bottom: 10px;
        }
        input, textarea {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            background-color: #6C63FF;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #5555CC;
        }
        .question-block {
            margin-top: 20px;
            padding: 15px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
        }
        .question-block h3 {
            margin-top: 0;
        }
        .add-button {
            background-color: #4CAF50;
        }
        .remove-button {
            background-color: #f44336;
        }
        .alternativa-block {
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Adicionar Questionário</h1>
        <form method="POST">
            <label for="titulo">Título do Questionário:</label>
            <input type="text" name="titulo" required>

            <label for="profQuePostou">Professor que Postou:</label>
            <input type="text" name="profQuePostou" required>

            <label for="idConteudoPertencente">ID do Conteúdo Pertencente:</label>
            <input type="number" name="idConteudoPertencente" required>

            <div id="questions-container">
                <div class="question-block">
                    <h3>Pergunta 1</h3>
                    <label for="txtPergunta">Texto da Pergunta:</label>
                    <textarea name="perguntas[0][txtPergunta]" required></textarea>

                    <label for="explicacaoResposta">Explicação da Resposta:</label>
                    <textarea name="perguntas[0][explicacaoResposta]" required></textarea>

                    <div class="alternativa-block">
                        <h4>Alternativas</h4>
                        <label for="textoAlternativa">Texto da Alternativa Correta:</label>
                        <input type="text" name="perguntas[0][alternativas][0][textoAlternativa]" required>
                        <input type="hidden" name="perguntas[0][alternativas][0][corretaOuErrada]" value="1">

                        <label for="textoAlternativa">Texto da Alternativa Incorreta:</label>
                        <input type="text" name="perguntas[0][alternativas][1][textoAlternativa]" required>
                        <input type="hidden" name="perguntas[0][alternativas][1][corretaOuErrada]" value="0">
                    </div>
                    <button type="button" class="add-button" onclick="addAlternative(0)">Adicionar Alternativa</button>
                </div>
            </div>

            <button type="button" onclick="addQuestion()">Adicionar Pergunta</button>
            <button type="submit">Salvar Questionário</button>
        </form>
    </div>

    <script>
        let questionCount = 1;

        function addQuestion() {
            const container = document.getElementById('questions-container');
            const questionBlock = document.createElement('div');
            questionBlock.classList.add('question-block');
            questionBlock.innerHTML = `
                <h3>Pergunta ${questionCount + 1}</h3>
                <label for="txtPergunta">Texto da Pergunta:</label>
                <textarea name="perguntas[${questionCount}][txtPergunta]" required></textarea>

                <label for="explicacaoResposta">Explicação da Resposta:</label>
                <textarea name="perguntas[${questionCount}][explicacaoResposta]" required></textarea>

                <div class="alternativa-block">
                    <h4>Alternativas</h4>
                    <label for="textoAlternativa">Texto da Alternativa Correta:</label>
                    <input type="text" name="perguntas[${questionCount}][alternativas][0][textoAlternativa]" required>
                    <input type="hidden" name="perguntas[${questionCount}][alternativas][0][corretaOuErrada]" value="1">

                    <label for="textoAlternativa">Texto da Alternativa Incorreta:</label>
                    <input type="text" name="perguntas[${questionCount}][alternativas][1][textoAlternativa]" required>
                    <input type="hidden" name="perguntas[${questionCount}][alternativas][1][corretaOuErrada]" value="0">
                </div>
                <button type="button" class="add-button" onclick="addAlternative(${questionCount})">Adicionar Alternativa</button>
            `;
            container.appendChild(questionBlock);
            questionCount++;
        }

        function addAlternative(questionIndex) {
            const alternativaBlock = document.querySelectorAll('.question-block')[questionIndex].querySelector('.alternativa-block');
            const alternativeCount = alternativaBlock.querySelectorAll('input[type="text"]').length;
            const newAlternativeHTML = `
                <label for="textoAlternativa">Texto da Alternativa:</label>
                <input type="text" name="perguntas[${questionIndex}][alternativas][${alternativeCount}][textoAlternativa]" required>
                <label for="corretaOuErrada">Correta (1) ou Errada (0):</label>
                <input type="number" name="perguntas[${questionIndex}][alternativas][${alternativeCount}][corretaOuErrada]" required>
            `;
            alternativaBlock.insertAdjacentHTML('beforeend', newAlternativeHTML);
        }
    </script>
</body>
</html>
