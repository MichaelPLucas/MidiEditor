package cs3500.music.model;

/**
 * Created by NGeye on 3/1/2016.
 * Represents a note
 */
public class Note implements Comparable<Note>{

    private NoteName name;
    private int octave;
    private int duration;
    private int instrument;
    private int volume;

    public Note() { }

    public Note(NoteName name, int octave, int duration, int instrument, int volume) {
        //Check that the range for the octave is good

        if (octave <= 0) {
            throw new IllegalArgumentException("Invalid octave: " + octave);
        }
        else if (duration < 0) {
            throw new IllegalArgumentException("Invalid duration: " + duration);
        }
        else if (instrument < 0) {
            throw new IllegalArgumentException("Invalid insrument: " + instrument);
        }
        else if (volume < 0) {
            throw new IllegalArgumentException("Invalid volume: " + volume);
        }

        this.name = name;
        this.octave = octave;
        this.duration = duration;
        this.instrument = instrument;
        this.volume = volume;
    }

    /*

        Getters

     */

    public NoteName getName() {
        return this.name;
    }

    public int getOctave() {
        return this.octave;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getInstrument() {
        return this.instrument;
    }

    public int getVolume() {
        return this.volume;
    }

    /*

        Methods

     */

    /**
     * For use to show the name of the given note
     * @return the name of the note as a String
     */
    public String getNameAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name.getName() + this.octave + " ");
        return sb.toString();
    }

    public int toInt() {
        return octave * 12 + name.toInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Note) {
            Note that = (Note)o;
            if (that.getDuration() == this.duration &&
                    that.getName() == this.getName() &&
                    that.getOctave() == this.getOctave()) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }

    }

    @Override
    public int compareTo(Note note) {
        if (this.octave < note.octave) {
            return -1;
        }
        else if (this.octave > note.octave) {
            return 1;
        }
        else {

            return this.name.compareTo(note.getName());
        }
    }
}
