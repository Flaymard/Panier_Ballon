import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class fond extends JFrame {




    public fond(JLabel im){

        setLocation(100, 100);
        setSize (800,600);

        //im = new JLabel (new ImageIcon ("temp.jpg"));
        im.setBounds(0,0,getWidth(),getHeight());
        im.setVisible(true);
        this.add(im);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
