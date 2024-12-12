<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Configurações do banco de dados
    $servername = "127.0.0.1:3306"; 
    $username = "u740411060_user";        
    $password = "OWYzZ?o2";            
    $dbname = "u740411060_hio";     

    try {
        $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    } catch (PDOException $e) {
        echo "<script>alert('Erro na conexão com o banco de dados: " . $e->getMessage() . "');</script>";
        exit;
    }

    // Recebendo os dados do formulário
    $isbn = $_POST['isbn'];
    $titulo = $_POST['titulo'];
    $autor = $_POST['autor'];
    $edicao = (int)$_POST['edicao'];
    $dataPublicacao = $_POST['dataPublicacao'];
    $siglaOlimpiadaPertencente = $_POST['sigla'];
    $email = $_POST['email'];  // Adicionando o email que será passado na URL

    // Verificando se o arquivo de imagem foi enviado corretamente
    if (isset($_FILES['capa']) && $_FILES['capa']['error'] === UPLOAD_ERR_OK) {
        $capaTmp = $_FILES['capa']['tmp_name'];
        $capaNome = $_FILES['capa']['name'];
        $capaExt = pathinfo($capaNome, PATHINFO_EXTENSION);
        
        // Verificando a extensão do arquivo
        $allowedExts = ['jpg', 'jpeg', 'png', 'gif'];
        if (!in_array(strtolower($capaExt), $allowedExts)) {
            echo "<script>alert('Formato de imagem não permitido. Aceitos: jpg, jpeg, png, gif.');</script>";
            exit;
        }

        // Definir o caminho de upload da capa
        $uploadDir = 'uploads/covers/';
        if (!is_dir($uploadDir)) {
            mkdir($uploadDir, 0777, true); // Cria o diretório caso não exista
        }
        
        $uploadPath = $uploadDir . basename($capaNome);
        if (move_uploaded_file($capaTmp, $uploadPath)) {
            // A capa foi movida para o diretório e o caminho foi salvo
        } else {
            echo "<script>alert('Erro ao fazer upload da capa.');</script>";
            exit;
        }
    } else {
        echo "<script>alert('Erro ao fazer upload da imagem da capa.');</script>";
        exit;
    }

    // Inserir os dados no banco de dados
    $sql = "INSERT INTO Livro (isbn, capa, titulo, autor, edicao, dataPublicacao, siglaOlimpiadaPertencente) 
            VALUES (:isbn, :capa, :titulo, :autor, :edicao, :dataPublicacao, :sigla)";

    try {
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':isbn', $isbn);
        $stmt->bindParam(':capa', $uploadPath); // Aqui agora é o caminho da capa no servidor
        $stmt->bindParam(':titulo', $titulo);
        $stmt->bindParam(':autor', $autor);
        $stmt->bindParam(':edicao', $edicao);
        $stmt->bindParam(':dataPublicacao', $dataPublicacao);
        $stmt->bindParam(':sigla', $siglaOlimpiadaPertencente);

        $stmt->execute();

        // Redireciona após o cadastro
        echo "<script>
                alert('Livro cadastrado com sucesso!');
                window.location.href = 'TelaOlimpiadaProfessorHTML.php?sigla=" . urlencode($siglaOlimpiadaPertencente) . "&email=" . urlencode($email) . "';
              </script>";
    } catch (PDOException $e) {
        echo "<script>alert('Erro ao cadastrar o livro: " . $e->getMessage() . "');</script>";
    }

    $pdo = null; // Fechar conexão
}
?>
