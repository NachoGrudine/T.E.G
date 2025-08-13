package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.LoginResponseDto;
import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import ar.edu.utn.frc.tup.piii.models.UsuarioModel;
import ar.edu.utn.frc.tup.piii.repositories.UsuarioRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IAutenticacionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements IAutenticacionService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder;

    public AutenticacionService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    public UsuarioModel registrarUsuario(UsuarioModel model) throws Exception {
        if (usuarioRepository.findByUsername(model.getUsername()).isPresent()) {
            throw new Exception("Usuario ya existe");
        }
        String hash = encoder.encode(model.getPassword());
        Usuarios usuario = new Usuarios();
        usuario.setUsername(model.getUsername());
        usuario.setPassword(encoder.encode(model.getPassword()));
        Usuarios guardado = usuarioRepository.save(usuario);
        UsuarioModel usuarioModel=  new UsuarioModel();
        usuarioModel.mapUsuarioModel(guardado);
        return usuarioModel;
    }

    public LoginResponseDto loginUsuario(UsuarioModel model) throws Exception {
        return usuarioRepository.findByUsername(model.getUsername())
                .filter(user -> encoder.matches(model.getPassword(), user.getPassword()))
                .map(LoginResponseDto::new)
                .orElseThrow(() -> new Exception("Credenciales inválidas"));
    }
}

