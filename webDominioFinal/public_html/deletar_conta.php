<?php
// verificar_exclusao.php

// Configurações do banco de dados
$dsn = 'mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';

$email = ''; // Inicializa a variável email para o uso posterior

// Verifica se o email está na URL
if (isset($_GET['email'])) {
    $email = $_GET['email'];
}

// Verifica se o formulário de verificação de senha foi enviado
if (isset($_POST['senha']) && isset($_POST['email'])) {
    $email = $_POST['email'];
    $senha = $_POST['senha'];

    try {
        // Conectar ao banco de dados
        $pdo = new PDO($dsn, $username, $password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Preparar a consulta para buscar a senha do usuário no banco de dados
        $sql = "SELECT senha FROM Professor WHERE email = :email";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':email', $email);
        $stmt->execute();

        // Verificar se o email existe no banco de dados
        if ($stmt->rowCount() > 0) {
            $user = $stmt->fetch(PDO::FETCH_ASSOC);
            $senhaHash = $user['senha'];

            // Verificar se a senha fornecida é a mesma que está no banco de dados
            if (password_verify($senha, $senhaHash)) {
                // Se a senha estiver correta, exibe o modal de confirmação de exclusão
                $senhaValida = true;
            } else {
                // Se a senha estiver incorreta
                $senhaValida = false;
            }
        } else {
            // Se o email não for encontrado
            $senhaValida = false;
        }
    } catch (PDOException $e) {
        $erro = 'Erro ao conectar ao banco de dados: ' . htmlspecialchars($e->getMessage());
    }
}

// Verifica se a exclusão foi confirmada
if (isset($_POST['confirmarExclusao']) && $_POST['confirmarExclusao'] == 'true') {
    try {
        // Conectar ao banco de dados novamente para excluir a conta
        $pdo = new PDO($dsn, $username, $password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Preparar a consulta para excluir o usuário
        $sql = "DELETE FROM Professor WHERE email = :email";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':email', $email);
        $stmt->execute();

        // Exibir um script para recarregar a página após a exclusão
        echo "<script>
                alert('Conta excluída com sucesso!');
                location.reload(); // Recarregar a página atual
              </script>";
        exit;

    } catch (PDOException $e) {
        $erro = 'Erro ao excluir a conta: ' . htmlspecialchars($e->getMessage());
    }
}
?>
