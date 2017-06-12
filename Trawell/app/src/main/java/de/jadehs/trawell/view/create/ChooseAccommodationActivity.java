package de.jadehs.trawell.view.create;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.api.AccommodationsService;
import de.jadehs.trawell.api.OnTaskCompletedListener;
import de.jadehs.trawell.models.Accommodation;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.graph.Location;
import de.jadehs.trawell.graph.TrawellGraph;
import de.jadehs.trawell.miscellaneous.TourArrayAdapter;

public class ChooseAccommodationActivity extends AppCompatActivity implements OnTaskCompletedListener<List<Accommodation>>, GoogleApiClient.OnConnectionFailedListener {

    private static int cityId;
    private TrawellGraph graph;
    private GoogleApiClient mGoogleApiClient;


    private ListView accoListView;
    private TourArrayAdapter<Accommodation> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_accommodation);
        graph = TrawellGraph.get(getApplicationContext());

        Intent intent = getIntent();
        cityId = intent.getIntExtra("cityId", -1);

        City city = City.findById(City.class, new Long(cityId));
        this.setTitle("Choose your Lodgings for " + city.getName());

        Location location = graph.getLocationByName(city.getName());


        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        AccommodationsService.getAccomodationsFor(location, mGoogleApiClient, this);



//        accoListView = (ListView) this.findViewById(R.id.accoListView);
//        accoListView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        adapter = new TourArrayAdapter<>(this,R.layout.tour_item,(ArrayList) accommodations, Accommodation.class);
//        accoListView.setAdapter(adapter);

    }



    @Override
    public void onSuccess(List<Accommodation> list) {
        Log.d("LODGING","size "+list.size());

        for(int i = 0; i < list.size(); i++){
            Log.d("LODGING","name "+list.get(i).getName());
            Log.d("LODGING","adresse "+list.get(i).getAdresse());
            Log.d("LODGING","rating "+list.get(i).getBewertung());
        }
    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("trawell", connectionResult.getErrorMessage());
    }
}
