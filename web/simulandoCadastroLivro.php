
    <?php
    if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submit'])) {
        // Configurações do banco de dados
        $servername = "localhost"; 
        $username = "root";        
        $password = "root";            
        $dbname = "hio";     

        try {
            $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            echo "<p>Erro na conexão com o banco de dados: " . $e->getMessage() . "</p>";
            exit;
        }

        // Recebendo os dados do formulário
        $isbn = $_POST['isbn'];
        $titulo = $_POST['titulo'];
        $autor = $_POST['autor'];
        $edicao = (int)$_POST['edicao'];
        $dataPublicacao = $_POST['dataPublicacao'];
        $siglaOlimpiadaPertencente = $_POST['sigla'];

        // Verificando se o arquivo de imagem foi enviado corretamente
        if (isset($_FILES['capa']) && $_FILES['capa']['error'] === UPLOAD_ERR_OK) {
            $capaTmp = $_FILES['capa']['tmp_name'];
            $capaConteudo = file_get_contents($capaTmp); // Conteúdo da imagem
        } else {
            echo "<p>Erro ao fazer upload da imagem da capa.</p>";
            exit;
        }

        // Inserir os dados no banco de dados
        $sql = "INSERT INTO Livro (isbn, capa, titulo, autor, edicao, dataPublicacao, siglaOlimpiadaPertencente) 
                VALUES (:isbn, :capa, :titulo, :autor, :edicao, :dataPublicacao, :sigla)";

        try {
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam(':isbn', $isbn);
            $stmt->bindParam(':capa', $capaConteudo, PDO::PARAM_LOB);
            $stmt->bindParam(':titulo', $titulo);
            $stmt->bindParam(':autor', $autor);
            $stmt->bindParam(':edicao', $edicao);
            $stmt->bindParam(':dataPublicacao', $dataPublicacao);
            $stmt->bindParam(':sigla', $siglaOlimpiadaPertencente);

            $stmt->execute();
            echo "<p>Livro cadastrado com sucesso!</p>";
        } catch (PDOException $e) {
            echo "<p>Erro ao cadastrar o livro: " . $e->getMessage() . "</p>";
        }

        $pdo = null; // Fechar conexão
    }
    ?>
</body>
</html>
