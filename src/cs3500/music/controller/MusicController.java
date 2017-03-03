package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.NoteName;
import cs3500.music.view.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Created by ngeyer on 3/31/2016.
 * Represents a controller and what it can and can not do
 * Must be able to handle key events
 */
public class MusicController implements IController {

    private CompoundView view;
    private IMusicModel _model;
    private boolean isPlaying;
    private MouseListenerImpl mouseListener;
    private KeyboardListener keyboardListener;
    private Map<String, Callback> callbacks;
    private int beat;

    //For testing
    private boolean isTesting;

    public MusicController(IMusicModel model) {
        _model = model;
        view = new CompoundView(model.getAllNotes(), model.getTempo());
        configKeyMaps();
        callbacks = new TreeMap<>();
        configCallbacks();
        isTesting = false;
    }

    public MusicController(IMusicModel model, KeyboardListener kl, StringBuilder sb) {
        _model = model;
        keyboardListener = kl;
        view = new CompoundView(model.getAllNotes(), model.getTempo());
        configKeyMapsTesting(kl, sb);
        callbacks = new TreeMap<>();
        configCallbacks();
        isTesting = true;
    }

    /**
     * Configures the key maps for the player
     */
    private void configKeyMaps() {
        Map<Character, Runnable> keyTypes = new HashMap<>();
        Map<Integer, Runnable> keyPresses = new HashMap<>();
        Map<Integer, Runnable> keyReleases = new HashMap<>();

        //Add play/stop/restart
        keyPresses.put(KeyEvent.VK_ENTER, new Play());
        keyPresses.put(KeyEvent.VK_R, new Restart());
        keyPresses.put(KeyEvent.VK_BRACELEFT, new Home());
        keyPresses.put(KeyEvent.VK_BRACERIGHT, new End());
        keyPresses.put(KeyEvent.VK_PERIOD, new Right());
        keyPresses.put(KeyEvent.VK_COMMA, new Left());

        //Add key types
        keyTypes.put(' ', new Play());
        keyTypes.put('r', new Restart());
        keyTypes.put(',', new Left());
        keyTypes.put('.', new Right());
        keyTypes.put('[', new Home());
        keyTypes.put(']', new End());




        //Set the listeners
        KeyboardListener kbd = new KeyboardListener();
        kbd.setKeyTypedMap(keyTypes);
        kbd.setKeyPressedMap(keyPresses);
        kbd.setKeyReleasedMap(keyReleases);
        view.addKeyListener(kbd);

    }

    private void configKeyMapsTesting(KeyboardListener kl, StringBuilder sb) {
        Map<Character, Runnable> keyTypes = new HashMap<>();
        Map<Integer, Runnable> keyPresses = new HashMap<>();
        Map<Integer, Runnable> keyReleases = new HashMap<>();

        //Add play/stop/restart
        keyPresses.put(KeyEvent.VK_ENTER, new Play(sb));
        keyPresses.put(KeyEvent.VK_R, new Restart(sb));
        keyPresses.put(KeyEvent.VK_BRACELEFT, new Home(sb));
        keyPresses.put(KeyEvent.VK_BRACERIGHT, new End(sb));
        keyPresses.put(KeyEvent.VK_PERIOD, new Right(sb));
        keyPresses.put(KeyEvent.VK_COMMA, new Left(sb));

        //Add key types
        keyTypes.put(' ', new Play(sb));
        keyTypes.put('r', new Restart(sb));
        keyTypes.put(',', new Left(sb));
        keyTypes.put('.', new Right(sb));
        keyTypes.put('[', new Home(sb));
        keyTypes.put(']', new End(sb));




        //Set the listeners
        kl.setKeyTypedMap(keyTypes);
        kl.setKeyPressedMap(keyPresses);
        kl.setKeyReleasedMap(keyReleases);


    }

    private void configCallbacks() {
        this.callbacks.put("add", new Add());
        this.callbacks.put("edit", new Edit());
        this.callbacks.put("delete", new Delete());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add.confirm":
                Map<String, String> fields = this.mouseListener.getAdd().
                        getDisplayPanel().getFields();

                NoteName pit;
                try {
                    pit = NoteName.valueOf(Integer.valueOf(fields.get("p")));
                } catch(IllegalArgumentException er) {
                    return;
                }
                int oct;
                try {
                    oct = Integer.parseInt(fields.get("o"));
                } catch(NumberFormatException er) {
                    return;
                }
                int len;
                try {
                    len = Integer.parseInt(fields.get("l"));
                } catch(NumberFormatException er) {
                    return;
                }
                int vol;
                try {
                    vol = Integer.parseInt(fields.get("v"));
                } catch(NumberFormatException er) {
                    return;
                }
                int ins;
                try {
                    ins = Integer.parseInt(fields.get("i"));
                } catch(NumberFormatException er) {
                    return;
                }

                Callback c = this.callbacks.get("add");
                c.setTime(this.mouseListener.getAdd().getStartingBeat());
                c.setAfter(new Note(pit, oct, len, ins, vol));

                c.run();

                this.mouseListener.getAdd().dispatchEvent(
                        new WindowEvent(this.mouseListener.getAdd(), WindowEvent.WINDOW_CLOSING));

                break;
            case "edit.confirm":
                System.out.println(mouseListener.getEdit().getDisplayPanel());
                fields = this.mouseListener.getEdit().getDisplayPanel().getFields();

                int tim = 0;
                try {
                    tim = Integer.parseInt(fields.get("t"));
                } catch(NumberFormatException er) {
                    return;
                }
                pit = null;
                try {
                    pit = NoteName.valueOf(Integer.parseInt(fields.get("p")));
                } catch(NumberFormatException er) {
                    return;
                }
                oct = 0;
                try {
                    oct = Integer.parseInt(fields.get("o"));
                } catch(NumberFormatException er) {
                    return;
                }
                len = 0;
                try {
                    len = Integer.parseInt(fields.get("l"));
                } catch(NumberFormatException er) {
                    return;
                }
                vol = 0;
                try {
                    vol = Integer.parseInt(fields.get("v"));
                } catch(NumberFormatException er) {
                    return;
                }
                ins = 0;
                try {
                    ins = Integer.parseInt(fields.get("i"));
                } catch(NumberFormatException er) {
                    return;
                }

                c = this.callbacks.get("edit");
                c.setTime(tim);
                c.setBefore(this.mouseListener.getEdit().getStartingNote());
                c.setAfter(new Note(pit, oct, len, ins, vol));

                c.run();

                this.mouseListener.getEdit().dispatchEvent(
                        new WindowEvent(this.mouseListener.getEdit(), WindowEvent.WINDOW_CLOSING));
                break;
            case "delete.confirm":
                c = this.callbacks.get("delete");
                c.setTime(this.mouseListener.getDelete().getBeat());
                c.setBefore(this.mouseListener.getDelete().getNote());

                c.run();

                this.mouseListener.getDelete().dispatchEvent(
                        new WindowEvent(this.mouseListener.getDelete(),
                                WindowEvent.WINDOW_CLOSING));
                break;
            case "cancel":
                this.mouseListener.getAdd().dispatchEvent(
                        new WindowEvent(this.mouseListener.getAdd(), WindowEvent.WINDOW_CLOSING));
                this.mouseListener.getEdit().dispatchEvent(
                        new WindowEvent(this.mouseListener.getEdit(), WindowEvent.WINDOW_CLOSING));
                this.mouseListener.getDelete().dispatchEvent(
                        new WindowEvent(this.mouseListener.getDelete(),
                                WindowEvent.WINDOW_CLOSING));
                break;
        }
    }


    public void setMouseListener(MouseListenerImpl ml) {
        this.mouseListener = ml;
        this.view.registerMouseListener(ml);
        this.mouseListener.setM(this._model.getAllNotes());
    }

    public void addCallback(String s, Callback c) {
        this.callbacks.put(s, c);
    }


    private int getLastBeat() {
        int last = 0;
        for (Map.Entry<Integer, List<Note>> s : this._model.getAllNotes().entrySet()) {
            for (Note n : s.getValue()) {
                if (s.getKey() + n.getDuration() > last) {
                    last = s.getKey() + n.getDuration();
                }
            }
        }
        return last;
    }



    /*

        Nested Runnable classes

     */

    class Play implements Runnable {

        StringBuilder sb;

        public Play() {

        }

        public Play(StringBuilder sb) {
            this.sb = sb;
        }

        @Override
        public void run() {
            if (isPlaying) {
                if (isTesting) {
                    sb.append("Playing! Beat: ").append(beat).append("\n");
                }
                view.stop();

                beat = (int) view.getBeat() - 1;
            } else {
                if (isTesting) {
                    sb.append("Stopping! Beat: ").append(beat).append("\n");
                }
                view.play(beat);
            }
            isPlaying = !isPlaying;

        }


    }

    class Restart implements Runnable {

        StringBuilder sb;

        public Restart() {

        }

        public Restart(StringBuilder sb) {
            this.sb = sb;
        }

        @Override
        public void run() {
            if (isTesting) {
                sb.append("Restarting!");
            }
            else {
                //Starts from beginning
                view.play(0);
                view.stop();
                view.update();
                isPlaying = false;
            }
        }
    }

    class Right implements Runnable {

        StringBuilder sb;

        public Right() {

        }
        public Right(StringBuilder sb) {
            this.sb = sb;
        }
        @Override
        public void run() {
            if (isTesting) {
                sb.append("Moving right");
            }
            else {
                if (beat < getLastBeat()) beat++;
                view.setTime(beat);
                view.update();
            }
        }
    }

    class Left implements Runnable {

        StringBuilder sb;

        public Left() {

        }
        public Left(StringBuilder sb) {
            this.sb = sb;
        }
        @Override
        public void run() {
            if (isTesting) {
                sb.append("Moving left");
            }
            else {
                if (beat != 0) beat--;
                view.setTime(beat);
                view.update();
            }
        }
    }

    class Home implements Runnable {

        StringBuilder sb;

        public Home() {

        }
        public Home(StringBuilder sb) {
            this.sb = sb;
        }
        @Override
        public void run() {
            if (isTesting) {
                sb.append("Going home");
            }
            else {
                beat = 0;
                view.setTime(0);
                view.play(0);
                view.stop();
                view.update();
                isPlaying = false;
            }
        }
    }

    class End implements Runnable {

        StringBuilder sb;

        public End() {

        }
        public End(StringBuilder sb) {
            this.sb = sb;
        }
        @Override
        public void run() {
            if (isTesting) {
                sb.append("Going to the end");
            }
            else {
                beat = getLastBeat() + 1;
                view.setTime(beat);
                view.play(beat);
                view.stop();
                view.update();
                isPlaying = false;
            }
        }
    }

    class Add extends Callback {

        @Override
        public void run() {
            _model.addNote(this.after.getName(), this.after.getOctave(),
                    this.after.getDuration(), this.time,
                    this.after.getInstrument(), this.after.getVolume());
            view.processNotes(_model.getAllNotes());
            view.update();
            mouseListener.setM(_model.getAllNotes());
        }
    }

    class Edit extends Callback {

        @Override
        public void run() {
            _model.removeNote(this.before.getName(), this.before.getOctave(),
                    this.before.getDuration(), mouseListener.getEdit().getStartingBeat(),
                    this.before.getInstrument(), this.before.getVolume());
            _model.addNote(this.after.getName(), this.after.getOctave(),
                    this.after.getDuration(), this.time,
                    this.after.getInstrument(), this.after.getVolume());
            view.processNotes(_model.getAllNotes());
            view.update();
            mouseListener.setM(_model.getAllNotes());
        }
    }

    class Delete extends Callback {

        @Override
        public void run() {
            _model.removeNote(this.before.getName(), this.before.getOctave(),
                    this.before.getDuration(), this.time,
                    this.before.getInstrument(), this.before.getVolume());
            view.processNotes(_model.getAllNotes());
            view.update();
            mouseListener.setM(_model.getAllNotes());
        }
    }
}
