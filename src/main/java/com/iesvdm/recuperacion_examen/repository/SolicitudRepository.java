package com.iesvdm.recuperacion_examen.repository;

import com.iesvdm.recuperacion_examen.model.ProgramaEuropeo;
import com.iesvdm.recuperacion_examen.model.SolicitudAyuda;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

    @Repository
    public class SolicitudRepository {

        private final JdbcTemplate jdbcTemplate;

        public SolicitudRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        // Mostrar to dos los programas
        public List<ProgramaEuropeo> findAllProgramas(){
            String sql= """
                SELECT * FROM programa_europeo;
                """;
            List<ProgramaEuropeo> programas = jdbcTemplate.query(sql,
                    (rs, rowNum) -> new ProgramaEuropeo(
                            rs.getLong("id"),
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getString("descripcion")
                    ));
            return programas;
        }

        // Mostrar todas las Solicitudes
        public List<SolicitudAyuda> findAllSolicitudes(){
            String sql= """
                SELECT * FROM solicitud_ayuda;
                """;
            List<SolicitudAyuda> solicitudes = jdbcTemplate.query(sql,
                    (rs, rowNum) -> new SolicitudAyuda(
                            rs.getLong("id"),
                            rs.getLong("programa_id"),
                            rs.getString("entidad_solicitante"),
                            rs.getString("cif_nif"),
                            rs.getString("pais"),
                            rs.getString("region"),
                            rs.getString("persona_contacto"),
                            rs.getString("email_contacto"),
                            rs.getString("titulo_proyecto"),
                            rs.getBigDecimal("importe_total_proyecto"),
                            rs.getBigDecimal("importe_ayuda_solicitada"),
                            rs.getTimestamp("fecha_inicio_prevista").toLocalDateTime(),
                            rs.getInt("duracion_meses"),
                            rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                            rs.getString("estado")
                    ));
            return solicitudes;
        }

        //Encontrar Programa por ID
        public ProgramaEuropeo findProgramaById (Long id){
            String sql = """
                SELECT * FROM programa_europeo
                WHERE id = ?
                """;
            return jdbcTemplate.queryForObject(sql,
                    new Object[] {id},
                    (rs, rowNum) -> new ProgramaEuropeo(
                            rs.getLong("id"),
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getString("descripcion")
                    ));

        }

        //Encontrar Solicitud por ID
        public SolicitudAyuda findSolicitudById(Long id){
            String sql = """
                SELECT * FROM solicitud_ayuda
                WHERE id = ?
                """;
            return jdbcTemplate.queryForObject(sql,
                    new Object[] {id},
                    (rs, rowNum) -> new SolicitudAyuda(
                            rs.getLong("id"),
                            rs.getLong("programa_id"),
                            rs.getString("entidad_solicitante"),
                            rs.getString("cif_nif"),
                            rs.getString("pais"),
                            rs.getString("region"),
                            rs.getString("persona_contacto"),
                            rs.getString("email_contacto"),
                            rs.getString("titulo_proyecto"),
                            rs.getBigDecimal("importe_total_proyecto"),
                            rs.getBigDecimal("importe_ayuda_solicitada"),
                            rs.getTimestamp("fecha_inicio_prevista").toLocalDateTime(),
                            rs.getInt("duracion_meses"),
                            rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                            rs.getString("estado")
                    ));
        }

        // Crear Solicitud
        public void crearSol (SolicitudAyuda solicitudAyuda){
            String sql = """
                INSERT INTO solicitud_ayuda (programa_id, entidad_solicitante, cif_nif, pais, region, persona_contacto,
                                            email_contacto, titulo_proyecto, importe_total_proyecto, importe_ayuda_solicitada,
                                            fecha_inicio_prevista, duracion_meses, fecha_creacion, estado)
                                                      VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String [] ids={"id"};

            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql,ids);
                ps.setLong(1, solicitudAyuda.getPrograma_id());
                ps.setString(2, solicitudAyuda.getEntidadSolicitante());
                ps.setString(3, solicitudAyuda.getCifNif());
                ps.setString(4, solicitudAyuda.getPais());
                ps.setString(5, solicitudAyuda.getRegion());
                ps.setString(6, solicitudAyuda.getPersonaContacto());
                ps.setString(7, solicitudAyuda.getEmailContacto());
                ps.setString(8, solicitudAyuda.getTituloProyecto());
                ps.setBigDecimal(9, solicitudAyuda.getImporteTotalProyecto());
                ps.setBigDecimal(10, solicitudAyuda.getImporteAyudaSolicitada());
                ps.setTimestamp(11, Timestamp.valueOf(solicitudAyuda.getFechaInicioPrevista()));
                ps.setInt(12,solicitudAyuda.getDuracionMeses());
                ps.setTimestamp(13, Timestamp.valueOf(solicitudAyuda.getFechaCreacion()));
                ps.setString(14, solicitudAyuda.getEstado());
                return ps;
            }, keyHolder);
            solicitudAyuda.setId(keyHolder.getKey().longValue());
        }
    }
