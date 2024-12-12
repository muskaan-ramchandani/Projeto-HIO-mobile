<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Recebendo os dados enviados pelo formulário
    $nomeCompleto = $_POST['nomeCompleto'];
    $nomeUsuario = $_POST['nomeUsuario'];
    $email = $_POST['email'];

    try {
        // Configurações do banco de dados
        $dsn = "mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8mb4";
        $dbUser = "u740411060_user";
        $dbPassword = "OWYzZ?o2";

        // Conexão usando PDO
        $pdo = new PDO($dsn, $dbUser, $dbPassword);

        // Configurando o PDO para lançar exceções em caso de erro
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Query de atualização
        $sql = "UPDATE Professor SET nomeCompleto = :nomeCompleto, nomeUsuario = :nomeUsuario WHERE email = :email";

        // Preparando e executando a declaração
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':nomeCompleto', $nomeCompleto, PDO::PARAM_STR);
        $stmt->bindParam(':nomeUsuario', $nomeUsuario, PDO::PARAM_STR);
        $stmt->bindParam(':email', $email, PDO::PARAM_STR);

        if ($stmt->execute()) {
            echo "Dados alterados com sucesso!";
        } else {
            echo "Erro ao alterar os dados.";
        }
    } catch (PDOException $e) {
        echo "Erro na conexão ou na execução: " . $e->getMessage();
    }
}
?> 
