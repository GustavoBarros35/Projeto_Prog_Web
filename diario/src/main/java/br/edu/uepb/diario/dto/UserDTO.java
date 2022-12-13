package br.edu.uepb.diario.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class UserDTO {
    
    private String username;
    private String password;
    private String authority;
    public Object getNome() {
        return null;
    }
    public Object getSenha() {
        return null;
    }

}