package cs3500.music.tests;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.NoteName;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.ViewFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ngeyer on 3/23/2016.
 */
public class ConsoleTest {

    @Test
    public void testConsole() {
        StringBuilder sb = new StringBuilder();

        ViewFactory vf = new ViewFactory(maryConfig(), sb);

        vf.build("console").play();

        assertEquals(sb.toString(), "    E4   F4  F#4   G4  G#4   A4  A#4   B4   C5" +
                "  C#5   D5  D#5   E5   F5  F#5   G5 \n" +
                " 0                 X                                            X       " +
                "          \n" +
                " 1                 |                                            |        " +
                "         \n" +
                " 2                 |                                  X                  " +
                "         \n" +
                " 3                 |                                  |                  " +
                "         \n" +
                " 4                 |                        X                            " +
                "         \n" +
                " 5                 |                        |                            " +
                "         \n" +
                " 6                 |                                  X                  " +
                "         \n" +
                " 7                                                    |                   " +
                "        \n" +
                " 8                 X                                            X         " +
                "        \n" +
                " 9                 |                                            |         " +
                "        \n" +
                "10                 |                                            X        " +
                "         \n" +
                "11                 |                                            |        " +
                "         \n" +
                "12                 |                                            X        " +
                "         \n" +
                "13                 |                                            |        " +
                "         \n" +
                "14                 |                                            |        " +
                "         \n" +
                "15                                                                       " +
                "         \n" +
                "16                 X                                  X                  " +
                "         \n" +
                "17                 |                                  |                  " +
                "         \n" +
                "18                 |                                  X                  " +
                "         \n" +
                "19                 |                                  |                  " +
                "         \n" +
                "20                 |                                  X                  " +
                "         \n" +
                "21                 |                                  |                  " +
                "         \n" +
                "22                 |                                  |                  " +
                "         \n" +
                "23                 |                                  |                  " +
                "         \n" +
                "24                 X                                            X        " +
                "         \n" +
                "25                 |                                            |        " +
                "         \n" +
                "26                                                                       " +
                "      X  \n" +
                "27                                                                       " +
                "      |  \n" +
                "28                                                                        " +
                "     X  \n" +
                "29                                                                        " +
                "     |  \n" +
                "30                                                                        " +
                "     |  \n" +
                "31                                                                        " +
                "     |  \n" +
                "32                 X                                            X         " +
                "        \n" +
                "33                 |                                            |         " +
                "        \n" +
                "34                 |                                  X                   " +
                "        \n" +
                "35                 |                                  |                   " +
                "        \n" +
                "36                 |                        X                             " +
                "        \n" +
                "37                 |                        |                             " +
                "        \n" +
                "38                 |                                  X                   " +
                "        \n" +
                "39                 |                                  |                   " +
                "        \n" +
                "40                 X                                            X          " +
                "       \n" +
                "41                 |                                            |         " +
                "        \n" +
                "42                 |                                            X         " +
                "        \n" +
                "43                 |                                            |         " +
                "        \n" +
                "44                 |                                            X         " +
                "        \n" +
                "45                 |                                            |         " +
                "        \n" +
                "46                 |                                            X         " +
                "        \n" +
                "47                 |                                            |         " +
                "        \n" +
                "48                 X                                  X                    " +
                "       \n" +
                "49                 |                                  |                   " +
                "        \n" +
                "50                 |                                  X                   " +
                "        \n" +
                "51                 |                                  |                   " +
                "        \n" +
                "52                 |                                            X         " +
                "        \n" +
                "53                 |                                            |         " +
                "        \n" +
                "54                 |                                  X                  " +
                "         \n" +
                "55                 |                                  |                  " +
                "         \n" +
                "56  X                                       X                            " +
                "         \n" +
                "57  |                                       |                            " +
                "         \n" +
                "58  |                                       |                            " +
                "         \n" +
                "59  |                                       |                            " +
                "         \n" +
                "60  |                                       |                            " +
                "         \n" +
                "61  |                                       |                            " +
                "         \n" +
                "62  |                                       |                            " +
                "         \n" +
                "63  |                                       |                            " +
                "         \n");
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
