package com.example.lpelle.zumars;

public enum Action {


    MAUVAISE_ACTION(-1, "Inutile", R.drawable.bonhomme),
    FIN_PROGRAMMATION(1, "C'est la fin de la programmation.", R.drawable.bonhomme),
    AVANCE_PREMIER_TRAIT(61, "J'avance jusqu'au premier trait.", R.drawable.premier_trait),
    AVANCE_PETITE(62, "J'avance un petit peu.", R.drawable.avancer_un_peu),
    RECULER(63, "Je recule un peu.", R.drawable.marche_arriere),
    TOURNER_DROITE(64, "Faire tourner le plateau à droite.", R.drawable.tourner_droite),
    TOURNER_GAUCHE(65, "Faire tourner le plateau à gauche.", R.drawable.tourner_gauche),
    TOURNER_SWITCH_DROITE(66, "Faire tourner le plateau vers le switch à droite.", R.drawable.switch_droite),
    TOURNER_SWITCH_GAUCHE(67, "Faire tourner le plateau vers le switch à gauche.", R.drawable.switch_gauche),
    APPUI_SWITCH(68, "Appuyer sur le switch.", R.drawable.appui_switch),
    DEMI_TOUR(69, "Faire demi tour et rentrer à la base.", R.drawable.demi_tour),
    TEST_BARIL(70, "Tester le baril.", R.drawable.test_baril),
    TEST_ROUTE(73, "J'étalonne mes capteurs.", R.drawable.calibrage_ligne),
    POUSSER_BARIL(74, "Pousser le baril.", R.drawable.pousse_baril),
    TEST_CONNECTION(2, "Je teste la connection.", R.drawable.bonhomme);

    private int id, image;
    private String description;

    // Constructeur
    Action(int id, String description, int image) {
        this.id = id;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    @Override
    public String toString() {
        return (""+this.description);
    }

    public static int getImageIcon(int id) {
        int image = R.drawable.bonhomme;
        Action[] resources = Action.values();
        for (Action action : resources ) {
            if (action.getId() == id)
                image = action.getImage();
        }
        return image;
    }
}
