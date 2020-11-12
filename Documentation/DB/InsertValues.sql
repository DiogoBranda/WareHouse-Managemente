--SET search_path to warehouse;
INSERT INTO armazem(nome,localizacao)
VALUES('Armazem Auto Boys','Porto');

INSERT INTO fornecedor(nome,id_armazem)
VALUES ('Adidas', (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('Nike', (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('ZARA', (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('Jumbo', (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('SportZone', (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('decathlon', (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('C&A', (SELECT id from armazem where nome='Armazem Auto Boys'));

INSERT INTO maquina(numero_serie,nome,avariada,id_armazem)
VALUES ('10003-2123','Broca', 'false',(SELECT id from armazem where nome='Armazem Auto Boys')),
       ('1100-xa-12','Forno', 'false',(SELECT id from armazem where nome='Armazem Auto Boys')),
       ('1200','Tapete estacao 1', 'false',(SELECT id from armazem where nome='Armazem Auto Boys')),
       ('1201','Tapete estacao 2', 'false',(SELECT id from armazem where nome='Armazem Auto Boys')),
       ('1202','Tapete estacao 3', 'false',(SELECT id from armazem where nome='Armazem Auto Boys')),
       ('432012314','Impressora 3D', 'false',(SELECT id from armazem where nome='Armazem Auto Boys')),
       ('43212aa','Maquina de costura', 'false',(SELECT id from armazem where nome='Armazem Auto Boys')),
       ('aswr142-2','Maquina de sapatilhas', 'false',(SELECT id from armazem where nome='Armazem Auto Boys')),
       ('45100','Maquina de 2 agulhas', 'false',(SELECT id from armazem where nome='Armazem Auto Boys'));

INSERT INTO funcionario(nif,nome,idade,funcao,salario,id_armazem)
VALUES ('252637780','João Silva',35,'Engenheiro',2000, (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('298978890','Carlos Couto',35,'Receiver/Shipper',1000.1, (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('240446208','Joana Andrade',42,'Receiver/Shipper',1000, (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('280126441','Davide Soares',32,'Loader/Unloader',800, (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('282424830','Duarte Pereira',37,'Loader/Unloader',800, (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('244825440','João Martins',31,'Loader/Unloader',800.5, (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('268724261','Bernardo Costa',37,'Loader/Unloader',800, (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('255550855','João Martins',39,'Manager',2000.75, (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('215269152','José Azevedo',42,'Janitor',700, (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('211954179','Luís Silva',42,'Janitor',700, (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('253342139','Samuel Azevedo',30,'Janitor',700, (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('257488820','Fábio Morais',22,'Engenheiro Eltrotecnico',1200, (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('209098252','Diogo Araújo',28,'Janitor',700, (SELECT id from armazem where nome='Armazem Auto Boys'))
EXCEPT SELECT nif,nome,idade,funcao,salario,id_armazem FROM funcionario;

INSERT INTO login(username,password,data_criacao,admin,nif_funcionario)
VALUES('admin','$2y$10$bJcqwN1.bAA4EbdZxxPsKeOaradjdjqF3BZoXKeO7dRriJ7vt2DLm','2019-11-09 12:20:19','true','252637780');

--username = admin; password = admin

INSERT INTO categoria_produto(nome,id_armazem)
VALUES ('calçado', (SELECT id from armazem where nome='Armazem Auto Boys')),
       ('roupa', (SELECT id from armazem where nome='Armazem Auto Boys'));

INSERT INTO sub_categoria(sub_categoria,categoria)
VALUES('Mulher','calçado'),
      ('Homem','calçado'),
      ('Casacos','roupa'),
      ('Calças','roupa'),
      ('Camisolas','roupa');

INSERT INTO lote(numero_lote,origem,data_de_chegada,sub_categoria,nome)
VALUES('1001','Portugal','2019-11-25 17:10:19','Camisolas','modelo1'),
      ('1002','Portugal','2019-11-25 17:10:19','Calças','modelo2'),
      ('1003','Portugal','2019-11-25 17:10:19','Homem','modelo3'),
      ('1004','Portugal','2019-11-25 17:10:19','Mulher','modelo4'),
      ('1005','Portugal','2019-11-25 17:10:19','Casacos','modelo5'),
      ('1006','China','2019-11-30 14:50:19','Casacos','modelo6'),
      ('1007','China','2019-11-30 14:50:19','Camisolas','modelo7'),
      ('1008','China','2019-11-30 14:50:19','Calças','modelo8'),
      ('1009','China','2019-11-30 14:50:19','Homem','modelo9'),
      ('1010','China','2019-11-30 14:50:19','Mulher','modelo10'),
      ('1020','Espanha','2019-11-27 16:10:19','Casacos','modelo11'),
      ('1030','Espanha','2019-11-27 16:10:19','Camisolas','modelo12'),
      ('1040','Espanha','2019-11-27 16:10:19','Calças','modelo13'),
      ('1050','Espanha','2019-11-27 16:10:19','Homem','modelo14'),
      ('1060','Espanha','2019-11-28 16:10:19','Mulher','modelo15'),
      ('1120','Espanha','2019-11-28 16:10:19','Casacos','modelo16'),
      ('1130','Espanha','2019-11-28 16:10:19','Camisolas','modelo17'),
      ('1140','Espanha','2019-11-28 16:10:19','Calças','modelo18'),
      ('1150','Espanha','2019-11-29 16:10:19','Homem','modelo19'),;

INSERT INTO produto(sku,data_saida,com_defeito,num_lote,vendido)
VALUES('AD-H40-1000',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-27 16:10:19' and sub_categoria='Homem'),'false'),
      ('AD-H37-1100',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-27 16:10:19' and sub_categoria='Homem'),'false'),
      ('NK-H43-1200','2019-12-15 17:10:19','false',(SELECT numero_lote from lote where data_de_chegada='2019-11-25 17:10:19' and sub_categoria='Homem'),'true'),
      ('AD-H37-1300',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-25 17:10:19' and sub_categoria='Homem'),'false'),
      ('NK-H43-1400','2019-12-15 17:10:19','false',(SELECT numero_lote from lote where data_de_chegada='2019-11-30 14:50:19' and sub_categoria='Homem'),'true'),
      ('AD-H37-1500',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-30 14:50:19' and sub_categoria='Homem'),'false'),

      ('AD-W40-1234',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-27 16:10:19' and sub_categoria='Mulher'),'false'),
      ('AD-W37-9334','2019-12-16 17:10:19','false',(SELECT numero_lote from lote where data_de_chegada='2019-11-27 16:10:19' and sub_categoria='Mulher'),'true'),
      ('NK-W43-8134',null,'true',(SELECT numero_lote from lote where data_de_chegada='2019-11-25 17:10:19' and sub_categoria='Mulher'),'false'),
      ('AD-W37-7334','2019-12-17 17:10:19','true',(SELECT numero_lote from lote where data_de_chegada='2019-11-25 17:10:19' and sub_categoria='Mulher'),'true'),
      ('NK-W43-6134','2019-12-18 17:10:19','false',(SELECT numero_lote from lote where data_de_chegada='2019-11-30 14:50:19' and sub_categoria='Mulher'),'true'),
      ('AD-W37-5334',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-30 14:50:19' and sub_categoria='Mulher'),'false'),

      ('ZA-HM-2000',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-27 16:10:19' and sub_categoria='Casacos'),'false'),
      ('ZA-HM-2100',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-27 16:10:19' and sub_categoria='Casacos'),'false'),
      ('ZA-HM-2200',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-25 17:10:19' and sub_categoria='Casacos'),'false'),
      ('ZA-HM-2300',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-25 17:10:19' and sub_categoria='Casacos'),'false'),
      ('ZA-HM-2400','2019-12-15 17:10:19','false',(SELECT numero_lote from lote where data_de_chegada='2019-11-30 14:50:19' and sub_categoria='Casacos'),'true'),
      ('ZA-HM-2500',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-30 14:50:19' and sub_categoria='Casacos'),'false'),

      ('BU-HM-3001',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-27 16:10:19' and sub_categoria='Camisolas'),'false'),
      ('BU-WM-3002',null,'true',(SELECT numero_lote from lote where data_de_chegada='2019-11-27 16:10:19' and sub_categoria='Camisolas'),'false'),
      ('BU-WM-3003',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-25 17:10:19' and sub_categoria='Camisolas'),'false'),
      ('BU-WM-3004',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-25 17:10:19' and sub_categoria='Camisolas'),'false'),
      ('BU-WM-3005',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-25 17:10:19' and sub_categoria='Camisolas'),'false'),
      ('BU-WM-3006',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-30 14:50:19' and sub_categoria='Camisolas'),'false'),
      ('BU-WM-3007',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-30 14:50:19' and sub_categoria='Camisolas'),'false'),

      ('BU-WM-4001',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-27 16:10:19' and sub_categoria='Calças'),'false'),
      ('BU-WM-4002','2019-12-25 17:10:19','true',(SELECT numero_lote from lote where data_de_chegada='2019-11-27 16:10:19' and sub_categoria='Calças'),'true'),
      ('BU-WM-4003',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-25 17:10:19' and sub_categoria='Calças'),'false'),
      ('BU-WM-4004',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-25 17:10:19' and sub_categoria='Calças'),'false'),
      ('BU-WM-4005',null,'false',(SELECT numero_lote from lote where data_de_chegada='2019-11-30 14:50:19' and sub_categoria='Calças'),'false'),
      ('BU-WM-4006','2020-01-17 17:10:19','false',(SELECT numero_lote from lote where data_de_chegada='2019-11-30 14:50:19' and sub_categoria='Calças'),'true');
