package cs3500.music.view;

import cs3500.music.controller.AMouseListener;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import java.util.List;
import java.util.Map;

/**
 * Created by lucasmic on 3/31/2016.
 */
public abstract class GuiView extends MusicView {
    public static final int NOTEWIDTH = 30;
    public static final int NOTEHEIGHT = 20;
    public static final int SCOREWIDTH = 30;
    public static final int BEATHEIGHT = 30;

    public GuiView(Map<Integer, List<Note>> map) {
        super(map);
    }

    public abstract void setTime(int i);

    public abstract void addMouseListener(AMouseListener m);

    public abstract void refresh();
}