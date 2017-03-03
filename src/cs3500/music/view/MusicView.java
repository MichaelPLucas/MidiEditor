package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Nathan on 3/22/2016.
 * Abstract class being all music views
 */
public abstract class MusicView implements IView {

    protected Map<Integer, List<Note>> notes;

    /**
     * Constructor for initializing a view
     * @param notes as a Map
     */
    public MusicView(Map<Integer, List<Note>> notes) {
        this.notes = notes;
    }


    @Override
    public void addNote(int start, Note note) {

        //Do a correct put
        List<Note> currentBeat = this.notes.get(start);
        if (currentBeat.isEmpty()) {
            currentBeat = new ArrayList<>();
        }
        currentBeat.add(note);
    }

    @Override
    public void removeNote(int start, Note note) {

        //Do a correct remove

        List<Note> currentBeat = this.notes.get(start);

        if (currentBeat.isEmpty()) {
            //Can't happen
        }
        currentBeat.remove(note);
    }

    @Override
    public void processNotes(Map<Integer, List<Note>> notes) {
        this.notes = notes;
    }

}
