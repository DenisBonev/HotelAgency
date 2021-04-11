package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.entity.enums.RoomTypeEnum;
import bg.softuni.hotelagency.model.exception.EntityNotFoundException;
import bg.softuni.hotelagency.model.service.RoomServiceModel;
import bg.softuni.hotelagency.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

    @Mock
    RoomRepository roomRepository;
    ModelMapper modelMapper = new ModelMapper();

    private Hotel hotel;
    private Room room1;
    private Room room2;

    private RoomServiceImpl serviceToTest;

    @BeforeEach
    public void setUp() {
        hotel = new Hotel();
        hotel.
                setName("testHotel").
                setId(2L);

        room1 = new Room();
        room1.
                setName("testRoom").
                setCount(9).
                setPrice(BigDecimal.valueOf(30.0)).
                setSingleBedsCount(0).
                setTwinBedsCount(1).
                setType(RoomTypeEnum.DOUBLE)
                .setHotel(hotel).
                setId(1L);
        room2 = new Room();
        room2.
                setName("testRoom").
                setCount(6).
                setPrice(BigDecimal.valueOf(20.0)).
                setSingleBedsCount(2).
                setTwinBedsCount(0).
                setType(RoomTypeEnum.DOUBLE).
                setHotel(hotel).
                setId(2L);

        serviceToTest = new RoomServiceImpl(roomRepository, new ModelMapper());
    }

//    @Test
//    public void testCreateRoom() {
//        RoomServiceModel roomServiceModel = new RoomServiceModel();
//        roomServiceModel.
//                setName("testRoom").
//                setCount(9).
//                setPrice(BigDecimal.valueOf(30.0)).
//                setSingleBedsCount(0).
//                setTwinBedsCount(1).
//                setType(RoomTypeEnum.DOUBLE)
//                .setHotel(hotel);
//        when(roomRepository.save(modelMapper.map(roomServiceModel, Room.class)))
//                .thenReturn(modelMapper.map(roomServiceModel, Room.class));
//
//        Room room = serviceToTest.createRoom(roomServiceModel);
//
//        assertEquals(room.getCount(),roomServiceModel.getCount());
//    }

    @Test
    public void testGetHotelsRooms() {

        when(roomRepository.getRoomsByHotelIdOrderByPrice(hotel.getId()))
                .thenReturn(List.of(room1, room2));

        List<RoomServiceModel> roomServiceModels = serviceToTest.getHotelsRooms(hotel.getId());

        assertEquals(2, roomServiceModels.size());
        assertEquals(room1.getId(), roomServiceModels.get(0).getId());
        assertEquals(room2.getId(), roomServiceModels.get(1).getId());
        assertEquals(room1.getHotel().getId(), roomServiceModels.get(0).getHotel().getId());
        assertEquals(room2.getHotel().getId(), roomServiceModels.get(1).getHotel().getId());
    }

    @Test
    public void testGetRoomsCountByRoom() {
        when(roomRepository.getRoomsCountByRoom(room1))
                .thenReturn(room1.getCount());

        Integer roomCount = serviceToTest.getRoomsCountByRoom(room1);

        assertEquals(room1.getCount(), roomCount);
    }

    @Test
    public void testGetRoomById() {
        when(roomRepository.findById(room1.getId())).
                thenReturn(Optional.of(room1));

        Room room = serviceToTest.getRoomById(room1.getId());

        assertEquals(room1.getId(), room.getId());
        assertEquals(room1.getHotel(), room.getHotel());
        assertEquals(room1.getName(), room.getName());
    }

    @Test
    public void testGetRoomByIdThrow() {
        Long invalidId = 11111L;
        when(roomRepository.findById(invalidId)).
                thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serviceToTest.getRoomById(invalidId));
    }

    @Test
    public void testPatchChanges() {
        RoomServiceModel roomServiceModel = new RoomServiceModel();
        roomServiceModel.
                setHotel(hotel).
                setName("updated").
                setType(RoomTypeEnum.APARTMENT).
                setPrice(BigDecimal.valueOf(15.1)).
                setCount(5).
                setTwinBedsCount(1).
                setSingleBedsCount(2).
                setId(room1.getId());
        when(roomRepository.findById(roomServiceModel.getId()))
                .thenReturn(Optional.of(room1));

        Long hotelId = serviceToTest.patchChanges(roomServiceModel);

        assertEquals(room1.getHotel().getId(), hotelId);
    }

    @Test
    public void testPatchChangesThrow() {
        Long invalidId = 11111L;
        RoomServiceModel roomServiceModel = new RoomServiceModel();
        roomServiceModel.
                setHotel(hotel).
                setName("updated").
                setType(RoomTypeEnum.APARTMENT).
                setPrice(BigDecimal.valueOf(15.1)).
                setCount(5).
                setTwinBedsCount(1).
                setSingleBedsCount(2).
                setId(invalidId);
        when(roomRepository.findById(roomServiceModel.getId()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serviceToTest.patchChanges(roomServiceModel));
    }
}
