package pastebin.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pastebin.mainservice.entity.User;
import pastebin.mainservice.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

}
