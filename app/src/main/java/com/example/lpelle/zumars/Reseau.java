package com.example.lpelle.zumars;


import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by abroussell on 18/05/2016.
 */


public class Reseau {


    private final static String TAG = "mTag";
    LinkedBlockingQueue<Byte> flux = new LinkedBlockingQueue();
    ThreadNouveauTour threadTour;
    ThreadEnvoi threadEmission;
    ActivityProgrammation activityProgrammation;

    /***
     * Constructeur du reseau
     * @param threadTour pour garder en mémoire le thread principal
     * @param activityProgrammation pour garder en mémoire l'activité
     */
    public Reseau(ThreadNouveauTour threadTour, ActivityProgrammation activityProgrammation){
        this.threadTour=threadTour;
        this.activityProgrammation = activityProgrammation;
    }

    /***
     * crée le thread d'envoi
     */
    public void connect(){      //crée le thread qui envoit l'info au zumo
        threadEmission=new ThreadEnvoi(flux, threadTour, activityProgrammation);
        Log.i(TAG, "===============================");
        Log.i(TAG, "Connexion du réseau");
    }

    /***
     * ajoute au flux liant le réseau au thread d'envoi l'action à effectuer
     * @param action : le code de l'action à effectuer en byte
     */
    public void send(byte action){
        try{
            flux.put(action);          //Envoi de l'action dans le flux
        }catch (InterruptedException e1){
            e1.printStackTrace();
        }
    }

    /***
     * ferme le réseau
     */
    public void close() {
        threadTour=null;
        threadEmission=null;
        flux=null;
    }
}
