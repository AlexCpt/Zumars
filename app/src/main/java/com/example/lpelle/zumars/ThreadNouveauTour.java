package com.example.lpelle.zumars;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ThreadNouveauTour extends Thread implements ObservableExecution {

    private final static String TAG = "mTag";
    private ArrayList<Action> arrayListActions;
    private byte action;
    private Reseau reseau;
    private List recus = new LinkedList<String>();
    private ActivityProgrammation activityProgrammation;
    private Activity activity;
    private ArrayList<ObservateurExecution> listObservateur = new ArrayList<ObservateurExecution>();

    /***
     * Constructeur du thread principal pour l'activité programmation
     * @param arrayListActions la liste d'actions à effectuer
     * @param activityProgrammation pour garder en mémoire l'activité principale
     */
    public ThreadNouveauTour(ArrayList<Action> arrayListActions, ActivityProgrammation activityProgrammation) {
        super();
        this.arrayListActions = arrayListActions;
        this.activity=activityProgrammation;
        this.activityProgrammation = activityProgrammation;
    }

    /***
     * Constructeur du thread principal pour l'activité télécommande
     * @param arrayListActions la liste d'actions à effectuer (composée de l'action à effectuer et de "l'action de fin de programmation")
     * @param activity pour garder en mémoire l'activitée principale
     */
    public ThreadNouveauTour(ArrayList<Action> arrayListActions, ActivityTelecommande activity) {
        super();
        this.arrayListActions = arrayListActions;
        this.activity=activity;
    }

    @Override
    public void run() {
        super.run();
        reseau=new Reseau(this, activityProgrammation);    //crée le réseau
        reseau.connect();                                  //crée le thread d'envoi
        // Tant qu'il y a des actions à envoyer, il les envoie au réseau
        while (!arrayListActions.isEmpty()) {
            action=(byte)arrayListActions.get(0).getId();
            this.updateObservateurExecution(arrayListActions.get(0).getId()); // Communication activité programmation
            reseau.send(action);
            Log.i(TAG, "Action dans le flux : " + arrayListActions.get(0).getId());
            arrayListActions.remove(0);
        }
        reseau.close();   //ferme le réseau
    }

    /**
     * Cette méthode met Ã  jour la liste contenant le message renvoyé par le zumo
     * @param msg est le message renvoyÃ© par le robot
     */
    public void updateValues(final String msg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recus.add(msg);
                Log.i(TAG, "Message recu : " + msg);
            }
        });
    }

    /***
     * @return la liste des messages reçus
     */
    public List getRecus(){
        return recus;
    }


    public void addObservateurExecution(ObservateurExecution obs) {
        this.listObservateur.add(obs);
    }
    public void updateObservateurExecution(int update) {
        for (ObservateurExecution obs : listObservateur)
            obs.updateExecution(update);
    }
    public void delObservateurExecution() {
        this.listObservateur = new ArrayList<ObservateurExecution>();
    }
}
