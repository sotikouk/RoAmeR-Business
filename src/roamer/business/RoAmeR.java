package roamer.business;

import jade.MicroBoot;

/* αρχικοποιηση του συστήματος των split container για τους πράκτορες
των επιχειρήσεων. η κλαση RoAmeR εκκινει με την φόρμα που δημιουργεί τον
πράκτορα της επιχείρησης και θέτει το ειδος της επιχείρησης και την τοποθεσία της
 */

public class RoAmeR extends MicroBoot {

    public static void main(String[] args){
        // δημιουργία του split container για τις επιχειρήσεις
        MicroBoot.main(args);
        startUI myUI = new startUI();
        myUI.showUI();
    }
}
