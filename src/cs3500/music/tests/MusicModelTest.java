package cs3500.music.tests;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.NoteName;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Created by NGeye on 3/1/2016.
 */

public class MusicModelTest {

    private IMusicModel model;

    //Note Test
    @Test
    public void testNote() {
        Note cSharp = new Note(NoteName.CSHARP, 4, 4, 1, 50);
        assertEquals(cSharp.getNameAsString(), " C#4 ");
    }

    @Test
    public void testEquals() {
        Note cSharp = new Note(NoteName.CSHARP, 4, 4, 1, 50);
        Note cSharpCopy = new Note(NoteName.CSHARP, 4, 4, 1, 50);
        assertEquals(cSharp, cSharpCopy);
    }

    @Test
    public void testNoteHigher() {
        Note c4 = new Note(NoteName.C, 4, 0, 1, 50);
        Note g4 = new Note(NoteName.G, 4, 0, 1, 50);
        assert(c4.compareTo(g4) < 0);
    }

    @Test
    public void testAddNote() {
        this.model = new MusicModel();
        this.model.addNote(NoteName.A, 4, 4, 0, 1, 50);

        Map<Integer, List<Note>> result = new TreeMap<>();
        List<Note> list = new ArrayList<>();
        list.add(new Note(NoteName.A, 4, 4, 1, 50));
        result.put(0, list);
        assertEquals(this.model.getAllNotes(), result);
    }

    @Test
    public void testRemoveNote() {
        this.model = new MusicModel();
        this.model.addNote(NoteName.A, 4, 4, 0, 1, 50);
        this.model.addNote(NoteName.A, 5, 4, 0, 1, 50);

        Map<Integer, List<Note>> result = new TreeMap<>();
        List<Note> list = new ArrayList<>();
        list.add(new Note(NoteName.A, 4, 4, 1, 50));
        result.put(0, list);

        //Remove the note
        this.model.removeNote(NoteName.A, 5, 4, 0, 1, 50);

        assertEquals(this.model.getAllNotes(), result);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIllegalOctave() {
        this.model = new MusicModel();
        this.model.addNote(NoteName.C, -1, 4, 0, 1, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalNoteDuration() {
        this.model = new MusicModel();
        this.model.addNote(NoteName.C, 3, -1, 0, 1, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalNoteStart() {
        this.model = new MusicModel();
        this.model.addNote(NoteName.C, 3, 1, -1, 1, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalRemoveNote() {
        this.model = new MusicModel();
        this.model.addNote(NoteName.C, 3, 2, 0,1 , 50);
        this.model.removeNote(NoteName.A, 3, 2, 0, 1, 50);
    }

    @Test
    public void testGetOutput() {
        this.model = new MusicModel();

        this.model.addNote(NoteName.A, 4, 2, 0, 1, 50);
        this.model.addNote(NoteName.A, 4, 2, 2, 1, 50);
        this.model.addNote(NoteName.A, 5, 2, 0, 1, 50);

        Map<Integer, List<Note>> result = new TreeMap<>();

        List<Note> notes1 = new ArrayList<>();
        List<Note> notes2 = new ArrayList<>();
        notes1.add(new Note(NoteName.A, 4, 2, 1, 50));
        notes1.add(new Note(NoteName.A, 5, 2, 1, 50));
        result.put(0, notes1);
        notes2.add(new Note(NoteName.A, 4, 2, 1, 50));
        result.put(2, notes2);

        assertEquals(this.model.getAllNotes(), result);
    }

    //Tests that getting a copy of the map is correct
    @Test
    public void testGetCopy() {
        this.model = new MusicModel();
        this.model.addNote(NoteName.A, 4, 2, 0, 1, 50);
        this.model.addNote(NoteName.A, 4, 2, 2, 1, 50);
        this.model.addNote(NoteName.A, 5, 2, 0, 1, 50);
        Map<Integer, List<Note>> result = new TreeMap<>();

        List<Note> notes1 = new ArrayList<>();
        notes1.add(new Note(NoteName.A, 4, 2, 1, 50));
        notes1.add(new Note(NoteName.A, 5, 2, 1, 50));
        result.put(0, notes1);

        List<Note> notes2 = new ArrayList<>();
        notes2.add(new Note(NoteName.A, 4, 2, 1, 50));
        result.put(2, notes2);

        assertEquals(this.model.getAllNotes(), result);


    }


    @Test
    public void testAppendModel() {
        this.model = new MusicModel();
        IMusicModel newModel = new MusicModel();

        this.model.addNote(NoteName.A, 4, 2, 0, 1, 50);
        newModel.addNote(NoteName.A, 4, 2, 0, 1, 50);

        //Append them, this places all notes after
        this.model.append(newModel);

        Map<Integer, List<Note>> result = new TreeMap<>();
        List<Note> list1 = new ArrayList<>();
        list1.add(new Note(NoteName.A, 4, 2, 1, 50));
        result.put(0, list1);

        List<Note> list2 = new ArrayList<>();
        list2.add(new Note(NoteName.A, 4, 2, 1, 50));
        result.put(2, list2);

        assertEquals(this.model.getAllNotes(), result);
    }

    @Test
    public void testCombineModels() {
        this.model = new MusicModel();
        IMusicModel newModel = new MusicModel();
        this.model.addNote(NoteName.A, 4, 2, 0, 1, 50);
        newModel.addNote(NoteName.A, 4, 2, 0, 1, 50);

        Map<Integer, List<Note>> result = new TreeMap<>();
        List<Note> list1 = new ArrayList<>();

        list1.add(new Note(NoteName.A, 4, 2, 1, 50));
        list1.add(new Note(NoteName.A, 4, 2, 1, 50));

        result.put(0, list1);

        this.model.combine(newModel);

        assertEquals(this.model.getAllNotes(), result);

    }
}