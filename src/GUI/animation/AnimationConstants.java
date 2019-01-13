package GUI.animation;

public class AnimationConstants {

    private static int numberOfColumns, numberOfFrames;

    //CAT CONSTANTS:
    public static final int[] CAT_LEFT = getConstants("cat left");
    public static final int[] CAT_RIGHT = CAT_LEFT;
    public static final int[] CAT_UP = getConstants("cat up");
    public static final int[] CAT_DOWN = getConstants("cat down");
    public static final int[] CAT_UP_LEFT = getConstants("cat up left");
    public static final int[] CAT_UP_RIGHT = CAT_UP_LEFT;
    public static final int[] CAT_DOWN_LEFT = getConstants("cat down left");
    public static final int[] CAT_DOWN_RIGHT = CAT_DOWN_LEFT;

    //DOG CONSTANTS:
    public static final int[] DOG_LEFT = getConstants("DOG left");
    public static final int[] DOG_RIGHT = DOG_LEFT;
    public static final int[] DOG_UP = getConstants("DOG up");
    public static final int[] DOG_DOWN = getConstants("DOG down");
    public static final int[] DOG_UP_LEFT = getConstants("DOG up left");
    public static final int[] DOG_UP_RIGHT = DOG_UP_LEFT;
    public static final int[] DOG_DOWN_LEFT = getConstants("DOG down left");
    public static final int[] DOG_DOWN_RIGHT = DOG_DOWN_LEFT;

    //BUFFALO CONSTANTS:
    public static final int[] BUFFALO_DEATH = getConstants("buffalo death");
    public static final int[] BUFFALO_EAT = getConstants("buffalo eat");
    public static final int[] BUFFALO_LEFT = getConstants("BUFFALO left");
    public static final int[] BUFFALO_RIGHT = BUFFALO_LEFT;
    public static final int[] BUFFALO_UP = getConstants("BUFFALO up");
    public static final int[] BUFFALO_DOWN = getConstants("BUFFALO down");
    public static final int[] BUFFALO_UP_LEFT = getConstants("BUFFALO up left");
    public static final int[] BUFFALO_UP_RIGHT = BUFFALO_UP_LEFT;
    public static final int[] BUFFALO_DOWN_LEFT = getConstants("BUFFALO down left");
    public static final int[] BUFFALO_DOWN_RIGHT = BUFFALO_DOWN_LEFT;

    //GUINEA_FOWL CONSTANTS:
    public static final int[] GUINEA_FOWL_DEATH = getConstants("GUINEA_FOWL death");
    public static final int[] GUINEA_FOWL_EAT = getConstants("GUINEA_FOWL eat");
    public static final int[] GUINEA_FOWL_LEFT = getConstants("GUINEA_FOWL left");
    public static final int[] GUINEA_FOWL_RIGHT = GUINEA_FOWL_LEFT;
    public static final int[] GUINEA_FOWL_UP = getConstants("GUINEA_FOWL up");
    public static final int[] GUINEA_FOWL_DOWN = getConstants("GUINEA_FOWL down");
    public static final int[] GUINEA_FOWL_UP_LEFT = getConstants("GUINEA_FOWL up left");
    public static final int[] GUINEA_FOWL_UP_RIGHT = GUINEA_FOWL_UP_LEFT;
    public static final int[] GUINEA_FOWL_DOWN_LEFT = getConstants("GUINEA_FOWL down left");
    public static final int[] GUINEA_FOWL_DOWN_RIGHT = GUINEA_FOWL_DOWN_LEFT;

    //OSTRICH CONSTANTS:
    public static final int[] OSTRICH_DEATH = getConstants("OSTRICH death");
    public static final int[] OSTRICH_EAT = getConstants("OSTRICH eat");
    public static final int[] OSTRICH_LEFT = getConstants("OSTRICH left");
    public static final int[] OSTRICH_RIGHT = OSTRICH_LEFT;
    public static final int[] OSTRICH_UP = getConstants("OSTRICH up");
    public static final int[] OSTRICH_DOWN = getConstants("OSTRICH down");
    public static final int[] OSTRICH_UP_LEFT = getConstants("OSTRICH up left");
    public static final int[] OSTRICH_UP_RIGHT = OSTRICH_UP_LEFT;
    public static final int[] OSTRICH_DOWN_LEFT = getConstants("OSTRICH down left");
    public static final int[] OSTRICH_DOWN_RIGHT = OSTRICH_DOWN_LEFT;

    //SHEEP CONSTANTS:
    public static final int[] SHEEP_DEATH = getConstants("SHEEP death");
    public static final int[] SHEEP_EAT = getConstants("SHEEP eat");
    public static final int[] SHEEP_LEFT = getConstants("SHEEP left");
    public static final int[] SHEEP_RIGHT = SHEEP_LEFT;
    public static final int[] SHEEP_UP = getConstants("SHEEP up");
    public static final int[] SHEEP_DOWN = getConstants("SHEEP down");
    public static final int[] SHEEP_UP_LEFT = getConstants("SHEEP up left");
    public static final int[] SHEEP_UP_RIGHT = SHEEP_UP_LEFT;
    public static final int[] SHEEP_DOWN_LEFT = getConstants("SHEEP down left");
    public static final int[] SHEEP_DOWN_RIGHT = SHEEP_DOWN_LEFT;

    //COW CONSTANTS:
    public static final int[] COW_DEATH = getConstants("COW death");
    public static final int[] COW_EAT = getConstants("COW eat");
    public static final int[] COW_LEFT = getConstants("COW left");
    public static final int[] COW_RIGHT = COW_LEFT;
    public static final int[] COW_UP = getConstants("COW up");
    public static final int[] COW_DOWN = getConstants("COW down");
    public static final int[] COW_UP_LEFT = getConstants("COW up left");
    public static final int[] COW_UP_RIGHT = COW_UP_LEFT;
    public static final int[] COW_DOWN_LEFT = getConstants("COW down left");
    public static final int[] COW_DOWN_RIGHT = COW_DOWN_LEFT;

    //LION CONSTANTS:
    public static final int[] LION_LEFT = getConstants("LION left");
    public static final int[] LION_RIGHT = LION_LEFT;
    public static final int[] LION_UP = getConstants("LION up");
    public static final int[] LION_DOWN = getConstants("LION down");
    public static final int[] LION_UP_LEFT = getConstants("LION up left");
    public static final int[] LION_UP_RIGHT = LION_UP_LEFT;
    public static final int[] LION_DOWN_LEFT = getConstants("LION down left");
    public static final int[] LION_DOWN_RIGHT = LION_DOWN_LEFT;
    public static final int[] LION_CAGED = getConstants("lion caged");

    //GRIZZLY CONSTANTS:
    public static final int[] GRIZZLY_LEFT = getConstants("GRIZZLY left");
    public static final int[] GRIZZLY_RIGHT = GRIZZLY_LEFT;
    public static final int[] GRIZZLY_UP = getConstants("GRIZZLY up");
    public static final int[] GRIZZLY_DOWN = getConstants("GRIZZLY down");
    public static final int[] GRIZZLY_UP_LEFT = getConstants("GRIZZLY up left");
    public static final int[] GRIZZLY_UP_RIGHT = GRIZZLY_UP_LEFT;
    public static final int[] GRIZZLY_DOWN_LEFT = getConstants("GRIZZLY down left");
    public static final int[] GRIZZLY_DOWN_RIGHT = GRIZZLY_DOWN_LEFT;
    public static final int[] GRIZZLY_CAGED = getConstants("GRIZZLY caged");

    //CAGE CONSTANTS:
    public static final int[] CAGE_BREAK = getConstants("cage break");
    public static final int[] CAGE_BREAK_1 = CAGE_BREAK;
    public static final int[] CAGE_BREAK_2 = CAGE_BREAK;
    public static final int[] CAGE_BREAK_3 = CAGE_BREAK;
    public static final int[] CAGE_BREAK_4 = CAGE_BREAK;
    public static final int[] CAGE_BUILD_1 = getConstants("cage build 1");
    public static final int[] CAGE_BUILD_2 = getConstants("cage build 2");
    public static final int[] CAGE_BUILD_3 = getConstants("cage build 3");
    public static final int[] CAGE_BUILD_4 = getConstants("cage build 4");

    //GRASS CONSTANTS:
    public static final int[] GRASS = getConstants("grass");
    public static final int[] GRASS_1 = GRASS;
    public static final int[] GRASS_2 = GRASS;
    public static final int[] GRASS_3 = GRASS;
    public static final int[] GRASS_4 = GRASS;

    //WELL CONSTANTS:
    public static final int[] WELL = getConstants("well");
    public static final int[] WELL_1 = WELL;
    public static final int[] WELL_2 = WELL;
    public static final int[] WELL_3 = WELL;
    public static final int[] WELL_4 = WELL;

    //HELICOPTER CONSTANTS:
    public static final int[] HELICOPTER_MINI = getConstants("helicopter");
    public static final int[] HELICOPTER_MINI_1 = HELICOPTER_MINI;
    public static final int[] HELICOPTER_MINI_2 = HELICOPTER_MINI;
    public static final int[] HELICOPTER_MINI_3 = HELICOPTER_MINI;
    public static final int[] HELICOPTER_MINI_4 = HELICOPTER_MINI;

    //TRUCK CONSTANTS:
    public static final int[] TRUCK_MINI = getConstants("truck");
    public static final int[] TRUCK_MINI_1 = TRUCK_MINI;
    public static final int[] TRUCK_MINI_2 = TRUCK_MINI;
    public static final int[] TRUCK_MINI_3 = TRUCK_MINI;
    public static final int[] TRUCK_MINI_4 = TRUCK_MINI;

    //WORKSHOP CONSTANTS:
    public static final int[] WORKSHOP = getConstants("workshop");


    private static int[] getConstants(String animalName) {

        numberOfFrames = 24;

        String str = animalName.replaceAll("_", " ").toLowerCase();
        str = str.replace("right", "left");

        String[] splitted = str.split(" ");
        String typeName = splitted[0];

        if (typeName.equals("grass") || typeName.equals("well") || typeName.equals("workshop")) {
            numberOfFrames = 16;
            numberOfColumns = 4;
        }

        if (typeName.equals("helicopter")) {
            numberOfColumns = 3;
            numberOfFrames = 6;
        }

        if (typeName.equals("truck")) {
            numberOfFrames = numberOfColumns = 2;
        }

        if (typeName.equals("cow"))
            numberOfColumns = 3;
        if (typeName.equals("grizzly") || typeName.equals("sheep") || typeName.equals("ostrich"))
            numberOfColumns = 4;
        if (typeName.equals("guinea") || typeName.equals("cage"))
            numberOfColumns = 5;
        if (typeName.equals("buffalo") || typeName.equals("cat") || typeName.equals("dog") || typeName.equals("lion"))
            numberOfColumns = 6;

        switch (str) {
            case "cage build 2":
                numberOfFrames = 8;
                numberOfColumns = 4;
                break;

            case "cage build 5":
                numberOfColumns = 2;
                numberOfFrames = 4;
                break;

            case "lion left":
                numberOfColumns = 3;
                break;

            case "cage build 1":
            case "cage build 3":
                numberOfColumns = 3;
                numberOfFrames = 9;
                break;

            case "cow eat":
            case "cow up":
            case "buffalo left":
            case "buffalo up left":
            case "cat left":
            case "lion down left":
                numberOfColumns = 4;
                break;

            case "sheep down":
            case "sheep up":
            case "sheep up left":
            case "dog down left":
            case "dog up left":
            case "lion down":
                numberOfColumns = 5;
                break;

            case "buffalo death":
                numberOfColumns = 5;
                numberOfFrames = 30;
                break;

            case "grizzly caged":
            case "ostrich death":
            case "ostrich down":
            case "ostrich down left":

                numberOfColumns = 6;
                break;

            case "buffalo eat":
                numberOfFrames = 22;
                break;

        }

        return new int[]{numberOfColumns, numberOfFrames};
    }

}
