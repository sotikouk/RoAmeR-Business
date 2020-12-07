package roamer.business;

import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class startUI extends JFrame{
    private JTextField businessName;
    private JComboBox businessKind;
    private JTextField longitude;
    private JTextField latitude;
    private JTextField altitude;
    private JButton ButtonInsert;
    private JPanel mainPanel;

    startUI(){
        this.setContentPane(mainPanel);
        ButtonInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = businessName.getText().trim();
                double lon = Double.parseDouble(longitude.getText().trim());
                double lat = Double.parseDouble(latitude.getText().trim());
                double alt = Double.parseDouble(altitude.getText().trim());

                try {
                    MicroRuntime.startAgent(name, "roamer.business.businessAgent", null);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                dispose();
// TODO: 21/11/2020 να περάσω την τοποθεσία της περιοχής στον πράκτορα της επιχείρησης
                businessAgent.Location loc = new businessAgent.Location(lon, lat, alt);

            }
        });
    }
    public void showUI() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() / 2;
        int centerY = (int)screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }

}
