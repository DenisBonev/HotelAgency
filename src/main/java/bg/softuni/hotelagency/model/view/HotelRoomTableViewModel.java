package bg.softuni.hotelagency.model.view;

public class HotelRoomTableViewModel {
    private Long id;
    private String name;

    public HotelRoomTableViewModel() {
    }

    public Long getId() {
        return id;
    }

    public HotelRoomTableViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public HotelRoomTableViewModel setName(String name) {
        this.name = name;
        return this;
    }
}
