package cs3500.music.tests;

import javax.sound.midi.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngeyer on 3/23/2016.
 * This is a sequencer for testing
 */
public class MockSequencer implements Sequencer {


    private StringBuilder sb;
    private Sequence sequence;
    private int tempo;

    public MockSequencer(StringBuilder sb) {
        this.sb = sb;
    }



    @Override
    public void setSequence(Sequence sequence) throws InvalidMidiDataException {
        this.sequence = sequence;
    }

    @Override
    public void setSequence(InputStream stream) throws IOException, InvalidMidiDataException {
        //Don't use this one
    }

    @Override
    public Sequence getSequence() {
        return sequence;
    }

    @Override
    public void start() {
        Track[] tracks = this.sequence.getTracks();

        if (tracks == null) {
            int a = 0;
        }
        sb.append("tempo ").append(tempo).append("\n");

        for (Track t : tracks) {
            //Process all the notes in each track
            for (int i = 0; i < t.size() - 1; i++) {
                MidiEvent ev1 = t.get(i);
                MidiMessage msg1 = ev1.getMessage();

                //Parse the midi message from the bytes
                int[] nums = new int[3];
                byte[] bytes = msg1.getMessage();
                for (int k = 0; k < 3; k++) {
                    nums[k] = (int)(bytes[k] & 0xFF);
                }
                int instrument;
                if (nums[0] > 144) {
                    instrument = nums[0] - 144;
                } else {
                    instrument = nums[0] - 128;
                }

                int status = nums[0] - instrument;
                int note = nums[1];
                int vol = nums[2];


                sb.append("note ");
                if (status == 144) {
                    sb.append("start: ");
                }
                else {
                    sb.append("end: ");
                }

                sb.append(ev1.getTick() / 96).append(" ");
                sb.append(instrument).append(" ");
                sb.append(note).append(" ").append(vol);

                sb.append("\n");


            }
        }

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void startRecording() {

    }

    @Override
    public void stopRecording() {

    }

    @Override
    public boolean isRecording() {
        return false;
    }

    @Override
    public void recordEnable(Track track, int channel) {

    }

    @Override
    public void recordDisable(Track track) {

    }

    @Override
    public float getTempoInBPM() {
        return 0;
    }

    @Override
    public void setTempoInBPM(float bpm) {

    }

    @Override
    public float getTempoInMPQ() {
        return 0;
    }

    @Override
    public void setTempoInMPQ(float mpq) {
        this.tempo = (int)mpq;
    }

    @Override
    public void setTempoFactor(float factor) {

    }

    @Override
    public float getTempoFactor() {
        return 0;
    }

    @Override
    public long getTickLength() {
        return 0;
    }

    @Override
    public long getTickPosition() {
        return 0;
    }

    @Override
    public void setTickPosition(long tick) {

    }

    @Override
    public long getMicrosecondLength() {
        return 0;
    }

    @Override
    public Info getDeviceInfo() {
        return null;
    }

    @Override
    public void open() throws MidiUnavailableException {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public long getMicrosecondPosition() {
        return 0;
    }

    @Override
    public int getMaxReceivers() {
        return 0;
    }

    @Override
    public int getMaxTransmitters() {
        return 0;
    }

    @Override
    public Receiver getReceiver() throws MidiUnavailableException {
        return null;
    }

    @Override
    public List<Receiver> getReceivers() {
        return null;
    }

    @Override
    public Transmitter getTransmitter() throws MidiUnavailableException {
        return null;
    }

    @Override
    public List<Transmitter> getTransmitters() {
        return null;
    }

    @Override
    public void setMicrosecondPosition(long microseconds) {

    }

    @Override
    public void setMasterSyncMode(SyncMode sync) {

    }

    @Override
    public SyncMode getMasterSyncMode() {
        return null;
    }

    @Override
    public SyncMode[] getMasterSyncModes() {
        return new SyncMode[0];
    }

    @Override
    public void setSlaveSyncMode(SyncMode sync) {

    }

    @Override
    public SyncMode getSlaveSyncMode() {
        return null;
    }

    @Override
    public SyncMode[] getSlaveSyncModes() {
        return new SyncMode[0];
    }

    @Override
    public void setTrackMute(int track, boolean mute) {

    }

    @Override
    public boolean getTrackMute(int track) {
        return false;
    }

    @Override
    public void setTrackSolo(int track, boolean solo) {

    }

    @Override
    public boolean getTrackSolo(int track) {
        return false;
    }

    @Override
    public boolean addMetaEventListener(MetaEventListener listener) {
        return false;
    }

    @Override
    public void removeMetaEventListener(MetaEventListener listener) {

    }

    @Override
    public int[] addControllerEventListener(ControllerEventListener listener,
                                            int[] controllers) {
        return new int[0];
    }

    @Override
    public int[] removeControllerEventListener(ControllerEventListener listener,
                                               int[] controllers) {
        return new int[0];
    }

    @Override
    public void setLoopStartPoint(long tick) {

    }

    @Override
    public long getLoopStartPoint() {
        return 0;
    }

    @Override
    public void setLoopEndPoint(long tick) {

    }

    @Override
    public long getLoopEndPoint() {
        return 0;
    }

    @Override
    public void setLoopCount(int count) {

    }

    @Override
    public int getLoopCount() {
        return 0;
    }
}
