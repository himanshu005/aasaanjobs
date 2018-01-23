package com.android.assignment.service.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.android.assignment.AjobApplication;
import com.android.assignment.service.model.City;
import com.android.assignment.service.model.Objects;
import com.android.assignment.service.persistence.Cites;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectRepository {
    private static final String TAG = ProjectRepository.class.getName();
    private static final String BASE_URL = "https://api.aasaanjobs.com/";
    private static ProjectRepository instance;
    private AasaanJobsService aasaanJobsService;

    private ProjectRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aasaanJobsService = retrofit.create(AasaanJobsService.class);
    }

    public synchronized static ProjectRepository getInstance() {
        if (instance == null) {
            instance = new ProjectRepository();
        }
        return instance;
    }

    public LiveData<List<Objects>> getCityList(Application application) {
        final MutableLiveData<List<Objects>> data = new MutableLiveData<>();
        ConnectivityManager conMgr = (ConnectivityManager) application.getApplicationContext().getSystemService(application.getApplicationContext().CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            Log.v(TAG, "NetworkInfo online");
            // notify user you are online

            aasaanJobsService.getCity().enqueue(new Callback<City>() {
                @Override
                public void onResponse(@NonNull Call<City> call, @NonNull Response<City> response) {
                    Log.v(TAG, "onResponse" + response.body());
                    data.setValue(response.body().getObjects());
                    List<Cites> list = new ArrayList<>();
                    for (Objects objects : response.body().getObjects()) {
                        list.add(new Cites((long) objects.getId(), objects.getName(), objects.getSlug()));
                    }
                    ((AjobApplication) application).getDaoSession().getCitesDao().insertOrReplaceInTx(list);
                }

                @Override
                public void onFailure(@NonNull Call<City> call, @NonNull Throwable t) {
                    Log.v(TAG, "onFailure" + t.getMessage());
                    data.setValue(null);
                }
            });

        } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {
            Log.v(TAG, "NetworkInfo offline");
            // notify user you are not online
            List<Cites> list = ((AjobApplication) application).getDaoSession().getCitesDao().loadAll();
            List<Objects> objects = new ArrayList<>();
            for (Cites cites : list) {
                objects.add(new Objects(cites.getId().intValue(), cites.getName(), cites.getSlug()));
            }
            data.setValue(objects);
        }
        return data;
    }
}
