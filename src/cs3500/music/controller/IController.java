package cs3500.music.controller;

import java.awt.event.ActionListener;

/**
 * Created by ngeyer on 3/31/2016.
 */
public interface IController extends ActionListener {


    void setMouseListener(MouseListenerImpl ml);


}
