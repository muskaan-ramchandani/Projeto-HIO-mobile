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

#VALORES FICTÍCIOS
INSERT INTO Professor(nomeCompleto,nomeUsuario, email, senha)
VALUES ('Muskaan Ramchandani', 'muskaan.r13', 'ramchandani@gmail.com', '1302'),
('Juan Marcello Dell Oso', 'juanDellOso', 'juan@gmail.com', 'juan'),
('Maria Dorotéia Cunha Sevalho', 'doroteia', 'marydorotty@gmail.com', '1609');

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

#MATERIAIS
DROP TABLE IF EXISTS Texto;
CREATE TABLE Texto(
	id INT AUTO_INCREMENT NOT NULL,
    titulo VARCHAR(300) NOT NULL,
    texto TEXT NOT NULL,
    profQuePostou VARCHAR(100) NOT NULL,
    idConteudoPertencente INT NOT NULL,
    FOREIGN KEY(idConteudoPertencente) REFERENCES Conteudo(id), 
	FOREIGN KEY(profQuePostou) REFERENCES Professor(email),
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS Video;
CREATE TABLE Video(
	id INT AUTO_INCREMENT NOT NULL,
    capa LONGBLOB NOT NULL,
    titulo VARCHAR(300) NOT NULL,
    link TEXT NOT NULL,
	profQuePostou VARCHAR(100) NOT NULL,
    idConteudoPertencente INT NOT NULL,
	FOREIGN KEY(idConteudoPertencente) REFERENCES Conteudo(id), 
	FOREIGN KEY(profQuePostou) REFERENCES Professor(email),
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS Flashcard;
CREATE TABLE Flashcard(
	id INT AUTO_INCREMENT NOT NULL,
    imagem LONGBLOB NOT NULL,
    titulo VARCHAR(300) NOT NULL,
    texto TEXT NOT NULL,
	profQuePostou VARCHAR(100) NOT NULL,
    idConteudoPertencente INT NOT NULL,
	FOREIGN KEY(idConteudoPertencente) REFERENCES Conteudo(id), 
	FOREIGN KEY(profQuePostou) REFERENCES Professor(email),
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS Questionario;
CREATE TABLE Questionario(
	id INT AUTO_INCREMENT NOT NULL,
	titulo VARCHAR(300) NOT NULL,
    profQuePostou VARCHAR(100) NOT NULL,
    idConteudoPertencente INT NOT NULL,
	FOREIGN KEY(idConteudoPertencente) REFERENCES Conteudo(id), 
	FOREIGN KEY(profQuePostou) REFERENCES Professor(email),
	PRIMARY KEY(id)
);
    
DROP TABLE IF EXISTS Questao;
CREATE TABLE Questao(
	id INT AUTO_INCREMENT NOT NULL,
	txtPergunta TEXT NOT NULL,
    explicacaoResposta TEXT NOT NULL,
    idQuestionarioPertencente INT NOT NULL,
	FOREIGN KEY(idQuestionarioPertencente) REFERENCES Questionario(id), 
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS AlternativasQuestao;
CREATE TABLE AlternativasQuestao(
	id INT AUTO_INCREMENT NOT NULL,
	textoAlternativa TEXT NOT NULL,
    corretaOuErrada BOOLEAN NOT NULL,
    idQuestionarioPertencente INT NOT NULL,
    idQuestaoPertencente INT NOT NULL,
	FOREIGN KEY(idQuestionarioPertencente) REFERENCES Questionario(id), 
	FOREIGN KEY(idQuestaoPertencente) REFERENCES Questao(id), 
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS AcertosAluno;
CREATE TABLE AcertosAluno(
	id INT AUTO_INCREMENT NOT NULL,
    idAlternativaMarcada INT NOT NULL,
    idQuestionarioPertencente INT NOT NULL,
    idQuestaoPertencente INT NOT NULL,
    dataAcerto DATE NOT NULL,
	emailAluno VARCHAR(100) NOT NULL,
	FOREIGN KEY(emailAluno) REFERENCES Aluno(email),
    FOREIGN KEY(idAlternativaMarcada) REFERENCES AlternativasQuestao(id),
	FOREIGN KEY(idQuestionarioPertencente) REFERENCES Questionario(id), 
	FOREIGN KEY(idQuestaoPertencente) REFERENCES Questao(id), 
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS ErrosAluno;
CREATE TABLE ErrosAluno(
	id INT AUTO_INCREMENT NOT NULL,
    idAlternativaMarcada INT NOT NULL,
    idAlternativaCorreta INT NOT NULL,
    idQuestionarioPertencente INT NOT NULL,
    idQuestaoPertencente INT NOT NULL,
	dataErro DATE NOT NULL,
	emailAluno VARCHAR(100) NOT NULL,
	FOREIGN KEY(emailAluno) REFERENCES Aluno(email),
    FOREIGN KEY(idAlternativaMarcada) REFERENCES AlternativasQuestao(id),
	FOREIGN KEY(idQuestionarioPertencente) REFERENCES Questionario(id), 
	FOREIGN KEY(idQuestaoPertencente) REFERENCES Questao(id), 
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS PontuacaoAlunos;
CREATE TABLE PontuacaoAlunos(
	id INT AUTO_INCREMENT NOT NULL,
	emailAluno VARCHAR(100) NOT NULL,
    pontuacao INT NOT NULL,
	FOREIGN KEY(emailAluno) REFERENCES Aluno(email),
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS PerguntasForum;
CREATE TABLE PerguntasForum(
	id INT AUTO_INCREMENT NOT NULL,
	emailAluno VARCHAR(100) NOT NULL,
    titulo TEXT NOT NULL,
    pergunta TEXT NOT NULL,
	dataPublicacao DATE NOT NULL,
    siglaOlimpiadaRelacionada VARCHAR(10) NOT NULL,
	FOREIGN KEY(siglaOlimpiadaRelacionada) REFERENCES Olimpiada(sigla),
	FOREIGN KEY(emailAluno) REFERENCES Aluno(email),
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS RespostasForum;
CREATE TABLE RespostasForum(
	id INT AUTO_INCREMENT NOT NULL,
	emailProf VARCHAR(100) NOT NULL,
    resposta TEXT NOT NULL,
    dataResposta DATE NOT NULL,
    idPergunta INT NOT NUll,
	FOREIGN KEY(idPergunta) REFERENCES PerguntasForum(id),
	FOREIGN KEY(emailProf) REFERENCES Professor(email),
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS Eventos;
CREATE TABLE Eventos(
	id INT AUTO_INCREMENT NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    dataOcorrencia DATE NOT NULL,
    horarioComeco TIME NOT NULL,
    horarioFim TIME NOT NULL,
    link TEXT, 
    siglaOlimpiadaPertencente VARCHAR(10) NOT NULL,
	FOREIGN KEY(siglaOlimpiadaPertencente) REFERENCES Olimpiada(sigla),
	PRIMARY KEY(id)
);

#testes respostas
Insert into RespostasForum(emailProf, resposta, dataResposta, idPergunta)
values ('juan@gmail.com', 'Para organizar 6 RadioButtons em 2 linhas de 3 itens cada, mantendo a funcionalidade de selecionar apenas um botão por vez, você pode usar um RadioGroup com o atributo android:orientation="vertical", junto com um contêiner LinearLayout horizontal para cada linha.', '2024-11-20', 4),
('marydorotty@gmail.com', 'As temperaturas usadas no cálculo de rendimento (eficiência) de um ciclo termodinâmico devem sempre estar em Kelvin, pois a fórmula de rendimento térmico depende de razões absolutas. A escala Kelvin é usada para garantir que a razão entre temperaturas faça sentido físico, pois Kelvin começa no zero absoluto (0 K), onde não há energia térmica.', '2024-11-20', 5),
('marydorotty@gmail.com', 'Você pode usar um ConstraintLayout, pode organizar os botões em duas linhas de 3 itens, mantendo a funcionalidade de seleção única com o RadioGroup', '2024-11-20', 4),
('ramchandani@gmail.com', 'Outra abordagem é utilizar um TableLayout, que organiza os botões em uma grade de 2 linhas e 3 colunas. O RadioGroup ainda mantém a funcionalidade de seleção única.', '2024-11-20', 4),
('ramchandani@gmail.com', 'As temperaturas usadas no cálculo de rendimento devem estar em Kelvin, e isso não é apenas uma convenção, mas uma necessidade física. A escala Celsius não é absoluta e, ao usá-la diretamente na fórmula do rendimento o resultado pode ser incorreto, especialmente se t2 for maior que t1, o que não ocorre com Kelvin, pois ela não permite valores negativos.', '2024-11-20', 5);

INSERT INTO AcertosAluno (idAlternativaMarcada, idQuestionarioPertencente, idQuestaoPertencente, dataAcerto, emailAluno)
VALUES (61, 3, 11, '2024-10-21', 'isassevalho@gmail.com');

INSERT INTO ErrosAluno (idAlternativaMarcada, idAlternativaCorreta, idQuestionarioPertencente, idQuestaoPertencente, dataErro, emailAluno)
VALUES (22, 21, 1, 5, '2024-10-11', 'isassevalho@gmail.com');