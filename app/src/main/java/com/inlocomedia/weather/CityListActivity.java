package com.inlocomedia.weather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.inlocomedia.weather.adapters.CityListAdapter;
import com.inlocomedia.weather.models.City;
import com.inlocomedia.weather.services.ApiConnectionService;

import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends AppCompatActivity {

    ArrayList<City> cities  = new ArrayList<>();
    CityListAdapter cityAdapter = new CityListAdapter(cities);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        String lat = "-8.0475458";
        String lon = "-34.8769621";

        FetchCityListAsync fetchCityListAsync = new FetchCityListAsync();
        fetchCityListAsync.execute(lat, lon);

        adapterSetup();
    }

    private void adapterSetup(){

        RecyclerView cityListView = (RecyclerView) this.findViewById(R.id.cityList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cityListView.setLayoutManager(llm);

        cityListView.setAdapter(cityAdapter);

    }

    private void notifyRecyclerView(){
        cityAdapter.notifyDataSetChanged();
    }

    public class FetchCityListAsync extends AsyncTask<String, Void, Void> {

        public FetchCityListAsync() {
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notifyRecyclerView();
        }

        @Override
        protected Void doInBackground(String... params) {
            ApiConnectionService apiProvider = new ApiConnectionService();
            List<City> citiesFromApi = apiProvider.retrieveCities(params[0], params[1]);
            cities.addAll(citiesFromApi);
            return null;

        }
    }

}
