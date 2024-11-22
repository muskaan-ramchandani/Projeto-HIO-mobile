<?php
session_start();
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'vendor/autoload.php'; // Carregar PHPMailer

// Função para enviar o código de verificação
function enviarCodigoVerificacao($email) {
    // Gerar um código aleatório de 6 dígitos
    $codigoVerificacao = rand(100000, 999999);
    
    // Armazenar o código na sessão
    $_SESSION['codigoVerificacao'] = $codigoVerificacao;

    // Configuração do PHPMailer
    $mail = new PHPMailer(true);
    try {
        $mail->isSMTP();
        $mail->Host = 'smtp.gmail.com';
        $mail->SMTPAuth = true;
        $mail->Username = 'seu-email@gmail.com';
        $mail->Password = 'sua-senha';
        $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
        $mail->Port = 587;

        $mail->setFrom('seu-email@gmail.com', 'Sistema');
        $mail->addAddress($email);

        $mail->isHTML(true);
        $mail->Subject = 'Código de Verificação';
        $mail->Body    = "Seu código de verificação para deletar a conta é: <b>$codigoVerificacao</b>";

        $mail->send();
        return true;
    } catch (Exception $e) {
        return false;
    }
}

// Receber o e-mail da URL
$email = $_GET['email'] ?? '';
if ($email) {
    if (enviarCodigoVerificacao($email)) {
        echo "Código de verificação enviado.";
    } else {
        echo "Erro ao enviar o código de verificação.";
    }
}
?>
