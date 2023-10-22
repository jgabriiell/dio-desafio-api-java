package desafiodio.backend.services;

import desafiodio.backend.domain.users.User;
import desafiodio.backend.domain.users.UserType;
import desafiodio.backend.dtos.UserDTO;
import desafiodio.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validate(User sender, BigDecimal amount) throws Exception {
        if (sender.getType() == UserType.MERCHANT) {
            throw new Exception("Merchant type cannot transact");
        }
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient balance");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("User Not Found"));
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }

    public User createUser(UserDTO userDto) {
        User user = new User(userDto);
        this.saveUser(user);
        return user;
    }

    public List<User> getAllUsers() {
        return this.repository.findAll();
    }
}
