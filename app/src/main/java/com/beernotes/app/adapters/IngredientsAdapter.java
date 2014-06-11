package com.beernotes.app.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beernotes.app.R;
import com.beernotes.app.adapters.DataAdapter;
import com.beernotes.app.models.Ingredient;

import java.util.ArrayList;

/**
 * Created by andrew on 6/1/14.
 */
public class IngredientsAdapter extends DataAdapter {

    private ArrayList<Ingredient> ingredients;

    public IngredientsAdapter() {
        super();
        this.ingredients = new ArrayList<Ingredient>();
    }

    @Override
    public void seedDataArray(ArrayList array) {
        ingredients.clear();
        ingredients.addAll(array);
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int i) {
        return ingredients.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Once you've set this adapter as the adapter for some ListView, the ListView will call
        // this method every time it needs a new row's view. You basically are in charge of
        // implementing this method so that it creates the row view (or modifies the one the ListView
        // is trying to recycle (the View named "view" that's given as a parameter) and then
        // returning it to the ListView.

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View ingredientRowView = layoutInflater.inflate(R.layout.ingredient_row_view, viewGroup, false);

        TextView nameTextView = (TextView) ingredientRowView.findViewById(R.id.nameTextView);
        TextView amountTextView = (TextView) ingredientRowView.findViewById(R.id.amountTextView);
        TextView unitTextView = (TextView) ingredientRowView.findViewById(R.id.unitTextView);
        TextView addTimeTextView = (TextView) ingredientRowView.findViewById(R.id.addTimeTextView);

        nameTextView.setText(((Ingredient) getItem(i)).getName());
        amountTextView.setText(((Ingredient) getItem(i)).getAmount());
        unitTextView.setText(((Ingredient) getItem(i)).getUnit());
        addTimeTextView.setText(((Ingredient) getItem(i)).getAddTime());

        return ingredientRowView;
    }
}
