package com.beernotes.app.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beernotes.app.R;
import com.beernotes.app.models.Beer;

import java.util.ArrayList;

/**
 * Created by andrew on 2/22/14.
 */
public class BeersAdapter extends DataAdapter {

    private ArrayList<Beer> beers;

    public BeersAdapter() {
        super();
        this.beers = new ArrayList<Beer>();
    }

    @Override
    public void seedDataArray(ArrayList array) {
        beers.clear();
        beers.addAll(array);
    }

    @Override
    public int getCount() {
        return beers.size();
    }

    @Override
    public Object getItem(int i) {
        return beers.get(i);
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
        View beerRowView = layoutInflater.inflate(R.layout.beer_row_view, viewGroup, false);

        TextView nameTextView = (TextView) beerRowView.findViewById(R.id.nameTextView);
        TextView typeTextView = (TextView) beerRowView.findViewById(R.id.typeTextView);
        TextView notesTextView = (TextView) beerRowView.findViewById(R.id.notesTextView);

        nameTextView.setText(((Beer) getItem(i)).getName());
        typeTextView.setText(((Beer) getItem(i)).getBeerType());
        notesTextView.setText(((Beer) getItem(i)).getNotes());

        return beerRowView;
    }
}
