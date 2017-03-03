package cs3500.music.view;

import cs3500.music.model.Note;

/**
 * Created by lucasmic on 4/6/2016.
 */
public abstract class ANoteDeletionWindow extends APopupWindow {
    protected int beat;
    protected Note n;

    public abstract void display();

    public void init(int b, Note no) {
        beat = b;
        n = no;
    }

    public int getBeat() {
        return this.beat;
    }

    public Note getNote() {
        return this.n;
    }
}
