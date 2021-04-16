package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LogRestController {

    private final LogService logService;

    public LogRestController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/stats/registered")
    public ResponseEntity<Map<String,Integer>> getRegisteredUsers(){
       return ResponseEntity.ok().body(logService.getRegisterStats());
    }

}
