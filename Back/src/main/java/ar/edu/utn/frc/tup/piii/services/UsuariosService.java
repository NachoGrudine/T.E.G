package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import ar.edu.utn.frc.tup.piii.repositories.UsuarioRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuariosService implements IUsuariosService {
    private final UsuarioRepository usuariosRepository;

    @Autowired
    public UsuariosService(UsuarioRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public Usuarios getUsuarioById(Long id) {
        Optional<Usuarios> user = usuariosRepository.findById(id);
        if (user.isEmpty()){
            throw new RuntimeException("Usuario no encontrado");
        }
        return user.get();
    }
}
