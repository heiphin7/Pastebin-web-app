package pastebin.mainservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pastebin.mainservice.entity.Paste;
import pastebin.mainservice.entity.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface PasteRepository extends JpaRepository<Paste, Long> {

    // get all paste by user entity
    List<Paste> findByAuthor(User user);

    Optional<Paste> findById(Long id);
}
