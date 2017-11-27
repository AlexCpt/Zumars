package com.example.lpelle.zumars;

        import android.app.Activity;
        import android.content.Intent;
        import android.util.Log;

public class Jeu {

    private int commande; // 0 pour télécommande et 1 pour programmation
    private int modeDeJeu; // 1, 2 ou 3 pour les modes de jeu
    private String nomUtilisateur;
    private Activity activity;
    private final static String TAG = "mTag";
    private static final int MAX_NOM_UTILISATEUR = 20;

    public Jeu(int commande, int modeDeJeu, String nomUtilisateur, Activity activity) {
        this.commande = commande;
        this.modeDeJeu = modeDeJeu;
        this.nomUtilisateur = nomUtilisateur;
        this.activity = activity;
        demarrerNouvellePartie();
    }

    public static String verificationParametres(int interfaceX, int difficulte, String nomUtilisateur) {
        if (interfaceX != 0 && interfaceX != 1)
            return "Veuillez choisir une interface !";
        if (difficulte != 1 && difficulte != 2 && difficulte != 3)
            return "Veuillez choisir un mode de jeu !";
        if (nomUtilisateur.equals(""))
            return "Rentrez un nom d'utilisateur.";
        if (nomUtilisateur.length() >= MAX_NOM_UTILISATEUR)
            return "Le nom d'utilisateur est trop grand !";
        return "";
    }

    private void demarrerNouvellePartie() {
        Log.i(TAG, "Nouvelle partie");
        switch (commande) {
            case 0: // Télécommande
                Intent intentTelecommande = new Intent(activity.getBaseContext(), ActivityTelecommande.class);
                intentTelecommande.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.getBaseContext().startActivity(intentTelecommande);
                break;
            case 1: // Programmation
                Intent intentProgrammation = new Intent(activity.getBaseContext(), ActivityProgrammation.class);
                intentProgrammation.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.getBaseContext().startActivity(intentProgrammation);
                break;
        }

    }

    public int getCommande() {
        return commande;
    }

    public int getModeDeJeu() {
        return modeDeJeu;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }
}
