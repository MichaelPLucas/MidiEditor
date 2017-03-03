package cs3500.music.controller;

import cs3500.music.model.Note;
import cs3500.music.view.GuiView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

/**
 * Created by lucasmic on 4/5/2016.
 */
public abstract class AMouseListener implements MouseListener {
    protected Map<Integer, List<Note>> m;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setM(Map<Integer, List<Note>> map) {
        this.m = map;
    }
}
