package com.inlocomedia.weather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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

        if(savedInstanceState==null) {
            //Pega as informações que foram passadas pelo mapa na tela anterior pelo intent
            String lat = getIntent().getExtras().getString("LATITUDE");
            String lon = getIntent().getExtras().getString("LONGITUDE");


            //Inicia a atividade assíncrona para puxar as informações da API
            FetchCityListAsync fetchCityListAsync = new FetchCityListAsync();
            fetchCityListAsync.execute(lat, lon);

            adapterSetup();
        }
    }


    //Método para inicialização do adapter que vai alimentar a RecyclerView
    private void adapterSetup(){

        RecyclerView cityListView = (RecyclerView) this.findViewById(R.id.cityList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cityListView.setLayoutManager(llm);

        cityListView.setAdapter(cityAdapter);

    }


    //Método de callback da atividade assíncrona
    private void notifyRecyclerView(){
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        cityAdapter.notifyDataSetChanged();
    }

    public class FetchCityListAsync extends AsyncTask<String, Void, Void> {

        public FetchCityListAsync() {
        }


        @Override
        protected void onPostExecute(Void aVoid) {

            /*
            Ao terminar a chamada da API, esse método é chamado e irá notificar a classe pai
            que deve atualizar as informações do adapter e esconder a progressbar
             */
            super.onPostExecute(aVoid);
            notifyRecyclerView();
        }

        @Override
        protected Void doInBackground(String... params) {

            //Chama o serviço de comunicação com a API
            ApiConnectionService apiProvider = new ApiConnectionService();
            List<City> citiesFromApi = apiProvider.retrieveCities(params[0], params[1]);
            cities.addAll(citiesFromApi);
            return null;

        }
    }

}
