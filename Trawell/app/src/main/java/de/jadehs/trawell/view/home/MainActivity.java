
package de.jadehs.trawell.view.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.orm.SchemaGenerator;
import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.orm.SugarRecord;
import com.orm.util.SugarConfig;
import com.orm.util.SugarCursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.validation.Schema;

import de.jadehs.trawell.R;
import de.jadehs.trawell.models.Accommodation;
import de.jadehs.trawell.models.Tour;

public class MainActivity extends AppCompatActivity {

    private static Long tourId;
    private static Long cityId;
    private static FragmentManager fragmentManager;
    private static ArrayList<Accommodation> accommodations;
    private List<Tour> myTours;
    private static Context context;

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
                        MainActivity.goTo(CurrentTourFragment.class);
                        return true;
                    case R.id.navigation_newTour:
                        MainActivity.goTo(CreateTourFragment.class);
                        return true;
                    case R.id.navigation_myTours:
                        MainActivity.goTo(TourOverviewFragment.class);
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

        myTours = new ArrayList<>();
        myTours = Tour.listAll(Tour.class);

//        SugarContext.terminate();
//        SchemaGenerator schemaGenerator = new SchemaGenerator(getApplicationContext());
//        schemaGenerator.deleteTables(new SugarDb(getApplicationContext()).getDB());
//        SugarContext.init(getApplicationContext());
//        schemaGenerator.createDatabase(new SugarDb(getApplicationContext()).getDB());

        MainActivity.context = getApplicationContext();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        MainActivity.fragmentManager = getSupportFragmentManager();
        Fragment myFragment = new HomeFragment();

        MainActivity.fragmentManager.beginTransaction().add(
                R.id.fragmentContainer, myFragment).commit();

    }

    public static Long getTourId() {
        return tourId;
    }

    public static void setTourId(Long tourId) {
        MainActivity.tourId = tourId;
    }

    public static Long getCityId() {
        return cityId;
    }

    public static void setCityId(Long cityId) {
        MainActivity.cityId = cityId;
    }

    public static ArrayList<Accommodation> getAccommodations() {
        return accommodations;
    }

    public static void setAccommodations(ArrayList<Accommodation> accommodations) {
        MainActivity.accommodations = accommodations;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MainActivity.context = context;
    }
}


