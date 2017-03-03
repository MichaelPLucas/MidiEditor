package cs3500.music.tests;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.view.*;
import org.junit.Test;

/**
 * Created by ngeyer on 3/23/2016.
 */
public class ViewFactoryTest {


    IMusicModel model;
    ViewFactory factory;

    @Test(expected= IllegalArgumentException.class)
    public void testException() {
        config();

        IView thingy = factory.build("JOHN CENA!");
    }

    @Test
    public void testMidi() {
        config();

        IView midi = factory.build("midi");

        assert(midi instanceof MidiViewImpl);
    }

    @Test
    public void testConsole() {
        config();

        IView console = factory.build("console");

        assert(console instanceof ConsoleView);
    }

    @Test
    public void testGui() {
        config();

        IView gui = factory.build("gui");

        assert(gui instanceof GuiView);
    }

    private void config() {
        model = new MusicModel();
        factory = new ViewFactory(model);
    }
}
