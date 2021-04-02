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


@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper, UserService userService) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Hotel createHotel(HotelServiceModel hotelServiceModel) {
        return hotelRepository.save(modelMapper.map(hotelServiceModel, Hotel.class));
    }

    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).
                orElseThrow(()->new EntityNotFoundException("Hotel"));
    }

}
