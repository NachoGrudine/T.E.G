package ar.edu.utn.frc.tup.piii;


import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private Integer id;
    private String username;

    public LoginResponseDto(Usuarios usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
    }
}
