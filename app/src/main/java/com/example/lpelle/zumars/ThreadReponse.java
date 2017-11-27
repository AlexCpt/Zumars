package com.example.lpelle.zumars;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadReponse extends Thread {

    private final static String TAG = "mTag";
    BufferedReader in=null;
    private Socket socket;
    private ThreadNouveauTour threadTour;
    private boolean finProgrammation;


    /***
     * Constructeur du thread réponse
     * @param socket  pour garder en mémoire le socket
     * @param threadTour  pour garder en mémoire le thread principal
     */
    public ThreadReponse(Socket socket, ThreadNouveauTour threadTour){
        this.threadTour=threadTour;
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch(IOException e2) {
            e2.printStackTrace();
        }
    }

    public void run() {
        String messageFin = null;
        finProgrammation=false;
        try {
            while(!finProgrammation) {
                messageFin = in.readLine();      //réception des informations venant de l'arduino
                if(messageFin!=null){
//                    Log.i(TAG,"Message fin: "+messageFin);
                    threadTour.updateValues(messageFin);         //Envoi de ces informations au Thread du tour
                }
            }
        } catch(IOException e2) {
            e2.printStackTrace();
        }
    }

    /***
     * Appelée dans le thread d'envoi lorsque la programmation est terminée
     */
    public void finEnvoi(){
        finProgrammation=true;
    }
}
