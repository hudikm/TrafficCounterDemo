package sk.uniza.trafficcounterdemo;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView laneUpCat1;
    private TextView laneUpCat2;
    private TextView laneUpCat3;
    private TextView laneDownCat1;
    private TextView laneDownCat2;
    private TextView laneDownCat3;
    private TrafficCounterViewModel trafficCounterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        laneUpCat1 = findViewById(R.id.laneUpCat1);
        laneUpCat2 = findViewById(R.id.laneUpCat2);
        laneUpCat3 = findViewById(R.id.laneUpCat3);

        laneDownCat1 = findViewById(R.id.laneDownCat1);
        laneDownCat2 = findViewById(R.id.laneDownCat2);
        laneDownCat3 = findViewById(R.id.laneDownCat3);
        trafficCounterViewModel =
                new ViewModelProvider(this)
                        .get(TrafficCounterViewModel.class);


        trafficCounterViewModel.carDown.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                laneDownCat1.setText(integer.toString());
            }
        });

        trafficCounterViewModel.vanDown.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                laneDownCat2.setText(integer.toString());
            }
        });

        trafficCounterViewModel.truckDown.observe(this,
                new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        laneDownCat3.setText(integer.toString());
                    }
                });

        trafficCounterViewModel.carUp.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                laneUpCat1.setText(integer.toString());
            }
        });

        trafficCounterViewModel.vanUp.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                laneUpCat2.setText(integer.toString());
            }
        });

        trafficCounterViewModel.truckUp.observe(this,
                new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        laneUpCat3.setText(integer.toString());
                    }
                });
    }


    public void onBtnClick(View btn) {

        switch (btn.getId()) {
            case R.id.carDownBtn:
                trafficCounterViewModel.carDown++;
                break;
            case R.id.vanDownBtn:
                trafficCounterViewModel.vanDown++;
                break;
            case R.id.truckDownBtn:
                trafficCounterViewModel.truckDown++;
                break;
            case R.id.carUpBtn:
                trafficCounterViewModel.carUp++;
                break;
            case R.id.vanUpBtn:
                trafficCounterViewModel.vanUp++;
                break;
            case R.id.truckUpBtn:
                trafficCounterViewModel.truckUp++;
                break;
        }
        updateUi();
    }

    private void updateUi() {

    }

}
