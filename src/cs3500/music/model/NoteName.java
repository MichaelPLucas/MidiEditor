package cs3500.music.model;

/**
 * Created by NGeye on 3/1/2016.
 * For use with Notes and their names for designation and output
 */
public enum NoteName {
    C("  C"), CSHARP(" C#"), D("  D"), DSHARP(" D#"), E("  E"), F("  F"),
    FSHARP(" F#"), G("  G"), GSHARP(" G#"), A("  A"), ASHARP(" A#"), B("  B");

    private String name;

    NoteName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Returns a NoteName with the given value
     * @param i Value of NoteName as an int
     * @return Corresponding NoteName
     */
    public static NoteName valueOf(int i) {
        switch (i) {
            case 0:
                return NoteName.C;
            case 1:
                return NoteName.CSHARP;
            case 2:
                return NoteName.D;
            case 3:
                return NoteName.DSHARP;
            case 4:
                return NoteName.E;
            case 5:
                return NoteName.F;
            case 6:
                return NoteName.FSHARP;
            case 7:
                return NoteName.G;
            case 8:
                return NoteName.GSHARP;
            case 9:
                return NoteName.A;
            case 10:
                return NoteName.ASHARP;
            case 11:
                return NoteName.B;
            default:
                throw new IllegalArgumentException("NoteName can only be constructed from an int" +
                        " between 0 and 11 inclusive.");
        }
    }

    public int toInt() {
        switch (name) {
            case "  C":
                return 0;
            case " C#":
                return 1;
            case "  D":
                return 2;
            case " D#":
                return 3;
            case "  E":
                return 4;
            case "  F":
                return 5;
            case " F#":
                return 6;
            case "  G":
                return 7;
            case " G#":
                return 8;
            case "  A":
                return 9;
            case " A#":
                return 10;
            case "  B":
                return 11;
            default:
                return 0;
        }
    }
}
