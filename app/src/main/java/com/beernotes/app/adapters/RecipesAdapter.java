package com.beernotes.app.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beernotes.app.R;
import com.beernotes.app.adapters.DataAdapter;
import com.beernotes.app.models.Recipe;

import java.util.ArrayList;

/**
 * Created by andrew on 6/1/14.
 */
public class RecipesAdapter extends DataAdapter {

    private ArrayList<Recipe> recipes;

    public RecipesAdapter() {
        super();
        this.recipes = new ArrayList<Recipe>();
    }

    @Override
    public void seedDataArray(ArrayList array) {
        recipes.clear();
        recipes.addAll(array);
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int i) {
        return recipes.get(i);
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
        View recipeRowView = layoutInflater.inflate(R.layout.recipe_row_view, viewGroup, false);

        TextView nameTextView = (TextView) recipeRowView.findViewById(R.id.nameTextView);
        TextView notesTextView = (TextView) recipeRowView.findViewById(R.id.notesTextView);

        nameTextView.setText(((Recipe) getItem(i)).getName());
        notesTextView.setText(((Recipe) getItem(i)).getBoilNotes());

        return recipeRowView;
    }
}
