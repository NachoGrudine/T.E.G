package ar.edu.utn.frc.tup.piii.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroRequest {
    private String username;
    private String password;
    private String repeatPassword;

    public RegistroRequest() {}

    public RegistroRequest(String username, String password, String repeatPassword) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }
}
