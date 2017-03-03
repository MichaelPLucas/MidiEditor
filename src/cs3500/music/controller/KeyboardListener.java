package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Created by ngeyer on 4/6/2016.
 */
public class KeyboardListener implements KeyListener {
    private Map<Character, Runnable> keyTypedMap;
    private Map<Integer, Runnable> keyPressedMap, keyReleasedMap;

    /**
     * Empty constructor
     */
    public KeyboardListener() {

    }


    /**
     * Set the map for key typed events. Key typed events in Java Swing are characters
     */

    public void setKeyTypedMap(Map<Character, Runnable> map) {
        keyTypedMap = map;
    }

    /**
     * Set the map for key pressed events. Key pressed events in Java Swing are integer codes
     */

    public void setKeyPressedMap(Map<Integer, Runnable> map) {
        keyPressedMap = map;
    }

    /**
     * Set the map for key released events. Key released events in Java Swing are integer codes
     */

    public void setKeyReleasedMap(Map<Integer, Runnable> map) {
        keyReleasedMap = map;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        if (keyTypedMap.containsKey(e.getKeyChar()))
            keyTypedMap.get(e.getKeyChar()).run();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (keyPressedMap.containsKey(e.getKeyChar()))
            keyPressedMap.get(e.getKeyChar()).run();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (keyReleasedMap.containsKey(e.getKeyChar()))
            keyReleasedMap.get(e.getKeyChar()).run();
    }
}
