package bg.softuni.hotelagency.service;

import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.service.RoomServiceModel;

public interface RoomService {
    Room createRoom(RoomServiceModel map);
}
