package Dto;

public enum Status {
    AVAILABLE ("available"),
    PENDING ("pending"),
    SOLD("sold"),
    APPROVED("approved"),
    PLACED("placed"),
    DELIVERED ("delivered");



    public final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
