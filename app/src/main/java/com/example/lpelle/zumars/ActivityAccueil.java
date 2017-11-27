package com.example.lpelle.zumars;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityAccueil extends AppCompatActivity {

    private static final String TAG = "mTag";
    private Button boutonDemarrer;
    private EditText editTextNomUtilisateur;
    private Spinner modeDeJeuSpinner, difficulteSpinner;

    private int diff; // Difficulté de jeu
    private int mode; // Mode de jeu (télécommande ou programmation)

    private final int FACILE = 1;
    private final int MOYEN = 2;
    private final int DIFFICILE = 3;
    private final int INTERFACE_TELECOMMANDE = 0;
    private final int INTERFACE_PROGRAMMATION = 1;
    private final int NONE = 1;

    private String nomUtilisateur = "";
    private String[] listeMode = {"Choisir l'interface","Interface telecommande", "Interface programmation"};
    private String[] listeDifficulté = {"Niveau de difficulté","Difficulté 1 - Facile", "Difficulté 2 - Moyen", "Difficulté 3 - Difficile"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // Import views
        editTextNomUtilisateur = (EditText) findViewById(R.id.editText);
        modeDeJeuSpinner = (Spinner) findViewById(R.id.modeDeJeu);
        boutonDemarrer = (Button) findViewById(R.id.buttonStart);

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, R.layout.spinner_item, listeMode);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeDeJeuSpinner.setAdapter(dataAdapterR);

        diff = NONE;
        mode = NONE;

        if (modeDeJeuSpinner == null) {
            Log.i(TAG, "MDJ null");
        }


        modeDeJeuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String myMode = String.valueOf(modeDeJeuSpinner.getSelectedItem());
                if(myMode == listeMode[1]) {
                    mode = INTERFACE_TELECOMMANDE;
                }
                else if(myMode == listeMode[2]) {
                    mode = INTERFACE_PROGRAMMATION;
                }
                else {
                    mode = NONE;
                }
            }
        });

        editTextNomUtilisateur.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nomUtilisateur = editTextNomUtilisateur.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        difficulteSpinner = (Spinner) findViewById(R.id.difficulté);
        ArrayAdapter<String> dataAdapterR2 = new ArrayAdapter<String>(this, R.layout.spinner_item, listeDifficulté);
        dataAdapterR2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulteSpinner.setAdapter(dataAdapterR2);


        difficulteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String myDifficulté = String.valueOf(difficulteSpinner.getSelectedItem());
                if(myDifficulté == listeDifficulté[1]) {
                    diff = FACILE;
                }
                else if(myDifficulté == listeDifficulté[2]){
                    diff = MOYEN;
                }
                else if(myDifficulté == listeDifficulté[3]) {
                    diff = DIFFICILE;
                }
                else {
                    diff = NONE;
                }
            }


        });
        TextView Titre=(TextView)findViewById(R.id.textView);
        Typeface myFont = Typeface.createFromAsset(getAssets(),"fonts/space_age.ttf");
        Titre.setTypeface(myFont);

        boutonDemarrer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    boutonDemarrer.setAlpha(0.6f);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    boutonDemarrer.setAlpha(1);
                    nouvellePartie();
                }
                return true;
            }
        });
    }
    public void nouvellePartie(){
//        diff = FACILE;
//        mode = INTERFACE_PROGRAMMATION;
//        nomUtilisateur = "s";
        String param = Jeu.verificationParametres(mode, diff, nomUtilisateur);
        if(param.equals("")) {
            Jeu jeu = new Jeu(mode, diff, nomUtilisateur, this);
        }
        else {
            Toast.makeText(getApplicationContext(), param, Toast.LENGTH_SHORT).show();
        }
    }
}