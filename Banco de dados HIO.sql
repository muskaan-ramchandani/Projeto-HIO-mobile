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

SELECT * FROM Olimpiada;
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
SELECT * FROM Aluno;
CREATE TABLE Aluno(
	nomeCompleto VARCHAR(200) NOT NULL,
    nomeUsuario VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    fotoPerfil LONGBLOB,
    PRIMARY KEY(email)
);

