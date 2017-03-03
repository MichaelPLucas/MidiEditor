package cs3500.music.model;

import cs3500.music.util.CompositionBuilder;

import java.util.*;

/**
 * Created by NGeye on 3/1/2016.
 * Model created to represent Music Notes and durations
 */
public class MusicModel implements IMusicModel {

    private Map<Integer, List<Note>> notes;
    private int tempo;

    public MusicModel() {
        this.notes = new TreeMap<>();
        this.tempo = 0;
    }

    public MusicModel(Map<Integer, List<Note>> score, int _tempo) {
        notes = score;
        tempo = _tempo;
    }

    public static final class Builder implements CompositionBuilder<IMusicModel> {
        private Map<Integer, List<Note>> score;
        private int tempo;

        public Builder() {
            score = new TreeMap<>();
            tempo = 0;
        }

        @Override
        public IMusicModel build() {
            return new MusicModel(score, tempo);
        }

        @Override
        public CompositionBuilder<IMusicModel> setTempo(int tempo) {
            this.tempo = tempo;
            return this;
        }

        @Override
        public CompositionBuilder<IMusicModel> addNote(int start, int end, int instrument,
                                                       int pitch, int volume) {
            if (!score.containsKey(start)) {
                score.put(start, new ArrayList<Note>());
            }

            this.score.get(start).add(new Note(NoteName.valueOf(pitch % 12),
                    (int) Math.floor(pitch / 12), end - start, instrument, volume));

            return this;
        }
    }


    @Override
    public int getTempo() {
        return this.tempo;
    }

    @Override
    public void addNote(NoteName note,int octave, int duration, int start, int instrument,
                        int volume) throws IllegalArgumentException{
        if (duration < 0) {
            throw new IllegalArgumentException("Invalid duration: " + duration);
        }
        else if (start < 0) {
            throw new IllegalArgumentException("Invalid start: " + start);
        }
        else if (octave < 0) {
            throw new IllegalArgumentException("Invalid octave: " + octave);
        }

        //Instantiate list for note
        List<Note> noteList = notes.get(start);
        if (noteList == null) {
            noteList = new ArrayList<>();
        }
        Note newNote = new Note(note, octave, duration, instrument, volume);
        noteList.add(newNote);

        //Put the notes into the map
        notes.put(start, noteList);
    }

    @Override
    public void removeNote(NoteName note, int octave, int duration, int start, int instrument,
                           int volume) throws IllegalArgumentException {
        if (duration < 0) {
            throw new IllegalArgumentException("Invalid duration: " + duration);
        }
        else if (octave < 0) {
            throw new IllegalArgumentException("Invalid octave: " + octave);
        }
        else if (start < 0) {
            throw new IllegalArgumentException("Invalid start: " + start);
        }
        //Do this for safety
        boolean found = false;
        List<Note> noteList = notes.get(start);
        Note tmp = new Note(note, octave, duration, instrument, volume);
        for (Note n : noteList) {
            if (n.equals(tmp)) {
                found = true;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Invalid note: " + tmp.getNameAsString());
        }
        noteList.remove(tmp);

    }

    @Override
    public void combine(IMusicModel model) {
        Map<Integer, List<Note>> noteCopy = model.getAllNotes();

        //Iterate through entry set
        for (Map.Entry<Integer, List<Note>> s : noteCopy.entrySet()) {
            //Find the list at this key
            List<Note> noteList = this.notes.get(s.getKey());
            if (noteList == null) {
                //No list? Just set it to the new value
                this.notes.put(s.getKey(), s.getValue());
            }
            else {
                //If there is a list add every value from s
                for (Note n : s.getValue()) {
                    noteList.add(n);
                }
                //Put it back
                this.notes.put(s.getKey(), noteList);
            }
        }
    }

    @Override
    public void append(IMusicModel model) {
        int last = this.getLastBeat();

        Map<Integer, List<Note>> noteCopy = model.getAllNotes();
        for (Map.Entry<Integer, List<Note>> s : noteCopy.entrySet()) {
            notes.put(s.getKey() + last, s.getValue());
        }
    }

    @Override
    public Map<Integer, List<Note>> getAllNotes() {
        Map<Integer, List<Note>> result = new TreeMap<>();


        //Make a copy of all the notes
        for (Map.Entry<Integer, List<Note>> s : this.notes.entrySet()) {
            List<Note> copy = new ArrayList<>();
            for (Note n : s.getValue()) {
                Note newNote = new Note(n.getName(), n.getOctave(), n.getDuration(),
                        n.getInstrument(), n.getVolume());
                copy.add(newNote);
            }
            result.put(s.getKey(), copy);
        }



        return result;
    }

    /*

        Private Methods

     */




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
