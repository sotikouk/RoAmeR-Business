package roamer.business;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.awt.*;
import java.util.Hashtable;

public class businessAgent extends Agent {
    public businessUI myUI;
    // Ο πίνακας με τα προιόντα της επιχείρησης
    private String[] catalogue;
    // Το είδος της επιχείρησης
    private String businessKind;
    // Ο κατάλογος των προσφορών
    private Hashtable offers;
    // το UI στο οποίο ο επιχειρηματίας μπορεί να εισάγει
    // τα προιόντα της επιχείρησης του και τις προσφορές
    private String businessName;
    private Location location;

    protected void setup() {

        myUI = new businessUI(this);
        myUI.showGui();

// todo να ορίσω τα behaviors του πράκτορα
    }

    // Ενημέρωση του καταλόγου των προιόντων
    public void updateCatalogue(final String sellItem) {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {

                int i = catalogue.length;
                i++;
                catalogue[i] = sellItem;
            }
        });
    }

    // Ενημέρωση του καταλόγου των προιόντων

    // Put agent clean-up operations here
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        // Close the GUI
        myUI.dispose();
        // Printout a dismissal message
        System.out.println("Business-agent " + getAID().getName() + " terminating.");
    }

// ορισμός του αντικειμένου location
    public static class Location {
        private String name;
        private double longitude;
        private double latitude;
        private double altitude;

        // create and initialize a point with given name and
        // (latitude, longitude) specified in degrees
        public Location(double latitude, double longitude, double altitude) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.altitude = altitude;
        }

    // todo να ορίσω την μέθοδο που περνάει τις συντεταγμένες στον πράκτορα της επιχείρησης
    }

}
