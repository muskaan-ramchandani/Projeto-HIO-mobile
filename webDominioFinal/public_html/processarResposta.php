<?php
// Configurações do banco de dados
$servername = "127.0.0.1:3306";
$username = "u740411060_user";
$password = "OWYzZ?o2";
$dbname = "u740411060_hio";

// Conexão com o banco de dados
try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "<p>Erro ao conectar ao banco de dados: " . htmlspecialchars($e->getMessage()) . "</p>";
    exit;
}

// Verifica se o formulário foi enviado
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $idPergunta = isset($_POST['idPergunta']) ? $_POST['idPergunta'] : null;
    $resposta = isset($_POST['resposta']) ? $_POST['resposta'] : null;
     $emailProf = isset($_POST['emailProf']) ? $_POST['emailProf'] : null;

    // Verifica se os dados foram preenchidos corretamente
    if ($idPergunta && $resposta && $emailProf) {
        // Sanitize os dados para evitar SQL Injection
        $idPergunta = htmlspecialchars($idPergunta);
        $resposta = htmlspecialchars($resposta);

        // Data da resposta
        $dataResposta = date("Y-m-d H:i:s");

        // Insere a resposta no banco de dados
        $sql = "INSERT INTO RespostasForum (emailProf, resposta, dataResposta, idPergunta) 
                VALUES (:emailProf, :resposta, :dataResposta, :idPergunta)";
        
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':emailProf', $emailProf);
        $stmt->bindParam(':resposta', $resposta);
        $stmt->bindParam(':dataResposta', $dataResposta);
        $stmt->bindParam(':idPergunta', $idPergunta);

      if ($stmt->execute()) {
            echo "<script>
                    alert('Resposta enviada com sucesso!');
                 window.location.href = 'TelaForumHTML.php?email=' + encodeURIComponent('$emailProf');

                  </script>";
        } else {
            echo "<script>alert('Erro ao enviar a resposta. Tente novamente.');</script>";
        }
    } else {
        echo "<script>alert('Por favor, preencha todos os campos corretamente.');</script>";
    }
} else {
    echo "<script>alert('Requisição inválida.');</script>";
}

?>
