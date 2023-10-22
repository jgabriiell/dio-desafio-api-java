package desafiodio.backend.dtos;

import desafiodio.backend.domain.users.UserType;

import java.math.BigDecimal;

public record UserDTO(String name, String email, String password, BigDecimal balance, UserType type) {
}
