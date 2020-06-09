package sk.uniza.trafficcounterdemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrafficCounterViewModel extends ViewModel {
    // No. of cars going in up lane
    public MutableLiveData<Integer> carUp = new MutableLiveData(0);

    // No. of vans going in up lane
    public MutableLiveData<Integer> vanUp = new MutableLiveData(0);

    // No. of trucks going in up lane
    public MutableLiveData<Integer> truckUp = new MutableLiveData(0);

    // No. of cars going in down lane
    public MutableLiveData<Integer> carDown = new MutableLiveData(0);

    // No. of vans going in down lane
    public MutableLiveData<Integer> vanDown = new MutableLiveData(0);

    // No. of trucks going in down lane
    public MutableLiveData<Integer> truckDown = new MutableLiveData(0);

}
