package ar.edu.utn.frc.tup.piii.controllers;



import ar.edu.utn.frc.tup.piii.dtos.common.*;
import ar.edu.utn.frc.tup.piii.models.*;
import ar.edu.utn.frc.tup.piii.services.interfaces.IPartidasService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidas")
@CrossOrigin(origins = "*")
public class PartidasController {

    private final IPartidasService partidasService;

    public PartidasController(IPartidasService partidasService) {
        this.partidasService = partidasService;
    }


    @GetMapping("/usuario/{idUsuario}")
    public List<PartidaModel> obtenerPartidasPorUsuario(@PathVariable Long idUsuario) {
        return partidasService.getPartidas(idUsuario);
    }

    @PostMapping
    public PartidaModel crearPartida(@RequestBody Long idUsuario) {
        return partidasService.crearPartida(idUsuario);
    }

    @GetMapping("/{id}")
    public PartidaModel buscarPartida(@PathVariable Long id) {

        return partidasService.buscarPartida(id);
    }

    @GetMapping("/{id}/colores")
    public List<ColorModel> buscarColores(@PathVariable Long id) {

        return partidasService.buscarColores(id);
    }

    @GetMapping("/{id}/paises")
    public List<PaisJugadorModel> buscarPaises(@PathVariable Long id) {

        return partidasService.getPaisesFromPartida(id);
    }

    @GetMapping("/publicas")
    public List<PartidaModel> obtenerPartidasPublicas() {
        return partidasService.obtenerPartidasPorTipoYEstado();
    }

    @PutMapping("/{id}/empezar")
    public ResponseEntity<Void> empezarPartida(@PathVariable Long id) {
        try {
            partidasService.empezarPartida(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/{id}/configurar")
    public ResponseEntity<Void> configPartida(@PathVariable Long id, @RequestBody PartidaConfigDTO config) {
        try {
            partidasService.configurarPartida(id, config.getObjetivo(), config.getTipo());
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }




    @PostMapping("/{idPartida}/jugadores")
    public PartidaModel agregarJugador(@PathVariable Long idPartida, @RequestBody JugadorDTO jugador) {
        return partidasService.agregarJugador(idPartida, jugador);
    }

    @PutMapping("/{idPartida}/jugadores")
    public PartidaModel editarJugador(@PathVariable Long idPartida, @RequestBody JugadorDTO jugador) {
        return partidasService.agregarJugador(idPartida, jugador);
    }

    @GetMapping("/{id}/rondas/ultima/{idJugador}")
    public RondaModel buscarUltimaRonda( @PathVariable Long id, @PathVariable Long idJugador) {

        return partidasService.buscarUltimaRonda(id, idJugador);
    }
//obtener siguiente ronda


    @GetMapping("/turnos-refuerzos/{id}")
    public ResponseEntity<TurnoRefuerzoModel> obtenerTurnoRefuerzo(@PathVariable Long id) {
        TurnoRefuerzoModel turno = partidasService.obtenerTurnoRefuerzo(id);
        if (turno == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(turno);
    }

    @GetMapping("/turnos-refuerzos/{id}/fichas")
    public FichasDTO obtenerFichas(@PathVariable Long id) {
        FichasDTO fichas = partidasService.obtenerFichas(id);

        return fichas;
    }

    @PostMapping("/turnos-refuerzos/refuerzos")
    public ResponseEntity<?> recibirRefuerzos(@RequestBody List<RefuerzoPostDTO> refuerzos) {
        partidasService.guardarRefuerzos(refuerzos);

        return ResponseEntity.ok().build();
    }



    @GetMapping("/{idPartida}/rondas/{idRonda}/turnos-refuerzos/{idTurno}/finalizar")
    public ResponseEntity<Void> terminarTurnoRefuerzo(@PathVariable Long idPartida, @PathVariable Long idRonda, @PathVariable Long idTurno) {

        partidasService.terminarTurnoRefuerzo(idPartida, idRonda, idTurno);

        return ResponseEntity.ok().build();
    }





    @GetMapping("/turnos-ataques/{id}")
    public ResponseEntity<TurnoAtaqueModel> obtenerTurnoAtaque(@PathVariable Long id) {
        TurnoAtaqueModel turno = partidasService.obtenerTurnoAtaque(id);
        if (turno == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(turno);
    }


    @PostMapping("/turnos-ataque/acciones/combates")
    public ResponseEntity<AccionModel> recibirCombate(@RequestBody AccionCombatePostDTO accionCombatePostDTO) {
        return ResponseEntity.ok(partidasService.hacerAtaque(accionCombatePostDTO));
    }

    @PostMapping("/turnos-ataque/acciones/reagrupaciones")
    public ResponseEntity<AccionModel> recibirReagrupacion(@RequestBody AccionReagrupacionPostDTO accionReagrupacionPostDTO) {
        return ResponseEntity.ok(partidasService.hacerReagrupacion(accionReagrupacionPostDTO));
    }

    @GetMapping("/{idPartida}/rondas/{idRonda}/turnos-ataques/{idTurno}/finalizar")
    public ResponseEntity<Void> terminarTurnoAtaque(@PathVariable Long idPartida, @PathVariable Long idRonda, @PathVariable Long idTurno) {

        partidasService.terminarTurnoAtaque(idPartida, idRonda, idTurno);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/ganador/{id}")
    public ResponseEntity<GanadorDTO> chekearGanador(@PathVariable Long id) {
        return ResponseEntity.ok(partidasService.chekearGanador(id.intValue()));
    }



}
