package cs3500.music.view;

import cs3500.music.model.Note;

/**
 * Created by lucasmic on 4/6/2016.
 */
public abstract class ANoteEditWindow extends APopupWindow {
    protected Note startingNote;
    protected int startingBeat;

    public abstract void display();

    public void init(int s, Note n) {
        startingBeat = s;
        startingNote = n;
    }

    public int getStartingBeat() {
        return this.startingBeat;
    }

    public Note getStartingNote() {
        return this.startingNote;
    }
}
