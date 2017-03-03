package cs3500.music.tests;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.NoteName;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import org.junit.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ngeyer on 3/23/2016.
 */
public class CompositionBuilderTest {





    @Test
    public void testLegalComp() {
        CompositionBuilder<IMusicModel> comp = new MusicModel.Builder();

        FileReader file;
        try {
            file = new FileReader("mary.txt");
        } catch (FileNotFoundException e) {
            file = null;
            e.printStackTrace();
        }


        MusicReader.parseFile(file, comp);
        assertEquals(maryConfig().getAllNotes(), comp.build().getAllNotes());



    }

    private IMusicModel maryConfig() {

        IMusicModel model = new MusicModel();

        //Add all of the notes from the page for HW

        //E3
        model.addNote(NoteName.E, 4, 8, 56, 1, 50);

        //G3
        model.addNote(NoteName.G, 4, 7, 0, 1, 50);
        model.addNote(NoteName.G, 4, 7, 8, 1, 50);
        model.addNote(NoteName.G, 4, 8, 16, 1, 50);
        model.addNote(NoteName.G, 4, 2, 24, 1, 50);
        model.addNote(NoteName.G, 4, 8, 32, 1, 50);
        model.addNote(NoteName.G, 4, 8, 40, 1, 50);
        model.addNote(NoteName.G, 4, 8, 48, 1, 50);

        //C4
        model.addNote(NoteName.C, 5, 2, 4, 1, 50);
        model.addNote(NoteName.C, 5, 2, 36, 1, 50);
        model.addNote(NoteName.C, 5, 8, 56, 1, 50);

        //D4
        model.addNote(NoteName.D, 5, 2, 2, 1, 50);
        model.addNote(NoteName.D, 5, 2, 6, 1, 50);
        model.addNote(NoteName.D, 5, 2, 16, 1, 50);
        model.addNote(NoteName.D, 5, 2, 18, 1, 50);
        model.addNote(NoteName.D, 5, 4, 20, 1, 50);
        model.addNote(NoteName.D, 5, 2, 34, 1, 50);
        model.addNote(NoteName.D, 5, 2, 38, 1, 50);
        model.addNote(NoteName.D, 5, 2, 48, 1, 50);
        model.addNote(NoteName.D, 5, 2, 50, 1, 50);
        model.addNote(NoteName.D, 5, 2, 54, 1, 50);

        //E4
        model.addNote(NoteName.E, 5, 2, 0, 1, 50);

        model.addNote(NoteName.E, 5, 2, 8, 1, 50);
        model.addNote(NoteName.E, 5, 2, 10, 1, 50);
        model.addNote(NoteName.E, 5, 3, 12, 1, 50);

        model.addNote(NoteName.E, 5, 2, 24, 1, 50);

        model.addNote(NoteName.E, 5, 2, 32, 1, 50);

        model.addNote(NoteName.E, 5, 2, 40, 1, 50);
        model.addNote(NoteName.E, 5, 2, 42, 1, 50);
        model.addNote(NoteName.E, 5, 2, 44, 1, 50);
        model.addNote(NoteName.E, 5, 2, 46, 1, 50);

        model.addNote(NoteName.E, 5, 2, 52, 1, 50);

        //G4
        model.addNote(NoteName.G, 5, 2, 26, 1, 50);

        model.addNote(NoteName.G, 5, 4, 28, 1, 50);
        return model;
    }

}
