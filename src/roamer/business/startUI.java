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
                String kind = (String) businessKind.getItemAt(businessKind.getSelectedIndex());
                String lon = longitude.getText().trim();
                String lat = latitude.getText().trim();
                String alt = altitude.getText().trim();

                String arg[] = {lon, lat, alt, kind};
                try {
                    MicroRuntime.startAgent(name, "roamer.business.businessAgent", arg);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                dispose();
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
