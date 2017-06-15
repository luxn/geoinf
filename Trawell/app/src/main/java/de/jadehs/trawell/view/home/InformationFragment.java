package de.jadehs.trawell.view.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.jadehs.trawell.R;

public class InformationFragment extends Fragment {

    TextView informations;
    ImageView interrailIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Interrail");

        View view = inflater.inflate(R.layout.fragment_information, container, false);

        informations = (TextView) view.findViewById(R.id.informationsTextView);
        informations.setText("Mit einem Interrail Pass können Sie mit dem Zug jede Ecke Europas" +
                "erkunden und viele andere Reisende kennenlernen. Lassen Sie sich von uns alles" +
                "über den Interrail Pass erzählen. Wir erklären Ihnen, wie Sie diesen ideal nutzen," +
                "um ein atemberaubendes Abenteuer zu erleben!");

        interrailIcon = (ImageView) view.findViewById(R.id.informationInterrail);

        interrailIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent("www.interrail.eu");
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_APP_BROWSER);
//                intent.setData(Uri.parse("http://www.interrail.eu/de"));
//                startActivity(intent);
            }

        });
        // Inflate the layout for this fragment
        return view;
    }

}
