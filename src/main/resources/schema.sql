CREATE DATABASE IF NOT EXISTS fondos_europeos
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;


USE fondos_europeos;


DROP TABLE IF EXISTS solicitud_ayuda;
DROP TABLE IF EXISTS programa_europeo;


CREATE TABLE programa_europeo (
                                  id           BIGINT NOT NULL AUTO_INCREMENT,
                                  codigo       VARCHAR(50)  NOT NULL,   -- Ej: 'FEDER', 'FSE+', 'ERASMUS+', 'HORIZON'
                                  nombre       VARCHAR(150) NOT NULL,   -- Nombre largo si se quiere
                                  descripcion  VARCHAR(500) NULL,


                                  PRIMARY KEY (id),
                                  UNIQUE KEY uq_programa_codigo (codigo)
) ENGINE=InnoDB;


-- Datos de ejemplo para los programas europeos
INSERT INTO programa_europeo (codigo, nombre, descripcion) VALUES
                                                               ('FEDER',   'Fondo Europeo de Desarrollo Regional',         'Apoyo a inversiones en crecimiento y empleo'),
                                                               ('FSE+',    'Fondo Social Europeo Plus',                     'Empleo, educación y inclusión social'),
                                                               ('ERASMUS+', 'Erasmus+',                                     'Educación, formación, juventud y deporte'),
                                                               ('HORIZON', 'Horizonte Europa',                              'Programa marco de investigación e innovación');


CREATE TABLE solicitud_ayuda (
                                 id                      BIGINT NOT NULL AUTO_INCREMENT,


    -- Relación con programa europeo (Paso 1)
                                 programa_id             BIGINT NOT NULL,          -- FK a programa_europeo.id


                                 entidad_solicitante     VARCHAR(200) NOT NULL,
                                 cif_nif                 VARCHAR(20)  NOT NULL,
                                 pais                    VARCHAR(100) NOT NULL,
                                 region                  VARCHAR(100) NOT NULL,


                                 persona_contacto        VARCHAR(150) NOT NULL,
                                 email_contacto          VARCHAR(190) NOT NULL,


    -- Datos del proyecto (Paso 2)
                                 titulo_proyecto         VARCHAR(255) NOT NULL,


                                 importe_total_proyecto  DECIMAL(15,2) NOT NULL,
                                 importe_ayuda_solicitada DECIMAL(15,2) NOT NULL,


                                 fecha_inicio_prevista   DATE         NOT NULL,
                                 duracion_meses          INT          NOT NULL,


    -- Metadatos de la solicitud
                                 fecha_creacion          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 estado                  VARCHAR(20)  NOT NULL DEFAULT 'BORRADOR',
    -- Posibles valores: 'BORRADOR', 'ENVIADA', 'REGISTRADA', 'RECHAZADA', etc.


                                 PRIMARY KEY (id),


                                 CONSTRAINT fk_solicitud_programa
                                     FOREIGN KEY (programa_id)
                                         REFERENCES programa_europeo (id)
                                         ON UPDATE CASCADE
                                         ON DELETE RESTRICT,


                                 CONSTRAINT chk_importes_positivos
                                     CHECK (importe_total_proyecto >= 0 AND importe_ayuda_solicitada >= 0),


                                 CONSTRAINT chk_importe_ayuda_no_superior
                                     CHECK (importe_ayuda_solicitada <= importe_total_proyecto),


                                 CONSTRAINT chk_duracion_positiva
                                     CHECK (duracion_meses > 0)
) ENGINE=InnoDB;




INSERT INTO solicitud_ayuda (
    programa_id,
    entidad_solicitante,
    cif_nif,
    pais,
    region,
    persona_contacto,
    email_contacto,
    titulo_proyecto,
    importe_total_proyecto,
    importe_ayuda_solicitada,
    fecha_inicio_prevista,
    duracion_meses,
    estado
) VALUES (
             1, -- FEDER
             'Ayuntamiento de Ejemplo',
             'P1234567Z',
             'España',
             'Andalucía',
             'Ana López',
             'ana.lopez@example.org',
             'GREEN-SCHOOL - Mejora energética de centro educativo',
             150000.00,
             120000.00,
             '2025-09-01',
             24,
             'ENVIADA'
         );
INSERT INTO solicitud_ayuda (
    programa_id,
    entidad_solicitante,
    cif_nif,
    pais,
    region,
    persona_contacto,
    email_contacto,
    titulo_proyecto,
    importe_total_proyecto,
    importe_ayuda_solicitada,
    fecha_inicio_prevista,
    duracion_meses,
    estado
) VALUES (
             2, -- FSE+
             'Asociación Inserción Laboral Futuro',
             'G12345678',
             'España',
             'Andalucía',
             'Carlos Martín',
             'carlos.martin@futuroempleo.org',
             'PRO-EMPLEO JOVEN - Programa de mejora de la empleabilidad juvenil',


             80000.00,
             60000.00,
             '2025-03-01',
             18,
             'ENVIADA'
         );


INSERT INTO solicitud_ayuda (
    programa_id,
    entidad_solicitante,
    cif_nif,
    pais,
    region,
    persona_contacto,
    email_contacto,
    titulo_proyecto,
    importe_total_proyecto,
    importe_ayuda_solicitada,
    fecha_inicio_prevista,
    duracion_meses,
    estado
) VALUES (
             3, -- ERASMUS+
             'IES Europa Innovadora',
             'Q8765432B',
             'España',
             'Madrid',
             'María Sánchez',
             'maria.sanchez@ieseuropa.es',
             'EURO-MOBILITY - Movilidad de alumnado y profesorado en FP',
             50000.00,
             45000.00,
             '2025-10-01',
             12,
             'BORRADOR'
         );