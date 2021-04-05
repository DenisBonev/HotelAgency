package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.entity.Picture;
import bg.softuni.hotelagency.service.PictureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class PictureRestController {
    private final PictureService pictureService;

    public PictureRestController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @DeleteMapping("/picture/delete")
    public ResponseEntity<Picture> deletePicture(@RequestBody String url) throws IOException {
        pictureService.deleteByUrl(url);
        return ResponseEntity.status(204).build();
    }
}
