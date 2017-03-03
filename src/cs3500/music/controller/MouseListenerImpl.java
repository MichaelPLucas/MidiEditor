package cs3500.music.controller;

import cs3500.music.model.Note;
import cs3500.music.model.NoteName;
import cs3500.music.view.*;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

/**
 * Created by lucasmic on 4/5/2016.
 */
public class MouseListenerImpl extends AMouseListener {
    private ANoteAdditionWindow add;
    private ANoteEditWindow edit;
    private ANoteDeletionWindow delete;

    public MouseListenerImpl(ANoteAdditionWindow a, ANoteEditWindow e, ANoteDeletionWindow d) {
        add = a;
        edit = e;
        delete = d;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() < GuiView.SCOREWIDTH || e.getY() < GuiView.BEATHEIGHT) {
            return;
        }

        int x = (e.getX() - GuiView.SCOREWIDTH)/ GuiView.NOTEWIDTH;
        int y = ((e.getY() - GuiView.BEATHEIGHT) / GuiView.NOTEHEIGHT);

        Note high = new Note(NoteName.C, 1, 1, 0, 0);

         for (Map.Entry<Integer, List<Note>> en : m.entrySet()) {
            for (Note n : en.getValue()) {
                if (n.compareTo(high) > 0) {
                    high = n;
                }
            }
        }

        int p = (high.toInt() - y) % 12;
        while (p < 0) {
            p = p + 12;
        }

        int o = (high.toInt() - y) / 12;

        switch (e.getButton()) {
            case 1:
                // open note addition window
                add.init(x,
                        NoteName.valueOf(p), //high note - dist down
                        o);

                add.display();
                break;
            case 2:
                // open note edit window
                int time = 0;
                Note target = null;

                for (int i = 0; i < x; i++) {
                    if (m.containsKey(i)) {
                        for (Note n : m.get(i)) {
                            if (i + n.getDuration() > x &&
                                    n.compareTo(new Note(NoteName.valueOf(p), o, 0, 0, 0)) == 0) {
                                target = n;
                                time = i;
                            }
                        }
                    }
                }

                if (target == null) {
                    break;
                }

                edit.init(time, target);

                edit.display();
                break;
            case 3:
                // open note deletion confirmation window
                time = 0;
                target = null;

                for (int i = 0; i < x; i++) {
                    if (m.containsKey(i)) {
                        for (Note n : m.get(i)) {
                            if (i + n.getDuration() > x &&
                                    n.compareTo(new Note(NoteName.valueOf(p), o, 0, 0, 0)) == 0) {
                                target = n;
                                time = i;
                            }
                        }
                    }
                }

                if (target == null) {
                    break;
                }

                delete.init(time, target);

                delete.display();
                break;
        }
    }

    public ANoteAdditionWindow getAdd() {
        return this.add;
    }

    public ANoteEditWindow getEdit() {
        return this.edit;
    }

    public ANoteDeletionWindow getDelete() {
        return this.delete;
    }
}