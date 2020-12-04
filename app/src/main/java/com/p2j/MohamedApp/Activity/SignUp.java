package com.p2j.MohamedApp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.p2j.MohamedApp.Data_Manager.RetrofitClient;
import com.p2j.MohamedApp.R;
import com.p2j.MohamedApp.Utils.BluetoothOperation;
import com.p2j.MohamedApp.Utils.BluetoothWorker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText Cin;
    TextInputEditText FirstName;
    TextInputEditText LastName;
    TextInputEditText Login;
    TextInputEditText Password;
    TextInputLayout FirstName_changeable;
    Button SignUp_Button;
    TextView WarningSign;
    TextView GoToSignIn;
        Spinner spinner;
    CircleImageView Profile_img;
    public int role;
    ImageView PickImgAction;
    LinearLayout ImgPage;
    LinearLayout SignUpPage;
    LinearLayout BluetoothPage;
    Button Subnet_Img;
    String S_Cin;
    Bitmap bitmap;
    TextView blutName;
    TextInputEditText MacAddress;
    Button Add_MAC;

    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int STORAGE_PERMISSION_CODE = 101;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //
        GoToSignIn = findViewById(R.id.GoToSignIn);
        GoToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,SignIn_Activity.class));
            }
        });
        MacAddress = findViewById(R.id.Mac_address);
        Add_MAC = findViewById(R.id.add_MAc);
        PickImgAction=findViewById(R.id.pickImgAction);
        ImgPage=findViewById(R.id.img_page);
        SignUpPage=findViewById(R.id.SignUp_page);
        BluetoothPage=findViewById(R.id.BluetoothPage);
        Profile_img=findViewById(R.id.profileImg);
        Cin = findViewById(R.id.ID_Card);
        FirstName = findViewById(R.id.FirstName);
        FirstName_changeable = findViewById(R.id.FirstName_hint);
        LastName = findViewById(R.id.LastName);
        Login = findViewById(R.id.Login);
        Password = findViewById(R.id.Password);
        SignUp_Button= findViewById(R.id.SignUp_Button);
        WarningSign = findViewById(R.id.warningSign);
        spinner=findViewById(R.id.inscripType_spinner);
        Subnet_Img = findViewById(R.id.Submit_img);

        // spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Client");
        categories.add("Store");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.list_item, categories);

        // Drop down layout style - list view with radio button
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);





        SignUp_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get data from inputs
                 S_Cin = Cin.getText().toString();
                String S_FirstName = FirstName.getText().toString();
                String S_LastName = LastName.getText().toString();
                String S_Login = Login.getText().toString();
                String S_Password = Password.getText().toString();
                Log.d("Inputs",""+S_Cin+S_FirstName+S_LastName+S_Login+S_Password);

                if ( (!S_Cin.equals("")) && (!S_FirstName.equals(""))  && (!S_Login.equals("")) && (!S_Password.equals(""))){



                        Call call = RetrofitClient
                                .getInstance()
                                .getApi_nodeservices()
                                .Signup(S_Cin, S_FirstName, S_LastName, S_Login, S_Password,"img.jpg",role,"blutooth");
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {


                                    try {

                                        JSONObject jsonResp =new JSONObject(new Gson().toJson(response.body()));
                                        String msg = jsonResp.getString("userInformation");
                                        if (msg.equals("User added")){

                                            Toast.makeText(getApplicationContext(),"Your inscription has succeeded !",Toast.LENGTH_LONG).show();

                                            SignUpPage.setVisibility(View.INVISIBLE);
                                            ImgPage.bringToFront();
                                            ImgPage.setVisibility(View.VISIBLE);

                                            PickImgAction.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    checkPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);
                                                }
                                            });


                                            //startActivity(new Intent(SignUp.this,SignIn_Activity.class));
                                        }else if(msg.equals("this user cin exists")) {

                                            Toast.makeText(getApplicationContext(),"This ID card is exist, please change the ID card or go to Sign In !",Toast.LENGTH_LONG).show();

                                        }else {

                                            Toast.makeText(getApplicationContext(),"This login name exists !",Toast.LENGTH_LONG).show();


                                        }

                                    }catch (JSONException e){
                                        System.out.println(e);
                                    }






                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {

                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                Log.d("onFailure msg",": "+t.getMessage());
                               // Toast.makeText(getApplicationContext(), "onFailure zone", Toast.LENGTH_LONG).show();
                            }
                        });




                }else if (S_Cin.isEmpty()){
                    WarningSign.setText("ID card field should not be empty !");}

                    else if (S_Cin.isEmpty()){
                        WarningSign.setText("ID card field should not be empty !");
                    }else if (S_FirstName.isEmpty()){
                        WarningSign.setText("First name field should not be empty !");
                    }else if (S_LastName.isEmpty()){
                        WarningSign.setText("Last name field should not be empty !");
                    }else if (S_Login.isEmpty()){
                        WarningSign.setText("Login field should not be empty !");
                    }else if (S_Password.isEmpty()){
                        WarningSign.setText("Password field should not be empty !");
                    }




            }
        });
        // perform the webservice


        Add_MAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UpdateStoreBT(S_Cin);
                    startActivity(new Intent(SignUp.this,SignIn_Activity.class));

                }catch (Exception e){e.printStackTrace();}

            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        if (item.equals("Client")){
            role=1;
        }else {
            role =0;
            LastName.setEnabled(false);
            LastName.setFocusable(false);
            FirstName_changeable.setHint("Store name");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    // Image pick and send to server


    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image

        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor;
        int columnIndex;
        String imgDecodableString;
        File imgFile;

        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    selectedImage = data.getData();
                    // Get the cursor
                    cursor = (getContentResolver().query(selectedImage, filePathColumn, null, null, null));
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String

                    Profile_img.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));


                    imgFile = new File(imgDecodableString);
                    if (imgFile.exists()) {
                         bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());


                        Subnet_Img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                uploadImage(bitmap,S_Cin,"profile");
                                Log.d("uploadImg",": "+S_Cin+", :"+bitmap.getByteCount());
                                if (role == 0){

                                    ImgPage.setVisibility(View.INVISIBLE);
                                    ImgPage.setEnabled(false);
                                    BluetoothPage.bringToFront();
                                    BluetoothPage.setVisibility(View.VISIBLE);
                                }
                            }
                        });



                    }
                    break;

            }
    }



    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();

            pickFromGallery();
            PickImgAction.requestLayout();
            PickImgAction.getLayoutParams().height = 80;
            PickImgAction.getLayoutParams().height = 80;


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

         if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }



    private void uploadImage(Bitmap mBitmap, String cin,String target) {
        try {

            if (mBitmap != null) {
                File filesDir = getFilesDir();

                File file = new File(filesDir, "image" + ".png");

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();


                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();


                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");



                if(target.equals("profile"))
                {

                    Call call = RetrofitClient
                            .getInstance()
                            .getApi_nodeservices()
                            .uploadImageUser(body, name, cin);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {



                                Toast.makeText(getApplicationContext(), "Profile Image uploaded", Toast.LENGTH_LONG).show();
                                if (role==1){
                                    startActivity(new Intent(SignUp.this,SignIn_Activity.class));
                                }

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                            Toast.makeText(getApplicationContext(), "Erreur uploading Image", Toast.LENGTH_LONG).show();
                        }
                    });
                }



            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UpdateStoreBT(String cin){


     try {

         Call call = RetrofitClient
                 .getInstance()
                 .getApi_nodeservices()
                 .UpdateStoreBT(cin, MacAddress.getText().toString());
         call.enqueue(new Callback() {
             @Override
             public void onResponse(Call call, Response response) {

                    try {
                        JSONObject jsonResp =new JSONObject(new Gson().toJson(response.body()));
                        String msg = jsonResp.getString("storeInformations");
                        if (msg.equals("User article updated with success")){

                            Toast.makeText(getApplicationContext(),"Your inscription has succeeded !",Toast.LENGTH_LONG).show();


                            //startActivity(new Intent(SignUp.this,SignIn_Activity.class));
                        }

                    }catch (Exception e) {e.printStackTrace();}

             }

             @Override
             public void onFailure(Call call, Throwable t) {

                 Toast.makeText(getApplicationContext(), "Erreur uploading Image", Toast.LENGTH_LONG).show();
             }
         });

     }catch (Exception e){e.printStackTrace();}


    }











}