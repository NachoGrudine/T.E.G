CREATE TABLE Usuarios (
    id_usuario SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);


CREATE TABLE Continentes (
    id_continente SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    premio INTEGER
);
CREATE TABLE Paises (
    id_pais SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_continente INTEGER NOT NULL,
    FOREIGN KEY (id_continente) REFERENCES Continentes(id_continente)
);
CREATE TABLE Colores (
    id_color SERIAL PRIMARY KEY,
    color VARCHAR(50) NOT NULL
);


CREATE TABLE Tarjetas (
    id_tarjeta SERIAL PRIMARY KEY,
    id_pais INTEGER NOT NULL,
    simbolo varchar(25),
    estado_uso varchar(25), --si ya se usó U: usada, N: no usada
    FOREIGN KEY (id_pais) REFERENCES Paises(id_pais)

);
CREATE TABLE Fronteras (
    id_pais_frontera SERIAL PRIMARY KEY,
    id_pais1 INTEGER NOT NULL,
    id_pais2 INTEGER NOT NULL,
    FOREIGN KEY (id_pais1) REFERENCES Paises(id_pais),
    FOREIGN KEY (id_pais2 ) REFERENCES Paises(id_pais),
    CONSTRAINT frontera_uniq UNIQUE (id_pais1, id_pais2)
);
----OBJETIVOS----------------------------------
CREATE TABLE Objetivos (
    id_objetivo SERIAL PRIMARY KEY,
    id_color INTEGER,
    FOREIGN KEY (id_color) REFERENCES Colores(id_color)
   
);
CREATE TABLE Objetivos_Conts (
    id_obj_cont SERIAL PRIMARY KEY,
    id_objetivo INTEGER NOT NULL,
    id_continente INTEGER NOT NULL,
    FOREIGN KEY (id_objetivo) REFERENCES Objetivos(id_objetivo),
    FOREIGN KEY (id_continente) REFERENCES Continentes(id_continente)
);
CREATE TABLE Objetivos_Cantidad_Paises (
    id_obj_pais SERIAL PRIMARY KEY,
    id_objetivo INTEGER NOT NULL,
    id_continente INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    FOREIGN KEY (id_objetivo) REFERENCES Objetivos(id_objetivo),
    FOREIGN KEY (id_continente) REFERENCES Continentes(id_continente)
);

----Partidas-----
CREATE TABLE Partidas (
    id_partida SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    estado varchar(25),--F finalizado, P en preparacion, C en curso...
    tipo varchar(25), --si es publica o priv
    cantidad_para_ganar INTEGER null,
    id_jugador_ganador integer null,


    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
    
);
CREATE TABLE Jugadores (
    id_jugador SERIAL PRIMARY KEY,
    id_partida INTEGER NOT NULL,
    tipo_jugador varchar(25),
    nombre VARCHAR(100) NOT NULL,
    id_color INTEGER NOT NULL,
    id_objetivo INTEGER NULL,
    estado_jugador varchar(25), --M muerto, V vivo
    id_usuario INTEGER null,
    id_jugador_asesino integer null,
    FOREIGN KEY (id_partida) REFERENCES Partidas(id_partida),

    FOREIGN KEY (id_color) REFERENCES Colores(id_color),
    FOREIGN KEY (id_objetivo) REFERENCES Objetivos(id_objetivo),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios (id_usuario),
    FOREIGN KEY (id_jugador_asesino) REFERENCES Jugadores(id_jugador)

);

ALTER TABLE Partidas
    ADD CONSTRAINT fk_jugador_ganador
        FOREIGN KEY (id_jugador_ganador) REFERENCES Jugadores(id_jugador);

CREATE TABLE Pilas_tarjetas (
    id_pila SERIAL PRIMARY KEY,
    id_partida INTEGER NOT NULL,
    id_tarjeta INTEGER NOT NULL,
    FOREIGN KEY (id_partida) REFERENCES Partidas(id_partida),
    FOREIGN KEY (id_tarjeta) REFERENCES Tarjetas(id_tarjeta)
);
CREATE TABLE Tarjetas_Jugadores (
    id_tarjetas_jugador SERIAL PRIMARY KEY,
    id_tarjeta INTEGER NOT NULL,
    id_jugador INTEGER NOT NULL,
    FOREIGN KEY (id_tarjeta) REFERENCES Tarjetas(id_tarjeta),
    FOREIGN KEY (id_jugador) REFERENCES Jugadores(id_jugador)
);
CREATE TABLE Paises_Jugadores (
    id_paises_jugador SERIAL PRIMARY KEY,
    id_partida INTEGER NOT NULL,
    fichas INTEGER NOT NULL,
    id_jugador INTEGER NOT NULL,
    id_pais INTEGER NOT NULL,
    FOREIGN KEY (id_partida) REFERENCES Partidas(id_partida),
    FOREIGN KEY (id_jugador) REFERENCES Jugadores(id_jugador),
    FOREIGN KEY (id_pais) REFERENCES Paises(id_pais)
);
CREATE TABLE Rondas (
    id_ronda SERIAL PRIMARY KEY,
    id_partida INTEGER NOT NULL,
    numero INTEGER NOT NULL,
    estado varchar(25),
    FOREIGN KEY (id_partida) REFERENCES Partidas(id_partida)
);

CREATE TABLE Turnos_ataques (
    id_turno_atq SERIAL PRIMARY KEY,
    id_jugador INTEGER NOT NULL,
    id_tarjeta INTEGER NULL,
    id_ronda integer not null,
    estado varchar(25),
    FOREIGN KEY (id_jugador) REFERENCES Jugadores(id_jugador),
    FOREIGN KEY (id_ronda) REFERENCES Rondas(id_ronda),
    FOREIGN KEY (id_tarjeta) REFERENCES Tarjetas(id_tarjeta)
);
CREATE TABLE Reagrupaciones (
    id_reagrupacion SERIAL PRIMARY KEY,
    id_paisOrigen INTEGER NOT NULL,
    id_paisDestino INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    FOREIGN KEY (id_paisOrigen) REFERENCES Paises(id_pais),
    FOREIGN KEY (id_paisDestino) REFERENCES Paises(id_pais)
);
CREATE TABLE Combates (
    id_combate SERIAL PRIMARY KEY,
    id_jugador_ataque INTEGER NOT NULL,
    id_pais_atk INTEGER NOT NULL,
    fichas_atk INTEGER NOT NULL,
    Atk_dado_1 INTEGER NOT NULL,
    Atk_dado_2 INTEGER  NULL,
    Atk_dado_3 INTEGER  NULL,
	id_jugador_defensa INTEGER NOT NULL,
    id_pais_def INTEGER NOT NULL,
    fichas_def INTEGER NOT NULL,
    Def_dado_1 INTEGER NOT NULL,
    Def_dado_2 INTEGER  NULL,
    Def_dado_3 INTEGER  NULL,
    FOREIGN KEY (id_jugador_ataque) REFERENCES Jugadores(id_jugador),
    FOREIGN KEY (id_jugador_defensa) REFERENCES Jugadores(id_jugador),
    FOREIGN KEY (id_pais_atk) REFERENCES Paises(id_pais),
    FOREIGN KEY (id_pais_def) REFERENCES Paises(id_pais)
);
CREATE TABLE Acciones (
    id_accion SERIAL PRIMARY KEY,
    id_turno_ataque INTEGER NOT NULL,
    id_reagrupacion INTEGER NULL,
    id_combate INTEGER NULL,
    FOREIGN KEY (id_turno_ataque) REFERENCES Turnos_ataques(id_turno_atq),
    FOREIGN KEY (id_reagrupacion) REFERENCES Reagrupaciones(id_reagrupacion),
    FOREIGN KEY (id_combate) REFERENCES Combates(id_combate)
);

CREATE TABLE Turnos_refuerzos (
    id_turno_refuerzo SERIAL PRIMARY KEY,
    id_jugador INTEGER NOT NULL,
    id_ronda integer not null,
    estado varchar(25),
    FOREIGN KEY (id_ronda) REFERENCES Rondas(id_ronda),
    FOREIGN KEY (id_jugador) REFERENCES Jugadores(id_jugador)
);
CREATE TABLE Refuerzos (
    id_refuerzo SERIAL PRIMARY KEY,
    id_turno_ref INTEGER NOT NULL,
    id_pais_jugador INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    tipo_fichas varchar(25),
    FOREIGN KEY (id_turno_ref) REFERENCES Turnos_refuerzos(id_turno_refuerzo),
    FOREIGN KEY (id_pais_jugador) REFERENCES Paises_Jugadores(id_paises_jugador)
);


CREATE TABLE Canjes (
    id_canje SERIAL PRIMARY KEY,
    id_jugador INTEGER NOT NULL,
    id_turno_refuerzo INTEGER NOT NULL,
    id_tarjeta_1 INTEGER NOT NULL,
    id_tarjeta_2 INTEGER NOT NULL,
    id_tarjeta_3 INTEGER NOT NULL,
    cantidad_fichas INTEGER NOT NULL,
    FOREIGN KEY (id_jugador) REFERENCES Jugadores(id_jugador),
    FOREIGN KEY (id_turno_refuerzo) REFERENCES Turnos_refuerzos(id_turno_refuerzo),
    FOREIGN KEY (id_tarjeta_1) REFERENCES Tarjetas(id_tarjeta),
    FOREIGN KEY (id_tarjeta_2) REFERENCES Tarjetas(id_tarjeta),
    FOREIGN KEY (id_tarjeta_3) REFERENCES Tarjetas(id_tarjeta)
);

CREATE TABLE Mensajes (
    id_mensaje SERIAL PRIMARY KEY,
    id_jugador INTEGER NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_hora TIMESTAMP NOT NULL,
    FOREIGN KEY (id_jugador) REFERENCES Jugadores(id_jugador)
);

CREATE TABLE Pactos (
    id_pactos SERIAL PRIMARY KEY,
    id_jugador1 INTEGER NOT NULL,
    id_jugador2 INTEGER NOT NULL,
    tipo_pacto varchar(25),
    activo BOOLEAN NOT NULL,
    id_turno_ref INTEGER NOT NULL,
    FOREIGN KEY (id_jugador1) REFERENCES Jugadores(id_jugador),
    FOREIGN KEY (id_jugador2) REFERENCES Jugadores(id_jugador),

    FOREIGN KEY (id_turno_ref) REFERENCES Turnos_refuerzos(id_turno_refuerzo)
);



