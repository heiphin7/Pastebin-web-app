package pastebin.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pastebin.mainservice.entity.Paste;
import pastebin.mainservice.error.ApplicationError;
import pastebin.mainservice.service.PasteService;

import java.nio.file.AccessDeniedException;

@Controller
@RequiredArgsConstructor
public class PasteController {

    private final PasteService pasteService;

    // TODO CUSTOM ERROR & SUCCES ENTITES FOR HTTP RESPONSE

    @PostMapping("/create/paste")
    public ResponseEntity<?> createNewPaste(@RequestParam Paste paste) {
        try {
            pasteService.save(paste);
            return ResponseEntity.ok("Paste успешно сохранен");
        } catch (NullPointerException exception) {
            return new ResponseEntity<>("Все поля должны быть заполнены!", HttpStatus.BAD_REQUEST);
        } catch (Exception exception) { // HTTP 500
            return ResponseEntity.ok("Произошла какая-то ошибка!");
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Paste paste = pasteService.findById(id);
            return ResponseEntity.ok(paste);
        } catch (ChangeSetPersister.NotFoundException exception) {
            return new ResponseEntity<>("Paste с " + id + " не найден!", HttpStatus.NOT_FOUND);
        } catch (Exception exception) { // HTTP 500
            return new ResponseEntity<>("Произошла какая-то ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/change/paste")
    public ResponseEntity<?> updatePaste(@RequestParam Paste paste, Long oldPasteId) {
        try {
            pasteService.updatePaste(oldPasteId, paste);
            return ResponseEntity.ok("Paste успешно изменен");
        } catch (ChangeSetPersister.NotFoundException exception) {
            return new ResponseEntity<>("Paste для изменения не найден!", HttpStatus.NOT_FOUND);
        } catch (Exception exception) { // HTTP 500
            return new ResponseEntity<>("Произошла какая-то ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/paste/{paste_id}")
    public ResponseEntity<?> deletePasteById(@PathVariable Long paste_id, Long user_id) {
        try {
            pasteService.deleteById(paste_id, user_id);
            return ResponseEntity.ok("Paste успешно удалён!");
        } catch (ChangeSetPersister.NotFoundException exception) {
            return new ResponseEntity<>("Paste с id=" + paste_id + " не найден!", HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException exception) {
            // Когда пользователь пытается удалить чужой paste
            return new ResponseEntity<>("Вы не можете удалять чужие посты!", HttpStatus.FORBIDDEN);
        } catch (Exception exception) { // HTTP 500
            return new ResponseEntity<>("Произошла какая-то ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
