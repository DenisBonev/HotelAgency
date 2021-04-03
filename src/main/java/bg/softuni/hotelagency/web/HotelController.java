package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.binding.HotelCreateBindingModel;
import bg.softuni.hotelagency.model.binding.ReservationCreateBindingModel;
import bg.softuni.hotelagency.model.binding.RoomAddBindingModel;
import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Reservation;
import bg.softuni.hotelagency.model.entity.enums.StarEnum;
import bg.softuni.hotelagency.model.service.HotelServiceModel;
import bg.softuni.hotelagency.model.service.ReservationServiceModel;
import bg.softuni.hotelagency.model.service.RoomServiceModel;
import bg.softuni.hotelagency.model.view.HotelDetailsViewModel;
import bg.softuni.hotelagency.service.*;
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
import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    private final ModelMapper modelMapper;
    private final HotelService hotelService;
    private final PictureService pictureService;
    private final RoomService roomService;
    private final UserService userService;
    private final ReservationService reservationService;

    public HotelController(ModelMapper modelMapper, HotelService hotelService, PictureService pictureService, RoomService roomService, UserService userService, ReservationService reservationService) {
        this.modelMapper = modelMapper;
        this.hotelService = hotelService;
        this.pictureService = pictureService;
        this.roomService = roomService;
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @ModelAttribute("hotelCreateBindingModel")
    public HotelCreateBindingModel hotelCreateBindingModel() {
        return new HotelCreateBindingModel();
    }

    @ModelAttribute("roomAddBindingModel")
    public RoomAddBindingModel roomAddBindingModel() {
        return new RoomAddBindingModel();
    }

    @ModelAttribute("reservationCreateBindingModel")
    public ReservationCreateBindingModel reservationCreateBindingModel() {
        return new ReservationCreateBindingModel();
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
    public String detailsGet(@PathVariable Long id, Model model) {
        //TODO: (POST)reserve,edit hotel(owner),add comments
        HotelDetailsViewModel hotelDetailsViewModel = modelMapper.map(hotelService.getHotelById(id), HotelDetailsViewModel.class);
        List<String> pictureUrls = pictureService.getPicturesByHotelId(id);
        hotelDetailsViewModel.setMainPictureUrl(pictureUrls.remove(0));
        hotelDetailsViewModel.setPictureUrls(pictureUrls);
        if (!model.containsAttribute("noRooms")) {
            model.addAttribute("noRooms", false);
        }
        model.addAttribute("hotel", hotelDetailsViewModel);
        model.addAttribute("rooms", roomService.getHotelsRooms(id));
        return "hotel-details";
    }

    @PostMapping("/reserve/{id}")
    public String reservePost(@Valid ReservationCreateBindingModel reservationCreateBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @PathVariable String id,
                              @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reservationCreateBindingModel", reservationCreateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reservationCreateBindingModel", bindingResult);
            return "redirect:/hotels/details/" + id;
        }
        ReservationServiceModel reservationServiceModel =
                modelMapper.map(reservationCreateBindingModel, ReservationServiceModel.class).
                        setUser(userService.getUserByEmail(userDetails.getUsername()))
                        .setRoom(roomService.getRoomById(reservationCreateBindingModel.getRoom()));
        Reservation reservation = reservationService.addReservation(reservationServiceModel);
        if (reservation == null) {
            redirectAttributes.addFlashAttribute("reservationCreateBindingModel", reservationCreateBindingModel);
            redirectAttributes.addFlashAttribute("noRooms", true);
            return "redirect:/hotels/details/" + id;
        }

        //TODO make template V
        return "redirect:/users/reservations";
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


