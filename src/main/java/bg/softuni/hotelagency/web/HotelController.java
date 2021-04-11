package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.binding.HotelCreateBindingModel;
import bg.softuni.hotelagency.model.binding.HotelEditBindingModel;
import bg.softuni.hotelagency.model.binding.ReservationCreateBindingModel;
import bg.softuni.hotelagency.model.binding.RoomAddBindingModel;
import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Reservation;
import bg.softuni.hotelagency.model.entity.enums.StarEnum;
import bg.softuni.hotelagency.model.service.HotelServiceModel;
import bg.softuni.hotelagency.model.service.ReservationServiceModel;
import bg.softuni.hotelagency.model.service.RoomServiceModel;
import bg.softuni.hotelagency.model.view.*;
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
import java.util.Objects;
import java.util.stream.Collectors;

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

    @ModelAttribute("hotelEditBindingModel")
    public HotelEditBindingModel hotelEditBindingModel() {
        return new HotelEditBindingModel();
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
        HotelServiceModel hotelServiceModel = modelMapper.map(hotelCreateBindingModel, HotelServiceModel.class);
        setStarEnum(hotelCreateBindingModel.getStars(), hotelServiceModel);
        hotelServiceModel.setOwner(userService.getUserByEmail(principal.getUsername()));
        Long hotelId = hotelService.createHotel(hotelServiceModel);
        pictureService.uploadHotelImages(hotelCreateBindingModel.getPictures(), hotelId);

        return "redirect:/hotels/add-room/" + hotelId;
    }


    @GetMapping("/rooms/manage/{hotelId}")
    public String manageRooms(Model model, @PathVariable Long hotelId) {
        HotelRoomTableViewModel hotelViewModel = modelMapper.map(hotelService.getHotelById(hotelId), HotelRoomTableViewModel.class);
        model.addAttribute("hotel", hotelViewModel);
        return "manage-rooms";
    }

    @GetMapping("/edit-room/{id}")
    private String editRoom(RoomAddBindingModel roomAddBindingModel, Model model, @PathVariable Long id) {
        RoomTableViewModel room = modelMapper.map(roomService.getRoomById(id), RoomTableViewModel.class);
        model.addAttribute("room", room);
        return "edit-room";
    }

    @PatchMapping("/edit-room/{roomId}")
    public String editRoomPatch(@Valid RoomAddBindingModel roomAddBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @PathVariable Long roomId) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("roomAddBindingModel", roomAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.roomAddBindingModel", bindingResult);
            return "redirect:/hotels/edit-room/" + roomId;
        }
        RoomServiceModel room = modelMapper.map(roomAddBindingModel, RoomServiceModel.class);
        room.setId(roomId);
        Long hotelId = roomService.patchChanges(room);
        return "redirect:/hotels/rooms/manage/" + hotelId;
    }

    @GetMapping("/add-room/{id}")
    public String addRoom(@PathVariable String id, Model model) {
        model.addAttribute("hotelId", id);
        return "add-room";
    }


    @PostMapping("/add-room/{hotelId}")
    public String addRoomPost(@Valid RoomAddBindingModel roomAddBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @PathVariable Long hotelId) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("roomAddBindingModel", roomAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.roomAddBindingModel", bindingResult);
            return "redirect:/hotels/add-room/" + hotelId;
        }
        RoomServiceModel roomServiceModel = modelMapper.map(roomAddBindingModel, RoomServiceModel.class).
                setHotel(hotelService.getHotelById(hotelId));
        roomService.createRoom(roomServiceModel);


        return "redirect:/hotels/rooms/manage/" + hotelId;
    }


    @GetMapping("/details/{id}")
    public String detailsGet(@PathVariable Long id,
                             Model model,
                             @AuthenticationPrincipal UserDetails userDetails) {
        Hotel hotel = hotelService.getHotelById(id);
        HotelDetailsViewModel hotelDetailsViewModel = modelMapper.map(hotel, HotelDetailsViewModel.class);
        List<String> pictureUrls = pictureService.getPicturesByHotelId(id);
        hotelDetailsViewModel.setMainPictureUrl(pictureUrls.remove(0));
        hotelDetailsViewModel.setPictureUrls(pictureUrls);
        if (!model.containsAttribute("noRooms")) {
            model.addAttribute("noRooms", false);
        }
        model.addAttribute("isOwner", userService.getUserByEmail(userDetails.getUsername()).getId().equals(hotel.getOwner().getId()));
        model.addAttribute("hotel", hotelDetailsViewModel);
        model.addAttribute("rooms", roomService.getHotelsRooms(id).
                stream().
                map(r -> modelMapper.map(r, RoomReserveViewModel.class)).
                collect(Collectors.toList()));
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

        return "redirect:/users/reservations";
    }

    @GetMapping("/edit/{id}")
    public String editHotel(@PathVariable Long id, Model model) {
        HotelEditViewModel hotelEditViewModel = modelMapper.map(hotelService.getHotelById(id), HotelEditViewModel.class);
        hotelEditViewModel.setImageUrls(pictureService.getPicturesByHotelId(id));
        model.addAttribute("hotelData", hotelEditViewModel);
        return "edit-hotel";
    }

    @PatchMapping("/edit/{id}")
    public String editHotelPost(@Valid HotelEditBindingModel hotelEditBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @PathVariable Long id) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("hotelEditBindingModel", hotelEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.hotelEditBindingModel", bindingResult);
            return "redirect:/hotels/edit/" + id;
        }

        HotelServiceModel hotelServiceModel = modelMapper.map(hotelEditBindingModel, HotelServiceModel.class).
                setId(id);
        setStarEnum(hotelEditBindingModel.getStars(), hotelServiceModel);
        if (!Objects.equals(hotelEditBindingModel.getPictures().get(0).getOriginalFilename(), "")) {
            pictureService.uploadHotelImages(hotelEditBindingModel.getPictures(), id);
        }
        hotelService.saveChanges(hotelServiceModel);

        return "redirect:/hotels/details/" + id;
    }


    @GetMapping("/owned")
    public String ownedHotels() {
        return "my-hotels";
    }

    @GetMapping("/reservations/{id}")
    public String reservations(Model model, @PathVariable Long id) {
        List<ReservationHotelViewModel> reservations = reservationService.getReservationsByHotelId(id).
                stream().map(r -> modelMapper.map(r, ReservationHotelViewModel.class)).
                collect(Collectors.toList());
        model.addAttribute("reservations", reservations);
        return "hotel-reservations";
    }

    private void setStarEnum(String bindedStars, HotelServiceModel hotelServiceModel) {
        Arrays.stream(StarEnum.values()).forEach(v -> {
            if (bindedStars.equals(v.toString())) {
                hotelServiceModel.setStars(v);
            }
        });
    }
}


