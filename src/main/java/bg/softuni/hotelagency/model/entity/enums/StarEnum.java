package bg.softuni.hotelagency.model.entity.enums;

public enum StarEnum {
    ONE("⭐"),TWO("⭐⭐"),THREE("⭐⭐⭐"),FOUR("⭐⭐⭐⭐"),FIVE("⭐⭐⭐⭐⭐");

    private String value;

    StarEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
