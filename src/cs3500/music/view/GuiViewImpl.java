package cs3500.music.view;

import cs3500.music.controller.AMouseListener;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.NoteName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Michael Lucas on 3/22/2016.
 */
public class GuiViewImpl extends GuiView {
    private GuiViewFrame viewFrame;

    public GuiViewImpl(Map<Integer, List<Note>> map) {
        super(map);
        this.viewFrame = new GuiViewFrame();
        viewFrame.initialize();
    }

    private class GuiViewFrame extends JFrame {

        private final GuiViewPanel displayPanel;
        private JScrollPane scrollPane;

        /**
         * Creates new GuiViewImpl
         */
        public GuiViewFrame() {
            this.displayPanel = new GuiViewPanel();
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            scrollPane = new JScrollPane(displayPanel,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            add(scrollPane);
        }

        /**
         * Initializes the GUI frame
         */
        public void initialize() {
            pack();

            setResizable(false);
            this.setVisible(true);
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension dim = super.getPreferredSize();
            dim.setSize(Math.min(1400, dim.width), Math.min(1000, dim.height + 10));
            return dim;
        }

        /**
         * Sets the time in the GUI
         * @param i Time to be set
         */
        public void setTime(int i) {
            displayPanel.setTime(i);

            if ((i + 1) * NOTEWIDTH >=
                    scrollPane.getHorizontalScrollBar().getModel().getValue() +
                    scrollPane.getHorizontalScrollBar().getModel().getExtent()) {
                scrollPane.getHorizontalScrollBar().getModel().setValue(i * NOTEWIDTH);
            }
        }
    }

    private class GuiViewPanel extends JPanel {
        private int time;

        public GuiViewPanel() {
            time = 0;
        }

        @Override
        public void paintComponent(Graphics g) {
            // Handle the default painting
            super.paintComponent(g);
            // Look for more documentation about the Graphics class,
            // and methods on it that may be useful
            int high = getHighNote().toInt();
            int low = getLowNote().toInt();
            Dimension dim = new Dimension(getLength() * NOTEWIDTH + SCOREWIDTH, (high - low + 1)
                    * NOTEHEIGHT + BEATHEIGHT);

            g.setColor(Color.BLACK);
            g.drawRect(SCOREWIDTH, BEATHEIGHT, dim.width - SCOREWIDTH, dim.height - BEATHEIGHT);

            for (int i = BEATHEIGHT; i < dim.height; i += NOTEHEIGHT) {
                g.drawLine(SCOREWIDTH, i, dim.width, i);

                int x = high - (i - BEATHEIGHT) / NOTEHEIGHT;
                Note n = new Note(NoteName.valueOf(x % 12), x / 12, 0, 0, 0);

                g.drawString(n.getNameAsString(), 0, i + 15);
            }

            for (Map.Entry<Integer, List<Note>> e : notes.entrySet()) {
                for (Note n : e.getValue()) {
                    g.setColor(Color.BLACK);
                    g.fillRect(SCOREWIDTH + e.getKey() * NOTEWIDTH,
                            BEATHEIGHT + (high - n.toInt()) * NOTEHEIGHT,
                            NOTEWIDTH,
                            NOTEHEIGHT);
                    g.setColor(Color.GREEN);
                    g.fillRect(SCOREWIDTH + (e.getKey() + 1) * NOTEWIDTH,
                            BEATHEIGHT + (high - n.toInt()) * NOTEHEIGHT,
                            NOTEWIDTH * (n.getDuration() - 1),
                            NOTEHEIGHT);
                }
            }

            g.setColor(Color.BLACK);
            for (int i = 0; i < getLength(); i += 4) {
                g.drawLine(SCOREWIDTH + i * NOTEWIDTH, BEATHEIGHT,
                        SCOREWIDTH + i * NOTEWIDTH, dim.height);
                if ((i % 16) == 0) {
                    g.drawString(i + "", i * NOTEWIDTH + SCOREWIDTH, BEATHEIGHT - 5);
                }
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            g2.draw(new Line2D.Float(SCOREWIDTH + time * NOTEWIDTH, BEATHEIGHT, SCOREWIDTH + time *
                    NOTEWIDTH, dim.height));
        }

        @Override
        public Dimension getPreferredSize() {
            Note low = getLowNote();
            Note high = getHighNote();

            if (low.equals(new Note(NoteName.B, 1000000, 4, 0, 0)) &&
                    high.equals(new Note(NoteName.C, 1, 1, 0, 0))) {
                //center on c4 show two octaves
                return new Dimension(0, 0);
            } else {
                return new Dimension(getLength() * NOTEWIDTH + SCOREWIDTH,
                        (high.toInt() - low.toInt() + 1) * NOTEHEIGHT + BEATHEIGHT);
            }
        }

        /**
         * Sets the time in the view
         * @param i Time to be set
         */
        public void setTime(int i) {
            time = i;
        }

        /**
         * Gets the length of the score
         * @return int representing the length of the score in beats
         */
        private int getLength() {
            int last = 0;
            for (Map.Entry<Integer, List<Note>> s : notes.entrySet()) {
                for (Note n : s.getValue()) {
                    if (s.getKey() + n.getDuration() > last) {
                        last = s.getKey() + n.getDuration();
                    }
                }
            }
            return last;
        }

        /**
         * Gets the lowest note in the score
         * @return Note which is the lowest frequency
         */
        private Note getLowNote() {

            //Set arbitrary high note
            Note low = new Note(NoteName.B, 1000000, 4, 0, 0);

            for (Map.Entry<Integer, List<Note>> s : notes.entrySet()) {
                for (Note n : s.getValue()) {
                    if (n.compareTo(low) < 0) low = n;
                }
            }

            return low;
        }

        /**
         * Gets the highest noe in the score
         * @return Note which is the highest frequency
         */
        private Note getHighNote() {

            //Set arbitrary low note
            Note high = new Note(NoteName.C, 1, 1, 0, 0);

            for (Map.Entry<Integer, List<Note>> s : notes.entrySet()) {
                for (Note n : s.getValue()) {
                    if (n.compareTo(high) > 0) high = n;
                }
            }

            return high;
        }
    }

    @Override
    public void addNote(int start, Note note) {
        super.addNote(start, note);
        viewFrame.repaint();
        viewFrame.pack();
    }

    @Override
    public void removeNote(int start, Note note) {
        super.removeNote(start, note);
        viewFrame.repaint();
        viewFrame.pack();
    }

    @Override
    public void play() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void setTime(int i) {
        viewFrame.setTime(i);
        viewFrame.repaint();
    }

    public void addKeyListener(KeyListener kl) {
        this.viewFrame.addKeyListener(kl);
    }

    @Override
    public void addMouseListener(AMouseListener ml) {
        this.viewFrame.displayPanel.addMouseListener(ml);
    }

    @Override
    public void refresh() {
        viewFrame.repaint();
        viewFrame.pack();
    }

}