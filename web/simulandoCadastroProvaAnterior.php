<?php
    session_start();

    // Configurações do banco de dados
    $servername = "localhost";
    $username = "root";
    $password = "root";
    $dbname = "hio";

    try {
        // Conectando ao banco de dados usando PDO
        $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    } catch (PDOException $e) {
        die("Erro na conexão com o banco de dados: " . $e->getMessage());
    }

    // Verificar e definir o email do professor
    if (isset($_GET['email'])) {
        $email = $_GET['email'];
        $_SESSION['email'] = $email;  // Armazena o email na sessão
    } elseif (isset($_SESSION['email'])) {
        $email = $_SESSION['email'];
    } else {
        die("Erro: Professor não autenticado.");
    }

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $anoDaProva = $_POST['anoDaProva'];
        $estado = isset($_POST['estado']) ? 1 : 0;
        $fase = $_POST['fase'];
        $profQuePostou = $_POST['email'];
        $siglaOlimpiadaPertencente = $_POST['sigla'];

        // Verifica se o arquivo PDF foi enviado sem erros
        if (isset($_FILES['arquivoPdf']) && $_FILES['arquivoPdf']['error'] === UPLOAD_ERR_OK) {
            $arquivoPdf = file_get_contents($_FILES['arquivoPdf']['tmp_name']);

            // Inserir os dados no banco de dados
            $sql = "INSERT INTO ProvaAnterior (anoDaProva, estado, fase, profQuePostou, arquivoPdf, siglaOlimpiadaPertencente) 
                    VALUES (:anoDaProva, :estado, :fase, :profQuePostou, :arquivoPdf, :siglaOlimpiadaPertencente)";
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam(':anoDaProva', $anoDaProva);
            $stmt->bindParam(':estado', $estado);
            $stmt->bindParam(':fase', $fase);
            $stmt->bindParam(':profQuePostou', $profQuePostou);
            $stmt->bindParam(':arquivoPdf', $arquivoPdf, PDO::PARAM_LOB);
            $stmt->bindParam(':siglaOlimpiadaPertencente', $siglaOlimpiadaPertencente);

            if ($stmt->execute()) {
                echo "<p>Prova cadastrada com sucesso!</p>";
            } else {
                echo "<p>Erro ao cadastrar prova.</p>";
            }
        } else {
            // Verifica se houve um erro no upload do arquivo PDF
            $uploadError = $_FILES['arquivoPdf']['error'];
            if ($uploadError !== UPLOAD_ERR_OK) {
                echo "<p>Erro ao enviar o arquivo PDF. Código de erro: $uploadError</p>";
            }
        }
    }

    // Recuperar todas as olimpíadas para o select no formulário
    $sql = "SELECT nome, sigla FROM Olimpiada";
    $stmt = $pdo->prepare($sql);
    $stmt->execute();
    $olimpiadas = $stmt->fetchAll(PDO::FETCH_ASSOC);
    ?>
