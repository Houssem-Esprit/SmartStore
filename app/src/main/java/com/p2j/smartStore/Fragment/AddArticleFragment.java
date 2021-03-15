package com.p2j.smartStore.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.p2j.smartStore.Data_Manager.RetrofitClient;
import com.p2j.smartStore.R;
import com.p2j.smartStore.Utils.Session;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import br.com.onimur.handlepathoz.HandlePathOz;
import br.com.onimur.handlepathoz.HandlePathOzListener;
import br.com.onimur.handlepathoz.model.PathOz;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class AddArticleFragment extends Fragment implements HandlePathOzListener.SingleUri {

    TextInputEditText IdArticle;
    TextInputEditText ArticleName;
    TextInputEditText ArticlePrice;
    TextInputEditText ArticleQuant;
    SwitchMaterial  ColorAvailable;
    SwitchMaterial  ProductAvailable;
    Button addArticle;
    int ColorState;
    int ProductState;
    CircleImageView ArticleImg;
    ImageView PickAndSend;
    Bitmap imgToUpload;
    private static final int STORAGE_PERMISSION_CODE = 102;
    private static final int RESULT_LOAD_IMAGE = 999 ;
    private static String TAG = AddArticleFragment.class.getCanonicalName();
    private HandlePathOz handlePathOz;
    private String selectedImagePath;
    Bitmap mBitmap;
    String IdArt;



    public AddArticleFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewroot = inflater.inflate(R.layout.fragment_add_article, container, false);

        //
        handlePathOz = new HandlePathOz(getContext(), this);
        //

        IdArticle = viewroot.findViewById(R.id.ID_Item);
        ArticleName = viewroot.findViewById(R.id.Item_name);
        ArticlePrice = viewroot.findViewById(R.id.PriceInput);
        ArticleQuant = viewroot.findViewById(R.id.QuantityInput);
        ColorAvailable = viewroot.findViewById(R.id.colorSwitch);
        ProductAvailable = viewroot.findViewById(R.id.productSwitch);
        addArticle=viewroot.findViewById(R.id.AddArticle);

//        FloatingActionButton disableAddbutton = getActivity().findViewById(R.id.addArticlefloating_button);
//        disableAddbutton.setEnabled(false);
//        disableAddbutton.setFocusable(false);
//        disableAddbutton.setClickable(false);




        addArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 IdArt = IdArticle.getText().toString();
                String NameArt = ArticleName.getText().toString();
                Double PriceArt =  Double.parseDouble(ArticlePrice.getText().toString());
                int QuantityArt = Integer.parseInt(ArticleQuant.getText().toString());

                if (ColorAvailable.isChecked()==true){
                    ColorState=1;
                }else {ColorState=0;}

                if (ProductAvailable.isChecked()==true){
                    ProductState=1;
                }else {ProductState=0;}


                Call call = RetrofitClient
                        .getInstance()
                        .getApi_nodeservices()
                        .AddArticle(IdArt, NameArt, ColorState, QuantityArt, PriceArt,ProductState,"imgArticle.jpg", Session.getInstance().getUser().getCin());
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {


                        try {

                            JSONObject jsonResp =new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonResp.getString("articleInformations");
                            if (msg.equals("Article added")){


                                Toast.makeText(getContext(),"Article Created successfully !",Toast.LENGTH_LONG).show();
                                String ArticleId=jsonResp.getString("article");
                                Log.d("ArticleId","Id : "+ArticleId);

                                if (mBitmap != null){
                                    uploadImage(mBitmap,IdArt);

                                }

                                //startActivity(new Intent(SignUp.this,SignIn_Activity.class));
                            }  else
                            {
                                Toast.makeText(getContext(),"Error !",Toast.LENGTH_LONG).show();

                            }

                        }catch (JSONException e){
                            System.out.println(e);
                        }






                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("onFailure msg",": "+t.getMessage());
                        // Toast.makeText(getApplicationContext(), "onFailure zone", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


        ArticleImg=viewroot.findViewById(R.id.ArticleImg);
        PickAndSend=viewroot.findViewById(R.id.pickImgAndSend);

        PickAndSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickFromGallery();

            }
        });




        return viewroot;
    }



    private void pickFromGallery() {

        //---------------------
        String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getContext(), galleryPermissions)) {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
            } else {
                //Create an Intent with action as ACTION_PICK
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        } else {
            EasyPermissions.requestPermissions(getActivity(), "Access for storage",
                    101, galleryPermissions);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image

        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage;

        if (resultCode == Activity.RESULT_OK){

                    //data.getData return the content URI for the selected Image
                    selectedImage = data.getData();
                    if(selectedImage != null){
                        handlePathOz.getRealPath(selectedImage);
                        Glide.with(this)
                                .load(selectedImage)
                                .into(ArticleImg);
                    }

        }

    }


    private void uploadImage(Bitmap mBitmap,String articleId) {
        try {

            if (mBitmap != null) {
                File filesDir = getContext().getFilesDir();
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


                Call call = RetrofitClient
                        .getInstance()
                        .getApi_nodeservices()
                        .uploadImageArticle(body, name, articleId);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {

                        try {

                            //JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));

                            Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_LONG).show();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                        Toast.makeText(getContext(), "Erreur uploading Image", Toast.LENGTH_LONG).show();
                    }
                });


            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestHandlePathOz(@NotNull PathOz pathOz, @Nullable Throwable throwable) {
        selectedImagePath = pathOz.getPath();
        Log.d(TAG,"onRequestHandlePathOz SELECTEDIMAGE_PATH : "+selectedImagePath);
        if (selectedImagePath.length() != 0) {
            try {
                File file = new File(selectedImagePath);
                mBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            }catch (Exception e){e.printStackTrace();}

        }
    }
}