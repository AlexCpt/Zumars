package com.example.lpelle.zumars;

/**
 * Created by abroussell on 18/05/2016.
 */

import android.content.Intent;
import android.util.Log;
import android.view.ViewDebug;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

class ThreadEnvoi extends Thread{  //implements Runnable

    private final static String TAG = "mTag";
    OutputStream out =null;
    LinkedBlockingQueue<Byte> flux;
    private Socket socket;
    private static final int SERVERPORT = 5555;
    private static final String SERVER_IP = "192.168.240.1";
    InetAddress serverAddr;
    byte commande;
    private ThreadNouveauTour threadTour;
    private ThreadReponse threadReception;
    private boolean finProgrammation;
    private ActivityProgrammation activityProgrammation;

    /***
     * Constructeur du thread d'envoi
     * @param LBQ flux liant le réseau au thread d'envoi
     * @param threadTour  pour garder en mémoire le thread principal
     * @param activityProgrammation  pour garder en mémoire l'activité
     */
    public ThreadEnvoi(LinkedBlockingQueue LBQ, ThreadNouveauTour threadTour, ActivityProgrammation activityProgrammation) {
        flux = LBQ;
        this.threadTour = threadTour;
        this.activityProgrammation = activityProgrammation;
        start();
    }

    @Override
    public void run() {
        try {
            serverAddr = InetAddress.getByName(SERVER_IP);              //Transformation de l'IP si elle eest sous la forme "y001.insa..."
            socket = new Socket(serverAddr, SERVERPORT);                //création du socket
            out = socket.getOutputStream();
            threadReception=new ThreadReponse(socket,threadTour);      // création du thread réponse
            threadReception.start();
            commande = flux.take();                         //Prends en mémoire la première action du flux
            Log.i(TAG, "Envoi : " + (int)commande);
<<<<<<< HEAD
            communicationProgrammation((int) commande);
            out.write(commande);
=======
            out.write(commande);                            //Envoie l'action en mémoire
>>>>>>> origin/master
            finProgrammation=false;
            Thread.sleep(200);      //Fais une pause pour laisser le temps au thread réponse de répondre
            while (!finProgrammation) {
<<<<<<< HEAD
                while(threadTour.getRecus().size() == 0)
                    Thread.sleep(10);
                if (threadTour.getRecus().get(threadTour.getRecus().size() - 1).toString().contains(Integer.toString((int) commande))){
                    communicationProgrammation(Integer.parseInt(threadTour.getRecus().get(threadTour.getRecus().size() - 1).toString()));
                        Log.i(TAG, "Action suivante");
                        commande = flux.take(); //Prise en mémoire de l'action suivante
                        communicationProgrammation((int) commande);
                        if((int)commande == Action.FIN_PROGRAMMATION.getId()){
                            Log.i(TAG, "finProg");
                            finProgrammation=true;
                            threadReception.finEnvoi();
                            socket.close();
                            Log.i(TAG, "Fermeture de la connection client");
                        }else{
                            out.write(commande);
                            Log.i(TAG, "Envoi : " + (int) commande);
                        }
=======
                //Si le dernier message reçu contient la dernière action envoyé on envoit l'action suivante sauf si elle correspond à la fin programmation où on sort de la boucle et on coupe le thread de réception.
                if (threadTour.getRecus().get(threadTour.getRecus().size() - 1).toString().contains(Integer.toString((int) commande))){
                    communicationProgrammation(Integer.parseInt(threadTour.getRecus().get(threadTour.getRecus().size() - 1).toString()));
                    Log.i(TAG, "Action suivante");
                    commande = flux.take();
                    communicationProgrammation((int) commande);
                    if((int)commande == Action.FIN_PROGRAMMATION.getId()){
                        Log.i(TAG, "finProg");
                        finProgrammation=true;
                        threadReception.finEnvoi();
                        socket.close();
                        Log.i(TAG, "Fermeture de la connection client");
                    }else{
                        out.write(commande);
                        Log.i(TAG, "Envoi : " + (int) commande);
                    }
>>>>>>> origin/master

                }
            }
        } catch(InterruptedException e1) {
            e1.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        //Mise à null de tous les attributs pour libérer les ressources
        out=null;
        flux=null;
        socket=null;
        serverAddr=null;
        threadTour=null;
    }

    /***
     * Transmettre des information à l'activité pour qu'elle les transmette à l'utilisateur.
     * @param value
     */
    public void communicationProgrammation(int value) {
        if (activityProgrammation != null) {
            activityProgrammation.traiterInformationReseau(value);
        }
    }
}
