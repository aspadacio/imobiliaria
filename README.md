#Imobiliaria
Housing system to a specific client.

#add user to authenticate into app.
INSERT INTO imobiliaria.tb_grupo VALUES (null, 'Administrador', '');
INSERT INTO imobiliaria.tb_grupo VALUES (null, 'Consulta', '');

SELECT * FROM imobiliaria.tb_usuario;
INSERT INTO imobiliaria.tb_usuario VALUES ( null, user_name, user_mail@something.com, pass);

SELECT * FROM imobiliaria.tb_usuario_grupo;
INSERT INTO imobiliaria.tb_usuario_grupo VALUES ( 01, 01);
INSERT INTO imobiliaria.tb_usuario_grupo VALUES ( 01, 01);
INSERT INTO imobiliaria.tb_usuario_grupo VALUES ( 01, 02);
