package cs3500.music.tests;

import cs3500.music.controller.IController;
import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.MusicController;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.*;

/**
 * Created by ngeyer on 4/7/2016.
 */
public class ControllerTest {

    @Test
    public void testPlay() {

        CompositionBuilder<IMusicModel> comp = new MusicModel.Builder();
        FileReader file;
        try {
            file = new FileReader("mary.txt");
        } catch (FileNotFoundException e) {
            file = null;
            e.printStackTrace();
        }


        MusicReader.parseFile(file, comp);
        StringBuilder result = new StringBuilder();
        KeyboardListener kl = new KeyboardListener();

        IController ctrl = new MusicController(comp.build(), kl, result);

        KeyEvent ke = new KeyEvent(new Button("Play"), 1, 10, 1, KeyEvent.VK_SPACE, ' ');

        kl.keyTyped(ke);

        assertEquals("Playing! Beat: 0\n", result.toString());

    }


}
