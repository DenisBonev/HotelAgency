package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.view.HotelCardViewModel;
import bg.softuni.hotelagency.model.view.RoomTableViewModel;
import bg.softuni.hotelagency.service.HotelService;
import bg.softuni.hotelagency.service.PictureService;
import bg.softuni.hotelagency.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hotels")
public class HotelRestController {

    private final HotelService hotelService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final RoomService roomService;

    public HotelRestController(HotelService hotelService, PictureService pictureService, ModelMapper modelMapper, RoomService roomService) {
        this.hotelService = hotelService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.roomService = roomService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<HotelCardViewModel>> getAll() {
        List<HotelCardViewModel> hotelCardViewModels = hotelService.
                getAllHotels().
                stream().
                map(h -> {
                    HotelCardViewModel model = modelMapper.map(h, HotelCardViewModel.class);
                    model.setMainPictureUrl(pictureService.getPicturesByHotelId(h.getId()).get(0));
                    return model;
                }).
                collect(Collectors.toList());

        return ResponseEntity.ok().body(hotelCardViewModels);
    }

    @GetMapping("/api/owned")
    public ResponseEntity<List<HotelCardViewModel>> getOwnedHotels(@AuthenticationPrincipal UserDetails principal) {
        List<HotelCardViewModel> hotelCardViewModels = hotelService.
                getHotelsByOwnerEmail(principal.getUsername()).
                stream().
                map(h -> {
                    HotelCardViewModel model = modelMapper.map(h, HotelCardViewModel.class);
                    model.setMainPictureUrl(pictureService.getPicturesByHotelId(h.getId()).get(0));
                    return model;
                }).
                collect(Collectors.toList());

        return ResponseEntity.ok().body(hotelCardViewModels);
    }

    @GetMapping("/rooms/{hotelId}")
    public ResponseEntity<List<RoomTableViewModel>> getRoomsByHotelId(@PathVariable Long hotelId) {
        List<RoomTableViewModel> rooms = roomService.
                getHotelsRooms(hotelId).
                stream().
                map(r -> modelMapper.map(r, RoomTableViewModel.class)).
                collect(Collectors.toList());

        return ResponseEntity.ok().body(rooms);
    }

}
