package roamer.business;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class businessUI extends JFrame {
    private businessAgent agent;
    private JPanel mainPanel;
    private JTextField textField2;
    private JButton εισαγωγήButton;
    private JList list1;
    private JTextField textField3;
    private JTextField textField4;
    private JList list2;
    private JButton αφαίρεσηΠροσφοράςButton;
    private JButton offerButton;

    businessUI(businessAgent a){
        super(a.getLocalName());
        agent = a;
        this.setContentPane(mainPanel);

        //όταν κλείνουμε το παράθυρο UI αυτομάτως θα διαγράφεται και ο πράκτορας
        addWindowListener(new	WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                agent.doDelete();
            }
        } );
    }

    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() / 2;
        int centerY = (int)screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }
}
