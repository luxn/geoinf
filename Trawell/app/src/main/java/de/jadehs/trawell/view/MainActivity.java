
package de.jadehs.trawell.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.Set;

import de.jadehs.trawell.R;
import de.jadehs.trawell.api.OnTaskCompletedListener;
import de.jadehs.trawell.api.Weather;

public class MainActivity extends AppCompatActivity {

    public static Set<Fragment> mFragments;
    public static FragmentManager fragmentManager;

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
                        Intent intent = new Intent(getApplicationContext(), NewTourActivity.class);
                        startActivity(intent);
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        MainActivity.fragmentManager = getSupportFragmentManager();
        Fragment myFragment = new HomeFragment();
        MainActivity.fragmentManager.beginTransaction().add(
                R.id.fragmentContainer, myFragment).commit();

    }
}


