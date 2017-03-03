package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.tests.MockSequencer;

import javax.sound.midi.*;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A skeleton for MIDI playback
 * Uses a sequence and sequencer:
 * Go to http://docs.oracle.com/javase/8/docs/technotes/guides/sound/programmer_guide/chapter11.html
 *
 */
public class MidiViewImpl extends MusicView implements IView {

    //Tempo track located at -1
    private Map<Integer, Track> _tracks;
    private final Sequencer sequencer;
    private Sequence sequence;
    private long position;
    private MetaEventListener listener;
    private int tempo;
    private long beats;
    private long tick;


    public MidiViewImpl(Map<Integer, List<Note>> notes) {
        super(notes);


        Sequencer tempSequencer;
        try {
            tempSequencer = MidiSystem.getSequencer();
            tempSequencer.open();
            //Default for better resolution
            sequence = new Sequence(Sequence.PPQ, 96);

        } catch (MidiUnavailableException e) {
            tempSequencer = null;
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            tempSequencer = null;
            e.printStackTrace();
        }
        sequencer = tempSequencer;
        _tracks = new TreeMap<>();
    }

    /**
     * Constructor for testing
     * @param notes
     * @param seq
     */
    public MidiViewImpl(Map<Integer, List<Note>> notes, int tempo, MockSequencer seq) {
        super(notes);


        try {
            //Default for better resolution
            sequence = new Sequence(Sequence.PPQ, 96);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        sequencer = seq;
        _tracks = new TreeMap<>();
        this.tempo = tempo;
        sequencer.setTempoInMPQ(tempo);
    }

    public void setBeat(long beat) {
        this.tick = beat * 96;
    }

    public long getBeat() {
        return tick/96;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    @Override
    public void play() {

        if (listener != null) {
            this.sequencer.addMetaEventListener(listener);
        }
        processSequence();
        if (this.sequence.getTracks().length != 0) {

            this.sequencer.setTickPosition(tick);
            this.sequencer.setTempoInMPQ(tempo);
            this.sequencer.start();
        }

    }

    public void play(int beat) {
        if (listener != null) {
            this.sequencer.addMetaEventListener(listener);
        }
        processSequence();
        if (this.sequence.getTracks().length != 0) {

            this.sequencer.setTickPosition(beat * 96);
            this.sequencer.setTempoInMPQ(tempo);
            this.sequencer.start();
        }
    }

    @Override
    public void stop() {
        tick = this.sequencer.getTickPosition();
        this.sequencer.stop();

    }


    /**
     * Adds the specified beat listener for adequate playback
     * Thanks sequencer obj.
     * @param beatListener
     */
    public void addListener(BeatListener beatListener) {
        listener = beatListener;
    }

    private void processSequence() {



        if (this.notes.isEmpty()) {
            //Do nothing
        }
        else {


            if (listener != null) {
                this.sequencer.addMetaEventListener(listener);
            }

            //iterate through all the notes in the model and place in the sequencer
            for (Map.Entry<Integer, List<Note>> s : this.notes.entrySet()) {
                //Iterate through all the notes to process them
                for (Note n : s.getValue()) {
                    Track currentTrack = _tracks.get(n.getInstrument());

                    //If this track is null get a new one
                    if (currentTrack == null) {
                        currentTrack = sequence.createTrack();
                        _tracks.put(n.getInstrument(), currentTrack);
                    }



                    //Lets create the start and end of this note
                    try {

                        MidiMessage startMessage = new ShortMessage(ShortMessage.NOTE_ON,
                                n.getInstrument(), n.toInt(), n.getVolume());
                        MidiEvent ev = new MidiEvent(startMessage, s.getKey() *
                                this.sequence.getResolution());

                        //Creates the end of the note at the end tick
                        MidiMessage endMessage = new ShortMessage(ShortMessage.NOTE_OFF,
                                n.getInstrument(), n.toInt(), n.getVolume());
                        MidiEvent end = new MidiEvent(endMessage, (s.getKey() + n.getDuration()) *
                                this.sequence.getResolution());
                        currentTrack.add(ev);
                        currentTrack.add(end);


                    } catch (InvalidMidiDataException e) {
                        e.printStackTrace();
                    }

                }

            }

            //Add the tempo track
            Track tempoTrack = _tracks.get(-1);

            if (tempoTrack != null) sequence.deleteTrack(tempoTrack);

            tempoTrack = sequence.createTrack();

            //Set the sequence
            try {
                this.sequencer.setSequence(sequence);
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            }

            _tracks.put(-1, tempoTrack);
            //make the byte array
            byte[] data = new byte[6];
            beats = sequencer.getTickLength() / 96;
            for (int i = 2; i <= beats; i++) {
                try {
                    MetaMessage t = new MetaMessage(127, data, 0);
                    MidiEvent ev = new MidiEvent(t, i * this.sequence.getResolution());
                    tempoTrack.add(ev);
                } catch (InvalidMidiDataException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}