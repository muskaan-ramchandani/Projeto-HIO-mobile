<?php
// Configurações do banco de dados
$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$dbUsername = 'root';
$dbPassword = 'root';

$mensagem = '';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    try {
        // Conectar ao banco de dados
        $pdo = new PDO($dsn, $dbUsername, $dbPassword);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Verificar se os campos foram preenchidos
        if (isset($_POST['email'], $_POST['password'])) {
            $emailOrUsername = trim($_POST['email']);
            $password = $_POST['password'];

            // Verificar se o usuário existe
            $sql = "SELECT senha FROM professores WHERE email = :emailOrUsername OR nome_usuario = :emailOrUsername";
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam(':emailOrUsername', $emailOrUsername);
            $stmt->execute();

            if ($stmt->rowCount() > 0) {
                // Recuperar o hash da senha
                $hashedPassword = $stmt->fetchColumn();

                // Verificar a senha
                if (password_verify($password, $hashedPassword)) {
                    // Login bem-sucedido
                    session_start();
                    $_SESSION['loggedin'] = true;
                    $_SESSION['user'] = $emailOrUsername; // Guardar o nome de usuário ou email
                    header("Location: TelaInicialProfessor.html"); // Redirecionar para a página inicial do professor
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

    // Redirecionar de volta com uma mensagem
    header("Location: TelaRecepcao.html?mensagem=" . urlencode($mensagem));
    exit();
}
?>
