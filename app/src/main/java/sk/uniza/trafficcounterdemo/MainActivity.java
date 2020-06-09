package sk.uniza.trafficcounterdemo;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    }


    public void onBtnClick(View btn) {

        switch (btn.getId()) {
            case R.id.carDownBtn:
                carDown++;
                break;
            case R.id.vanDownBtn:
                vanDown++;
                break;
            case R.id.truckDownBtn:
                truckDown++;
                break;
            case R.id.carUpBtn:
                carUp++;
                break;
            case R.id.vanUpBtn:
                vanUp++;
                break;
            case R.id.truckUpBtn:
                truckUp++;
                break;
        }
        updateUi();
    }

    private void updateUi() {
        laneDownCat1.setText(carDown.toString());
        laneDownCat2.setText(vanDown.toString());
        laneDownCat3.setText(truckDown.toString());
        
        laneUpCat1.setText(carUp.toString());
        laneUpCat2.setText(vanUp.toString());
        laneUpCat3.setText(truckUp.toString());
    }

}
