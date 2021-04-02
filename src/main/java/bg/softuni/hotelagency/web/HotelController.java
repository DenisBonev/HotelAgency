package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.binding.HotelCreateBindingModel;
import bg.softuni.hotelagency.model.binding.RoomAddBindingModel;
import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.enums.StarEnum;
import bg.softuni.hotelagency.model.service.HotelServiceModel;
import bg.softuni.hotelagency.model.service.RoomServiceModel;
import bg.softuni.hotelagency.service.HotelService;
import bg.softuni.hotelagency.service.PictureService;
import bg.softuni.hotelagency.service.RoomService;
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
import java.util.Arrays;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    private final ModelMapper modelMapper;
    private final HotelService hotelService;
    private final PictureService pictureService;
    private final RoomService roomService;
    private final UserService userService;

    public HotelController(ModelMapper modelMapper, HotelService hotelService, PictureService pictureService, RoomService roomService, UserService userService) {
        this.modelMapper = modelMapper;
        this.hotelService = hotelService;
        this.pictureService = pictureService;
        this.roomService = roomService;
        this.userService = userService;
    }

    @ModelAttribute("hotelCreateBindingModel")
    public HotelCreateBindingModel hotelCreateBindingModel() {
        return new HotelCreateBindingModel();
    }

    @ModelAttribute("roomAddBindingModel")
    public RoomAddBindingModel roomAddBindingModel() {
        return new RoomAddBindingModel();
    }


    @GetMapping("/create")
    public String addHotel() {
        return "add-hotel";
    }


    @GetMapping("/add-room/{id}")
    public String addRoom(@PathVariable String id, Model model) {
        model.addAttribute("hotelId", id);
        return "add-room";
    }


    @PostMapping("/add-room/{id}")
    public String addRoomPost(@Valid RoomAddBindingModel roomAddBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("roomAddBindingModel", roomAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.roomAddBindingModel", bindingResult);
            return "redirect:/hotels/add-room/" + id;
        }
        RoomServiceModel roomServiceModel = modelMapper.map(roomAddBindingModel, RoomServiceModel.class).
                setHotel(hotelService.getHotelById(id));
        roomService.createRoom(roomServiceModel);


        return "redirect:/hotels/details/" + id;
    }


    @PostMapping("/create")
    public String addHotelPost(@Valid HotelCreateBindingModel hotelCreateBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal UserDetails principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("hotelCreateBindingModel", hotelCreateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.hotelCreateBindingModel", bindingResult);
            return "redirect:/hotels/create";
        }
        //TODO:Reduce logic in controller
        HotelServiceModel hotelServiceModel = modelMapper.map(hotelCreateBindingModel, HotelServiceModel.class);
        setStarEnum(hotelCreateBindingModel, hotelServiceModel);
        hotelServiceModel.setOwner(userService.getUserByEmail(principal.getUsername()));
        Hotel hotel = hotelService.createHotel(hotelServiceModel);
        pictureService.uploadHotelImages(hotelCreateBindingModel.getPictures(), hotel);

        return "redirect:/hotels/add-room/" + hotel.getId();
    }


    @GetMapping("/details/{id}")
    public String getDetails(@PathVariable Long id) {
        //TODO:visualise details (POST)reserve,edit hotel(owner),add comments
        return "hotel-details";
    }
//TODO:(GLOBAL) validation(front and back end,home and index page)

    private void setStarEnum(HotelCreateBindingModel hotelCreateBindingModel, HotelServiceModel hotelServiceModel) {
        Arrays.stream(StarEnum.values()).forEach(v -> {
            if (hotelCreateBindingModel.getStars().equals(v.toString())) {
                hotelServiceModel.setStars(v);
            }
        });
    }
}


