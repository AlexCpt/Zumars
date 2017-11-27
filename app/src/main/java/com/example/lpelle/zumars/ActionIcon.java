package com.example.lpelle.zumars;


public enum ActionIcon {

    DRAG_1(R.drawable.tourner_droite, R.drawable.premier_trait, R.drawable.test_baril, Action.TOURNER_DROITE, Action.AVANCE_PREMIER_TRAIT, Action.TEST_BARIL),
    DRAG_2(R.drawable.tourner_gauche, R.drawable.avancer_un_peu, R.drawable.calibrage_ligne, Action.TOURNER_GAUCHE, Action.AVANCE_PETITE, Action.TEST_ROUTE),
    DRAG_3(R.drawable.switch_droite, R.drawable.pousse_baril, R.drawable.bonhomme, Action.TOURNER_SWITCH_DROITE, Action.POUSSER_BARIL, Action.MAUVAISE_ACTION),
    DRAG_4(R.drawable.switch_gauche, R.drawable.marche_arriere, R.drawable.bonhomme, Action.TOURNER_SWITCH_GAUCHE, Action.RECULER, Action.MAUVAISE_ACTION),
    DRAG_5(R.drawable.appui_switch, R.drawable.demi_tour, R.drawable.bonhomme, Action.APPUI_SWITCH, Action.DEMI_TOUR, Action.MAUVAISE_ACTION);

    public int srcBras, srcRoues, srcCapteurs;
    public Action actionBras, actionRoues, actionCapteurs;

    // Constructeur
    ActionIcon(int srcBras, int srcRoues, int srcCapteurs, Action actionBras, Action actionRoues, Action actionCapteurs) {
        this.srcBras = srcBras;
        this.srcCapteurs = srcCapteurs;
        this.srcRoues = srcRoues;
        this.actionBras = actionBras;
        this.actionCapteurs = actionCapteurs;
        this.actionRoues = actionRoues;
    }

    public int getSrc(int typeIcon) {
        switch (typeIcon) {
            case 0:
                return this.srcBras;
            case 1:
                return this.srcRoues;
            case 2:
                return this.srcCapteurs;
            default:
                return -1;
        }
    }

    public Action getAction(int typeIcon) {
        switch (typeIcon) {
            case 0:
                return this.actionBras;
            case 1:
                return this.actionRoues;
            case 2:
                return this.actionCapteurs;
            default:
                return Action.MAUVAISE_ACTION;
        }
    }

}
