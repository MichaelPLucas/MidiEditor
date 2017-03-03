package cs3500.music.view;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.Synthesizer;

/**
 * Created by lucasmic on 3/31/2016.
 */
public class BeatListener implements MetaEventListener {
    private int counter;
    private GuiView view;

    BeatListener(GuiView v) {
        counter = 0;
        view = v;
    }

    @Override
    public void meta(MetaMessage meta) {

        //Default meta message for timing on every beat


        System.out.println(meta.getType());
        view.setTime(counter);
        counter++;
    }

    public void setCounter(int beat) {
        counter = beat;
    }
}
