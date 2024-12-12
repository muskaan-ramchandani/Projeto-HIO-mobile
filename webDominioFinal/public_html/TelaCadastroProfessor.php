<?php
// Configurações do banco de dados
$dsn = 'mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';

// Verifica se o formulário foi enviado
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

            // Verifica se as senhas coincidem
            if ($password !== $confirmPassword) {
                $mensagem = "As senhas não coincidem.";
                header("Location: TelaCadastroProfessor.php?mensagem=" . urlencode($mensagem));
                exit();
            } else {
                // Gera o hash da senha
                $hashedPassword = password_hash(trim($password), PASSWORD_DEFAULT);

                // Prepara a consulta SQL para inserir um novo professor
                $sql = "INSERT INTO Professor (nomeCompleto, nomeUsuario, email, senha) VALUES (:name, :username, :email, :senha)";
                $stmt = $pdo->prepare($sql);

                // Associa os parâmetros aos valores fornecidos
                $stmt->bindParam(':name', $name);
                $stmt->bindParam(':username', $username);
                $stmt->bindParam(':email', $email);
                $stmt->bindParam(':senha', $hashedPassword);

                if ($stmt->execute()) {
                    // Exibe o alerta de sucesso e redireciona para a página inicial
                    echo "<script>
                        alert('Cadastro realizado com sucesso!');
                        window.location.href = 'index.php';
                    </script>";
                    exit();
                } else {
                    $mensagem = "Erro ao inserir professor.";
                }
            }
        } else {
            $mensagem = "Dados do formulário incompletos.";
        }
    } catch (PDOException $e) {
        // Captura e exibe erros de conexão ou execução de consultas
        $mensagem = "Erro: " . $e->getMessage();
    }

    // Fecha a conexão com o banco de dados
    $pdo = null;

    // Se ocorreu um erro, redireciona para o formulário com a mensagem de erro
    header("Location: TelaCadastroProfessor.php?mensagem=" . urlencode($mensagem));
    exit();
}
?>
