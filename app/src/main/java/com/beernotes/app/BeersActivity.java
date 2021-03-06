package com.beernotes.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.beernotes.app.adapters.BeersAdapter;
import com.beernotes.app.adapters.DataAdapter;
import com.beernotes.app.adapters.IngredientsAdapter;
import com.beernotes.app.adapters.RecipesAdapter;
import com.beernotes.app.models.Beer;
import com.beernotes.app.models.Ingredient;
import com.beernotes.app.models.Recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BeersActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private final static int BEERS_SECTION_NUMBER = 1;
    private final static int RECIPES_SECTION_NUMBER = 2;
    private final static int INGREDIENTS_SECTION_NUMBER = 3;
    private final static int SETTINGS_SECTION_NUMBER = 4;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beers);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case BEERS_SECTION_NUMBER:
                mTitle = getString(R.string.beers_section);
                break;
            case RECIPES_SECTION_NUMBER:
                mTitle = getString(R.string.recipes_section);
                break;
            case INGREDIENTS_SECTION_NUMBER:
                mTitle = getString(R.string.ingredients_section);
                break;
            case SETTINGS_SECTION_NUMBER:
                mTitle = getString(R.string.settings_section);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.beers, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        public static final String BEERS_JSON_URL = "http://serene-wildwood-6609.herokuapp.com/beers.json";
        public static final String RECIPES_JSON_URL = "http://serene-wildwood-6609.herokuapp.com/all_recipes.json";
        public static final String INGREDIENTS_JSON_URL = "http://serene-wildwood-6609.herokuapp.com/all_ingredients.json";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int check = getArguments().getInt(ARG_SECTION_NUMBER);

            View rootView = null;
            if (check == BEERS_SECTION_NUMBER) {
                rootView = inflater.inflate(R.layout.fragment_beers, container, false);
                ListView listView = (ListView) rootView.findViewById(R.id.beers_list_view);
                BeersAdapter adapter = new BeersAdapter();
                listView.setAdapter(adapter);

                setupThreadPolicy();

                updateDataFromRemoteEndpoint(adapter, BEERS_JSON_URL, BEERS_SECTION_NUMBER);
            } else if (check == RECIPES_SECTION_NUMBER) {
                rootView = inflater.inflate(R.layout.fragment_recipes, container, false);

                ListView listView = (ListView) rootView.findViewById(R.id.recipes_list_view);
                RecipesAdapter adapter = new RecipesAdapter();
                listView.setAdapter(adapter);

                setupThreadPolicy();

                updateDataFromRemoteEndpoint(adapter, RECIPES_JSON_URL, RECIPES_SECTION_NUMBER);
            } else if (check == INGREDIENTS_SECTION_NUMBER) {
                rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

                ListView listView = (ListView) rootView.findViewById(R.id.ingredients_list_view);
                IngredientsAdapter adapter = new IngredientsAdapter();
                listView.setAdapter(adapter);

                setupThreadPolicy();

                updateDataFromRemoteEndpoint(adapter, INGREDIENTS_JSON_URL, INGREDIENTS_SECTION_NUMBER);
            } else if (check == SETTINGS_SECTION_NUMBER) {
            }

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((BeersActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        private ArrayList<Beer> readBeerStream(String in) {
            ArrayList<Beer> returnArray = new ArrayList<Beer>();

            try {
                JSONArray jArray = new JSONArray(in);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array
                    String oneObjectsItem = oneObject.getString("name");
                    String oneObjectsItem1 = oneObject.getString("beerType");
                    String oneObjectsItem2 = oneObject.getString("notes");

                    returnArray.add(new Beer(oneObjectsItem, oneObjectsItem1, oneObjectsItem2));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnArray;
        }

        private ArrayList<Recipe> readRecipeStream(String in) {
            ArrayList<Recipe> returnArray = new ArrayList<Recipe>();

            try {
                JSONArray jArray = new JSONArray(in);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array
                    String oneObjectsItem = oneObject.getString("name");
                    String oneObjectsItem2 = oneObject.getString("boilNotes");

                    returnArray.add(new Recipe(oneObjectsItem, oneObjectsItem2));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnArray;
        }

        private ArrayList<Ingredient> readIngredientStream(String in) {
            ArrayList<Ingredient> returnArray = new ArrayList<Ingredient>();

            try {
                JSONArray jArray = new JSONArray(in);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array
                    String oneObjectsItem = oneObject.getString("name");
                    String oneObjectsItem2 = oneObject.getString("amount");
                    String oneObjectsItem3 = oneObject.getString("unit");
                    String oneObjectsItem4 = oneObject.getString("addTime");

                    returnArray.add(new Ingredient(oneObjectsItem, oneObjectsItem2, oneObjectsItem3, oneObjectsItem4));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnArray;
        }

        private void updateDataFromRemoteEndpoint(DataAdapter adapter, String urlString, int sectionNumber) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection datConnection = (HttpURLConnection) url.openConnection();

                String result = "";
                InputStream is = null;

                is = (InputStream) datConnection.getContent();

                // Read response to string
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                } catch (Exception e) {
                }

                int rCode = datConnection.getResponseCode();
                if (rCode == 200) {
                    if (sectionNumber == BEERS_SECTION_NUMBER) {
                        adapter.seedDataArray(readBeerStream(result));
                    } else if (sectionNumber == RECIPES_SECTION_NUMBER) {
                        adapter.seedDataArray(readRecipeStream(result));
                    } else if (sectionNumber == INGREDIENTS_SECTION_NUMBER) {
                        adapter.seedDataArray(readIngredientStream(result));
                    }
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void setupThreadPolicy() {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

}
