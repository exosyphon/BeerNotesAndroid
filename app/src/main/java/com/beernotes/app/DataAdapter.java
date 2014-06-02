package com.beernotes.app;

import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by andrew on 6/1/14.
 */
public abstract class DataAdapter extends BaseAdapter {
    public abstract void seedDataArray(ArrayList array);
}
