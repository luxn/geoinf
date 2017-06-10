
package de.jadehs.trawell.view.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Set;

import de.jadehs.trawell.R;
import de.jadehs.trawell.view.create.MyToursFragment;

public class MainActivity extends AppCompatActivity {

    public static Set<Fragment> mFragments;
    public static FragmentManager fragmentManager;

    public static Context context;
    public TextView weatherText;

    public static <T extends Fragment> void goTo(Class<T> tClass) throws IllegalAccessException, InstantiationException {
        T fragment = tClass.newInstance();
        MainActivity.fragmentManager.beginTransaction().replace(
                R.id.fragmentContainer, fragment).addToBackStack(fragment.getTag()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            try {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        MainActivity.goTo(HomeFragment.class);
                        return true;
                    case R.id.navigation_newTour:
                        MainActivity.goTo(NewTourFragment.class);
                        return true;
                    case R.id.navigation_myTours:
                        MainActivity.goTo(MyToursFragment.class);
                        return true;
                    case R.id.navigation_informations:
                        MainActivity.goTo(InformationFragment.class);
                        return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SugarApp.getSugarContext().deleteDatabase("myTours.db");
        MainActivity.context = getApplicationContext();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        MainActivity.fragmentManager = getSupportFragmentManager();
        Fragment myFragment = new HomeFragment();
        MainActivity.fragmentManager.beginTransaction().add(
                R.id.fragmentContainer, myFragment).commit();

    }
}


