package bg.softuni.hotelagency.service;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.service.HotelServiceModel;

public interface HotelService {
    Hotel createHotel(HotelServiceModel hotelServiceModel);

    Hotel getHotelById(Long id);
}
