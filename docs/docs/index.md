# ViewModel: A Simple Example

!!! tip "**This simple step-by-step tutorial is an example of use of the [tutorial maker tools](https://github.com/hudikm/PatchCreator).**"
	

<!--tgen file='/home/martin/AndroidStudioProjects/TrafficCounterDemo/out.patch' lang=java tabs t_new="New" t_old="Old" -->

<!--___________tgen step=all template='gen_tags_separate_header'  -->

#### ViewModel Overview

The [`ViewModel`](https://developer.android.com/reference/androidx/lifecycle/ViewModel) class is designed to store and manage UI-related data in a lifecycle conscious way. The [`ViewModel`](https://developer.android.com/reference/androidx/lifecycle/ViewModel) class allows data to survive configuration changes such as screen rotations.

<!--tgen step=1.0 template='files_list' noupdate  -->

#### Project structure [:link:](https://github.com/hudikm/TrafficCounterDemo/commit/5f21036ddb9817a180274c3dea381ffee7ab20ed/)

```
 .
 └─ app
    ├─ src
    │  └─ main
    │     └─ java
    │        └─ sk
    │           └─ uniza
    │              └─ trafficcounterdemo
    │                 └─ MainActivity.java
    └─ build.gradle
```


<!--end-->

<!--tgen step=1.0 template='mkdocs_header_only'  -->
<br/>
#### 1.0 Basic functionality [:link:](https://github.com/hudikm/TrafficCounterDemo/commit/afaa7056d0c65ceda8092be575807b9205becdbe/)
<!--end-->

Start of this project on [github](https://github.com/hudikm/TrafficCounterDemo/tree/gh-pages).

Traffic-Counter is a very simple app with buttons that modify counts of cars going in lane 1 or lane 2. The finished app has a bug; After the phone rotation, your traffic counts  will inexplicably disappear. This behaviour occur because of the phenomenon called ["configuration changes"](https://developer.android.com/guide/topics/manifest/activity-element.html#config). When you rotate your phone Android OS need to redraw application screen and this is done by simply restarting the whole application. As you can see in code in step 1.0: after restarting the application all variables will be set back to zero.



![](images/rotation_viewmodel.gif#center)

<!--tgen step=1.0 template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/TrafficCounterDemo/blob/afaa7056d0c65ceda8092be575807b9205becdbe/app/src/main/java/sk/uniza/trafficcounterdemo/MainActivity.java) app/src/main/java/sk/uniza/trafficcounterdemo/MainActivity.java**

``` java tab="New" hl_lines="4 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 37 38 39 40 41 42 43 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81"
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // No. of cars going in up lane
    private Integer carUp = 0;

    // No. of vans going in up lane
    private Integer vanUp = 0;

    // No. of trucks going in up lane
    private Integer truckUp = 0;

    // No. of cars going in down lane
    private Integer carDown = 0;

    // No. of vans going in down lane
    private Integer vanDown = 0;

    // No. of trucks going in down lane
    private Integer truckDown = 0;

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

```

``` java tab="Old" 
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}

```

<!--end-->

<br/>

### ViewModel overview

**Dealing with the problem of restarting the application**

To understand the problem, you need to [understand the activity lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle). Activity might cycle through  different states many times not only due to the configuration changes.

![](images/life_cycle.png#center)

There are more options how to avoid data lost due to screen rotation:

- turn off the screen rotation (**not recommended**)
- save and retrieve data to/from bundle object in methods: `onSaveInstanceState`,`onRestoreInstanceState`
- use fragment with [`setRetainInstance(true)`](https://developer.android.com/reference/android/app/Fragment#setRetainInstance(boolean))(**deprecated**)  
- use ViewModels (**recommended**)

<!--tgen step=1.1 template='mkdocs_header_only'  -->
<br/>
#### 1.1 Create a ViewModel class [:link:](https://github.com/hudikm/TrafficCounterDemo/commit/292eb1719d3d34aac984285fd468b6b41701abe6/)
<!--end-->

To store the data outside of the activity where they are not affected by an activity lifecycle we can use `ViewModel` class.

In the diagram below, you can see the lifecycle of an Activity which undergoes a restart due to the rotation of a screen and then it is finally finished. The lifetime of the ViewModel is shown next to the associated Activity lifecycle. Note that ViewModels can be easily used with both Fragments and Activities.

![](images/viewmodel.png#center)

The ViewModel exists from when you first request an instance of class ViewModel (usually in the `onCreate` the Activity) until the associated Activity is finished and destroyed. `onCreate` may be called several times during the life of an Activity, such as when the app is rotated, but the ViewModel is not affected.

Continue with below steps to finished the application.

<!--tgen step=1.1 template='files_list' noupdate -->
**Create new class TrafficCounterViewModel.java [:link:](https://github.com/hudikm/TrafficCounterDemo/commit/d98d7c1637478d61e5abdef52bda20db7c3f74f6/)**

```
 .
 └─ app
    ├─ src
    │  └─ main
    │     └─ java
    │        └─ sk
    │           └─ uniza
    │              └─ trafficcounterdemo
    │                 ├─ MainActivity.java
    │                 └─ TrafficCounterViewModel.java   # create new file
    └─ build.gradle
```

<!--end-->

<!--tgen step=1.1 template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/TrafficCounterDemo/blob/292eb1719d3d34aac984285fd468b6b41701abe6/app/src/main/java/sk/uniza/trafficcounterdemo/MainActivity.java) app/src/main/java/sk/uniza/trafficcounterdemo/MainActivity.java**

``` java tab="New" 
public class MainActivity extends AppCompatActivity {

    private TextView laneUpCat1;
    private TextView laneUpCat2;
    private TextView laneUpCat3;

```

``` java tab="Old" hl_lines="3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20"
public class MainActivity extends AppCompatActivity {

    // No. of cars going in up lane
    private Integer carUp = 0;

    // No. of vans going in up lane
    private Integer vanUp = 0;

    // No. of trucks going in up lane
    private Integer truckUp = 0;

    // No. of cars going in down lane
    private Integer carDown = 0;

    // No. of vans going in down lane
    private Integer vanDown = 0;

    // No. of trucks going in down lane
    private Integer truckDown = 0;

    private TextView laneUpCat1;
    private TextView laneUpCat2;
    private TextView laneUpCat3;

```

>  **[🖹](https://github.com/hudikm/TrafficCounterDemo/blob/292eb1719d3d34aac984285fd468b6b41701abe6/app/src/main/java/sk/uniza/trafficcounterdemo/TrafficCounterViewModel.java) app/src/main/java/sk/uniza/trafficcounterdemo/TrafficCounterViewModel.java**

``` java tab="New" hl_lines="1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23"
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

```



<!--end-->


<!--tgen step=1.2 template='mkdocs_header_only'  -->
<br/>
#### 1.2 Add lifecycle-extensions to the project [:link:](https://github.com/hudikm/TrafficCounterDemo/commit/24a1de18d28242f0e0e6a1c69d47661ca0f234ae/)
<!--end-->

<!--tgen step=1.2 template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/TrafficCounterDemo/blob/24a1de18d28242f0e0e6a1c69d47661ca0f234ae/app/build.gradle) app/build.gradle**

 > dependencies {

``` java tab="New" hl_lines="4"
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
}

```

``` java tab="Old" 
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
}

```


<!--end-->


<!--tgen step=1.3 template='mkdocs_header_only'  -->
<br/>
#### 1.3 Associate the UI Controller and ViewModel [:link:](https://github.com/hudikm/TrafficCounterDemo/commit/7cad36cd8aa245295c4c64ac03a3243c85128fd0/)
<!--end-->

<!--tgen step=1.3 template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/TrafficCounterDemo/blob/7cad36cd8aa245295c4c64ac03a3243c85128fd0/app/src/main/java/sk/uniza/trafficcounterdemo/MainActivity.java) app/src/main/java/sk/uniza/trafficcounterdemo/MainActivity.java**

``` java tab="New" hl_lines="3 4"
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.view.Menu;

```

``` java tab="Old" 
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;

```

``` java tab="New" hl_lines="4"
    private TextView laneDownCat1;
    private TextView laneDownCat2;
    private TextView laneDownCat3;
    private TrafficCounterViewModel trafficCounterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

```

``` java tab="Old" 
    private TextView laneDownCat1;
    private TextView laneDownCat2;
    private TextView laneDownCat3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

```

 > protected void onCreate(Bundle savedInstanceState) {

``` java tab="New" hl_lines="4 5 6 7 8"
        laneDownCat1 = findViewById(R.id.laneDownCat1);
        laneDownCat2 = findViewById(R.id.laneDownCat2);
        laneDownCat3 = findViewById(R.id.laneDownCat3);
        trafficCounterViewModel =
                new ViewModelProvider(this)
                .get(TrafficCounterViewModel.class);

        updateUi();
    }



```

``` java tab="Old" 
        laneDownCat1 = findViewById(R.id.laneDownCat1);
        laneDownCat2 = findViewById(R.id.laneDownCat2);
        laneDownCat3 = findViewById(R.id.laneDownCat3);
    }



```


<!--end-->


<!--tgen step=1.4 template='mkdocs_header_only'  -->
<br/>
#### 1.4 Use the ViewModel in your UI Controller [:link:](https://github.com/hudikm/TrafficCounterDemo/commit/997dcbf7bd556fbdd8580909f4a6892adb79babe/)
<!--end-->

<!--tgen step=1.4 template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/TrafficCounterDemo/blob/997dcbf7bd556fbdd8580909f4a6892adb79babe/app/src/main/java/sk/uniza/trafficcounterdemo/MainActivity.java) app/src/main/java/sk/uniza/trafficcounterdemo/MainActivity.java**

 > public void onBtnClick(View btn) {

``` java tab="New" hl_lines="3 6 9 12 15 18 25 26 27 29 30 31"
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
        laneDownCat1.setText(trafficCounterViewModel.carDown.toString());
        laneDownCat2.setText(trafficCounterViewModel.vanDown.toString());
        laneDownCat3.setText(trafficCounterViewModel.truckDown.toString());
        
        laneUpCat1.setText(trafficCounterViewModel.carUp.toString());
        laneUpCat2.setText(trafficCounterViewModel.vanUp.toString());
        laneUpCat3.setText(trafficCounterViewModel.truckUp.toString());
    }

}

```

``` java tab="Old" hl_lines="3 6 9 12 15 18 25 26 27 29 30 31"
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

```


<!--end-->

<br/>

!!! info "At this point you can try to *run* the application and see how it works"



<br/>

### LiveData overview

[`LiveData`](https://developer.android.com/topic/libraries/architecture/livedata) is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. This awareness ensures LiveData only updates app component observers that are in an active lifecycle state.

**Work with LiveData objects**

Follow these steps to work with [`LiveData`](https://developer.android.com/reference/androidx/lifecycle/LiveData) objects:

1. Create an instance of `LiveData` to hold a certain type of data. This is usually done within your [`ViewModel`](https://developer.android.com/reference/androidx/lifecycle/ViewModel) class.
2. Create an [`Observer`](https://developer.android.com/reference/androidx/lifecycle/Observer) object that defines the [`onChanged()`](https://developer.android.com/reference/androidx/lifecycle/Observer#onChanged(T)) method, which controls what happens when the `LiveData` object's held data changes. You usually create an `Observer` object in a UI controller, such as an activity or fragment.
3. Attach the `Observer` object to the `LiveData` object using the [`observe()`](https://developer.android.com/reference/androidx/lifecycle/LiveData#observe(android.arch.lifecycle.LifecycleOwner, android.arch.lifecycle.Observer)) method. The `observe()` method takes a [`LifecycleOwner`](https://developer.android.com/reference/androidx/lifecycle/LifecycleOwner) object. This subscribes the `Observer` object to the `LiveData` object so that it is notified of changes. You usually attach the `Observer` object in a UI controller, such as an activity or fragment.

<br/>

<!--tgen step=2.0 template='mkdocs_header_only'  -->
<br/>
#### 2.0 Create LiveData objects in ViewModel class [:link:](https://github.com/hudikm/TrafficCounterDemo/commit/1f5ad8a2cb4932e6d639c546b9fddb6f43b3c047/)
<!--end-->

<!--tgen step=2.0 template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/TrafficCounterDemo/blob/1f5ad8a2cb4932e6d639c546b9fddb6f43b3c047/app/src/main/java/sk/uniza/trafficcounterdemo/TrafficCounterViewModel.java) app/src/main/java/sk/uniza/trafficcounterdemo/TrafficCounterViewModel.java**

``` java tab="New" hl_lines="3 8 11 14 17 20 23 24"
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

```

``` java tab="Old" hl_lines="7 10 13 16 19 22"
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

```


<!--end-->


<!--tgen step=2.1 template='mkdocs_header_only'  -->
<br/>
#### 2.1 Observe LiveData objects [:link:](https://github.com/hudikm/TrafficCounterDemo/commit/e92ab48512a1710ef33ba52ce365cf3519b6fbcd/)
<!--end-->

<!--tgen step=2.1 template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/TrafficCounterDemo/blob/e92ab48512a1710ef33ba52ce365cf3519b6fbcd/app/src/main/java/sk/uniza/trafficcounterdemo/MainActivity.java) app/src/main/java/sk/uniza/trafficcounterdemo/MainActivity.java**

``` java tab="New" hl_lines="3"
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


```

``` java tab="Old" 
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


```

 > protected void onCreate(Bundle savedInstanceState) {

``` java tab="New" hl_lines="4 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49"
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



```

``` java tab="Old" hl_lines="4 6"
        laneDownCat3 = findViewById(R.id.laneDownCat3);
        trafficCounterViewModel =
                new ViewModelProvider(this)
                .get(TrafficCounterViewModel.class);

        updateUi();
    }



```

 > public void onBtnClick(View btn) {

``` java tab="New" hl_lines="4"
    }

    private void updateUi() {

    }

}

```

``` java tab="Old" hl_lines="4 5 6 7 8 9 10"
    }

    private void updateUi() {
        laneDownCat1.setText(trafficCounterViewModel.carDown.toString());
        laneDownCat2.setText(trafficCounterViewModel.vanDown.toString());
        laneDownCat3.setText(trafficCounterViewModel.truckDown.toString());
        
        laneUpCat1.setText(trafficCounterViewModel.carUp.toString());
        laneUpCat2.setText(trafficCounterViewModel.vanUp.toString());
        laneUpCat3.setText(trafficCounterViewModel.truckUp.toString());
    }

}

```


<!--end-->


<!--tgen step=2.2 template='mkdocs_header_only'  -->
<br/>
#### 2.2 Update LiveData objects [:link:](https://github.com/hudikm/TrafficCounterDemo/commit/ec0dfe6fd95a74495af8a22a1ae217636d0a53ee/)
<!--end-->

<!--tgen step=2.2 template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/TrafficCounterDemo/blob/ec0dfe6fd95a74495af8a22a1ae217636d0a53ee/app/src/main/java/sk/uniza/trafficcounterdemo/MainActivity.java) app/src/main/java/sk/uniza/trafficcounterdemo/MainActivity.java**

 > public void onBtnClick(View btn) {

``` java tab="New" hl_lines="3 4 5 8 9 10 13 14 15 18 19 20 23 24 25 28 29 30 33"
        switch (btn.getId()) {
            case R.id.carDownBtn:
                Integer carDown =
                        trafficCounterViewModel.carDown.getValue();
                trafficCounterViewModel.carDown.setValue(++carDown);
                break;
            case R.id.vanDownBtn:
                Integer vanDown =
                        trafficCounterViewModel.vanDown.getValue();
                trafficCounterViewModel.vanDown.setValue(++vanDown);
                break;
            case R.id.truckDownBtn:
                Integer truckDown =
                        trafficCounterViewModel.truckDown.getValue();
                trafficCounterViewModel.truckDown.setValue(++truckDown);
                break;
            case R.id.carUpBtn:
                Integer carUp =
                        trafficCounterViewModel.carUp.getValue();
                trafficCounterViewModel.carUp.setValue(++carUp);
                break;
            case R.id.vanUpBtn:
                Integer vanUp =
                        trafficCounterViewModel.vanUp.getValue();
                trafficCounterViewModel.vanUp.setValue(++vanUp);
                break;
            case R.id.truckUpBtn:
                Integer truckUp =
                        trafficCounterViewModel.truckUp.getValue();
                trafficCounterViewModel.truckUp.setValue(++truckUp);
                break;
        }
        // Remove this line: updateUi();
    }

    private void updateUi() {

```

``` java tab="Old" hl_lines="3 6 9 12 15 18 21"
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

```

<!--end-->

At this point the basic application is finished and you can test it. 


<!--end-->
