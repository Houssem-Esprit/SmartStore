package com.p2j.MohamedApp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.p2j.MohamedApp.Adapters.AdapterStore;
import com.p2j.MohamedApp.Data_Manager.RetrofitClient;
import com.p2j.MohamedApp.Model.BluetoothSearch;
import com.p2j.MohamedApp.Model.User;
import com.p2j.MohamedApp.R;
import com.p2j.MohamedApp.Utils.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    CoordinatorLayout CoordinatorLay;
    public NavController navController;
    CircleImageView UserImgMainPage;
    FloatingActionButton AddArticlefloating_button;
    public static final String SHARED_FILE_NAME = "userPreferences";
    SharedPreferences data;
    ImageView welcomeImg;
    View view;
    AppBarLayout appBarLayout ;
    CollapsingToolbarLayout layout;
    NestedScrollView nestedScrollView ;
    BluetoothSocket clientSocket;
    TextView Bluetooth;
    List<BluetoothSearch> Bluetoothlist = new ArrayList();
     FloatingActionButton ActivateBluetooth;
    NotificationDemo notificationDemo;
    public static final  String CHANNEL_ID ="CHANNEL_ID_STATIC_ONE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserImgMainPage = findViewById(R.id.UserImgMainPage);
        String url = "http://localhost:3001/uploads/users/"+ Session.getInstance().getUser().getImg();
        Glide.with(this).load(url).into(UserImgMainPage);
        //******************************* Load Bluetooth Mac *************************************************************
         notificationDemo = new NotificationDemo(getApplicationContext());
         createNotificationChannel();
        loadBluetooth loadBluetoothh = new loadBluetooth();
        loadBluetoothh.execute();

        // navigation
        //****************************************************************************************************************
         layout= findViewById(R.id.collapsingToolbarLayout);
        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(layout,toolbar,navController,appBarConfiguration);

        //*****************************************************************************************************************

        AddArticlefloating_button= findViewById(R.id.addArticleFragment);
        if(Session.getInstance().getUser().getRole() == 0){
        AddArticlefloating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.addArticleFragment);
            }
        });
        }else {
            AddArticlefloating_button.setEnabled(false);
            AddArticlefloating_button.setVisibility(View.INVISIBLE);
        }

        //*************************************************************** front design ***************************
        nestedScrollView=findViewById(R.id.nestedScroll);
        welcomeImg = findViewById(R.id.welcomeImg);
        view = findViewById(R.id.GradiantMesh);
        CoordinatorLay = findViewById(R.id.coordinatorLay);
        appBarLayout = findViewById(R.id.appbarLayout);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.homePage_Fragment) {

                    welcomeImg.setImageResource(R.drawable.entryway);
                    toolbar.setBackgroundResource(R.color.M_colorPrimary);
                    welcomeImg.setBackgroundResource(R.color.M_colorPrimary);
                    appBarLayout.setBackgroundResource(R.color.M_colorPrimary);
                    CoordinatorLay.setBackgroundResource(R.color.M_colorPrimary);
                    layout.setBackgroundResource(R.color.M_colorPrimary);
                    welcomeImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    nestedScrollView.setBackgroundResource(R.color.M_white);
                    view.setBackgroundResource(R.drawable.homepage_titlebackground);

                } else if(destination.getId() == R.id.article_Fragment) {

                    toolbar.setBackgroundResource(R.color.M_colorSecond);
                    appBarLayout.setBackgroundResource(R.color.M_colorSecond);
                    CoordinatorLay.setBackgroundResource(R.color.M_colorSecond);
                    layout.setBackgroundResource(R.color.M_colorSecond);
                    welcomeImg.setImageResource(R.drawable.shopping);
                    welcomeImg.setBackgroundResource(R.color.M_colorSecond);
                    welcomeImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    view.setBackgroundResource(R.drawable.homepage_titlebackground_blue);

                }else if(destination.getId() == R.id.wishList_Fragment){

                    toolbar.setBackgroundResource(R.color.M_colorThird);
                    welcomeImg.setImageResource(R.drawable.support_note);
                    welcomeImg.setBackgroundResource(R.color.M_colorThird);
                    welcomeImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    view.setBackgroundResource(R.drawable.homepage_titlebackground_yellow);
                    appBarLayout.setBackgroundResource(R.color.M_colorThird);
                    CoordinatorLay.setBackgroundResource(R.color.M_colorThird);
                    layout.setBackgroundResource(R.color.M_colorThird);
                }
            }
        });

        //************************************ Bluetooth Manipulation* ***************************
        ActivateBluetooth = findViewById(R.id.ActivateBluetooth);
        if (Session.getInstance().getUser().getRole()==1){
            ActivateBluetooth.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {

                    String action ;
                    action = BluetoothDevice.ACTION_ACL_DISCONNECTED;
                    Bluetooth=findViewById(R.id.bluetoothName);
                    String enableBT = BluetoothAdapter.ACTION_REQUEST_ENABLE;
                    startActivityForResult(new Intent(enableBT), 0);
                    BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
                    if (Bluetoothlist.size() != 0){
                    for (int i =0;i<Bluetoothlist.size();i++){
                    try {
                        Log.d("BLUETOOTH ","IN FOR LOOP"+Bluetoothlist.get(i).getBluetoothMac());
                        BluetoothDevice device = bluetooth.getRemoteDevice(Bluetoothlist.get(i).getBluetoothMac());
                        if (device.ACTION_ACL_DISCONNECTED.equals(action)){
                            bluetooth.startDiscovery();
                        }
                        Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                        clientSocket = (BluetoothSocket) m.invoke(device, 1);
                        Bluetooth.setText(device.getName());
                        if (!device.getName().equals("")){
                            ActivateBluetooth.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.OnGreen)));
                            String Title = "We detect you around !";
                            String Storename = Bluetoothlist.get(i).getFirstName();
                            String desc ="Your order is ready, tap on for more details";
                            notificationDemo.generateBigPictureStyleNotification(Title,desc,Storename,Bluetoothlist.get(i).getUserCin());

                        }else{ ActivateBluetooth.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.OffGreen)));}
                        Log.d("BLUETOOTH_Name",device.getName() );
                        bluetooth.cancelDiscovery();
                        clientSocket.connect();

                    } catch (IOException e) {
                        // Unable to connect; close the socket and return.
                        try {
                            clientSocket.close();
                        } catch (IOException closeException) {
                            Log.d("BLUETOOTH", "Could not close the client socket", closeException);
                        }
                        Log.d("BLUETOOTH", e.getMessage());
                        return;
                    } catch (SecurityException e) {
                        Log.d("BLUETOOTH", e.getMessage());
                    } catch (NoSuchMethodException e) {
                        Log.d("BLUETOOTH", e.getMessage());
                    } catch (IllegalArgumentException e) {
                        Log.d("BLUETOOTH", e.getMessage());
                    } catch (IllegalAccessException e) {
                        Log.d("BLUETOOTH", e.getMessage());
                    } catch (InvocationTargetException e) {
                        //Log.d("BLUETOOTH", e.getMessage());
                    }

                    Toast.makeText(getApplicationContext(), "CONNECTED", Toast.LENGTH_LONG).show();
                    }}

                }
            });
        }else {
            ActivateBluetooth.setVisibility(View.INVISIBLE);
            ActivateBluetooth.setEnabled(false);
        }

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        switch (menu.getItemId()){
            case R.id.logout :
                data = getSharedPreferences(SHARED_FILE_NAME,MODE_PRIVATE);
                SharedPreferences.Editor editor = data.edit();
                editor.clear().apply();
                Intent i=new Intent(this,SignIn_Activity.class);
                startActivity(i);

                return true;

            default:
                return NavigationUI.onNavDestinationSelected(menu,navController) || super.onOptionsItemSelected(menu);


        }
    }



    public class loadBluetooth extends AsyncTask<String, BluetoothSearch, List<BluetoothSearch>> {

        private List<BluetoothSearch> BluetoothList = new ArrayList<>();


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected List<BluetoothSearch> doInBackground(String... strings) {
            // *********************************************************
            Call call = RetrofitClient
                    .getInstance()
                    .getApi_nodeservices()
                    .getStoreBluetooth(Session.getInstance().getUser().getCin());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    try {

                        JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));
                        String msg=jsonResp.getString("BluetoothInformations");
                        if(msg.equals("Stores bluetooth retrieved"))
                        {
                            JSONArray store=jsonResp.getJSONArray("Bluetooth");

                            for (int i = 0 ; i < store.length(); i++) {
                                JSONObject obj = store.getJSONObject(i);
                                BluetoothSearch e=new BluetoothSearch();
                                e.setBluetoothMac(obj.getString("bluetoothMac"));
                                e.setFirstName(obj.getString("firstName"));
                                e.setUserCin(obj.getString("cin"));

                                BluetoothList.add(e);
                            }

                            //   shimmerFrameLayout.stopShimmer();
                            //shimmerFrameLayout.setVisibility(View.GONE);
                        }
                        else{}



                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
            return  BluetoothList;
        }

        @Override
        protected void onProgressUpdate(BluetoothSearch ... store) {
            super.onProgressUpdate(store);

        }

        @Override
        protected void onPostExecute(List<BluetoothSearch> o) {
            super.onPostExecute(o);
            // This is your code
            Bluetoothlist=o;
            Log.d("BLUETOOTH ","IN FOR LOOP"+Bluetoothlist.size());


        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Smart Store";
            String description = "You have an order ou had asked available in the store in front on you";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}