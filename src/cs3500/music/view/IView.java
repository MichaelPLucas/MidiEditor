package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;

/**
 * Created by ngeyer on 3/18/2016.
 * Interface for all views
 */
public interface IView {

    /**
     * Adds a specific note to the view
     * @param start integer
     * @param note Note
     */
    void addNote(int start, Note note);

    /**
     * Removes a note from the view
     * @param start integer
     * @param note Note
     */
    void removeNote(int start, Note note);

    /**
     * Processes all the notes given by the model
     * @param notes
     */
    void processNotes(Map<Integer, List<Note>> notes);

    /**
     * Plays the piece of music (either on the gui or midi)
     */
    void play();

    /**
     * Stops the music
     */
    void stop();

}
