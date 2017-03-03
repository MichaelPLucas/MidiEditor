package cs3500.music.view;

import cs3500.music.model.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lucasmic on 4/6/2016.
 */
public class NoteEditWindowImpl extends ANoteEditWindow {
    private class NoteEditPanel extends PopupPanel {
        private JTextField tim;
        private JTextField pit;
        private JTextField oct;
        private JTextField len;
        private JTextField vol;
        private JTextField ins;

        public NoteEditPanel(NoteEditWindowImpl w) {
            tim = new JTextField("" + w.startingBeat, 4);
            pit = new JTextField("" + w.startingNote.getName().toInt(), 2);
            oct = new JTextField("" + w.startingNote.getOctave(), 2);
            len = new JTextField("" + w.startingNote.getDuration(), 3);
            vol = new JTextField("" + w.startingNote.getVolume(), 3);
            ins = new JTextField("" + w.startingNote.getInstrument(), 2);

            JPanel timPane = new JPanel();
            timPane.add(new JLabel("Beat: "));
            timPane.add(tim);

            JPanel pitPane = new JPanel();
            pitPane.add(new JLabel("Pitch: "));
            pitPane.add(pit);

            JPanel octPane = new JPanel();
            octPane.add(new JLabel("Octave: "));
            octPane.add(oct);

            JPanel lenPane = new JPanel();
            lenPane.add(new JLabel("Length: "));
            lenPane.add(len);

            JPanel volPane = new JPanel();
            lenPane.add(new JLabel("Volume: "));
            lenPane.add(vol);

            JPanel insPane = new JPanel();
            lenPane.add(new JLabel("Instrument: "));
            lenPane.add(ins);

            JButton b1 = new JButton("Confirm");
            b1.setActionCommand("edit.confirm");
            b1.addActionListener(actionListener);

            JButton b2 = new JButton("Cancel");
            b2.setActionCommand("cancel");
            b2.addActionListener(actionListener);

            JPanel buttonPane = new JPanel();
            buttonPane.add(b1);
            buttonPane.add(b2);

            JPanel topPane = new JPanel();
            topPane.add(timPane);
            topPane.add(pitPane);
            topPane.add(octPane);
            topPane.add(lenPane);
            topPane.add(volPane);
            topPane.add(insPane);

            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(topPane);
            this.add(buttonPane);
        }

        @Override
        public Map<String, String> getFields() {
            Map<String, String> result = new TreeMap<>();

            result.put("t", tim.getText());
            result.put("p", pit.getText());
            result.put("o", oct.getText());
            result.put("l", len.getText());
            result.put("v", vol.getText());
            result.put("i", ins.getText());

            return result;
        }
    }

    @Override
    public void init(int s, Note n) {
        super.init(s, n);

        if (displayPanel != null) {
            this.remove(displayPanel);
        }

        displayPanel = new NoteEditPanel(this);

        this.getContentPane().add(displayPanel);
        this.pack();
    }

    @Override
    public void display() {
        this.setVisible(true);
    }
}
