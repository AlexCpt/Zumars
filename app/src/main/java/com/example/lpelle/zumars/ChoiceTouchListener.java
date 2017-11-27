package com.example.lpelle.zumars;

        import android.graphics.Rect;
        import android.support.v7.app.AlertDialog;
        import android.util.Log;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.RelativeLayout;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Drag listener
 */
public final class ChoiceTouchListener implements View.OnTouchListener {

    private final int ID_DRAG1 = R.id.drag1;
    private final int ID_DRAG2 = R.id.drag2;
    private final int ID_DRAG3 = R.id.drag3;
    private final int ID_DRAG4 = R.id.drag4;
    private final int ID_DRAG5 = R.id.drag5;
    private final static String TAG = "mTag";
    private int delta_x, delta_y;
    private Rect rect = new Rect(455,58,966,690);
    private ViewGroup viewGroup;
    private List<ElementaryAction> listElementaryActions;
    private ElementaryAdapter adapter;
    private ArrayList<Action> arrayListActions;
    private ListView listView;
    private ActivityProgrammation activityProgrammation;
    private AddressedBoolean readyToStart;
    private AddressedInt menuOuvert;
    private AddressedBoolean robotIsPerforming;
    AlertDialog.Builder myAlert;

    /**
     * @param viewGroup
     * @param listView
     * @param list
     * @param adapter
     * @param arrayListActions
     * @param activityProgrammation
     * @param readyToStart
     * @param menuOuvert
     */
    public ChoiceTouchListener(ViewGroup viewGroup, ListView listView, List<ElementaryAction> list,
                               ElementaryAdapter adapter,ArrayList<Action> arrayListActions,
                               ActivityProgrammation activityProgrammation, AddressedBoolean readyToStart,
                               AddressedInt menuOuvert, AddressedBoolean robotIsPerforming) {
        super();
        this.viewGroup = viewGroup;
        this.listElementaryActions = list;
        this.adapter = adapter;
        this.listView = listView;
        this.arrayListActions = arrayListActions;
        this.activityProgrammation = activityProgrammation;
        this.readyToStart = readyToStart;
        this.menuOuvert = menuOuvert;
        this.robotIsPerforming = robotIsPerforming;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (!robotIsPerforming.b) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lparams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    delta_x = X - lparams.leftMargin;
                    delta_y = Y - lparams.topMargin;
                    break;
                case MotionEvent.ACTION_UP: // When the user release the action
                    // Si le bouton est dans la listView
                    activityProgrammation.dragRepositioning();
                    if (rect.contains(X, Y)) {
                        addActionListView(view);
//                    Log.i(TAG, "X = "+ X +" Y = "+ Y);
                    } else {
//                    Log.i(TAG, "rate");
//                    Log.i(TAG, "X = "+ X +" Y = "+ Y);
                    }
                    activityProgrammation.dragRepositioning();
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - delta_x;
                    layoutParams.topMargin = Y - delta_y;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            viewGroup.invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            myAlert = new AlertDialog.Builder(activityProgrammation);
            myAlert.setMessage("Attendez que le robot ait terminé ses actions avant d'en programmer de nouvelles.");
            myAlert.show();
        }
        return true;
    }

    public void invisible(View view) {
        view.setVisibility(view.INVISIBLE);
    }

    /**
     * Ajoute un nouvel élément à la listview
     * @param view La vue qui a été déposée
     */
    public void addActionListView(View view) {
        // Update adapter
        adapter.update();

        if (menuOuvert.i == -1)
            menuOuvert.i = 0;
        if (readyToStart.b) {
            listElementaryActions.remove(listElementaryActions.size()-1); // Enlever de carré rouge
            readyToStart.b = false;
        }
        switch (view.getId()) {
            case ID_DRAG1:
                listElementaryActions.add(new ElementaryAction(ActionIcon.DRAG_1.getSrc(menuOuvert.i)));
                arrayListActions.add(ActionIcon.DRAG_1.getAction(menuOuvert.i));
                break;
            case ID_DRAG2:
                listElementaryActions.add(new ElementaryAction(ActionIcon.DRAG_2.getSrc(menuOuvert.i)));
                arrayListActions.add(ActionIcon.DRAG_2.getAction(menuOuvert.i));
                break;
            case ID_DRAG3:
                listElementaryActions.add(new ElementaryAction(ActionIcon.DRAG_3.getSrc(menuOuvert.i)));
                arrayListActions.add(ActionIcon.DRAG_3.getAction(menuOuvert.i));
                break;
            case ID_DRAG4:
                listElementaryActions.add(new ElementaryAction(ActionIcon.DRAG_4.getSrc(menuOuvert.i)));
                arrayListActions.add(ActionIcon.DRAG_4.getAction(menuOuvert.i));
                break;
            case ID_DRAG5:
                listElementaryActions.add(new ElementaryAction(ActionIcon.DRAG_5.getSrc(menuOuvert.i)));
                arrayListActions.add(ActionIcon.DRAG_5.getAction(menuOuvert.i));
                break;
        }
        adapter.notifyDataSetChanged();
//        Log.i(TAG, "Menu ouvert : " + menuOuvert);
//        Log.i(TAG, "Ready : " + readyToStart);
    }
}