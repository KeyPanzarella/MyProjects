import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author SenorKey
 */
public class Ricochets extends Canvas implements MouseListener {

    private BufferedImage img;
    private BufferedImage trail;
    private Tracer tracer = new Tracer();
    private double[] pixAngles;
    public ArrayList<Integer> xVertexVals = new ArrayList<>();
    public ArrayList<Integer> yVertexVals = new ArrayList<>();
    public double startAngle = 0;
    public double endAngle = 3.14;
    public int ricochetsAllowed = 5;
    public double step = .00005;
    //public int generalRaySpeed = 10;

    public Ricochets() {
        this.addMouseListener(this);
    }

    public static void main(String[] args) {
        Ricochets canvas = new Ricochets();

        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        frame.add(canvas, c);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = .1;
        c.fill = GridBagConstraints.BOTH;
        frame.add(new ControlPanel(canvas), c);

        frame.setVisible(true);

    }

    public void updateImage(boolean resetAngles) {
        if (trail == null) {
            clearTrail(true);
        }
        img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        if (resetAngles) {
            pixAngles = new double[this.getWidth() * this.getHeight()];
            Arrays.fill(pixAngles, -1);
        }
        Graphics g = img.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.blue);
        if (xVertexVals.size() > 1) {
            for (int i = 0; i < xVertexVals.size() - 1; i++) {                        //drawing lines via x and y values stored in arraylists (and sneakily setting endAngle based on first angle of first line)
                g.drawLine(xVertexVals.get(i), yVertexVals.get(i), xVertexVals.get(i + 1), yVertexVals.get(i + 1));
                if (i == 0) {
                    endAngle = Math.atan2(yVertexVals.get(i + 1) - yVertexVals.get(i), xVertexVals.get(i + 1) - xVertexVals.get(i));
                    if (endAngle < 0) {
                        endAngle += Math.PI * 2;
                    }
                }
                for (int pixel = 0; pixel < pixAngles.length; pixel++) {
                    if (((DataBufferInt) img.getRaster().getDataBuffer()).getData()[pixel] != 0xFFFFFFFF    //white
                            && pixAngles[pixel] == -1) {
                        pixAngles[pixel] = Math.atan2(yVertexVals.get(i + 1) - yVertexVals.get(i), xVertexVals.get(i + 1) - xVertexVals.get(i));
                        while (pixAngles[pixel] < 0) {
                            pixAngles[pixel] += 2 * Math.PI;
                        }
                    }
                }
            }
            g.drawLine(xVertexVals.get(xVertexVals.size() - 1), yVertexVals.get(yVertexVals.size() - 1), xVertexVals.get(0), yVertexVals.get(0));
            startAngle = Math.atan2(yVertexVals.get(yVertexVals.size() - 1) - yVertexVals.get(0), xVertexVals.get(xVertexVals.size() - 1) - xVertexVals.get(0));
            if (startAngle < 0) {
                startAngle += Math.PI * 2;
            }
            for (int pixel = 0; pixel < pixAngles.length; pixel++) {
                if (((DataBufferInt) img.getRaster().getDataBuffer()).getData()[pixel] != 0xFFFFFFFF
                        && pixAngles[pixel] == -1) {
                    pixAngles[pixel] = Math.atan2(yVertexVals.get(yVertexVals.size() - 1) - yVertexVals.get(0), xVertexVals.get(xVertexVals.size() - 1) - xVertexVals.get(0));
                    while (pixAngles[pixel] < 0) {
                        pixAngles[pixel] += 2 * Math.PI;
                    }
                }
            }
            g.fillOval(xVertexVals.get(0) - 3, yVertexVals.get(0) - 3, 7, 7);
        } else if (xVertexVals.size() == 1) {
            g.fillOval(xVertexVals.get(0) - 3, yVertexVals.get(0) - 3, 7, 7);
        }
        /*for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                if (pixAngles[i + j * img.getWidth()] != -1) {
                    img.setRGB(i, j, 0xFF00FF00);
                }
            }
        }//*/
    }

    long lastUpdate = System.currentTimeMillis();

    public void repaint() {
        if (System.currentTimeMillis() > lastUpdate + 30) {
            super.repaint();
            lastUpdate = System.currentTimeMillis();
        }
    }

    public void paint(Graphics g) {
        if (trail == null) {
            updateImage(true);
        }
        for (int i = 0; i < trail.getWidth(); i++) {
            for (int j = 0; j < trail.getHeight(); j++) {
                if (trail.getRGB(i, j) == 0xFF000000) {
                    img.setRGB(i, j, 0xFF0000);
                }
            }
            
        }
        //System.out.println("print");
        g.drawImage(img, 0, 0, null);

    }

    public void start() {
        if (!tracer.isAlive()) {
            tracer = new Tracer();
            tracer.start();
        }
    }

    public void clear() {
        tracer.running = false;
        while (tracer.isAlive()) {
        }
        clearTrail(true);
        xVertexVals.clear();
        yVertexVals.clear();
        updateImage(true);
        
        repaint();
    }

    public void clearTrail(boolean resetAngles) {
        trail = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        if (resetAngles) {
            pixAngles = new double[this.getWidth() * this.getHeight()];
            Arrays.fill(pixAngles, -1);
        }
        Graphics g = trail.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, trail.getWidth(), trail.getHeight());
        updateImage(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (tracer == null || !tracer.isAlive()) {
            xVertexVals.add(e.getX());
            yVertexVals.add(e.getY());
            updateImage(true);
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private class Tracer extends Thread {

        
        int startingX, startingY;
        boolean running = true;
        double lastEncounter = -1;
        boolean collisionEnabled;
        
        

        public Tracer() {

        }

        
        @Override
        public void run() {
            if (xVertexVals.size() < 3) {
                return;
            }
            running = true;
            startingX = xVertexVals.get(0);
            startingY = yVertexVals.get(0);
            trail = new BufferedImage(Ricochets.this.getWidth(), Ricochets.this.getHeight(), 1);

            double[] initialRayAngles;
            if ((endAngle - startAngle) < 0) {
                initialRayAngles = new double[(int) (((endAngle - startAngle) + 2 * Math.PI) / (5 * Math.PI / 180)) - 2];
            } else {
                initialRayAngles = new double[(int) ((endAngle - startAngle) / (5 * Math.PI / 180)) - 2];
            }
            for (int i = 0; i < initialRayAngles.length; i++) {
                initialRayAngles[i] = startAngle + (i + 1) * (5 * Math.PI / 180);
            }
            for (double initialAngle : initialRayAngles) {
                double angle = initialAngle;
                clearTrail(false);
                collisionEnabled = false;
                lastEncounter = startAngle;
                double x = startingX;
                double y = startingY;
                int ricochets = 0;
                while (running && ricochets <= ricochetsAllowed) { //change 5 with variable for textfield
                    int lastX = (int) x;
                    int lastY = (int) y;
                    x += Math.cos(angle) * step;
                    y += Math.sin(angle) * step;
                    int currX = (int) x;
                    int currY = (int) y;
                    if (!collisionEnabled && Math.hypot(currX - startingX, currY - startingY) > 15) {
                        collisionEnabled = true;
                    }
                    if (collisionEnabled && Math.hypot(currX - startingX, currY - startingY) < 3) {
                        //kill
                        double displayAngle = 360-(initialAngle % (2* Math.PI))*180/(Math.PI);
                        System.out.println("Collision with original vertex at angle:\t" + displayAngle);
                        try{
                            Thread.sleep(5000);
                        }catch(InterruptedException e){System.out.println("??");}
                        break;
                    }
                    if (currX < 0 || currX >= getWidth() || currY < 0 || currY >= getHeight()) {
                        break;
                    }
                    if (lastX == currX && lastY == currY) {
                            //System.out.println("continue");
                            continue;
                    } 
                    if (collisionEnabled) {
                        if ((lastX == currX) != (lastY == currY)) {
                            if (pixAngles[currX + currY * getWidth()] != -1.0 && pixAngles[currX + currY * getWidth()] != lastEncounter) {
                                lastEncounter = pixAngles[currX + currY * getWidth()];
                                angle = 2 * lastEncounter - angle;
                                ricochets++;
                            }
                        } else if (pixAngles[currX + lastY * getWidth()] != -1.0 && pixAngles[currX + lastY * getWidth()] != lastEncounter) {
                            lastEncounter = pixAngles[currX + lastY * getWidth()];
                            angle = 2 * lastEncounter - angle;
                            ricochets++;
                        } else if (pixAngles[lastX + currY * getWidth()] != -1.0 && pixAngles[lastX + currY * getWidth()] != lastEncounter) {
                            lastEncounter = pixAngles[lastX + currY * getWidth()];
                            angle = 2 * lastEncounter - angle;
                            ricochets++;
                        } else if (pixAngles[currX + currY * getWidth()] != -1.0 && pixAngles[currX + currY * getWidth()] != lastEncounter) {
                            lastEncounter = pixAngles[currX + currY * getWidth()];
                            angle = 2 * lastEncounter - angle;
                            ricochets++;
                        }
                    }
                    trail.setRGB(currX, currY, 0xFF000000);
                    //System.out.println(currX + " " + currY);
                    //try{
                        //System.out.println("sleep here");
                        //Thread.sleep(0, 10);
                    //}catch(InterruptedException e){System.out.println("??");}
                    repaint();
                }
            }
        }
    }
}
