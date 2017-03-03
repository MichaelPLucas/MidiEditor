Hello!

|||||||||||||||||
|               |
|     Model     |
|               |
|||||||||||||||||

|   Interface   |

This uses the IMusicModel interface which has the following 6 Methods:

addNote(NoteName note,int octave, int duration, int start)

    This adds and creates the desired note to the model
    Takes a NoteName Enum, int octave, int duration, and int start

removeNote(NoteName note, int octave, int duration, int start)

    This removes the given Note from the sheet
    Takes a NoteName Enum, int octave, int duration, and int start

combine(IMusicModel model)

    This combines that music model with this music model

    ****************************************************

    Important to take note that when this does the combine, that it actually overlays
    everything from that model onto this model

append(IMusicModel model)

    This appends that music model with this music model starting with the last note

getMusicSheet()

    This gets the sheet music as a String from the music model

    The left hand side indicates the beat (starting at 0)
    While the top row indicates the note name

    An "X" denotes the beginning of a note
    A "|" denotes the sound on consecutive beats

    ***************************************************
    How it works
    ***************************************************

    This takes advantage of the way the model stores each note
    We know the beginning and end of each note base on the start and the duration.
    From here we can also calculate how many beats our entire piece of music is, and
    what is the range in which the music is played

    With the range and the amount of beats we can construct the the first row with
    the correct spacing, then construct the rest of the "board" with blank space.
    Then we go in and replace each location with the appropriate denotation "X" for the
    beginning, and "|" for the continuation of the note.

getAllNotes()

    This returns a deep copy of all the notes within the model
    It does this by returning a Map<Integer, List<Note>> without giving access to the notes within the model


|   Data Modelling   |


Note:

    Class representing a note

    Fields:
        Name
        Octave
        Duration

    Class invariants:
        Octave cannot be 0 or negative
        Duration cannot be 0 or negative

    Methods:
        getName
        getOctave
        getDuration
        getNameAsString
        equals
        compareTo

    This encapsulates a Note using the NoteName enum, and ints for octave and duration
    It is important that for design reasons a note is only naturals and sharps as that is
    what made the most sense to implement.

    There are accessors for each property "getters" and then a method for outputting the note
    as the correct string representation

MusicModel:

    Class representing the Data model for the music player

    fields:
        Notes

    class invariants:
        Map<Integer, List<Note>> cannot contain negative integers


    Methods:
        From interface IMusicModel


    For this class it was important to get the core functionality of adding and removing notes.
    The way in which this is done is by specifying the note that you want
    i.e. Name, Octave, Duration, Beat and then having the model actually create it for you.
    Thus both the add and remove method work with the data for a note, rather than a note object.

    As for the appending and combining, we took advantage of what each model had for notes.
    This used the getAllNotes() which in turn created a deep copy of the data that was going
    to be acted on. For combining, we went for each beat and just added the notes to the
    existing list, or creating a new one if one didn't exist. For appending we had to start
    at the last beat of the overall score and then copy the values into the existing notes
    structure.

    Lastly for creating the text representation of the score we had to take into consideration
    the formatting required. For this I decided to create the first row of notes, this was
    the "range" of notes from the lowest to the highest. This took advantage of
    getHighNote and getLowNote to determine the range. After creating the first row with
    the correct spacing, we went and created all the following rows beat by beat using getLastBeat
    with 5 spaces of white space for columns. Lastly we iterated through all the beats and the
    list of notes along with it and placed their identifiers in the correct location using
    the index of the note in the range.


|||||||||||||||||
|               |
|     Views     |
|               |
|||||||||||||||||


|   Interface   |

We used the IView Interface, here we were able to designate the following methods:

addNote(int start, Note note)

    This adds a note to the view at the given starting beat.

removeNote(int start, Note note)

    This removes a note from the view at the given starting beat.

play()

    This causes the music to start playing.

stop()

    This stops the music from playing.

|   Views   |

We produced three different views as requested, a console view, a midi view, and a gui view. We
also elected to produce an abstract class to govern the nature of these views. Furthermore, as
required for the assignment we defined a ViewFactory class which can produce any of our three
IViews.

MusicView implements IView:

    This abstract view helps to lay groundwork for the three views we were required to implement.

    Fields:
        model

    This class leaves play() and stop() unimplemented, but gives cursory implementations of both
    addNote(int start, Note note) and removeNote(int start, Note note). The cursory implementations
    add and remove notes from the model field, which is an IMusicModel.

ConsoleView extends MusicView:

    This view produces a console friendly output format which denotes the start of notes with 'X'
    and sustains with '|'. Beat numbers run down the left side of the view and a score runs across
    the top. Any space which does not contain the start of a note or a sustain is simply left as
    whitespace.

    Fields:
        out

    out is an Appendable which all of the output is sent to. By default it is System.out, but it
    can be set to any other Appendable for testing purposes.

    This class implements play() and stop() and does not make any changes to the implementations of
    either addNote(int start, Note note) or removeNote(int start, Note note). The implementation of
    play() merely takes the string which IMusicModel generates as the console output and sends it
    to the console, and stop is an empty function as it is an irrelevant functionality for this
    class.

GuiView extends MusicView:

    This view produces a gui made with the Java Swing library which denotes the start of notes with
    black boxes and sustains with green boxes. A score runs down the left side of the window and
    beat numbers run across the top. Every 4 beats there is a line from the top to the bottom of
    the area in which notes are drawn. Pitches are separated by horizontal lines that run the
    length of the piece. A red vertical line indicates the current beat. The window can scroll both
    horizontally and vertically if necessary to see the entire piece, but only has scroll bars if
    they are needed.

    Fields:
        viewFrame

    viewFrame is a GuiViewFrame which represents the window for the gui.

    This class implements play() and stop() and makes changes to both addNote(int start, Note note)
    and removeNote(int start, Note note). play() and stop() are not currently implemented as this
    assignment did not require the gui to be playable yet. addNote(int start, Note note) and
    removeNote(int start, Note note) are modified to reflect the changes to the model in the view.

MidiView extends MusicView:

    This view produces a view that actually plays all the notes in the music. It does this using
    the sequencer object. We know that this method is much harder and thus may be convoluted and
    overkill, but we did it. This takes advantage of putting each note specific to it's instrument
    on its own track, and thus is processed optimally when the amount of notes added to the view
    becomes very large.

    Fields:
        _tracks
        sequencer
        sequence

    _tracks is a Map<Integer, Track> for all the tracks contained in the sequence

    sequencer is a Sequencer which handles all of the playing of the music

    sequence is the sequence that will be sent out to be played by the sequencer

    This class implements play() and stop() and uses a private method processSequence.
    Here we are able to completely depend on the model for storing our notes, and any changes we
    make to it we, we use the implementation in MusicView. This allows us to keep a uniform model
    between different views. Lastly the processSequence allows us to create the entire sequence once
    play is requested the first time. This means that we always have the accurate number of notes
    and that the sequence is in the correct area.

    For testing the Midi View we were able to Mock the Sequencer, and then log the Bytes that were given
    in each midimessage. This allowed us to see the start time, end time, and note values like instrument
    pitch and volume.

ViewFactory:

    This class is a factory of views which can be used to produce any of the views that we have
    defined.

    Fields:
        model
        out

    model is the IMusicModel to initialize the view with.
    out is an Appendable which can be defined for any generated ConsoleViews but defaults to
    System.out.

    Methods:
        build(String type)

    build(String type) produces an IView of the type indicated by the input string.


Controller:

    We made a compound view that had a midiview and guiview within it
    The hardest part was syncing the sequencer up with gui view which we did
    by making a custom mete event listener (BeatListener) which then was able to pick up
    and timing messages that we placed within the song




Controls

Move left : ,
Move right : .

Play/Pause : Space bar
Restart: r

Add note : left click anywhere
Edit note: middle click
Delete note: right click note

Beginning : [
End : ]


|   MAIN METHOD   |

Our Music Editor takes the specified command line arguments and outputs the correct view. This is done
via specifying the file located in the same directory or a full path and then the type of view
whether it be gui, midi, or console.
