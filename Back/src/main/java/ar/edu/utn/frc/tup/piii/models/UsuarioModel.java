package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {
    private Integer id;
    private String username;
    private String password;

    public void mapUsuarioModel(Usuarios usuario) {//MODELADO
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
    }

    public UsuarioModel() {
    }

    public UsuarioModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
