package cs3500.music.view;

import cs3500.music.model.IMusicModel;

/**
 * Created by lucasmic on 3/23/2016.
 */
public class ViewFactory {
    private IMusicModel model;
    private int tempo;
    private Appendable out;

    public ViewFactory(IMusicModel m) {
        model = m;
        out = System.out;
    }

    public ViewFactory(IMusicModel m, Appendable o) {
        model = m;
        out = o;
    }

    /**
     * Builds a view of the given type
     * @param type String representing the view to be built
     * @return A view of the corresponding type
     */
    public IView build(String type) throws IllegalArgumentException {
        switch(type) {
            case "gui":
                return new GuiViewImpl(model.getAllNotes());
            case "midi":
                return new MidiViewImpl(model.getAllNotes());
            case "console":
                return new ConsoleView(model.getAllNotes(), out);
            case "default":
                return new CompoundView(model.getAllNotes(), model.getTempo());
            default:
                throw new IllegalArgumentException(type + " is not a valid view type.");
        }
    }
}
