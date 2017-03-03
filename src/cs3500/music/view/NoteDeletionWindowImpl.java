package cs3500.music.view;

import cs3500.music.model.Note;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Map;

/**
 * Created by lucasmic on 4/6/2016.
 */
public class NoteDeletionWindowImpl extends ANoteDeletionWindow {
    private class NoteDeletionPanel extends PopupPanel {
        public NoteDeletionPanel() {
            JButton b1 = new JButton("Confirm");
            b1.setActionCommand("delete.confirm");
            b1.addActionListener(actionListener);

            JButton b2 = new JButton("Cancel");
            b2.setActionCommand("cancel");
            b2.addActionListener(actionListener);

            add(b1);
            add(b2);
        }

        @Override
        public Map<String, String> getFields() {
            return null;
        }
    }

    @Override
    public void init(int b, Note no) {
        super.init(b, no);

        NoteDeletionPanel displayPanel = new NoteDeletionPanel();

        this.getContentPane().add(displayPanel);
        this.pack();
    }

    @Override
    public void display() {
        this.setVisible(true);
    }
}
