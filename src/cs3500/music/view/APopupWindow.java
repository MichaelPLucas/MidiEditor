package cs3500.music.view;

import cs3500.music.controller.Callback;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lucasmic on 4/7/2016.
 */
public abstract class APopupWindow extends javax.swing.JFrame {
    protected Callback confirmCallback;
    protected ActionListener actionListener;
    protected PopupPanel displayPanel;

    public void setCallback(Callback c) {
        confirmCallback = c;
    }

    public abstract class PopupPanel extends javax.swing.JPanel {
        public abstract Map<String, String> getFields();
    }

    public void setListner(ActionListener al) {
        actionListener = al;
    }

    public PopupPanel getDisplayPanel() {
        return this.displayPanel;
    }
}
