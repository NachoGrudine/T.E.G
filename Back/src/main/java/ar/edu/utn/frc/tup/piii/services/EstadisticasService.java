package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Objetivos;
import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.repositories.ColoresRepository;
import ar.edu.utn.frc.tup.piii.repositories.JugadoresRepository;
import ar.edu.utn.frc.tup.piii.repositories.ObjetivosRepository;
import ar.edu.utn.frc.tup.piii.repositories.PartidasRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IEstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstadisticasService implements IEstadisticasService {

    private final PartidasRepository partidasRepository;
    private final JugadoresRepository jugadoresRepository;
    private final ColoresRepository coloresRepository;
    private final ObjetivosRepository objetivosRepository;

    @Autowired
    public EstadisticasService(PartidasRepository partidasRepository, JugadoresRepository jugadoresRepository, ColoresRepository coloresRepository, ObjetivosRepository objetivosRepository) {
        this.partidasRepository = partidasRepository;
        this.jugadoresRepository = jugadoresRepository;
        this.coloresRepository = coloresRepository;
        this.objetivosRepository = objetivosRepository;
    }

    // Partidas jugadas y ganadas por usuario
    public Map<String, Object> partidasPorUsuario(Integer idUsuario) {
        Map<String, Object> result = new HashMap<>();
        long jugadas = partidasRepository.findAll().stream()
                .filter(p -> p.getUsuario() != null && p.getUsuario().getId().equals(idUsuario))
                .count();
        long ganadas = partidasRepository.findAll().stream()
                .filter(p -> p.getJugadorGanador() != null
                        && p.getJugadorGanador().getUsuario() != null
                        && p.getJugadorGanador().getUsuario().getId().equals(idUsuario))
                .count();
        result.put("jugadas", jugadas);
        result.put("ganadas", ganadas);
        return result;
    }

    // Porcentaje de victorias por usuario
    public Double porcentajeVictorias(Integer idUsuario) {
        long jugadas = partidasRepository.findAll().stream()
                .filter(p -> p.getUsuario() != null && p.getUsuario().getId().equals(idUsuario))
                .count();
        if (jugadas == 0) return 0.0;
        long ganadas = partidasRepository.findAll().stream()
                .filter(p -> p.getJugadorGanador() != null
                        && p.getJugadorGanador().getUsuario() != null
                        && p.getJugadorGanador().getUsuario().getId().equals(idUsuario))
                .count();
        return (ganadas * 100.0) / jugadas;
    }

    // Colores más usados globalmente
    public Map<String, Long> coloresMasUsados() {
        List<Jugadores> jugadores = jugadoresRepository.findAll();
        return jugadores.stream()
                .filter(j -> j.getColor() != null)
                .collect(Collectors.groupingBy(
                        j -> j.getColor().getColor(),
                        Collectors.counting()
                ));
    }

    // Objetivos más/menos cumplidos (cantidad de veces que un objetivo fue cumplido)
    public Map<String, Long> objetivosCumplidos() {
        List<Partidas> partidas = partidasRepository.findAll();
        Map<Integer, Long> conteo = partidas.stream()
                .filter(p -> p.getJugadorGanador() != null && p.getJugadorGanador().getObjetivo() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getJugadorGanador().getObjetivo().getId(),
                        Collectors.counting()
                ));

        // Mapear el id de objetivo a un nombre legible
        Map<String, Long> resultado = new HashMap<>();
        for (var entry : conteo.entrySet()) {
            Optional<Objetivos> obj = objetivosRepository.findById(Long.valueOf(entry.getKey()));
            String nombre = obj.map(o -> "Objetivo " + o.getId()).orElse("Objetivo " + entry.getKey());
            resultado.put(nombre, entry.getValue());
        }
        return resultado;
    }
}
