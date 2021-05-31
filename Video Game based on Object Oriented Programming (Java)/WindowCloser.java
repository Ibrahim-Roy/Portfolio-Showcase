import java.awt.event.*;
//All JFrames use this window closer to exit when x is clicked
public class WindowCloser extends WindowAdapter{
        public void windowClosing(WindowEvent evt){
            System.exit(0);
        }
    }