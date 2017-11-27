package com.example.lpelle.zumars;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Rect;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityTelecommande extends AppCompatActivity {

    private final static String TAG = "mTag";
    private ImageButton buttonDroite;
    private ImageButton buttonGauche;
    private ImageButton buttonDoubleTrait;
    private ImageButton buttonSimpleTrait;
    private ImageButton buttonMagnetique;
    private ImageButton buttonDemiTour;
    private ImageButton buttonAppuiSwitch;
    private ImageButton buttonSwitchDroit;
    private ImageButton buttonSwitchGauche;
    private ImageButton buttonPousserBaril;
    private ImageButton buttonReculer;
    private ImageButton buttonTestRoute;
    private ImageButton buttonPetiteAvance;
    private Button buttonAide;
    private Button buttonMenu;
    private Button buttonRetour;
    private SeekBar levierVitesse;
    private ImageView backgroundLevier, aide;
    private boolean rapide;
    private AddressedInt actionRunning = new AddressedInt(-1);
    private ArrayList<Action> arrayListActions = new ArrayList<Action>(); // Liste des actions à envoyer au Thread
    private ArrayList<Action> arrayListMemoireAction = new ArrayList<Action>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telecommande);

        importViews();
        rapide = false;

        buttonRetour.setEnabled(false);

        // Listeners
        buttonDroite.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonDroite.setImageAlpha(128);
                    if (verificationDisponibiliteRobot()) {
                        arrayListActions.add(Action.TOURNER_DROITE);
                        sendToZumo();
                    } else {
                        messageIndisponibilite();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonDroite.setImageAlpha(255);
                }
                return true;
            }


        });

        buttonGauche.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonGauche.setImageAlpha(128);
                    if (verificationDisponibiliteRobot()) {
                        arrayListActions.add(Action.TOURNER_GAUCHE);
                        sendToZumo();
                    } else {
                        messageIndisponibilite();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonGauche.setImageAlpha(255);
                }
                return true;
            }


        });

        buttonSimpleTrait.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonSimpleTrait.setImageAlpha(128);
                    if (verificationDisponibiliteRobot()) {
                        arrayListActions.add(Action.AVANCE_PREMIER_TRAIT);
                        sendToZumo();
                    } else {
                        messageIndisponibilite();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonSimpleTrait.setImageAlpha(255);
                }
                return true;
            }


        });

        buttonMagnetique.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonMagnetique.setImageAlpha(128);
                    if (verificationDisponibiliteRobot()) {
                        arrayListActions.add(Action.TEST_BARIL);
                        sendToZumo();
                    } else {
                        messageIndisponibilite();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonMagnetique.setImageAlpha(255);
                }
                return true;
            }


        });

        buttonDemiTour.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonDemiTour.setImageAlpha(128);
                    if (verificationDisponibiliteRobot()) {
                        arrayListActions.add(Action.DEMI_TOUR);
                        sendToZumo();
                    } else {
                        messageIndisponibilite();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonDemiTour.setImageAlpha(255);
                }
                return true;
            }


        });

        buttonPousserBaril.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonPousserBaril.setImageAlpha(128);
                    if (verificationDisponibiliteRobot()) {
                        arrayListActions.add(Action.POUSSER_BARIL);
                        sendToZumo();
                    } else {
                        messageIndisponibilite();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonPousserBaril.setImageAlpha(255);
                }
                return true;
            }


        });

        buttonAppuiSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonAppuiSwitch.setImageAlpha(128);
                    if (verificationDisponibiliteRobot()) {
                        arrayListActions.add(Action.APPUI_SWITCH);
                        sendToZumo();
                    } else {
                        messageIndisponibilite();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonAppuiSwitch.setImageAlpha(255);
                }
                return true;
            }


        });

        buttonSwitchDroit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonSwitchDroit.setImageAlpha(128);
                    if (verificationDisponibiliteRobot()) {
                        arrayListActions.add(Action.TOURNER_SWITCH_DROITE);
                        sendToZumo();
                    } else {
                        messageIndisponibilite();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonSwitchDroit.setImageAlpha(255);
                }
                return true;
            }


        });

        buttonSwitchGauche.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonSwitchGauche.setImageAlpha(128);
                    if (verificationDisponibiliteRobot()) {
                        arrayListActions.add(Action.TOURNER_SWITCH_GAUCHE);
                        sendToZumo();
                    } else {
                        messageIndisponibilite();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonSwitchGauche.setImageAlpha(255);
                }
                return true;
            }
        });

        buttonTestRoute.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonTestRoute.setImageAlpha(128);
                    if (verificationDisponibiliteRobot()) {
                        arrayListActions.add(Action.TEST_ROUTE);
                        sendToZumo();
                    } else {
                        messageIndisponibilite();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonTestRoute.setImageAlpha(255);
                }
                return true;
            }
        });

        buttonReculer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonReculer.setImageAlpha(128);
                    if (verificationDisponibiliteRobot()) {
                        arrayListActions.add(Action.RECULER);
                        sendToZumo();
                    } else {
                        messageIndisponibilite();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonReculer.setImageAlpha(255);
                }
                return true;
            }
        });

        buttonPetiteAvance.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonPetiteAvance.setImageAlpha(128);
                    if (verificationDisponibiliteRobot()) {
                        arrayListActions.add(Action.AVANCE_PETITE);
                        sendToZumo();
                    } else {
                        messageIndisponibilite();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonPetiteAvance.setImageAlpha(255);
                }
                return true;
            }
        });


        levierVitesse.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (rapide == false && progress > 50) {
                    rapide = true;
                }
                if (rapide == true && progress < 50) {
                    rapide = false;
                }

            }
        });
        buttonAide.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                aide.setVisibility(View.VISIBLE);
                buttonRetour.setVisibility(View.VISIBLE);
                buttonRetour.setEnabled(true);
                buttonAide.setEnabled(false);
                buttonAppuiSwitch.setEnabled(false);
                buttonDemiTour.setEnabled(false);
                buttonDroite.setEnabled(false);
                buttonGauche.setEnabled(false);
                buttonMagnetique.setEnabled(false);
                buttonMagnetique.setEnabled(false);
                buttonMenu.setEnabled(false);
                buttonPousserBaril.setEnabled(false);
                buttonSimpleTrait.setEnabled(false);
                buttonSwitchDroit.setEnabled(false);
                buttonSwitchGauche.setEnabled(false);
                buttonPetiteAvance.setEnabled(false);
                buttonReculer.setEnabled(false);
                buttonTestRoute.setEnabled(false);
            }

        });
        buttonRetour.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                aide.setVisibility(View.INVISIBLE);
                buttonRetour.setVisibility(View.INVISIBLE);
                buttonAide.setEnabled(true);
                buttonAppuiSwitch.setEnabled(true);
                buttonDemiTour.setEnabled(true);
                buttonDroite.setEnabled(true);
                buttonGauche.setEnabled(true);
                buttonMagnetique.setEnabled(true);
                buttonMagnetique.setEnabled(true);
                buttonMenu.setEnabled(true);
                buttonPousserBaril.setEnabled(true);
                buttonSimpleTrait.setEnabled(true);
                buttonSwitchDroit.setEnabled(true);
                buttonSwitchGauche.setEnabled(true);
                buttonPetiteAvance.setEnabled(true);
                buttonReculer.setEnabled(true);
                buttonTestRoute.setEnabled(true);
                buttonRetour.setEnabled(false);
            }

        });


        buttonMenu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ActivityTelecommande.this, ActivityAccueil.class);
                startActivity(intent);
            }

        });

    }

    public void importViews() {
        buttonDroite = (ImageButton) findViewById(R.id.droite);
        buttonGauche = (ImageButton) findViewById(R.id.gauche);
        aide =(ImageView)findViewById(R.id.aide);
        buttonSimpleTrait = (ImageButton) findViewById(R.id.simpleTrait);
        buttonMagnetique = (ImageButton) findViewById(R.id.magnetique);
        buttonDemiTour = (ImageButton) findViewById(R.id.demiTour);
        buttonPousserBaril = (ImageButton) findViewById(R.id.pousser_baril);
        buttonAppuiSwitch = (ImageButton) findViewById(R.id.appuiSwitch);
        buttonSwitchDroit = (ImageButton) findViewById(R.id.switchDroit);
        buttonSwitchGauche = (ImageButton) findViewById(R.id.switchGauche);
        buttonPetiteAvance = (ImageButton) findViewById(R.id.petiteAvance);
        buttonReculer = (ImageButton) findViewById(R.id.reculer);
        buttonTestRoute = (ImageButton) findViewById(R.id.testRoute);

        buttonAide = (Button) findViewById(R.id.buttonAide);
        buttonMenu = (Button) findViewById(R.id.buttonMenu);
        buttonRetour = (Button) findViewById(R.id.buttonRetour);

        backgroundLevier = (ImageView) findViewById(R.id.backgroudLevier);
        levierVitesse = (SeekBar) findViewById(R.id.seekBar);
    }

    public void seekBarClick(View view) {}


    /**
     * Verifie que le robot n'est pas en train de réaliser une action
     * @return true si les boutons sont disponibles
     */
    public boolean verificationDisponibiliteRobot() {
        if (actionRunning.i == -1)
            return true;
        else
            return false;
    }

    /**
     * Envoie les informations au zumo
     */
    public void sendToZumo() {
        // Verification compatibilité action!
        if (VerificationCommandes.verificationCommandesTelecommande(arrayListActions.get(0), arrayListMemoireAction).equals("")) {
            // Nouvelle mémoire -- La première et unique action dans l'arraylist est l'action à envoyer
            if (arrayListActions.get(0) == Action.DEMI_TOUR) {
                arrayListMemoireAction.clear();
            } else {
                arrayListMemoireAction.add(arrayListActions.get(0));
            }
            arrayListActions.add(Action.FIN_PROGRAMMATION);
            ThreadNouveauTour threadNouveauTour = new ThreadNouveauTour(arrayListActions, this);
            threadNouveauTour.addObservateurExecution(new ObservateurExecution() {
                @Override
                public void updateExecution(int update) { // Quand l'information est communiquée au réseau
//                    Log.i(TAG, "Mise à jour");
                }
            });
            threadNouveauTour.start();
//        int i = 0;
//        while (i != arrayListActions.size()) {
//            Log.i(TAG, "Action : " + arrayListActions.get(i));
//            i++;
//        }
        } else {
            AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
            myAlert.setMessage(VerificationCommandes.verificationCommandesTelecommande(arrayListActions.get(0), arrayListMemoireAction));
            myAlert.show();
        }
    }

    public void messageIndisponibilite() {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("Attendez que le Zumo ait terminé !");
        myAlert.show();
    }

}