package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.binding.UserEditBindingModel;
import bg.softuni.hotelagency.model.binding.UserRegisterBindingModel;
import bg.softuni.hotelagency.model.service.UserServiceModel;
import bg.softuni.hotelagency.model.view.ReservationTableViewModel;
import bg.softuni.hotelagency.model.view.UserEditViewModel;
import bg.softuni.hotelagency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("userRegisterBindingModel")
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @ModelAttribute("userEditBindingModel")
    public UserEditBindingModel userEditBindingModel() {
        return new UserEditBindingModel();
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        if (!model.containsAttribute("usernameOccupied")) {
            model.addAttribute("usernameOccupied", false);
        }
        return "register-user";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String registerUserPost(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:register";
        }
        if (userService.usernameExists(userRegisterBindingModel.getEmail())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("usernameOccupied", true);
            return "redirect:/users/register";
        }
        userService.registerUser(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        return "redirect:login";
    }

    @PostMapping("/login-error")
    public String loginFail(@ModelAttribute("email") String username,
                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("notFound", true);
        redirectAttributes.addFlashAttribute("username", username);
        return "redirect:login";
    }

    @GetMapping("/reservations")
    public String reservations(Model model,
                               @AuthenticationPrincipal UserDetails userDetails) {
        List<ReservationTableViewModel> reservations = userService.
                getUserReservationsByEmail(userDetails.getUsername()).
                stream().map(r -> {
            ReservationTableViewModel reservationTableViewModel = modelMapper.map(r, ReservationTableViewModel.class);
            reservationTableViewModel.setHotel(r.getRoom().getHotel());
            return reservationTableViewModel;
        }).collect(Collectors.toList());
        model.addAttribute("reservations", reservations);
        return "user-reservations";
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model,
                              @AuthenticationPrincipal UserDetails principal) {
        if (!model.containsAttribute("usernameOccupied")) {
            model.addAttribute("usernameOccupied", false);
        }
        model.addAttribute("userData", modelMapper.map(userService.getUserByEmail(principal.getUsername()), UserEditViewModel.class));
        return "edit-user";
    }

    @PatchMapping("/edit-profile")
    public String editProfilePatch(@Valid UserEditBindingModel userEditBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @AuthenticationPrincipal UserDetails principal) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEditBindingModel", userEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEditBindingModel", bindingResult);
            return "redirect:edit-profile";
        }
        if (!principal.getUsername().equals(userEditBindingModel.getEmail()) && userService.usernameExists(userEditBindingModel.getEmail())) {
            redirectAttributes.addFlashAttribute("userEditBindingModel", userEditBindingModel);
            redirectAttributes.addFlashAttribute("usernameOccupied", true);
            return "redirect:edit-profile";
        }
        UserServiceModel userServiceModel = modelMapper.map(userEditBindingModel, UserServiceModel.class);
        userServiceModel.setId(userService.getUserByEmail(principal.getUsername()).getId());
        userService.updateUser(userServiceModel);
        return "redirect:/home";
    }
}
