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
    $texto = $_POST['texto'];
    $conteudo_id = $_POST['conteudo_id'];
    
    // Verificar se o ID do conteúdo existe
    $stmt = $conn->prepare("SELECT COUNT(*) FROM Conteudo WHERE id = :conteudo_id");
    $stmt->bindParam(':conteudo_id', $conteudo_id, PDO::PARAM_INT);
    $stmt->execute();
    
    if ($stmt->fetchColumn() == 0) {
        throw new Exception("O ID do conteúdo não existe.");
    }
    
    // Processar a imagem
    if (isset($_FILES['imagem']) && $_FILES['imagem']['error'] == 0) {
        $imagem = file_get_contents($_FILES['imagem']['tmp_name']);
    } else {
        $imagem = null; // Ou definir uma imagem padrão, se preferir
    }
    
    // Preparar a consulta SQL para inserir o flashcard
    $sql = "INSERT INTO Flashcard (titulo, texto, imagem, conteudo_id) VALUES (:titulo, :texto, :imagem, :conteudo_id)";
    $stmt = $conn->prepare($sql);
    
    // Bind dos parâmetros
    $stmt->bindParam(':titulo', $titulo);
    $stmt->bindParam(':texto', $texto);
    $stmt->bindParam(':imagem', $imagem, PDO::PARAM_LOB);
    $stmt->bindParam(':conteudo_id', $conteudo_id, PDO::PARAM_INT);
    
    // Executar a consulta
    $stmt->execute();
    
    echo "Flashcard inserido com sucesso!";
    
} catch (Exception $e) {
    echo "Erro: " . $e->getMessage();
}

// Fechar a conexão
$conn = null;
?>
