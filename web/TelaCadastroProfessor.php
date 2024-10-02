<?php
// Configurações do banco de dados
$dsn = 'mysql:host=localhost;dbname=hio;charset=utf8';
$username = 'root';
$password = 'root';

// Variável para armazenar mensagens de status
$mensagem = '';

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
                    // Redireciona para a página de recepção após o sucesso
                    header("Location: TelaRecepcao.php");
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
    header("Location:TelaCadastroProfessor.php?mensagem=" . urlencode($mensagem));
    exit();
}
?>
