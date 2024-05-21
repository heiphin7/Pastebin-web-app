package pastebin.mainservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pastebin.mainservice.entity.User;
import pastebin.mainservice.repo.UserRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow
                (() -> new UsernameNotFoundException(
                        String.format("Пользователь %s найден", username)
                ));

        return new org.springframework.security.core.userdetails.User( // User in Spring security
                user.getUsername(),
                user.getPassword(),
                null // authorities
        );
    }

}