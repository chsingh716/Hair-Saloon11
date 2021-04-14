package uk.ac.tees.S6145076.HairSaloon.ui.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllServicesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AllServicesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
