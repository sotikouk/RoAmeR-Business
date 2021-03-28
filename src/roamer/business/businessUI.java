package roamer.business;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class businessUI extends JFrame {
    private businessAgent agent;
    private JPanel mainPanel;
    private JTextField textField2;
    private JButton εισαγωγήButton;
    private JList list1;
    private JTextField offerField;
    private JTextField offerPrice;
    private JList offersList;
    private HashMap<String, String> offersMap = new HashMap<String, String>();
    private JButton deleteOfferButton;
    private JButton offerButton;
    private String offer;
    private String price;

    businessUI(businessAgent a){
        super(a.getLocalName());
        agent = a;
        this.setContentPane(mainPanel);
        // Εισαγωγή προσφοράς
        offerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                offer = offerField.getText().trim();
                price = offerPrice.getText().trim();
                a.insertOffer(offer, price);
                offersMap.put(price, offer);
                offersList.setListData(offersMap.values().toArray());
            }
        });
        // Αφαίρεση προσφοράς
        deleteOfferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offer = String.valueOf(offersList.getSelectedValue());
                if (offersMap.containsKey(offer))
                price = offersMap.get(offer);
                offersMap.remove(offer);
                offersList.setListData(offersMap.values().toArray());
                a.deleteOffer(offer, price);
                // todo επίλυση bug στην διαγραφή των προσφορών.
            }
        });

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
