<?php
session_start(); // Inicia ou retoma uma sessão existente

$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$username = 'root';
$password = 'root';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    try {
        $pdo = new PDO($dsn, $username, $password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        if (isset($_POST['email'], $_POST['password'])) {
            $email = trim($_POST['email']);
            $password = $_POST['password'];

            $sql = "SELECT senha FROM Professor WHERE email = :email";
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam(':email', $email);
            $stmt->execute();

            $user = $stmt->fetch(PDO::FETCH_ASSOC);

            if ($user) {
                if (password_verify($password, $user['senha'])) {
                    $_SESSION['email'] = $email; // Armazena o email na sessão
                    header("Location: TelaInicialProfessor.html?email=" . urlencode($email));
                    exit();
                } else {
                    $mensagem = "Senha incorreta.";
                }
            } else {
                $mensagem = "Usuário não encontrado.";
            }
        } else {
            $mensagem = "Dados do formulário incompletos.";
        }
    } catch (PDOException $e) {
        $mensagem = "Erro: " . $e->getMessage();
    }

    $pdo = null;

    header("Location: TelaRecepcao.html?mensagem=" . urlencode($mensagem));
    exit();
}
?>
