package roamer.business;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.introspection.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class businessAgent extends Agent {

    String[] coordinates;
    public businessUI myUI;
    // Ο πίνακας με τα προιόντα της επιχείρησης
    private String[] catalogue;
    // Το είδος της επιχείρησης
    private String businessKind;
    // Ο κατάλογος των προσφορών
    private HashMap<String, String> offers = new HashMap<String, String>();
    // το UI στο οποίο ο επιχειρηματίας μπορεί να εισάγει
    // τα προιόντα της επιχείρησης του και τις προσφορές
    private String businessName;
    // οι πρακτορες των πελατων
    private AID[] clientAgents;

    protected void setup() {

        myUI = new businessUI(this);
        myUI.showGui();

        //Ο πίνακας coordinates[] περιέχει τις συντεταγμένες της επιχείρησης οι οποίες
        //περάστικαν ως όρισμα απο την κλάση startUI.java κατα την δημιουργία του πράκτορα

        coordinates = (String[]) getArguments();

        businessName = getAID().getName();

        System.out.println("business agent "+businessName+" has coordinates: lon "+coordinates[0]+", lat "+coordinates[1]
                +", alt "+coordinates[2]+" it's kind of business is "+coordinates[3]);
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Roamer-Retail business");
        sd.setName("Roamer-Business");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        AMSSubscriber myAMSSubscriber = new AMSSubscriber() {
            protected void installHandlers(Map handlers) {
                // Σύνδεση ενός χειριστή με τα γεγονότα δημιουργίας πράκτορα born-agent
                EventHandler creationsHandler = new EventHandler() {
                    public void handle(Event ev) {
                        BornAgent ba = (BornAgent) ev;
                        System.out.println("Born agent "+ba.getAgent().getName());
                        openBusiness(ba);
                    }
                };
                handlers.put(IntrospectionVocabulary.BORNAGENT, creationsHandler);
            }
        };
        addBehaviour(myAMSSubscriber);
      //  openBusiness();
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

    public void insertOffer(final String offer, final String price) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                offers.put(offer, price);
                System.out.println(offer+" inserted into offers catalogue. Price = "+price);
                for (int i=0; i<offers.size(); i++)
                    System.out.println("Valid offers are : "+ offers);
                System.out.println("Trying to inform clients for "+offer+" at the price "+price);

                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("Roamer-client2b");
                template.addServices(sd);
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    System.out.println("Found the following client agents:");
                    clientAgents = new AID[result.length];
                    for (int i = 0; i < result.length; ++i) {
                        clientAgents[i] = result[i].getName();
                        System.out.println(clientAgents[i].getName());
                    }
                }
                catch (FIPAException fe) {
                    fe.printStackTrace();
                }

                ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                for (int i = 0; i < clientAgents.length; ++i) {
                    cfp.addReceiver(clientAgents[i]);
                }
                cfp.setContent(offer+" "+price);
                cfp.setConversationId("offer-inform");
                myAgent.send(cfp);
            }
        } );
    }

    public void deleteOffer(final String offer, final String price) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                offers.remove(offer, price);
                System.out.println(offer+" deleted from offers catalogue");
                for (int i=0; i<offers.size(); i++)
                    System.out.println("Valid offers are : "+ offers);
            }
        } );
    }

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

    private void openBusiness(BornAgent ba) {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                DFAgentDescription template;
                template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("Roamer-client2b");
                template.addServices(sd);
             /*   try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    System.out.println("Found the following client agents:");
                    clientAgents = new AID[result.length];
                    for (int i = 0; i < result.length; ++i) {
                        clientAgents[i] = result[i].getName();
                        System.out.println(clientAgents[i].getName());
                    }
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                }
*/
                ACLMessage cfp = new ACLMessage(ACLMessage.INFORM);
               // for (int i = 0; i < clientAgents.length; ++i) {
                    cfp.addReceiver(ba.getAgent());
                //}
                cfp.setContent(coordinates[0] + ":" + coordinates[1] + ":" + coordinates[2] + ":" + coordinates[3]);
                //cfp.setConversationId("business-inform");
                myAgent.send(cfp);
            }
        });
    }
}
