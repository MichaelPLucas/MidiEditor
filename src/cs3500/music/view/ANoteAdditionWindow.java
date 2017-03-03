package cs3500.music.view;

import cs3500.music.model.NoteName;

/**
 * Created by lucasmic on 4/5/2016.
 */
public abstract class ANoteAdditionWindow extends APopupWindow {
    protected int startingBeat;
    protected NoteName pitch;
    protected int octave;

    public abstract void display();

    public void init(int s, NoteName p, int o) {
        startingBeat = s;
        pitch = p;
        octave = o;
    }

    public int getStartingBeat() {
        return this.startingBeat;
    }
}