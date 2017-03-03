package cs3500.music.view;
import cs3500.music.model.Note;
import cs3500.music.model.NoteName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lucasmic on 4/6/2016.
 */
public class NoteAdditionWindowImpl extends ANoteAdditionWindow {
    private class NoteAdditionPanel extends PopupPanel {
        private JTextField pit;
        private JTextField oct;
        private JTextField len;
        private JTextField vol;
        private JTextField ins;

        public NoteAdditionPanel(NoteAdditionWindowImpl w) {
            pit = new JTextField("" + pitch.toInt(), 3);
            oct = new JTextField("" + octave, 3);
            len = new JTextField(3);
            vol = new JTextField(3);
            ins = new JTextField(2);

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
            b1.setActionCommand("add.confirm");
            b1.addActionListener(actionListener);

            JButton b2 = new JButton("Cancel");
            b2.setActionCommand("cancel");
            b2.addActionListener(actionListener);

            JPanel buttonPane = new JPanel();
            buttonPane.add(b1);
            buttonPane.add(b2);

            JPanel topPane = new JPanel();
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

            result.put("p", pit.getText());
            result.put("o", oct.getText());
            result.put("l", len.getText());
            result.put("v", vol.getText());
            result.put("i", ins.getText());

            return result;
        }
    }

    @Override
    public void init(int s, NoteName p, int o) {
        super.init(s, p, o);

        if (displayPanel != null) {
            this.remove(displayPanel);
        }

        displayPanel = new NoteAdditionPanel(this);

        this.getContentPane().add(displayPanel);
        this.pack();
    }

    @Override
    public void display() {
        this.setVisible(true);
    }
}
