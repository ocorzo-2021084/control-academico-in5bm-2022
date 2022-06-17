/*
*Estudiantes:
*	Octavio Alejandro Corzo Reyes, 2021084
*	José Pablo Fabian Herrera García, 2018183
* Grupo: 2 (Lunes)
* Código Técnico: IN5BM
* Fecha creación: 26/04/22
* Fecha de modificación: 29/04/22  
*/
DROP DATABASE IF EXISTS db_control_academico_in5bm;
CREATE DATABASE db_control_academico_in5bm;
USE db_control_academico_in5bm;



DROP TABLE IF EXISTS alumnos;
CREATE TABLE IF NOT EXISTS alumnos(
	carne VARCHAR(7) NOT NULL,
    nombre1 VARCHAR(15) NOT NULL,
    nombre2 VARCHAR(15) NULL,
    nombre3 VARCHAR(15) NULL,
    apellido1 VARCHAR(15) NOT NULL,
    apellido2 VARCHAR(15) NULL,
    CONSTRAINT pk_alumnos PRIMARY KEY (carne)
);

DROP TABLE IF EXISTS instructores;
CREATE TABLE IF NOT EXISTS instructores(
	id INT NOT NULL AUTO_INCREMENT,
    nombre1 VARCHAR(15) NOT NULL,
    nombre2 VARCHAR(15) NULL,
    nombre3 VARCHAR(15) NULL,
    apellido1 VARCHAR(15) NOT NULL,
    apellido2 VARCHAR(15) NULL,
    direccion VARCHAR(45) NULL,
    email VARCHAR(45) NOT NULL,
    telefono VARCHAR(8) NOT NULL,
    fecha_nacimiento DATE NULL,
    CONSTRAINT pk_instructores PRIMARY KEY (id)
);

DROP TABLE IF EXISTS salones;
CREATE TABLE IF NOT EXISTS salones(
	codigo_salon VARCHAR(5) NOT NULL,
    descripcion VARCHAR(45) NULL,
    capacidad_maxima INT NOT NULL,
    edificio VARCHAR(15) NULL,
    nivel INT NULL,
    CONSTRAINT pk_salones PRIMARY KEY (codigo_salon)
);

DROP TABLE IF EXISTS carreras_tecnicas;
CREATE TABLE IF NOT EXISTS carreras_tecnicas(
	codigo_tecnico VARCHAR(6) NOT NULL,
    carrera VARCHAR(45) NOT NULL,
    grado VARCHAR(10) NOT NULL,
    seccion CHAR(1) NOT NULL,
    jornada VARCHAR(10) NOT NULL,
    CONSTRAINT pk_carreras_tecnicas PRIMARY KEY(codigo_tecnico)
);

DROP TABLE IF EXISTS horarios;
CREATE TABLE IF NOT EXISTS horarios(
	id INT NOT NULL AUTO_INCREMENT,
    horario_inicio TIME NOT NULL,
    horario_salida TIME NOT NULL,
    lunes TINYINT(1) NULL,
    martes TINYINT(1) NULL,
    miercoles TINYINT(1) NULL,
    jueves TINYINT(1) NULL,
    viernes TINYINT(1) NULL,
    CONSTRAINT pk_horarios PRIMARY KEY(id)
);


DROP TABLE IF EXISTS cursos;
CREATE TABLE IF NOT EXISTS cursos(
	id INT NOT NULL AUTO_INCREMENT,
    nombre_curso VARCHAR(255) NOT NULL,
    ciclo YEAR NULL,
    cupo_maximo INT NULL,
    cupo_minimo INT NULL,
    carrera_tecnica_id VARCHAR(6) NOT NULL,
    horario_id INT NOT NULL,
    instructor_id INT NOT NULL,
    salon_id VARCHAR(5) NOT NULL,
    CONSTRAINT pk_carreras_tecnicas PRIMARY KEY(id),
    
	CONSTRAINT fk_carreras_tecnicas_cursos
    FOREIGN KEY (carrera_tecnica_id)
    REFERENCES carreras_tecnicas (codigo_tecnico) ON DELETE CASCADE ON UPDATE CASCADE,

    
	CONSTRAINT fk_horarios_cursos
    FOREIGN KEY (horario_id)
    REFERENCES horarios (id) ON DELETE CASCADE ON UPDATE CASCADE,

    
	CONSTRAINT fk_instructores_cursos
    FOREIGN KEY (instructor_id)
    REFERENCES instructores (id) ON DELETE CASCADE ON UPDATE CASCADE,

    
	CONSTRAINT fk_salon_cursos
    FOREIGN KEY (salon_id)
    REFERENCES salones (codigo_salon) ON DELETE CASCADE ON UPDATE CASCADE

);

DROP TABLE IF EXISTS asignaciones_alumnos;
CREATE TABLE IF NOT EXISTS asignaciones_alumnos(
	id INT NOT NULL AUTO_INCREMENT,
	alumno_id VARCHAR(16) NOT NULL,
	curso_id INT NOT NULL,
	fecha_asignacion DATETIME NULL,
	CONSTRAINT pk_asignaciones_alumnos PRIMARY KEY (id),
	CONSTRAINT fk_asignaciones_alumnos_alumno
	FOREIGN KEY (alumno_id) REFERENCES alumnos(carne)
	ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_asignaciones_alumnos_cursos
	FOREIGN KEY (curso_id) REFERENCES cursos(id)
	ON UPDATE CASCADE ON DELETE CASCADE
);
-- creando procedimientos almacenados

-- alumnos create

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_create $$
CREATE PROCEDURE sp_alumnos_create(
IN _carne VARCHAR (7) ,
IN _nombre1 VARCHAR (15),
IN _nombre2 VARCHAR (15),
IN _nombre3 VARCHAR (15),
IN _apellido1 VARCHAR (15),
IN _apellido2 VARCHAR (15)
)
BEGIN
INSERT INTO alumnos (
carne,
nombre1,
nombre2,
nombre3,
apellido1,
apellido2
)
VALUES (
_carne,
_nombre1,
_nombre2,
_nombre3,
_apellido1,
_apellido2
);
END $$
DELIMITER ;

-- read alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read $$
CREATE PROCEDURE sp_alumnos_read()
BEGIN
    SELECT
        a.carne,
        a.nombre1,
        a.nombre2,
        a.nombre3,
        a.apellido1,
        a.apellido2
    FROM
        alumnos AS a;
END $$
DELIMITER ;

-- read by id alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read_by_id $$
CREATE PROCEDURE sp_alumnos_read_by_id(id_carne VARCHAR(7))
BEGIN 
	SELECT * FROM alumnos WHERE carne = id_carne;
END $$
DELIMITER ;

-- Update alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_update $$
CREATE PROCEDURE sp_alumnos_update(
IN _carne VARCHAR (7),
IN _nombre1 VARCHAR(15),
IN _nombre2 VARCHAR(15),
IN _nombre3 VARCHAR(15),
IN _apellido1 VARCHAR(15),
IN _apellido2 VARCHAR(15)
)
BEGIN
UPDATE
alumnos
SET
alumnos.nombre1=_nombre1,
alumnos.nombre2=_nombre2,
alumnos.nombre3=_nombre3,
alumnos.apellido1=_apellido1,
alumnos.apellido2=_apellido2
WHERE
alumnos.carne=_carne;
END$$
DELIMITER ;

-- delete alumnos 
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_delete $$
CREATE PROCEDURE sp_alumnos_delete(
IN _carne VARCHAR(7)
)
BEGIN
DELETE FROM
alumnos
WHERE
alumnos.carne=_carne;
END$$
DELIMITER ;

-- ----------------------------------------------------------------------------------------------------------------
-- Instructores
-- instructores create
DELIMITER $$
DROP PROCEDURE IF  EXISTS sp_instructores_create $$
CREATE PROCEDURE sp_instructores_create(
IN _nombre1 VARCHAR(15),
IN _nombre2 VARCHAR(15),
IN _nombre3 VARCHAR(15),
IN _apellido1 VARCHAR(15),
IN _apellido2 VARCHAR(15),
IN _direccion VARCHAR (45),
IN _email VARCHAR (45),
IN _telefono VARCHAR(8),
IN _fecha_nacimiento DATE
)
BEGIN
	INSERT INTO instructores (
		nombre1 ,
		nombre2 ,
		nombre3 ,
		apellido1,
		apellido2,
		direccion,
		email,
		telefono,
		fecha_nacimiento
        )
	VALUES 
        (
		_nombre1 ,
		_nombre2 ,
		_nombre3 ,
		_apellido1 ,
		_apellido2 ,
		_direccion,
		_email ,
		_telefono ,
		_fecha_nacimiento 
        );
END$$
DELIMITER ;

-- Instructores Read 
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read $$
CREATE PROCEDURE sp_instructores_read()
BEGIN
	SELECT * FROM 
		instructores;
END $$
DELIMITER ;

-- Instructores read by id
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read_by_id $$
CREATE PROCEDURE sp_instructores_read_by_id(id_instructores INT )
BEGIN 
	SELECT * FROM instructores WHERE id = id_instructores;
END $$
DELIMITER ;

-- Instructores Update
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_update $$
CREATE PROCEDURE sp_instructores_update(
IN _id INT,
IN _nombre1 VARCHAR(15),
IN _nombre2 VARCHAR(15),
IN _nombre3 VARCHAR(15),
IN _apellido1 VARCHAR(15),
IN _apellido2 VARCHAR(15),
IN _direccion VARCHAR(45),
IN _email VARCHAR(45),
IN _telefono VARCHAR(8),
IN _fecha_nacimiento DATE
)
BEGIN
	UPDATE
		instructores
	SET
		instructores.nombre1 = _nombre1 ,
		instructores.nombre2 = _nombre2,
		instructores.nombre3 = _nombre3,
		instructores.apellido1 = _apellido1,
		instructores.apellido2 = _apellido2,
		instructores.direccion = _direccion,
		instructores.email = _email,
		instructores.telefono = _telefono,
		instructores.fecha_nacimiento = _fecha_nacimiento
		
	WHERE
		instructores.id=_id;
END$$
DELIMITER ;


-- Instructores delete
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_delete $$
CREATE PROCEDURE sp_instructores_delete(
IN _id INT 
)
BEGIN
DELETE FROM instructores where id = _id;
END$$
DELIMITER ;

-- -----------------------------------------------------------------------------------------------------------
-- CRUD salones
-- Salones create
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_create $$
CREATE PROCEDURE sp_salones_create(
	IN _codigo_salon VARCHAR(5),
	IN _descripcion VARCHAR(45),
	IN _capacidad_maxima INT,
	IN _edificio VARCHAR (15),
	IN _nivel INT
)
BEGIN
	INSERT INTO salones(
		codigo_salon,
        descripcion,
        capacidad_maxima,
        edificio,
        nivel
    )
	VALUES(
		_codigo_salon,
        _descripcion,
        _capacidad_maxima,
        _edificio,
        _nivel
    );
END$$
DELIMITER ;





-- SALONES READ
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read $$
CREATE PROCEDURE sp_salones_read()
BEGIN
	SELECT 
		s.codigo_salon,
        s.descripcion,
        s.capacidad_maxima,
        s.edificio,
        s.nivel
	FROM salones AS s;
END$$
DELIMITER ;

-- salones read by id
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read_by_id $$
CREATE PROCEDURE sp_salones_read_by_id(_codigo VARCHAR(5))
BEGIN
	SELECT * FROM salones WHERE codigo_salon = _codigo;
END$$
DELIMITER ;

-- salones update
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_update $$
CREATE PROCEDURE sp_salones_update(
IN _codigo_salon VARCHAR(5),
IN _descripcion VARCHAR(45),
IN _capacidad_maxima INT,
IN _edificio VARCHAR(15),
IN _nivel INT 
)
BEGIN
	UPDATE
		salones
	SET
		salones.descripcion=_descripcion,
		salones.capacidad_maxima=_capacidad_maxima,
		salones.edificio=_edificio,
		salones.nivel=_nivel
	WHERE 
		salones.codigo_salon=_codigo_salon;
END$$
DELIMITER ;

-- -------------------------------------------------------------------------
-- Salones Delete
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_delete $$
CREATE PROCEDURE sp_salones_delete(
IN _codigo_salon VARCHAR(5)
)
BEGIN
	DELETE FROM
		salones
	WHERE
		salones.codigo_salon = _codigo_salon;
END$$
DELIMITER ;
-- --------------------------------------	
-- CRUD carreras tecnicas
-- Carreras tecnicas create
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_create $$
CREATE PROCEDURE sp_carreras_tecnicas_create(
	IN _codigo_tecnico VARCHAR (6) ,
	IN _carrera VARCHAR (45),
    IN _grado VARCHAR (10),
    IN _seccion CHAR (1),
    IN _jornada VARCHAR (10)
)
BEGIN
	INSERT INTO carreras_tecnicas (
		codigo_tecnico,
		carrera, 
		grado,
		seccion,
		jornada 
    ) 
    VALUES (
		_codigo_tecnico,
		_carrera, 
		_grado,
		_seccion,
		_jornada 
	);
END $$
DELIMITER ;

-- carreras tecnicas read
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read $$
CREATE PROCEDURE sp_carreras_tecnicas_read()
BEGIN
	SELECT
		carreras_tecnicas.codigo_tecnico,
		carreras_tecnicas.carrera,
		carreras_tecnicas.grado,
		carreras_tecnicas.seccion,
		carreras_tecnicas.jornada
	FROM
		carreras_tecnicas;
END$$
DELIMITER ;

-- Carreras tecnicas read by id
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read_by_id $$
CREATE PROCEDURE sp_carreras_tecnicas_read_by_id(_codigo_tecnico VARCHAR(6))
BEGIN
	SELECT * FROM carreras_tecnicas WHERE codigo_tecnico = _codigo_tecnico;
END$$
DELIMITER ;

-- Carreras tecnicas update
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_update $$
CREATE PROCEDURE sp_carreras_tecnicas_update(
IN _codigo_tecnico VARCHAR(6),
IN _carrera VARCHAR(45),
IN _grado VARCHAR(10),
IN _seccion CHAR(1),
IN _jornada VARCHAR(10)
)
BEGIN
	UPDATE
		carreras_tecnicas
	SET
		carreras_tecnicas.carrera=_carrera,
		carreras_tecnicas.grado=_grado,
		carreras_tecnicas.seccion=_seccion,
		carreras_tecnicas.jornada=_jornada
	WHERE
		carreras_tecnicas.codigo_tecnico=_codigo_tecnico;
END$$
DELIMITER ;

-- Carreras tecnicas delete
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_delete $$
CREATE PROCEDURE sp_carreras_tecnicas_delete(
IN _codigo_tecnico VARCHAR(6)
)
BEGIN
	DELETE FROM
		carreras_tecnicas
	WHERE
		codigo_tecnico = _codigo_tecnico;
END$$
DELIMITER ;

-- ------------------------------------------
-- CRUD horarios
-- horarios create
DELIMITER $$
DROP PROCEDURE IF  EXISTS sp_horarios_create $$
CREATE PROCEDURE sp_horarios_create(
IN _horario_inicio TIME,
IN _horario_salida TIME,
IN _lunes TINYINT(1),
IN _martes TINYINT(1),
IN _miercoles TINYINT(1),
IN _jueves TINYINT(1),
IN _viernes TINYINT(1)
)
BEGIN
	INSERT INTO horarios (
		horario_inicio ,
		horario_salida ,
		lunes ,
		martes,
		miercoles,
		jueves,
		viernes
	)
	VALUES (
		
		_horario_inicio ,
		_horario_salida ,
		_lunes,
		_martes,
		_miercoles,
		_jueves,
		_viernes 
	);
END$$        
DELIMITER ;

-- horarios read
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read $$
CREATE PROCEDURE sp_horarios_read()
BEGIN
	SELECT * FROM horarios;
END$$
DELIMITER ;

-- horarios read by id
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read_by_id $$
CREATE PROCEDURE sp_horarios_read_by_id(_id INT)
BEGIN
	SELECT * FROM horarios WHERE id = _id;
END$$
DELIMITER ;

-- Horarios update
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_update $$
CREATE PROCEDURE sp_horarios_update(
IN _id INT, 
IN _horario_inicio TIME, 
IN _horario_salida TIME, 
IN _lunes TINYINT(1), 
IN _martes TINYINT(1), 
IN _miercoles TINYINT(1), 
IN _jueves TINYINT(1),
IN _viernes TINYINT(1)
)
BEGIN 
	UPDATE horarios
	SET
		horario_inicio = _horario_inicio,
        horario_salida = horario_salida,
        lunes = _lunes,
        martes = _martes,
        miercoles = _miercoles,
        jueves = _jueves,
        viernes = _viernes
	WHERE
		id = _id;
END$$
DELIMITER ;
	
-- Horarios DELETE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_delete $$
CREATE PROCEDURE sp_horarios_delete (
IN _id INT
)
BEGIN 
DELETE FROM horarios WHERE horarios.id = _id;
END $$
DELIMITER ;
-- ------------------------------------------------------------
-- CRUD cursos
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_create $$
CREATE PROCEDURE sp_cursos_create(
IN _nombre_curso VARCHAR (255),
IN _ciclo YEAR,
IN _cupo_maximo INT,
IN _cupo_minimo INT,
IN _carrera_tecnica_id VARCHAR(6),
IN _horario_id INT,
IN _instructor_id INT,
IN _salon_id INT
)
BEGIN
	INSERT INTO cursos (
		nombre_curso ,
		ciclo ,
		cupo_maximo ,
		cupo_minimo,
		carrera_tecnica_id,
		horario_id,
		salon_id,
        instructor_id
        )
        VALUES (
			_nombre_curso ,
			_ciclo ,
			_cupo_maximo ,
			_cupo_minimo,
			_carrera_tecnica_id,
			_horario_id,
            _salon_id,
            _instructor_id
			
        );
END$$        
DELIMITER ;

-- cursos read
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read $$
CREATE PROCEDURE sp_cursos_read()
BEGIN
	SELECT 
		c.id,
        c.nombre_curso,
        c.ciclo,
        c.cupo_maximo,
        c.cupo_minimo,
        c.carrera_tecnica_id,
        c.horario_id,
        c.instructor_id,
        c.salon_id
	FROM
		cursos AS c;
END$$
DELIMITER ;

-- Cursos read by id
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read_by_id $$
CREATE PROCEDURE sp_cursos_read_by_id(
IN _id INT
)
BEGIN
	SELECT 
		c.id,
        c.nombre_curso,
        c.ciclo,
        c.cupo_maximo,
        c.cupo_minimo,
        c.carrera_tecnica_id,
        c.horario_id,
        c.instructor_id,
        c.salon_id
	FROM
		cursos AS c
	WHERE c.id = _id;
END$$
DELIMITER ;

-- Cursos UPDATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_update $$
CREATE PROCEDURE sp_cursos_update(
IN _id INT,
IN _nombre_curso VARCHAR(255),
IN _ciclo YEAR,
IN _cupo_maximo INT,
IN _cupo_minimo INT,
IN _carrera_tecninca_id VARCHAR(6),
IN _horario_id INT,
IN _instructor_id INT,
IN _salon_id VARCHAR(5)
)
BEGIN
UPDATE
	cursos
SET
	cursos.id = _id,
    cursos.nombre_curso = _nombre_curso,
    cursos.ciclo = _ciclo,
    cursos.cupo_maximo = _cupo_maximo,
    cursos.cupo_minimo = _cupo_minimo,
    cursos.carrera_tecnica_id = _carrera_tecninca_id,
    cursos.horario_id = _horario_id,
    cursos.instructor_id = _instructor_id,
    cursos.salon_id = _salon_id
WHERE
	cursos.id = _id;
END$$
DELIMITER ;

-- Cursos DELETE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_delete $$
CREATE PROCEDURE sp_cursos_delete(
IN _id INT
)
BEGIN
DELETE FROM cursos WHERE cursos.id = _id;
END$$

-- ------------------------------------------------------------------------------
-- CRUD asignaciones_alumnos
-- asignaciones_alumnos create
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_create $$
CREATE PROCEDURE sp_asignaciones_alumnos_create(
IN _alumno_id INT,
IN _curso_id INT,
IN _fecha_asignacion DATETIME
)
BEGIN
	INSERT INTO
		asignaciones_alumnos(
		alumno_id,
		curso_id,
		fecha_asignacion
        )
	VALUES(
        _alumno_id,
        _curso_id,
        _fecha_asignacion
    
    );
END$$


-- asignaciones_alumnos read
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read $$
CREATE PROCEDURE sp_asignaciones_alumnos_read()
BEGIN
SELECT 
	a.id,
    a.alumno_id,
    a.curso_id,
    a.fecha_asignacion
FROM 
	asignaciones_alumnos AS a;
END$$
DELIMITER ;

-- asignaciones_alumnos read by id
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read_by_id $$
CREATE PROCEDURE sp_asignaciones_alumnos_read_by_id(
IN _id VARCHAR(45)
)
BEGIN
SELECT 
	a.id,
    a.alumno_id,
    a.curso_id,
    a.fecha_asignacion
FROM 
	asignaciones_alumnos AS a
WHERE
	a.id = _id;
END$$
DELIMITER ;

-- UPDATE asig_alum
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_update $$
CREATE PROCEDURE sp_asignaciones_alumnos_update(
IN _id VARCHAR(45),
IN _alumno_id VARCHAR(7),
IN _curso_id INT,
IN _fecha_asignacion DATETIME
)
BEGIN
UPDATE 
	asignaciones_alumnos
SET
	asignaciones_alumnos.alumno_id = _alumno_id,
    asignaciones_alumnos.curso_id = _curso_id,
    asignaciones_alumnos.fecha_asignacion = _fecha_asignacion
WHERE 
	asignaciones_alumnos.id = _id;    
END$$

-- asig_alum delete
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_delete $$
CREATE PROCEDURE sp_asignaciones_alumnos_delete(
IN _id INT
)
BEGIN
	DELETE FROM asignaciones_alumnos WHERE asignaciones_alumnos.id = _id;
END$$
DELIMITER ;


-- Registros de alumnos
INSERT INTO alumnos(carne, nombre1, apellido1)
VALUES("2021084", "Octavio", "Corzo");

INSERT INTO alumnos(carne, nombre1, apellido1)
VALUES("2021085", "Alejandro", "Reyes");

INSERT INTO alumnos(carne, nombre1, apellido1)
VALUES("2021086", "Juan", "Lopez");

INSERT INTO alumnos(carne, nombre1, apellido1)
VALUES("2021087", "Mario", "Rodriguez");

INSERT INTO alumnos(carne, nombre1, apellido1)
VALUES("2021088", "Luis", "Barrios");

INSERT INTO alumnos(carne, nombre1, apellido1)
VALUES("2021089", "Pablo", "Roman");

INSERT INTO alumnos(carne, nombre1, apellido1)
VALUES("2021090", "Donaldo", "Sanum");

INSERT INTO alumnos(carne, nombre1, apellido1)
VALUES("2021091", "Fernando", "Garcia");

INSERT INTO alumnos(carne, nombre1, apellido1)
VALUES("2021092", "Emanuel", "Jolon");

INSERT INTO alumnos(carne, nombre1, apellido1)
VALUES("2021093", "Enrique", "Lopez");

-- registros de instructores
INSERT INTO instructores(nombre1, apellido1, email, telefono, fecha_nacimiento)
VALUES("Jorge", "Perez", "jorgeperez@gmail.com", "43429087", '1980-02-28');

INSERT INTO instructores(nombre1, apellido1, email, telefono, fecha_nacimiento)
VALUES("Luis", "Guillen", "luisguillen@gmail.com", "45872134", '1977-09-30');

INSERT INTO instructores(nombre1, apellido1, email, telefono, fecha_nacimiento)
VALUES("Juan", "Rivas", "juanrivas@gmail.com", "43426789", '1975-03-30');

INSERT INTO instructores(nombre1, apellido1, email, telefono, fecha_nacimiento)
VALUES("Samuel", "Perez", "samuelperez@gmail.com", "59864578", '1989-01-12');

INSERT INTO instructores(nombre1, apellido1, email, telefono, fecha_nacimiento)
VALUES("oscar", "Bernard", "oscarbernard@gmail.com", "56735790", '1969-05-22');

INSERT INTO instructores(nombre1, apellido1, email, telefono, fecha_nacimiento)
VALUES("Mercedes", "Mendez", "mercedesmendez@gmail.com", "89562136", '1983-07-30');

INSERT INTO instructores(nombre1, apellido1, email, telefono, fecha_nacimiento)
VALUES("Aaron", "Garcia", "aarongarcia@gmail.com", "43005067", '1999-01-01');

INSERT INTO instructores(nombre1, apellido1, email, telefono, fecha_nacimiento)
VALUES("Marcos", "Roman", "marcosroman@gmail.com", "56040096", '1995-02-17');

INSERT INTO instructores(nombre1, apellido1, email, telefono, fecha_nacimiento)
VALUES("Gerardo", "Sandoval", "gerardosandoval@gmail.com", "87905650", '1994-12-17');

INSERT INTO instructores(nombre1, apellido1, email, telefono, fecha_nacimiento)
VALUES("Esau", "Patzun", "esaupatzun@gmail.com", "67894501", '1991-04-23');

-- registros de salones
INSERT INTO salones(codigo_salon, capacidad_maxima)
VALUES("22010", 35);

INSERT INTO salones(codigo_salon, capacidad_maxima)
VALUES("22011", 35);

INSERT INTO salones(codigo_salon, capacidad_maxima)
VALUES("22012", 35);

INSERT INTO salones(codigo_salon, capacidad_maxima)
VALUES("22013", 35);

INSERT INTO salones(codigo_salon, capacidad_maxima)
VALUES("22014", 35);

INSERT INTO salones(codigo_salon, capacidad_maxima)
VALUES("22015", 35);

INSERT INTO salones(codigo_salon, capacidad_maxima)
VALUES("22016", 35);

INSERT INTO salones(codigo_salon, capacidad_maxima)
VALUES("22017", 35);

INSERT INTO salones(codigo_salon, capacidad_maxima)
VALUES("22018", 35);

INSERT INTO salones(codigo_salon, capacidad_maxima)
VALUES("22019", 35);

-- registros carreras tecnicas
INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN5BM1", "Informatica", "5to", "B", "Matutina");

INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN5BV1", "Informatica", "5to", "B", "Vespertina");

INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN4BM1", "Informatica", "4to", "B", "Matutina");

INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN4BV1", "Informatica", "4to", "B", "Vespertina");

INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN5CM1", "Informatica", "5to", "C", "Matutina");

INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN5CV1", "Informatica", "5to", "C", "Vespertina");

INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN5DM1", "Informatica", "5to", "D", "Matutina");

INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN5DV1", "Informatica", "5to", "D", "Vespertina");

INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN5EM1", "Informatica", "5to", "E", "Matutina");

INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
VALUES("IN5EV1", "Informatica", "5to", "E", "Vespertina");

-- Registros de horarios
INSERT INTO horarios(horario_inicio, horario_salida, lunes, martes, miercoles, jueves, viernes)
VALUES('07:10:00', '12:05:00', 1, 1, 0, 0, 0);

INSERT INTO horarios(horario_inicio, horario_salida, lunes, martes, miercoles, jueves, viernes)
VALUES('12:10:00', '17:05:00', 1, 1, 0, 0, 0);

INSERT INTO horarios(horario_inicio, horario_salida, lunes, martes, miercoles, jueves, viernes)
VALUES('07:10:00', '9:25:00', 0, 0, 1, 0, 0);

INSERT INTO horarios(horario_inicio, horario_salida, lunes, martes, miercoles, jueves, viernes)
VALUES('07:10:00', '11:30:00', 0, 0, 0, 1, 1);

INSERT INTO horarios(horario_inicio, horario_salida, lunes, martes, miercoles, jueves, viernes)
VALUES('09:40:00', '11:00:00', 1, 0, 0, 0, 0);

INSERT INTO horarios(horario_inicio, horario_salida, lunes, martes, miercoles, jueves, viernes)
VALUES('07:10:00', '10:00:00', 0, 0, 0, 1, 1);

INSERT INTO horarios(horario_inicio, horario_salida, lunes, martes, miercoles, jueves, viernes)
VALUES('12:10:00', '15:25:00', 0, 1, 1, 0, 0);

INSERT INTO horarios(horario_inicio, horario_salida, lunes, martes, miercoles, jueves, viernes)
VALUES('12:10:00', '14:00:00', 0, 1, 0, 0, 0);

INSERT INTO horarios(horario_inicio, horario_salida, lunes, martes, miercoles, jueves, viernes)
VALUES('07:10:00', '17:25:00', 0, 0, 1, 1, 0);

INSERT INTO horarios(horario_inicio, horario_salida, lunes, martes, miercoles, jueves, viernes)
VALUES('12:10:00', '13:25:00', 0, 0, 0, 0, 1);

-- registros de cursos
INSERT INTO cursos(nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
VALUES("Matematicas", 2022, 30, 20, "IN5BM1", 10, 8, 22010);

INSERT INTO cursos(nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
VALUES("Lengua y Literatura", 2022, 35, 20, "IN5BV1", 8, 10, 22011);

INSERT INTO cursos(nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
VALUES("Ciencias Sociales", 2022, 25, 20, "IN4BM1", 3, 5, 22012);

INSERT INTO cursos(nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
VALUES("Taller ll", 2022, 35, 25, "IN5CM1", 1, 1, 22013);

INSERT INTO cursos(nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
VALUES("Ingles ll", 2022, 30, 20, "IN5CV1", 4, 2, 22014);

INSERT INTO cursos(nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
VALUES("Calculo", 2022, 30, 20, "IN5DM1", 2, 6, 22015);

INSERT INTO cursos(nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
VALUES("Tecnologia", 2022, 30, 20, "IN5DV1", 6, 7, 22016);

INSERT INTO cursos(nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
VALUES("Quimica", 2022, 30, 20, "IN5EM1", 3, 9, 22017);

INSERT INTO cursos(nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
VALUES("Biologia", 2022, 30, 20, "IN5BM1", 5, 8, 22018);

INSERT INTO cursos(nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
VALUES("Dibujo tecnico", 2022, 30, 20, "IN5BM1", 7, 8, 22019);

-- Registros de asignaciones alumnos
INSERT INTO asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
VALUES( "2021084", 1, '2022-01-01 12:00:00');

INSERT INTO asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
VALUES("2021085", 2, '2022-01-01 12:00:01');

INSERT INTO asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
VALUES("2021086", 3, '2022-01-02 13:23:10');

INSERT INTO asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
VALUES("2021087", 4, '2022-01-02 13:25:14');

INSERT INTO asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
VALUES("2021088", 5, '2022-01-02 14:00:00');

INSERT INTO asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
VALUES("2021089", 6, '2022-01-02 14:02:00');

INSERT INTO asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
VALUES( "2021090", 7, '2022-01-02 14:05:00');

INSERT INTO asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
VALUES( "2021091", 8, '2022-01-02 14:11:00');

INSERT INTO asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
VALUES("2021092", 9, '2022-01-02 14:30:00');

INSERT INTO asignaciones_alumnos(alumno_id, curso_id, fecha_asignacion)
VALUES("2021093", 10, '2022-01-02 15:00:00');


CALL sp_alumnos_read();
CALL sp_instructores_read();
CALL sp_salones_read();
CALL sp_carreras_tecnicas_read();
CALL sp_horarios_read();
CALL sp_cursos_read();
CALL sp_cursos_read_by_id(1);
CALL sp_asignaciones_alumnos_read();
CALL sp_carreras_tecnicas_create("IN6BM", "Informática", "6to", "B", "Matutina");

CALL sp_instructores_create("Juancho", " ", " ", "Mendez", " ", " ", "juanchito@gmail.com", "43080776", '1980-01-05' );