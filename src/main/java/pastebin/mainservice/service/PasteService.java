package pastebin.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pastebin.mainservice.entity.Paste;
import pastebin.mainservice.entity.User;
import pastebin.mainservice.repo.PasteRepository;
import pastebin.mainservice.repo.UserRepository;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class PasteService {

    private final PasteRepository repository;
    private final UserRepository userRepository;

    public void save(Paste paste) {
        repository.save(paste);
    }

    public Paste findById(Long id) {
        // if dont founded, just return null & handling in pasteController
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long paste_id, Long user_id) throws ChangeSetPersister.NotFoundException, AccessDeniedException{
        // we take paste id and user id, cause we make sure that paste belongs to the user

        // before delete, we find paste
        Paste paste = repository.findById(paste_id).orElse(null);

        if(paste == null) {
            throw new ChangeSetPersister.NotFoundException();
        }

        User user = userRepository.findById(user_id).orElse(null);

        if(user == null) {
            throw new AccessDeniedException("Paste принадлежит не пользователю");
        }

        // if we find paste and all ok, just delete with repository
        repository.delete(paste);
    }



}
