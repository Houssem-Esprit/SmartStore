package com.p2j.smartStore.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.p2j.smartStore.Data_Manager.RetrofitClient;
import com.p2j.smartStore.Model.User;
import com.p2j.smartStore.R;
import com.p2j.smartStore.Utils.Session;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn_Activity extends AppCompatActivity {

    TextInputEditText Login;
    TextInputEditText Password;
    Button Login_Button;
    public static final String SHARED_FILE_NAME = "userPreferences";
    SharedPreferences data;
    User currentUser=new User();
    TextView GoToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_);


        // If you don't have an account go to sign up
        GoToSignUp = findViewById(R.id.GoToSignUp);
        GoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn_Activity.this,SignUp.class));
            }
        });


        // Login Directly

        data = getSharedPreferences(SHARED_FILE_NAME,MODE_PRIVATE);
        String sharedUserCin = data.getString("cin","");

        if(!sharedUserCin.equals(""))
        {


            Call call = RetrofitClient
                    .getInstance()
                    .getApi_nodeservices()
                    .GetUserDetails(sharedUserCin);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    try {

                        JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));
                        String msg=jsonResp.getString("userInformations");

                        if(msg.equals("User retrieved")){
                            JSONObject userJson=jsonResp.getJSONObject("user");

                            initSession(userJson);


                            Intent i = new Intent(SignIn_Activity.this,MainActivity.class);
                            startActivity(i);
                        }
                        else {

                            Toast.makeText(SignIn_Activity.this,"ERROR !",Toast.LENGTH_LONG).show();


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call call, Throwable t) {

                    Toast.makeText(SignIn_Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }





        // Perform Login event
        Login = findViewById(R.id.login);
        Password=findViewById(R.id.password);
        Login_Button= findViewById(R.id.login_button);

        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

                Call call = RetrofitClient
                        .getInstance()
                        .getApi_nodeservices()
                        .Login(Login.getText().toString(),Password.getText().toString());

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {

                        try {

                            JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));
                            String msg=jsonResp.getString("userInformations");

                            if(msg.equals("Connection success")){
                                JSONObject userJson=jsonResp.getJSONObject("user");

                                initSession(userJson);


                                Toast.makeText(SignIn_Activity.this,"Connecté !",Toast.LENGTH_LONG).show();

                                SharedPreferences.Editor editor = data.edit();
                                String cin=userJson.getString("cin");

                                editor.putString("cin",cin);

                                editor.apply();



                                Intent i = new Intent(SignIn_Activity.this,MainActivity.class);
                                startActivity(i);
                            }
                            else if(msg.equals("Wrong username")){
                                //nom d'utilisateur n'existe pas
                                //afficher suggestion de creation de compte si l'erreur s'affiche 3 fois
                                Toast.makeText(SignIn_Activity.this,"Username incorrect",Toast.LENGTH_LONG).show();


                            }else{
                                // mot de passe erroné
                                //afficher suggestion de changement de mot de passe si l'erreur s'affiche 3 fois
                                Toast.makeText(SignIn_Activity.this,"Password incorrect",Toast.LENGTH_LONG).show();
                            }


                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(SignIn_Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        });

    }






    public void initSession(JSONObject userJson)
    {

        try {
            String cin = userJson.getString("cin");
            String FirstName=userJson.getString("firstName");
            String LastName=userJson.getString("lastName");
            String Login=userJson.getString("login");
            String Password=userJson.getString("password");
            String img=userJson.getString("img");
            int role=userJson.getInt("role");


            currentUser.setCin(cin);
            currentUser.setFirstName(FirstName);
            currentUser.setLastName(LastName);
            currentUser.setLogin(Login);
            currentUser.setPassword(Password);
            currentUser.setImg(img);
            currentUser.setRole(role);

            Session.getInstance().setUser(currentUser);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}