package com.example.lpelle.zumars;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityProgrammation extends AppCompatActivity {

    // TODO: 26/05/2016 Changer les couleurs et les fonds des menus lorsque l'on clique dessus

    private final static String TAG = "mTag";

    private ArrayList<ElementaryAction> listViewElementaryActions = new ArrayList<ElementaryAction>();
    private ElementaryAdapter adapter;
    private ArrayList<Action> arrayListActions = new ArrayList<Action>(); // Liste des actions à envoyer au Thread

    private DynamicListView listView;
    private ImageView barreMenu, barreBoutons, curseurMenu, drag1, drag2, drag3, drag4, drag5, aide, actionEnCours, imageConnection;
    private ViewGroup viewGroup; // Absolute Layout
    private Button buttonMenu, buttonAide, buttonCommencer, buttonVerif, buttonToutEnlever, buttonEnleverDernier, buttonRetour, buttonTesterConnection;
    private TextView textHeure;

    private final int POS_X_ACTION = 267;
    private final int POS_DEPART_ACTION = 50;
    private final int ESPACE_POS_ACTION = 132;
    private final int BRAS = 0;
    private final int ROUES = 1;
    private final int CAPTEUR = 2;
    private final int NONE = -1;
    private final int WRONG_ICON_ID = R.drawable.bonhomme;
    private final int BOUTON_GRIS = R.drawable.bouton_gris;
    private final int BOUTON_ROUGE = R.drawable.bouton_rouge;
    private final int BOUTON_VERT = R.drawable.bouton_vert;

    private AddressedBoolean robotIsPerforming = new AddressedBoolean(false);
    private AddressedInt actionRunning = new AddressedInt(-1);
    private AddressedBoolean readyToStart = new AddressedBoolean(false);
    private AddressedInt menuOuvert = new AddressedInt(NONE);
    private int imageActionEnCours;
    private int imageIdConnection;
    private long tempsInitConnection;
    private boolean dataChanged;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_programmation);

        createDragButtons();
        createElementaryListView();
        importViews();
        demarrerEcouteImage();
        demarrerHorloge();
    }

    public void createDragButtons() {
    }

    public void createElementaryListView() {
        listViewElementaryActions.add(new ElementaryAction(R.drawable.start));
        adapter = new ElementaryAdapter(this.getApplicationContext(), R.layout.elementary, listViewElementaryActions);

        listView = (DynamicListView) findViewById(R.id.elementaryActionListView);
        listView.setActionList(listViewElementaryActions);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    /**
     * Importes toutes les vues depuis le layout.
     */
    public void importViews() {
        viewGroup = (ViewGroup) findViewById(R.id.relative_layout_drag);
        drag1 = (ImageView) findViewById(R.id.drag1);
        drag2 = (ImageView) findViewById(R.id.drag2);
        drag3 = (ImageView) findViewById(R.id.drag3);
        drag4 = (ImageView) findViewById(R.id.drag4);
        drag5 = (ImageView) findViewById(R.id.drag5);

        aide =(ImageView)findViewById(R.id.aide);
        buttonMenu =(Button)findViewById(R.id.buttonMenu);
        buttonAide = (Button) findViewById(R.id.buttonAide);
        buttonEnleverDernier = (Button) findViewById(R.id.buttonEnleverDernier);
        buttonToutEnlever = (Button) findViewById(R.id.buttonReset);
        buttonCommencer= (Button) findViewById(R.id.buttonStart);
        buttonVerif = (Button) findViewById(R.id.buttonVerifications);
        buttonRetour = (Button) findViewById(R.id.buttonRetour);
        buttonTesterConnection = (Button) findViewById(R.id.buttonTesterConnection);

        curseurMenu = (ImageView) findViewById(R.id.curseurMenu);
        barreMenu = (ImageView) findViewById(R.id.barreMenu);
        actionEnCours = (ImageView) findViewById(R.id.actionEnCours);
        imageConnection = (ImageView) findViewById(R.id.imageConnection);

        textHeure = (TextView) findViewById(R.id.text_heure);

        drag1.setOnTouchListener(new ChoiceTouchListener(viewGroup, listView, listViewElementaryActions, adapter, arrayListActions, this, readyToStart, menuOuvert, robotIsPerforming));
        drag2.setOnTouchListener(new ChoiceTouchListener(viewGroup, listView, listViewElementaryActions, adapter, arrayListActions, this, readyToStart, menuOuvert, robotIsPerforming));
        drag3.setOnTouchListener(new ChoiceTouchListener(viewGroup, listView, listViewElementaryActions, adapter, arrayListActions, this, readyToStart, menuOuvert, robotIsPerforming));
        drag4.setOnTouchListener(new ChoiceTouchListener(viewGroup, listView, listViewElementaryActions, adapter, arrayListActions, this, readyToStart, menuOuvert, robotIsPerforming));
        drag5.setOnTouchListener(new ChoiceTouchListener(viewGroup, listView, listViewElementaryActions, adapter, arrayListActions, this, readyToStart, menuOuvert, robotIsPerforming));

        dragRepositioning();
        setBarreMenuVisibility(View.INVISIBLE);
        imageActionEnCours = WRONG_ICON_ID;
        imageIdConnection = BOUTON_GRIS;
        tempsInitConnection = System.currentTimeMillis();
        buttonRetour.setEnabled(false);
        dataChanged = false;
    }


    /**
     * Envoie les actions à réaliser au robot vie le ThreadNouveauTour.
     */
    private void lancerNouveauTour() {
        // Arraylist
        arrayListActions.add(Action.FIN_PROGRAMMATION);

        // Lancement du nouveau Thread
        ThreadNouveauTour threadNouveauTour = new ThreadNouveauTour(this.arrayListActions, this);
        robotIsPerforming.b = true;
//        Log.i(TAG, "Start");
        threadNouveauTour.addObservateurExecution(new ObservateurExecution() {
            @Override
            public void updateExecution(int update) {
//                Log.i(TAG, "Mise à jour");
            }
        });
        threadNouveauTour.start();
    }

    /**
     * Tester la connection avec l'arduino
     * @param view le bouton tester connection
     */
   public void boutonTesterConnection(View view) {
       ArrayList<Action> arrayListTestConnection = new ArrayList<Action>();
       arrayListTestConnection.add(Action.TEST_CONNECTION);
       arrayListTestConnection.add(Action.FIN_PROGRAMMATION);
       imageIdConnection = BOUTON_ROUGE;
       tempsInitConnection = System.currentTimeMillis();
       ThreadNouveauTour threadNouveauTour = new ThreadNouveauTour(arrayListTestConnection, this);
       threadNouveauTour.start();
   }

    /**
     * Méthode appelée lorsque l'utilisateur souhaite envoyer les actions au robot
     * @param view le bouton start
     */
    public void boutonCommencerTour(View view) {
        // TODO: Régler le problème de la double vérification

        if (readyToStart.b) {
            String erreurProgrammation = VerificationCommandes.verificationCommandesProgrammation(this.arrayListActions);
            if (erreurProgrammation.equals("")) {
                lancerNouveauTour();
            } else {
                // Afficher le message erreurProgrammation
                AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
                myAlert.setMessage(erreurProgrammation);
                myAlert.show();
            }
        } else {
            AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
            myAlert.setMessage("Vous ne pouvez pas lancer un nouveau tour, votre programmation n'est pas terminée. Veuillez cliquer sur Vérifications avant d'envoyer les informations au robot.");
            myAlert.show();
        }
    }


    /**
     * @param view le bouton de vérifications
     */
    public void boutonVerifications(View view) {
        //TODO: Verfication de la list d'action

        if (!readyToStart.b) {
            // Mise à jour de l'arrayList
        affichageArrayList(arrayListActions); // Affichage ancienne

            String erreurProgrammation = VerificationCommandes.verificationCommandesProgrammation(this.arrayListActions);
            //robotIsPerforming.b = true;

            if (erreurProgrammation.equals("")) {
                // Ajouter carré de la fin
                listViewElementaryActions.add(new ElementaryAction(R.drawable.finish));

                adapter.notifyDataSetChanged();
                readyToStart.b = true;
            } else {
                Toast.makeText(getApplicationContext(), erreurProgrammation, Toast.LENGTH_SHORT).show();
            }
        } else {
            AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
            myAlert.setMessage("Vous avez déjà vérifié les commandes.");
            myAlert.show();
        }
    }

    /**
     * Enlever le dernier élément.
     * @param view
     */
    public void boutonEnleverDernierClick(View view){
        // Si la vérification est déjà faite
        if (readyToStart.b) {
            listViewElementaryActions.remove(listViewElementaryActions.size()-1); // Enlever de carré rouge
            readyToStart.b = false;
        }
        if (!listViewElementaryActions.isEmpty() && !arrayListActions.isEmpty()) {
            listViewElementaryActions.remove(listViewElementaryActions.size()-1); // Enlever de carré rouge
            arrayListActions.remove(arrayListActions.size() - 1);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Enlever tous les éléments.
     * @param view
     */
    public void boutonToutEnleverClick(View view) {
        readyToStart.b = false;
        listViewElementaryActions.clear();
        listViewElementaryActions.add(new ElementaryAction(R.drawable.start));
        arrayListActions.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     * Afficher l'aide
     * @param view
     */
    public void boutonAideClick(View view) {
        aide.setVisibility(View.VISIBLE);
        buttonRetour.setVisibility(View.VISIBLE);
        buttonEnleverDernier.setEnabled(false);
        buttonMenu.setEnabled(false);
        buttonAide.setEnabled(false);
        buttonVerif.setEnabled(false);
        buttonCommencer.setEnabled(false);
        buttonToutEnlever.setEnabled(false);
        buttonTesterConnection.setEnabled(false);
        buttonRetour.setEnabled(true);
    }

    /**
     * Sort du menu aide pour revenir vers la programmation
     * @param view
     */
    public void boutonRetourClick(View view) {
        aide.setVisibility(View.INVISIBLE);
        buttonRetour.setVisibility(View.INVISIBLE);
        buttonEnleverDernier.setEnabled(true);
        buttonMenu.setEnabled(true);
        buttonAide.setEnabled(true);
        buttonVerif.setEnabled(true);
        buttonCommencer.setEnabled(true);
        buttonToutEnlever.setEnabled(true);
        buttonTesterConnection.setEnabled(false);
        buttonRetour.setEnabled(false);
    }

    /**
     * Retourne vers le menu principal
     * @param view
     */
    public void boutonMenuClick(View view) {
        Intent intent = new Intent(ActivityProgrammation.this, ActivityAccueil.class);
        startActivity(intent);
    }

    /**
     * Méthode appelée lorsque l'utilisateur clique sur le bouton bras.
     * @param view
     */
    public void commandeBrasClick(View view) {
        if (menuOuvert.i != BRAS) {
            // Afficher le menu relatif au bras
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) curseurMenu.getLayoutParams();
            lp.setMargins(0, 130, 0, 0);
            curseurMenu.setLayoutParams(lp);
            dragRepositioning();

            // Changer les couleurs des menus
            setActionColor(BRAS);
            setBarreMenuVisibility(View.VISIBLE);
            dragChangeIcon(BRAS);
//            Log.i(TAG, "bras");
            this.menuOuvert.i = BRAS;
        } else {
            // Ne rien afficher
            setBarreMenuVisibility(View.INVISIBLE);
            this.menuOuvert.i = NONE;
        }

    }

    /**
     * Méthode appelée lorsque l'utilisateur clique sur le bouton roues.
     * @param view
     */
    public void commandeRouesClick(View view) {
        if (menuOuvert.i != ROUES) {
            // Afficher le menu relatif aux roues
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) curseurMenu.getLayoutParams();
            lp.setMargins(0, 350, 0, 0);
            curseurMenu.setLayoutParams(lp);
            dragRepositioning();

            // Changer les couleurs des menus
            setActionColor(ROUES);
            setBarreMenuVisibility(View.VISIBLE);
            dragChangeIcon(ROUES);
//            Log.i(TAG, "roues");
            this.menuOuvert.i = ROUES;
        } else {
            // Ne rien afficher
            setBarreMenuVisibility(View.INVISIBLE);
            this.menuOuvert.i = NONE;
        }
    }

    /**
     * Méthode appelée lorsque l'utilisateur clique sur le bouton capteurs.
     * @param view
     */
    public void commandeCapteursClick(View view) {
        if (menuOuvert.i != CAPTEUR) {
            // Afficher le menu relatif aux capteurs
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) curseurMenu.getLayoutParams();
            lp.setMargins(0, 600, 0, 0);
            curseurMenu.setLayoutParams(lp);
            dragRepositioning();

            // Changer les couleurs des menus
            setActionColor(CAPTEUR);
            setBarreMenuVisibility(View.VISIBLE);
            dragChangeIcon(CAPTEUR);
            this.menuOuvert.i = CAPTEUR;

        } else {
            // Ne rien afficher
            setBarreMenuVisibility(View.INVISIBLE);
            this.menuOuvert.i = NONE;
        }
    }

    /**
     * Rend la barre de menu, le curseur et les actions élémentaires visibles ou invisibles.
     * @param visibility Visibilité des composants
     */
    public void setBarreMenuVisibility(int visibility) {
        if (visibility != View.INVISIBLE && visibility != View.VISIBLE) {
            visibility = View.INVISIBLE;
        }
        curseurMenu.setVisibility(visibility);
        barreMenu.setVisibility(visibility);
        drag1.setVisibility(visibility);
        drag2.setVisibility(visibility);
        drag3.setVisibility(visibility);
        drag4.setVisibility(visibility);
        drag5.setVisibility(visibility);
    }

    /**
     * Change le thème de la barre des menus et du curseur.
     * @param actionColor La couleur du thème
     */
    public void setActionColor(int actionColor) {
        switch (actionColor) {
            case BRAS:
                curseurMenu.setImageResource(R.drawable.curseur_bras);
                barreMenu.setImageResource(R.drawable.menu_bras);
                break;
            case ROUES:
                curseurMenu.setImageResource(R.drawable.curseur_roues);
                barreMenu.setImageResource(R.drawable.menu_roues);
                break;
            case CAPTEUR:
                curseurMenu.setImageResource(R.drawable.curseur_capteur);
                barreMenu.setImageResource(R.drawable.menu_capteurs);
                break;
        }
    }

    /**
     * Repositionne toutes les actions élémentaires.
     */
    public void dragRepositioning() {
        drag1.setX(POS_X_ACTION);
        drag1.setY(POS_DEPART_ACTION);
        drag2.setX(POS_X_ACTION);
        drag2.setY(POS_DEPART_ACTION + ESPACE_POS_ACTION);
        drag3.setX(POS_X_ACTION);
        drag3.setY(POS_DEPART_ACTION + 2 * ESPACE_POS_ACTION);
        drag4.setX(POS_X_ACTION);
        drag4.setY(POS_DEPART_ACTION + 3 * ESPACE_POS_ACTION);
        drag5.setX(POS_X_ACTION);
        drag5.setY(POS_DEPART_ACTION + 4 * ESPACE_POS_ACTION);
    }

    public void dragChangeIcon(int typeIcon) {
        drag1.setImageResource(ActionIcon.DRAG_1.getSrc(typeIcon));
        drag2.setImageResource(ActionIcon.DRAG_2.getSrc(typeIcon));
        drag3.setImageResource(ActionIcon.DRAG_3.getSrc(typeIcon));
        drag4.setImageResource(ActionIcon.DRAG_4.getSrc(typeIcon));
        drag5.setImageResource(ActionIcon.DRAG_5.getSrc(typeIcon));

        if (ActionIcon.DRAG_1.getSrc(typeIcon) == WRONG_ICON_ID)
            drag1.setVisibility(View.INVISIBLE);
        if (ActionIcon.DRAG_2.getSrc(typeIcon) == WRONG_ICON_ID)
            drag2.setVisibility(View.INVISIBLE);
        if (ActionIcon.DRAG_3.getSrc(typeIcon) == WRONG_ICON_ID)
            drag3.setVisibility(View.INVISIBLE);
        if (ActionIcon.DRAG_4.getSrc(typeIcon) == WRONG_ICON_ID)
            drag4.setVisibility(View.INVISIBLE);
        if (ActionIcon.DRAG_5.getSrc(typeIcon) == WRONG_ICON_ID)
            drag5.setVisibility(View.INVISIBLE);
    }

    public void affichageArrayList(ArrayList arrayList) {
        Log.i(TAG, "================================");
        Log.i(TAG, "Affichage arrayList :");
        for (int i = 0; i < arrayList.size(); i++) {
            Log.i(TAG, "Element "+ i + " = "+ arrayList.get(i).toString());
        }
        Log.i(TAG, "================================");
    }

    public void demarrerEcouteImage() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable(){
                        public void run() {
                            if (imageActionEnCours == WRONG_ICON_ID)
                                actionEnCours.setVisibility(View.INVISIBLE);
                            else {
                                actionEnCours.setVisibility(View.VISIBLE);
                                actionEnCours.setImageResource(imageActionEnCours);
                            }
                            if ((System.currentTimeMillis() - tempsInitConnection) > 10000)
                                imageIdConnection = BOUTON_GRIS;
                            imageConnection.setImageResource(imageIdConnection);
                            if (dataChanged) {
                                dataChanged = false;
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        };
        Thread threadTraitementInformationZumo = new Thread(runnable);
        threadTraitementInformationZumo.start();
    }

    public void traiterInformationReseau(int valeur) {
//        Log.i(TAG, "Reception : "+ valeur);
        int imageRessource = R.drawable.bonhomme;
        switch (valeur) {
            case 1: // Fin programmation
                if (robotIsPerforming.b) {
                    listViewElementaryActions.clear();
                    listViewElementaryActions.add(new ElementaryAction(R.drawable.start));
                    robotIsPerforming.b = false;
                    readyToStart.b = false;
                    dataChanged = true;
                }
                break;
            case 2: // Test connection
                imageIdConnection = BOUTON_VERT;
                break;
            default:
                imageRessource = Action.getImageIcon(valeur);
                break;
        }
        this.imageActionEnCours = imageRessource;
    }


    public void demarrerHorloge() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                final long timeStart = System.currentTimeMillis();
                while (true) {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable(){
                        public void run() {
                            changeHour(convertirHeure((System.currentTimeMillis() - timeStart)/1000));
                        }
                    });
                }
            }
        };
        Thread threadHorloge = new Thread(runnable);
        threadHorloge.start();
    }


    public String convertirHeure(long heure) {
        String string = "";
        long minutes = heure/60;
        long secondes = heure%60;

        if (minutes < 10) {
            string += "0";
        }
        string += minutes;
        string += ":";
        if (secondes < 10) {
            string += "0";
        }
        string += secondes;
        return string;
    }

    public void changeHour(String time) {
        textHeure.setText(time);
    }
}
