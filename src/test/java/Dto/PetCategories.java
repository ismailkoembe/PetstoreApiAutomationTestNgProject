package Dto;

public enum PetCategories {
    /**
     * The following information were taken from PetStore app code
     *
     *         categories.add(createCategory(1, "Dogs"));
     *         categories.add(createCategory(2, "Cats"));
     *         categories.add(createCategory(3, "Rabbits"));
     *         categories.add(createCategory(4, "Lions"));
     * */

    DOGS("Dog",1 ),
    CATS("Cat", 2),
    RABBITS("Rabbit", 3),
    LIONS("Lion", 4);

    public final String name;


    public String getName() {
        return name;
    }

    public final int categoryNumber;

    PetCategories(String name, int categoryNumber) {
        this.name = name;
        this.categoryNumber = categoryNumber;
    }

    public int getCategoryNumber() {
        return categoryNumber;
    }
}
