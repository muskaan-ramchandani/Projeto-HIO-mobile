<?php
session_start();

$dsn = 'mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';

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
                    $_SESSION['email'] = $email; 
                    header("Location: TelaInicialProfessorHTML.php?email=" . urlencode($email));
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

    header("Location: index.php?mensagem=" . urlencode($mensagem));
    exit();
}
?>
