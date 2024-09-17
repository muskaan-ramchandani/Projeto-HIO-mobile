<?php
$servername = "localhost";
$username = "root"; // Substitua pelo seu nome de usuário
$password = "root"; // Substitua pela sua senha
$dbname = "hio"; // Nome do banco de dados

try {
    // Criar uma conexão com o banco de dados usando PDO
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    
    // Configurar o modo de erro do PDO para exceções
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
    // Obter dados do formulário
    $titulo = $_POST['titulo'];
    $subtitulo = $_POST['subtitulo'];
    $siglaOlimpiada = $_POST['siglaOlimpiada'];
    
    // Verificar se a sigla da olimpíada existe
    $stmt = $conn->prepare("SELECT COUNT(*) FROM Olimpiada WHERE sigla = :siglaOlimpiada");
    $stmt->bindParam(':siglaOlimpiada', $siglaOlimpiada, PDO::PARAM_STR);
    $stmt->execute();
    
    if ($stmt->fetchColumn() == 0) {
        throw new Exception("A sigla da olimpíada não existe.");
    }
    
    // Preparar a consulta SQL para inserir o conteúdo
    $sql = "INSERT INTO Conteudo (titulo, subtitulo, siglaOlimpiadaPertencente) VALUES (:titulo, :subtitulo, :siglaOlimpiada)";
    $stmt = $conn->prepare($sql);
    
    // Bind dos parâmetros
    $stmt->bindParam(':titulo', $titulo);
    $stmt->bindParam(':subtitulo', $subtitulo);
    $stmt->bindParam(':siglaOlimpiada', $siglaOlimpiada);
    
    // Executar a consulta
    $stmt->execute();
    
    echo "Conteúdo inserido com sucesso!";
    
} catch (Exception $e) {
    echo "Erro: " . $e->getMessage();
}

// Fechar a conexão
$conn = null;
?>
