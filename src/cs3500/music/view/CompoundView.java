package cs3500.music.view;

import cs3500.music.controller.AMouseListener;
import cs3500.music.model.Note;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;

/**
 * Created by ngeyer on 4/5/2016.
 */
public class CompoundView implements IView {

    private GuiViewImpl guiView;
    private MidiViewImpl midiView;
    private Map<Integer, List<Note>> notes;
    private BeatListener bl;
    private int tempo;

    public CompoundView(Map<Integer, List<Note>> notes, int tempo) {
        this.tempo = tempo;
        this.guiView = new GuiViewImpl(notes);
        bl = new BeatListener(this.guiView);
        this.midiView = new MidiViewImpl(notes);
        this.midiView.addListener(bl);
        this.notes = notes;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    @Override
    public void addNote(int start, Note note) {

    }

    @Override
    public void removeNote(int start, Note note) {

    }

    public long getBeat() {
        return midiView.getBeat();
    }

    @Override
    public void processNotes(Map<Integer, List<Note>> notes) {
        this.notes = notes;
        this.guiView.processNotes(notes);
        this.midiView.processNotes(notes);

    }

    @Override
    public void play() {
        this.midiView.setTempo(tempo);
        this.processNotes(notes);

        this.midiView.play();
    }

    /**
     * begins playback at the beginning
     */
    public void play(int beat) {


        if (beat != 0){
            this.guiView.setTime(beat-1);
            bl.setCounter(beat-1);
        }
        else {
            this.guiView.setTime(beat);
            bl.setCounter(beat);
        }


        this.processNotes(notes);
        this.midiView.setTempo(tempo);
        this.midiView.play(beat);

    }

    /**
     * Sets the time to the correct beat of the song
     * @param beat
     */
    public void setTime(int beat) {
        this.midiView.setBeat(beat);
        this.guiView.setTime(beat);
    }


    @Override
    public void stop() {
        midiView.stop();
    }

    public void addKeyListener(KeyListener kl) {
        guiView.addKeyListener(kl);
    }

    public void addActionListener(ActionListener al) {

    }

    public void registerMouseListener(AMouseListener ml) {
        this.guiView.addMouseListener(ml);
    }

    public void update() {
        this.guiView.refresh();
    }

}
