package pastebin.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pastebin.mainservice.entity.Paste;
import pastebin.mainservice.service.PasteService;

@Controller
@RequiredArgsConstructor
public class PasteController {

    private final PasteService pasteService;

    // TODO CUSTOM ERROR & SUCCES ENTITES FOR HTTP RESPONSE

    @PostMapping("/create/paste")
    public String createNewPaste(@RequestParam Paste paste) {
        try {
            pasteService.save(paste);
            return "paste успешно сохранен";
        } catch (NullPointerException exception) {
            return "Все поля должны быть заполнены!";
        } catch (Exception exception) { // HTTP 500
            return "Что то пошло не так: " + exception.getMessage();
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
}
