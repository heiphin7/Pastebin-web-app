package pastebin.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
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

    public void save(Paste paste) throws NullPointerException{

        if(checkAllFields(paste)) {
            throw new NullPointerException();
        }

        repository.save(paste);
    }

    public Paste findById(Long id) throws ChangeSetPersister.NotFoundException {
        // if dont founded, just return null & handling in pasteController
        Paste paste = repository.findById(id).orElse(null);

        if(paste == null) {
            throw new ChangeSetPersister.NotFoundException();
        }

        return paste;
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

    public void updatePaste(Long oldPasteId, Paste newPaste) throws ChangeSetPersister.NotFoundException {
        // before make changes, find paste
        Paste paste = repository.findById(oldPasteId).orElse(null);

        if(paste == null) {
            throw new ChangeSetPersister.NotFoundException();
        }

        // just use save method, cause JpaRepo authomaticly change old paste
        repository.save(newPaste);
    }


    public boolean checkAllFields(Paste paste) {
        return paste.getAuthor() == null || paste.getContent().isBlank() ||
                paste.getCreatedDate() == null || paste.getExpirationDate() == null;
    }
}
