import java.awt.*;
import javax.swing.*;

/**
 *
 * @author SenorKey
 */
class ControlPanel extends JPanel {

    JButton start = new JButton("Start");
    JButton clear = new JButton("Clear");
    JTextField tField1 = new JTextField();
    JLabel label1 = new JLabel("Max # of Ricochets [Default 5]");
    JTextField tField2 = new JTextField();
    JLabel label2 = new JLabel("Speed (1-1000) [Default 50]");

    public ControlPanel(Ricochets ricochet) {
        super(new GridBagLayout());

        start.addActionListener((ActionEvent) -> {
            ricochet.start();
        });
        clear.addActionListener((ActionEvent) -> {
            ricochet.clear();
        });
        tField1.addActionListener((ActionEvent) -> {
            ricochet.ricochetsAllowed = Integer.parseInt(tField1.getText());
        });
        tField2.addActionListener((ActionEvent) -> {
            ricochet.step = .02 * .00005 * Integer.parseInt(tField2.getText());
        });
        

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(2, 2, 2, 2);
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(start, c);
        
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(2, 2, 2, 2);
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(clear, c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = .1;
        c.insets = new Insets(2, 2, 2, 2);
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(tField1, c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = .1;
        c.insets = new Insets(2, 2, 2, 2);
        c.fill = GridBagConstraints.NONE;
        this.add(label1, c);
        
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = .1;
        c.insets = new Insets(2, 2, 2, 2);
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(tField2, c);
        
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = .1;
        c.insets = new Insets(2, 2, 2, 2);
        c.fill = GridBagConstraints.NONE;
        this.add(label2, c);
    }

}
