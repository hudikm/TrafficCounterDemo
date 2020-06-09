package sk.uniza.trafficcounterdemo;

import androidx.lifecycle.ViewModel;

public class TrafficCounterViewModel extends ViewModel {
    // No. of cars going in up lane
    public Integer carUp = 0;

    // No. of vans going in up lane
    public Integer vanUp = 0;

    // No. of trucks going in up lane
    public Integer truckUp = 0;

    // No. of cars going in down lane
    public Integer carDown = 0;

    // No. of vans going in down lane
    public Integer vanDown = 0;

    // No. of trucks going in down lane
    public Integer truckDown = 0;
}
