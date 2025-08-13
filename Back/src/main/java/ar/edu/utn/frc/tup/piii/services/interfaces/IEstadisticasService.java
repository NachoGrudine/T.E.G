package ar.edu.utn.frc.tup.piii.services.interfaces;

import java.util.Map;

public interface IEstadisticasService {
    Map<String, Object> partidasPorUsuario(Integer idUsuario);
    Double porcentajeVictorias(Integer idUsuario);
    Map<String, Long> coloresMasUsados();
    Map<String, Long> objetivosCumplidos();
}
