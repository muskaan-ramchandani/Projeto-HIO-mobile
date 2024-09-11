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
	while ($result = $statement->fetch(PDO::FETCH_ASSOC)){
		$olimpiadas['olimpiadas'][] = (object) $result;
	}
	
	$json = json_encode($olimpiadas);
	
	//Caso queiram salvar um arquivo no servidor com os dados no formato json
	//file_put_contents("funcionarios.json", $json); 
	
	header('Content-Type: application/json');
	header('Character-Encoding: utf-8');	
	echo $json;
?>