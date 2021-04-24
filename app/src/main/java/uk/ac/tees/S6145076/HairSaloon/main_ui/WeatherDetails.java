package uk.ac.tees.S6145076.HairSaloon.main_ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import uk.ac.tees.S6145076.HairSaloon.R;
import uk.ac.tees.S6145076.HairSaloon.extraJava.WeatherDataService;
import uk.ac.tees.S6145076.HairSaloon.extraJava.jsonHandler;

public class WeatherDetails extends Fragment {

    private static String weatherKey = "68a550b753c3784accaed012c1828338"; //key to access api
    private static String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
    private String cityName = "middlesbrough";
    WeatherDataService _service;                 //using volley library to GET request
    jsonHandler jsonData; // class to handle or filter json data


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_weather_details, container, false);
        _service = new WeatherDataService(getActivity());
/* below volley library function added */
        String finalUrl = weatherUrl + cityName + "&appid=" + weatherKey;
        _service.getHttpData(finalUrl, new WeatherDataService.volleyResponseListener() {
            @Override
            public void onError(String errorMsg) {

            }

            @Override
            public void onResponse(JSONObject object) {
                jsonData = new jsonHandler(); //class call
                jsonData.updateData(object);   //pass json data to function to filter data
                //get double value and convert to string
                //get double value and convert to string
                String _temp = String.valueOf((int)((jsonData.getTemp())-273.15));

                String wConditions=jsonData.getWeatherCondition();

                //pass data to app screen(main Activity)
                ((TextView) view.findViewById(R.id.temperature)).setText(_temp + "\u00B0" + "C"); //display data to textView
                ((TextView) view.findViewById(R.id.city)).setText((jsonData.getCityName())+"," + (jsonData.getCountry())); //city,country name display
                ((TextView) view.findViewById(R.id.weatherConditions)).setText(wConditions); //print weather conditions

                //now change image according to weather conditions
                changeImage(wConditions,view);

            }

        });

    return  view;
    }

    private void changeImage(String wCondtions,View view) {
        switch (wCondtions) {
            case "Mist":
                ((ImageView) view.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.fog);
                break;
            case "Clouds":
                ((ImageView) view.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.cloudy);
                break;
            case "Clear":
                ((ImageView) view.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.sunny);
                break;
            case "Haze":
                ((ImageView) view.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.overcast);
                break;
            default:
                ((ImageView) view.findViewById(R.id.weatherIcon)).setImageResource(R.drawable.finding);
                break;

        }
    }
}


