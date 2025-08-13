package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.LoginResponseDto;
import ar.edu.utn.frc.tup.piii.models.UsuarioModel;
import org.springframework.stereotype.Service;

public interface IAutenticacionService {
    LoginResponseDto loginUsuario(UsuarioModel model) throws Exception;
    UsuarioModel registrarUsuario(UsuarioModel model) throws Exception;

}
