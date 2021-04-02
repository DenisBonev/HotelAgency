package bg.softuni.hotelagency.service;

import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.service.RoomServiceModel;

import java.util.List;

public interface RoomService {
    Room createRoom(RoomServiceModel map);

    List<Room> getHotelsRooms(Long hotelId);

    Integer getRoomsCountByRoom(Room room);
}
