CREATE DATABASE hio;
DROP DATABASE IF exists HIO;
USE hio;

DROP TABLE IF EXISTS Olimpiada;
CREATE TABLE Olimpiada(
    nome VARCHAR(300) NOT NULL,
    sigla VARCHAR(10) NOT NULL,
    icone VARCHAR(700) NOT NULL,
    cor VARCHAR(10) NOT NULL,
    PRIMARY KEY(sigla)
);

INSERT INTO Olimpiada VALUES
    ('Olimpíada Brasileira de Astronomia', 'OBA', 'imgtelescopio', 'Rosa'),
    ('Olimpíada Brasileira de Física', 'OBF', 'imgmacacaindo', 'Azul'),
    ('Olimpíada Brasileira de Informática', 'OBI', 'imgcomputador', 'Laranja'),
    ('Olimpíada Brasileira de Matemática das Escolas Públicas', 'OBMEP', 'imgoperacoesbasicas', 'Ciano'),
    ('Olimpíada Nacional da História Brasileira', 'ONHB', 'imgpapiro', 'Rosa'),
    ('Olimpíada Brasileira de Química', 'OBQ', 'imgtubodeensaio', 'Azul'),
    ('Olimpíada Brasileira de Biologia', 'OBB', 'imgdna', 'Laranja'),
    ('Olimpíada Nacional de Ciências', 'ONC', 'imgatomo', 'Ciano');

DROP TABLE IF EXISTS Aluno;
CREATE TABLE Aluno(
	nomeCompleto VARCHAR(200) NOT NULL,
    nomeUsuario VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    fotoPerfil LONGBLOB,
    PRIMARY KEY(email)
);

DROP TABLE IF EXISTS OlimpiadasSelecionadas;
CREATE TABLE OlimpiadasSelecionadas(
	id INT AUTO_INCREMENT NOT NULL,
    sigla VARCHAR(10) NOT NULL,
    emailAluno VARCHAR(100) NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(sigla) REFERENCES Olimpiada(sigla),
    FOREIGN KEY(emailAluno) REFERENCES Aluno(email),
    CONSTRAINT olimpiadaNRepete UNIQUE (emailAluno, sigla) 
    #Tabela com condição impedindo que uma olimpíada se repita para um mesmo aluno
);

DROP TABLE IF EXISTS Professor;
CREATE TABLE Professor (
    nomeCompleto VARCHAR(200) NOT NULL,
    nomeUsuario VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    fotoPerfil LONGBLOB,
    PRIMARY KEY(email)
);

DROP TABLE IF EXISTS Conteudo;
CREATE TABLE Conteudo(
	id INT AUTO_INCREMENT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    subtitulo VARCHAR(200) NOT NULL,
    siglaOlimpiadaPertencente VARCHAR(10) NOT NULL,
	FOREIGN KEY(siglaOlimpiadaPertencente) REFERENCES Olimpiada(sigla),
	CONSTRAINT tituloNRepete UNIQUE (siglaOlimpiadaPertencente, titulo),
    PRIMARY KEY(id)
);

##DDOS FICTICIOS PARA TESTE (SERAO CADASTRADOS PELOS PROFESSORES)
INSERT INTO Conteudo(titulo, subtitulo, siglaOlimpiadaPertencente) VALUES
    ('Mecânica Clássica', 'Fundamentos da cinemática do ponto material', 'OBF'),
    ('Dilatação Superficial', 'Conceito e fórmulas', 'OBF'),
    ('Estática e Hidrostática', 'Princípios Básicos', 'OBF'),
    ('Termologia', 'Termometria, Calorimetria, Termodinâmica', 'OBF'),
	('Campo Elétrico', 'Energia e trabalho', 'OBF'),
    
    ('Conteúdo oba 1', 'Subtitulo 1', 'OBA'),
	('Conteúdo oba 2', 'Subtitulo 2', 'OBA'),
    ('Conteúdo oba 3', 'Subtitulo 3', 'OBA'),
    ('Conteúdo oba 4', 'Subtitulo 4', 'OBA'),
    ('Conteúdo oba 5', 'Subtitulo 5', 'OBA'),
    
    ('Conteúdo obb 1', 'Subtitulo 1', 'OBB'),
	('Conteúdo obb 2', 'Subtitulo 2', 'OBB'),
    ('Conteúdo obb 3', 'Subtitulo 3', 'OBB'),
    ('Conteúdo obb 4', 'Subtitulo 4', 'OBB'),
    ('Conteúdo obb 5', 'Subtitulo 5', 'OBB'),
    
    ('Conteúdo obi 1', 'Subtitulo 1', 'OBI'),
	('Conteúdo obi 2', 'Subtitulo 2', 'OBI'),
    ('Conteúdo obi 3', 'Subtitulo 3', 'OBI'),
    ('Conteúdo obi 4', 'Subtitulo 4', 'OBI'),
    ('Conteúdo obi 5', 'Subtitulo 5', 'OBI');
    

DROP TABLE IF EXISTS Livro;
CREATE TABLE Livro(
	id INT AUTO_INCREMENT NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    capa LONGBLOB NOT NULL,
    titulo VARCHAR(300) NOT NULL,
    autor VARCHAR(200) NOT NULL,
	edicao INT NOT NULL,
    dataPublicacao DATE NOT NULL,
    siglaOlimpiadaPertencente VARCHAR(10) NOT NULL,
	FOREIGN KEY(siglaOlimpiadaPertencente) REFERENCES Olimpiada(sigla),
	CONSTRAINT livroNRepete UNIQUE (siglaOlimpiadaPertencente, isbn),
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS ProvaAnterior;
CREATE TABLE ProvaAnterior(
	id INT AUTO_INCREMENT NOT NULL,
    anoDaProva YEAR NOT NULL,
    estado BOOLEAN NOT NULL,
    fase INT NOT NULL,
    profQuePostou VARCHAR(100) NOT NULL,
    arquivoPdf LONGBLOB NOT NULL,
	siglaOlimpiadaPertencente VARCHAR(10) NOT NULL,
	FOREIGN KEY(siglaOlimpiadaPertencente) REFERENCES Olimpiada(sigla),
	FOREIGN KEY(profQuePostou) REFERENCES Professor(email),
    PRIMARY KEY(id)
);