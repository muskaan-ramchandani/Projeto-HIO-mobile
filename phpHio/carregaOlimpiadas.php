<?php
    header('Content-Type: application/json');
    header('Character-Enconding: utf-8');
    
    $servername = "localhost"; 
    $username = "root";        
    $password = "root";            
    $dbname = "hio";        
    
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    
    $sql = 'SELECT nome, sigla, icone, cor FROM Olimpiada';
    $statement = $pdo->prepare($sql);
    $statement->execute();
    
    $olimpiadas = array();
    while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
        $olimpiadas[] = (object) $result;
    }

    $ordem_cores = ['Rosa', 'Azul', 'Laranja', 'Ciano'];

    $grupos = [];
    foreach ($ordem_cores as $cor) {
        $grupos[$cor] = array_filter($olimpiadas, function ($item) use ($cor) {
            return $item->cor === $cor;
        });
    }

    $resultado = [];
    while (true) {
        $adicionou = false;
        foreach ($ordem_cores as $cor) {
            if (!empty($grupos[$cor])) {
                $resultado[] = array_shift($grupos[$cor]);
                $adicionou = true;
            }
        }
        if (!$adicionou) break; 
    }

    $json_resultado = ['olimpiadas' => $resultado];

    $json = json_encode($json_resultado);

    header('Content-Type: application/json');
    header('Character-Encoding: utf-8');    
    echo $json;
?>
