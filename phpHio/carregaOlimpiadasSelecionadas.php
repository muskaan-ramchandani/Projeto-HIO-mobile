<?php
	header('Content-Type: application/json');
	header('Character-Enconding: utf-8');
	
	$pdo = new PDO('mysql:host=localhost;dbname=hio;port=3306;charset=utf8','root','root');
	
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