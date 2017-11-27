package com.example.lpelle.zumars;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class ElementaryAdapter extends ArrayAdapter<ElementaryAction> {

    private final static String TAG = "mTag";
    private Context context;
    private List<ElementaryAction> listElementaryActions;
    private int INVALID_ID;
    HashMap<ElementaryAction, Integer> mIdMap = new HashMap<ElementaryAction, Integer>();

    public ElementaryAdapter(Context context, int resource, List<ElementaryAction> list) {
        super(context, resource, list);
        this.context = context;
        this.listElementaryActions = list;
        for (int i = 0; i < list.size(); ++i) {
            mIdMap.put(list.get(i), i);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Make sur we have a view to work with
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(R.layout.elementary, parent, false);
        }

        // Find the elementary action to work with
        ElementaryAction currentAction = listElementaryActions.get(position);

        // Fill the view
        ImageView imageView = (ImageView) itemView.findViewById(R.id.item_icon);
        imageView.setImageResource(currentAction.getIconId());

        return itemView;
    }

    /**
     * Mise à jour du HashMap
     */
    public void update() {
        for (int i = 0; i < listElementaryActions.size(); ++i) {
            mIdMap.put(listElementaryActions.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        ElementaryAction item = getItem(position);
//        Log.i(TAG, "getItem(position) " + getItem(position));
//        Log.i(TAG, "mIdMap.get(item) " + mIdMap.get(item));

        // Bug réparé approximativement
        if (mIdMap.get(item) == null)
            return INVALID_ID;
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void affichageHashMap() {
        Log.i(TAG, "================================");
        Log.i(TAG, "Affichage HashMap :");
        for (int i = 0; i < mIdMap.size(); i++) {
            Log.i(TAG, "Element "+ i + " = "+ mIdMap.toString());
        }
        Log.i(TAG, "================================");
    }
}