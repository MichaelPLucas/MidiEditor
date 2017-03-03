package cs3500.music.controller;

import cs3500.music.model.Note;

/**
 * Created by lucasmic on 4/7/2016.
 */
public abstract class Callback implements Runnable {
    int time;
    Note before;
    Note after;

    public void setTime(int t) {
        time = t;
    }

    public void setBefore(Note b) {
        before = b;
    }

    public void setAfter(Note a) {
        after = a;
    }
}
