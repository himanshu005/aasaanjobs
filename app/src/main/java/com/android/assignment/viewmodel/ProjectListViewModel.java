package com.android.assignment.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import com.android.assignment.service.model.Objects;
import com.android.assignment.service.repository.ProjectRepository;

public class ProjectListViewModel extends AndroidViewModel {
    private final LiveData<List<Objects>> mProjectListObservable;

    public ProjectListViewModel(Application application) {
        super(application);

        mProjectListObservable = ProjectRepository.getInstance().getCityList(application);
    }

    public LiveData<List<Objects>> getmProjectListObservable() {
        return mProjectListObservable;
    }
}