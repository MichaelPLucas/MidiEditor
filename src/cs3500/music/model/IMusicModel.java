package cs3500.music.model;

import java.util.List;
import java.util.Map;

/**
 * Created by NGeye on 3/1/2016.
 */
public interface IMusicModel {

    /**
     * Adds a note to model
     * @param note note name as a string
     * @param octave octave as a positive int
     * @param duration as a positive duration
     * @param start as a positive start
     * @throws IllegalArgumentException if the string representation of the note is incorrect
     * Or if duration, octave or start are invalid
     */
    void addNote(NoteName note,int octave, int duration, int start, int instrument, int volume)
            throws IllegalArgumentException;

    /**
     * Removes a specific note from the sheet of music
     * @param note note name as a string
     * @param octave octave as a positive int
     * @param duration as a positive duration
     * @param start as a positive start
     * @throws IllegalArgumentException if the string representation of the note is incorrect
     * Or if duration, octave or start are invalid
     */
    void removeNote(NoteName note, int octave, int duration, int start, int instrument, int volume)
            throws IllegalArgumentException;

    /**
     * Combines that music model to this music model by overlaying it
     * @param model
     */
    void combine(IMusicModel model);

    /**
     * Appends that music model onto this one by placing it at the end
     * @param model
     */
    void append(IMusicModel model);


    /**
     * Gets the tempo of the model
     * @return
     */
    int getTempo();

    /**
     * Returns a deep copy of all the notes in the model
     * @return
     */
    Map<Integer, List<Note>> getAllNotes();

}
