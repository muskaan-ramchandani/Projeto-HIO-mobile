<?php
    header('Content-Type: application/json; charset=utf-8');

    $json_entrada = json_decode(file_get_contents('php://input'), true);

    if (isset($json_entrada['nomeCompleto'], $json_entrada['nomeUsuario'], $json_entrada['email'], $json_entrada['senha'])) {

        $nomeCompleto = $json_entrada['nomeCompleto'];
        $nomeUsuario = $json_entrada['nomeUsuario'];
        $email = $json_entrada['email'];
        $senha = $json_entrada['senha'];


        try {
            $pdo = new PDO('mysql:host=localhost;dbname=hio;port=3306;charset=utf8', 'root', 'root');
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            // Verifica se o e-mail já está cadastrado
            $verificarEmail = $pdo->prepare('SELECT email FROM Aluno WHERE email = :email');
            $verificarEmail->bindValue(':email', $email, PDO::PARAM_STR);
            $verificarEmail->execute();

            if ($verificarEmail->rowCount() > 0) {
                echo json_encode(["status" => "error", "message" => "O e-mail já está cadastrado."]);
                exit;
            }

            $sql = 'INSERT INTO Aluno (nomeCompleto, nomeUsuario, email, senha) VALUES (:nomeCompleto, :nomeUsuario, :email, :senha)';
            $statement = $pdo->prepare($sql);

            $statement->bindValue(':nomeCompleto', $nomeCompleto, PDO::PARAM_STR);
            $statement->bindValue(':nomeUsuario', $nomeUsuario, PDO::PARAM_STR);
            $statement->bindValue(':email', $email, PDO::PARAM_STR);
            $statement->bindValue(':senha', password_hash($senha, PASSWORD_DEFAULT), PDO::PARAM_STR); // Hash da senha

            $statement->execute();

            echo json_encode(["status" => "success", "message" => "Cadastro realizado com sucesso!"]);

        } catch (PDOException $e) {
            echo json_encode(["status" => "error", "message" => $e->getMessage()]);
        }

    } else {
        echo json_encode(["status" => "error", "message" => "Dados incompletos."]);
    }
?>
