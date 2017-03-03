package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.NoteName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lucasmic on 3/23/2016.
 */
public class ConsoleView extends MusicView {
    private Appendable out;


    public ConsoleView(Map<Integer, List<Note>> notes) {
        super(notes);
    }

    public ConsoleView(Map<Integer, List<Note>> notes, Appendable o) {
        super(notes);
        out = o;
    }


    @Override
    public void play() {
        try {
            out.append(getMusicSheet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        //serves no purpose in this class
    }

    public String getMusicSheet() {
        StringBuilder sb = new StringBuilder();

        //Every column is 5 spaces
        //First get the top row of notes
        sb.append(createNoteRange());
        return sb.toString();
    }

    private String createNoteRange() {
        String result = "";
        List<String> noteNames = new ArrayList<>();

        Note lowNote = getLowNote();
        Note highNote = getHighNote();

        for (int i = lowNote.getOctave(); i <= highNote.getOctave(); i++) {

            for (NoteName name : NoteName.values()) {
                Note n = new Note(name, i, 0, 0, 0);
                if (n.compareTo(lowNote) >= 0 && n.compareTo(highNote) <= 0) {
                    result += n.getNameAsString();
                    noteNames.add(n.getNameAsString());
                }

            }
        }

        //Add the new line
        result += "\n";

        int lastBeat = getLastBeat();

        //Add space to beginning line
        int space = 0;
        if (lastBeat < 10) {
            result = " " + result;
        }
        else if (lastBeat < 100) {
            result = "  " + result;
            space = 2;
        }
        else if (lastBeat < 1000) {
            result = "   " + result;
            space = 3;
        }
        else if (lastBeat < 10000) {
            result = "    " + result;
            space = 4;
        }

        result += findAndReplace(noteNames, space);

        return result;
    }

    private String findAndReplace(List<String> noteNames, int space) {
        String result = "";
        List<String> lines = new ArrayList<>();

        //Create the rest of the sheet line by line


        for (int j = 0; j < getLastBeat(); j++) {
            String line = "";
            for (int i = 0; i < noteNames.size(); i++) {
                //Add the spaces
                line += "     ";
            }
            lines.add(line);
        }


        //go and add the correct modifiers for each note depending on the position and replacing
        //First number is 3
        //Every consecutive is plus 5
        for (Map.Entry<Integer, List<Note>> s : this.notes.entrySet()) {
            int beat = s.getKey();
            List<Note> noteList = s.getValue();

            //Find the line to modify
            for (Note n : noteList) {
                int index = noteNames.indexOf(n.getNameAsString());
                String newLine = lines.get(beat).substring(0, index * 5 + 2)
                        + "X" + lines.get(beat).substring(index * 5 + 3);
                lines.set(beat, newLine);

                //Add the dashes for each consecutive beat
                //Make sure not duplicating lines for when you have to add dashes
                for (int i = 1; i < n.getDuration(); i++) {
                    String nextLine = lines.get(beat + i).substring(0, index * 5 + 2)
                            + "|" + lines.get(beat + i).substring(index * 5 + 3);
                    lines.set(beat + i, nextLine);
                }
            }



        }

        //Combine all the lines and then add
        int beat = 0;
        for (String str : lines) {

            switch (space) {
                case 0:
                    result += beat + str + "\n";
                    break;
                case 2:
                    if (beat < 10) {
                        result += " " + beat + str + "\n";
                    }
                    else {
                        result += beat + str + "\n";
                    }
                    break;
                case 3:
                    if (beat < 10) {
                        result += "  " + beat + str + "\n";
                    }
                    else if (beat < 100) {
                        result += " " + beat + str + "\n";
                    }
                    else {
                        result += beat + str + "\n";
                    }
                    break;
                case 4:
                    if (beat < 10) {
                        result += "   " + beat + str + "\n";
                    }
                    else if (beat < 100) {
                        result += "  " + beat + str + "\n";
                    }
                    else if (beat < 1000) {
                        result += " " + beat + str + "\n";
                    }
                    else {
                        result += beat + str + "\n";
                    }
                    break;
            }
            beat++;
        }

        return result;
    }


    private int getLastBeat() {
        int last = 0;
        for (Map.Entry<Integer, List<Note>> s : this.notes.entrySet()) {
            for (Note n : s.getValue()) {
                if (s.getKey() + n.getDuration() > last) {
                    last = s.getKey() + n.getDuration();
                }
            }
        }
        return last;
    }

    private Note getLowNote() {

        //Set arbitrary high note
        Note low = new Note(NoteName.B, 1000000, 4, 0, 0);

        for (Map.Entry<Integer, List<Note>> s : this.notes.entrySet()) {
            for (Note n : s.getValue()) {
                if (n.compareTo(low) < 0) low = n;
            }
        }

        return low;
    }

    private Note getHighNote() {

        //Set arbitrary low note
        Note high = new Note(NoteName.C, 1, 1, 0, 0);

        for (Map.Entry<Integer, List<Note>> s : this.notes.entrySet()) {
            for (Note n : s.getValue()) {
                if (n.compareTo(high) > 0) high = n;
            }
        }

        return high;
    }
}
