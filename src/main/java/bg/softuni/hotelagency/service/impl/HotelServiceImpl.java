package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.exception.EntityNotFoundException;
import bg.softuni.hotelagency.model.service.HotelServiceModel;
import bg.softuni.hotelagency.repository.HotelRepository;
import bg.softuni.hotelagency.service.HotelService;
import bg.softuni.hotelagency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper, UserService userService) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createHotel(HotelServiceModel hotelServiceModel) {
        return hotelRepository.save(modelMapper.map(hotelServiceModel, Hotel.class)).getId();
    }

    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Hotel"));
    }

    @Override
    public void saveChanges(HotelServiceModel hotelServiceModel) {
        Hotel hotel = hotelRepository.findById(hotelServiceModel.getId())
                .orElseThrow(() -> new EntityNotFoundException("Hotel"));
        hotel
                .setName(hotelServiceModel.getName())
                .setAddress(hotelServiceModel.getAddress())
                .setEmail(hotelServiceModel.getEmail())
                .setDescription(hotelServiceModel.getDescription())
                .setStars(hotelServiceModel.getStars());
        hotelRepository.save(hotel);

    }

    @Override
    public List<HotelServiceModel> getAllHotels() {
        return hotelRepository.
                findAll().
                stream().
                map(h -> modelMapper.map(h, HotelServiceModel.class)).
                collect(Collectors.toList());
    }

}
