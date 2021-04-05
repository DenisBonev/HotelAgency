package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.entity.Picture;
import bg.softuni.hotelagency.service.PictureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class PictureRestController {
    private final PictureService pictureService;

    public PictureRestController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostMapping("/picture/delete")
    public ResponseEntity<Picture> deletePicture(@RequestBody String url){
        System.out.println();
        //TODO:complete deleting picture process.
        //pictureService.getPictureByUrl(url);
        return null;
    }
}
