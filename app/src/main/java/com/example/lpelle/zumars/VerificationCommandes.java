package com.example.lpelle.zumars;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;

public final class VerificationCommandes {
    private final static int  TAILLE_MAX=25;
    private static ArrayList<Action> arrayListActions;

    /**
     * Cette méthode a pour objectif de déterminer si le programme peut envoyer les actions au robot
     * ou s'il y a une erreur et l'utilisateur doit revoir l'odre de ses choix d'actions élémentaires.
     * @param listActions Arraylist contenant les actions à envoyer au robot
     * @return erreurProgrammation "" si les actions peuvent être envoyées au robot sinon envoyer le message
     * d'erreur.
     */
    public static String verificationCommandesProgrammation(ArrayList<Action> listActions) {

        String erreurProgrammation = "";

//        for(int i=0;i<listActions.size()-1;i++){
//
//            if(((listActions.get(i)).equals(Action.POUSSER_BARIL))&&((listActions.get(i+1).equals(Action.TEST_ROUTE
//            ))|| (listActions.get(i+1).equals(Action.AVANCE_PETITE)))) {
//                erreurProgrammation = "Impossible d'avancer après avoir poussé le baril";
//            }
//
//        }

//        if(listActions.get(0).equals(Action.AVANCE_PREMIER_TRAIT))
//            erreurProgrammation="Il faut tester la route avant d'avancer";

//        for(int i=1;i<listActions.size();i++){
//
//            if((listActions.get(i).equals(Action.AVANCE_PREMIER_TRAIT))&&(listActions.get(i-1).equals(Action.TEST_ROUTE)==false)){
//                erreurProgrammation="Il faut tester la route avant d'avancer";
//            }
//
//        }

        for(int i=1;i<listActions.size();i++){

            if((listActions.get(i).equals(Action.POUSSER_BARIL))&&(listActions.get(i-1).equals(Action.POUSSER_BARIL))){
                erreurProgrammation = "Le baril a déjà été poussé";
            }

        }

        if( listActions.size() == 0 ){
            erreurProgrammation="Veuillez faire glisser des actions au centre de l'écran";
        }

        if( listActions.size() >= TAILLE_MAX ){
            erreurProgrammation="Vous avez rentré trop d'actions";
        }
        return  erreurProgrammation;
    }

    /**
     * Détermine si l'action à effectuer est cohérente avec les actions anciennement réalisées par le
     * robot.
     * A chaque fois que l'utilisateur rentre à la base, les actions en mémoire sont rénitialisées.
     * L'action demi-tour n'est jamais testée.
     * @param actionATester L'action
     * @param actionEnMemoire
     * @return erreurProgrammation "" si les actions peuvent être envoyées au robot sinon envoyer le message
     */
    public static String verificationCommandesTelecommande(Action actionATester, ArrayList<Action> actionEnMemoire) {
        String erreurProgrammation = "";
//        if(actionEnMemoire != null){
//            if(actionATester.equals(Action.AVANCE_PREMIER_TRAIT) && (actionEnMemoire.equals(Action.TEST_ROUTE))== false){
//                erreurProgrammation="Il faut tester la route avant d'avancer";
//            }
//        }
//        if(actionEnMemoire != null) {
//            if (actionATester.equals(Action.AVANCE_PREMIER_TRAIT) && actionEnMemoire.equals(Action.POUSSER_BARIL)) {
//                erreurProgrammation = "Impossible d'avancer après avoir poussé le baril";
//            }
//        }
        return erreurProgrammation;
    }
}
