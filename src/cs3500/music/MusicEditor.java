package cs3500.music;

import cs3500.music.controller.IController;
import cs3500.music.controller.MouseListenerImpl;
import cs3500.music.controller.MusicController;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.NoteName;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.*;

import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
    public static void main(String[] args) throws IOException, InvalidMidiDataException {

        if (args.length != 1) {
            System.out.println("Please use the correct parameters");
        }
        else {
            CompositionBuilder<IMusicModel> comp = new MusicModel.Builder();
            String arg1 = args[0];

            //Try to get the file
            FileReader file = new FileReader(arg1);

            MusicReader.parseFile(file, comp);

            IMusicModel model = comp.build();
            IController ctrl = new MusicController(model);

            ANoteAdditionWindow a = new NoteAdditionWindowImpl();
            ANoteEditWindow e = new NoteEditWindowImpl();
            ANoteDeletionWindow d = new NoteDeletionWindowImpl();

            a.setListner(ctrl);
            e.setListner(ctrl);
            d.setListner(ctrl);

            ctrl.setMouseListener(new MouseListenerImpl(a, e, d));
        }
    }


}
