<?php
$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$username = 'root';
$password = 'root';

$mensagem = '';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    try {
        $pdo = new PDO($dsn, $username, $password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        if (isset($_POST['name'], $_POST['username'], $_POST['email'], $_POST['password'], $_POST['confirmPassword'])) {
            $name = trim($_POST['name']);
            $username = trim($_POST['username']);
            $email = trim($_POST['email']);
            $password = $_POST['password'];
            $confirmPassword = $_POST['confirmPassword'];

            // Verificar se as senhas coincidem
            if ($password !== $confirmPassword) {
                $mensagem = "As senhas não coincidem.";
            } else {
                $hashedPassword = password_hash(trim($password), PASSWORD_DEFAULT);

                $sql = "INSERT INTO professores (nome, nome_usuario, email, senha) VALUES (:name, :username, :email, :senha)";
                $stmt = $pdo->prepare($sql);

                $stmt->bindParam(':name', $name);
                $stmt->bindParam(':username', $username);
                $stmt->bindParam(':email', $email);
                $stmt->bindParam(':senha', $hashedPassword);

                if ($stmt->execute()) {
                    $mensagem = "Professor inserido com sucesso.";
                } else {
                    $mensagem = "Erro ao inserir professor.";
                }
            }
        } else {
            $mensagem = "Dados do formulário incompletos.";
        }
    } catch (PDOException $e) {
        $mensagem = "Erro: " . $e->getMessage();
    }

    $pdo = null;

    // Redireciona para o formulário HTML com uma mensagem de status
    header("Location: cadastra_professor.html?mensagem=" . urlencode($mensagem));
    exit();
}
