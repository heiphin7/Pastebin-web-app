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
@RequestMapping("/v1/paste")
public class PasteController {

    private final PasteService pasteService;
    private final String currentPath = "/api/v1/paste";
    @PostMapping("/create")
    public ResponseEntity<?> createNewPaste(@RequestParam Paste paste) {
        try {
            pasteService.save(paste);
            return new ResponseEntity<>(new ApplicationError(HttpStatus.OK.value(), "Paste успешно сохранен!", "Succes", currentPath + "/create"), HttpStatus.OK);
        } catch (NullPointerException exception) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.value(), "Все поля должны быть заполнены!" , "NullPointerError" , currentPath + "/create"), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) { // HTTP 500
            return new ResponseEntity<>(new ApplicationError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Произошла какая-то ошибка!", "ServerError" , currentPath + "/create"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Paste paste = pasteService.findById(id);
            return ResponseEntity.ok(paste);
        } catch (ChangeSetPersister.NotFoundException exception) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.NOT_FOUND.value(), "Paste с id=" + id + " не найден!", "NotFoundError" , currentPath + "/find/" + id), HttpStatus.NOT_FOUND);
        } catch (Exception exception) { // HTTP 500
            return new ResponseEntity<>(new ApplicationError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Произошла какая-то ошибка" , "ServerError", currentPath + "/find/" + id), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/change")
    public ResponseEntity<?> updatePaste(@RequestParam Paste paste, Long oldPasteId) {
        try {
            pasteService.updatePaste(oldPasteId, paste);
            return ResponseEntity.ok("Paste успешно изменен");
        } catch (ChangeSetPersister.NotFoundException exception) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.NOT_FOUND.value(), "Paste не найден!", "NotFoundError", currentPath + "/change"), HttpStatus.NOT_FOUND);
        } catch (Exception exception) { // HTTP 500
            return new ResponseEntity<>(new ApplicationError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Произошла какая-то ошибка" , "ServerError", currentPath + "/change"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{paste_id}")
    public ResponseEntity<?> deletePasteById(@PathVariable Long paste_id, Long user_id) {
        try {
            pasteService.deleteById(paste_id, user_id);
            return ResponseEntity.ok("Paste успешно удалён!");
        } catch (ChangeSetPersister.NotFoundException exception) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.NOT_FOUND.value(), "Paste с id=" + paste_id + " не найден!","NotFoundError", currentPath+"/delete/" + paste_id), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException exception) {
            // Когда пользователь пытается удалить чужой paste
            return new ResponseEntity<>(new ApplicationError(HttpStatus.FORBIDDEN.value(), "Вы не можете удалять чужие посты" , "ForbiddenError" , currentPath + "/delete/" + paste_id), HttpStatus.FORBIDDEN);
        } catch (Exception exception) { // HTTP 500
            return new ResponseEntity<>(new ApplicationError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Произошла какая-то ошибка" , "ServerError", currentPath + "/delete/" + paste_id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
