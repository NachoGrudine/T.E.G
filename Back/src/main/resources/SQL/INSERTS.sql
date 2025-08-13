
Insert into Continentes ( nombre, premio) values 
( 'América del Sur', 3), 
( 'América del Norte', 5), 
( 'Europa', 5), 
( 'África', 3), 
( 'Asia', 8), 
( 'Oceanía', 2);

INSERT INTO Paises (nombre, id_continente) VALUES  
    -- América del Sur  
    ('Argentina', 1), ('Brasil', 1), ('Chile', 1), ('Uruguay', 1), ('Colombia', 1), ('Perú', 1),  
    
    -- América del Norte  
    ('Canadá', 2), ('Terranova', 2), ('México', 2), ('Groenlandia', 2), ('Alaska', 2), ('Oregón', 2), ('Nueva York', 2),  
    ('California', 2), ('Yukón', 2), ('Labrador', 2),  
    
    -- Europa  
    ('España', 3), ('Francia', 3), ('Alemania', 3), ('Italia', 3), ('Gran Bretaña', 3), ('Rusia', 3), ('Islandia', 3),  
    ('Suecia', 3), ('Polonia', 3),  
    
    -- África  
    ('Egipto', 4), ('Sudáfrica', 4), ('Sáhara', 4), ('Zaire', 4), ('Etiopía', 4), ('Madagascar', 4),  
    
    -- Asia  
    ('China', 5), ('India', 5), ('Japón', 5), ('Malasia', 5), ('Irán', 5), ('Siberia', 5), ('Mongolia', 5), ('Kamchatka', 5),  
    ('Turquía', 5), ('Israel', 5), ('Arabia', 5), ('Gobi', 5), ('Aral', 5), ('Tartaria', 5), ('Taimir', 5),  
    
    -- Oceanía  
    ('Australia', 6), ('Sumatra', 6), ('Borneo', 6), ('Java', 6); --50PAISES
	
Insert into Colores (color) values ('Rojo'), ('Amarillo'), ('Azul'), ('Verde'), ('Magenta'), ('Negro');




Insert into Fronteras (id_pais1,id_pais2) values (1,2),(1,3),(1,4),(1,6),(2,4),(2,6),(2,5),(3,6),(3,47),(5,6),(5,9),(7,8),(7,13),(7,12),(7,15),(8,16),(8,13),(9,14),(10,16),
(10,13),(10,23),(11,15),(11,12),(11,39),(12,15),(12,13),(12,14),(13,14),(17,18),(17,21),(17,28),(2,28),(18,20),(18,19),(19,20),(19,21),(19,25),(21,23),(22,25),(22,24),(22,44),
(22,36),(22,40),(23,24),(25,26),(25,40),(26,40),(26,41),(26,31),(26,30),(26,28),(27,30),(27,29),(28,30),(28,29),(29,30),(29,31),(32,33),(32,35),(32,34),(32,36),(32,43),
(32,38),(32,37),(32,39),(33,35),(33,36),(33,48),(34,39),(35,49),(36,40),(36,44),(36,38),(36,43),(37,38),(37,39),(37,46),(37,45),(37,44),(38,43),(40,41),(40,42),(41,42),(38,44),
(44,45),(45,46),(47,48),(47,49),(47,50);


Insert into Objetivos (id_color) values (null),(null),(null),(null),(null),(null),(null),(null),(null), (3),(1),(6),(2),(4),(5); --Destruir Colores

Insert into Objetivos_Conts (id_objetivo, id_continente) values (1,4),(2,1),(3,5),(4,3),(5,2),(7,6),(7,2),(8,1),(8,4),(9,6),(9,4);

Insert into Objetivos_Cantidad_Paises (id_objetivo, id_continente, cantidad) values (1,2,5),(1,3,4),(2,3,7),(2,4,3),(3,1,2),(4,5,4),(4,1,2),(5,6,2),(5,5,4),(6,6,2),(6,4,2),(6,1,2),
(6,3,3),(6,2,4),(6,5,3),(7,3,2),(8,5,4),(9,2,5);

INSERT INTO Tarjetas (id_pais, simbolo) VALUES
                                            (1, 'BARCO'),   (2, 'CANION'), (3, 'GLOBO'),
                                            (4, 'BARCO'),   (5, 'CANION'), (6, 'GLOBO'),
                                            (7, 'BARCO'),   (8, 'CANION'), (9, 'GLOBO'),
                                            (10, 'BARCO'),  (11, 'CANION'), (12, 'GLOBO'),
                                            (13, 'BARCO'),  (14, 'CANION'), (15, 'GLOBO'),
                                            (16, 'COMODIN'),(17, 'BARCO'), (18, 'CANION'),
                                            (19, 'GLOBO'),  (20, 'BARCO'), (21, 'CANION'),
                                            (22, 'GLOBO'),  (23, 'BARCO'), (24, 'CANION'),
                                            (25, 'GLOBO'),  (26, 'BARCO'), (27, 'CANION'),
                                            (28, 'GLOBO'),  (29, 'BARCO'), (30, 'CANION'),
                                            (31, 'GLOBO'),  (32, 'BARCO'), (33, 'CANION'),
                                            (34, 'GLOBO'),  (35, 'COMODIN'), (36, 'COMODIN'),
                                            (37, 'BARCO'),  (38, 'CANION'), (39, 'GLOBO'),
                                            (40, 'BARCO'),  (41, 'CANION'), (42, 'GLOBO'),
                                            (43, 'BARCO'),  (44, 'CANION'), (45, 'GLOBO'),
                                            (46, 'BARCO'),  (47, 'CANION'), (48, 'GLOBO'),
                                            (49, 'BARCO'),  (50, 'CANION');

