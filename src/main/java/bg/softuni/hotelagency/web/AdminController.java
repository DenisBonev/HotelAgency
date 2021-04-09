package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.view.ExceptionLogViewModel;
import bg.softuni.hotelagency.model.view.LogViewModel;
import bg.softuni.hotelagency.service.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final LogService logService;
    private final ModelMapper modelMapper;

    public AdminController(LogService logService, ModelMapper modelMapper) {
        this.logService = logService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/stats")
    public String registerLogs(Model model) {
        List<LogViewModel> logs = logService.
                getRegisterLog().
                stream().
                map(l -> modelMapper.map(l, LogViewModel.class)).
                collect(Collectors.toList());
        model.addAttribute("logs", logs);
        return "stats";
    }

    @GetMapping("/errors")
    public String exceptionLogs(Model model) {
        List<ExceptionLogViewModel> logs = logService.
                getExceptionLog().
                stream().
                map(l -> modelMapper.map(l, ExceptionLogViewModel.class)).
                collect(Collectors.toList());
        model.addAttribute("logs", logs);
        return "exception-stats";
    }

    @GetMapping("/manage-users")
    public String manageUsers() {
        return "manage-users";
    }

}
