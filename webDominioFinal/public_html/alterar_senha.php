<?php
session_start();
$sucesso = null;
$erro = null;

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['senha_atual'])) {
    $email = $_POST['email'];
    $senhaAtual = $_POST['senha_atual'];

  $dsn = 'mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';

    try {
        $pdo = new PDO($dsn, $username, $password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Consulta para verificar a senha atual
        $stmt = $pdo->prepare('SELECT senha FROM Professor WHERE email = :email');
        $stmt->bindParam(':email', $email);
        $stmt->execute();

        $user = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($user && password_verify($senhaAtual, $user['senha'])) {
            // Senha válida, mostrar o modal de alteração de senha
            $_SESSION['senha_verificada'] = true;
        } else {
            $erro = 'Senha atual incorreta.';
        }
    } catch (PDOException $e) {
        $erro = 'Erro ao conectar ao banco de dados: ' . $e->getMessage();
    }
}

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['nova_senha'])) {
    // Lógica para alterar a senha
    if ($_POST['nova_senha'] === $_POST['confirmar_nova_senha'] && isset($_SESSION['senha_verificada']) && $_SESSION['senha_verificada']) {
        $novaSenha = password_hash($_POST['nova_senha'], PASSWORD_DEFAULT);

        try {
            $stmt = $pdo->prepare('UPDATE professor SET senha = :nova_senha WHERE email = :email');
            $stmt->bindParam(':nova_senha', $novaSenha);
            $stmt->bindParam(':email', $email);
            $stmt->execute();

            $sucesso = 'Senha alterada com sucesso!';
            unset($_SESSION['senha_verificada']);
        } catch (PDOException $e) {
            $erro = 'Erro ao alterar a senha: ' . $e->getMessage();
        }
    } else {
        $erro = 'As senhas não coincidem ou a senha antiga não foi verificada.';
    }
}
?>


