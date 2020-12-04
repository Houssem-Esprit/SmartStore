package com.p2j.MohamedApp.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.p2j.MohamedApp.Fragment.ModifyArticle;
import com.p2j.MohamedApp.R;
import com.p2j.MohamedApp.Utils.BluetoothOperation;
import com.p2j.MohamedApp.Utils.GlobalNotificationBuilder;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.p2j.MohamedApp.Utils.BluetoothOperation.REQUEST_ENABLE_BT;


public class NotificationDemo  {

    public static final  String CHANNEL_ID ="CHANNEL_ID_STATIC_ONE";
    public static final  int Notif_ID =1;
    static  CircleImageView cv;
    private static  String TAG ="Bluetooth Operation:" ;
    TextView blutName;
    Context mContext;
    Drawable StoreImg;
    NotificationManager notificationManager;
    //***************
    BluetoothAdapter mBluetoothAdapter;


        //*************************************

        //***********************************

      /*  Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.cart_64)
                .setContentTitle("Smart Store")
                .setContentText("You NotificationCompat.PRIORITY_DEFAULThave an order ou had asked available in the store in front on you")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(Notif_ID, builder.build());   */
        //generateBigPictureStyleNotification();



    public NotificationDemo(Context context){
        this.mContext=context;
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    public void generateBigPictureStyleNotification(String title,String desc,String StoreName,String cin) {


        // Main steps for building a BIG_PICTURE_STYLE notification:
        //      0. Get your data
        //      1. Create/Retrieve Notification Channel for O and beyond devices (26+)
        //      2. Build the BIG_PICTURE_STYLE
        //      3. Set up main Intent for notification
        //      4. Set up RemoteInput, so users can input (keyboard and voice) from notification
        //      5. Build and issue the notification

        // 0. Get your data (everything unique per Notification).
       /* MockDatabase.BigPictureStyleSocialAppData bigPictureStyleSocialAppData =
                MockDatabase.getBigPictureStyleData();

        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        String notificationChannelId =
                NotificationUtil.createNotificationChannel(this, bigPictureStyleSocialAppData);*/

        // 2. Build the BIG_PICTURE_STYLE.
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
                // Provides the bitmap for the BigPicture notification.
                .bigPicture(
                        BitmapFactory.decodeResource(
                                mContext.getResources(),
                                R.drawable.bluetooth_cover))
                // Overrides ContentTitle in the big form of the template.
                .setBigContentTitle(title)
                // Summary line after the detail section in the big form of the template.
                .setSummaryText(desc);

        // 3. Set up main Intent for notification.
        Intent mainIntent = new Intent(mContext, com.p2j.MohamedApp.Activity.Notification.class);
        mainIntent.putExtra("StoreID",cin);

        // When creating your Intent, you need to take into account the back state, i.e., what
        // happens after your Activity launches and the user presses the back button.

        // There are two options:
        //      1. Regular activity - You're starting an Activity that's part of the application's
        //      normal workflow.

        //      2. Special activity - The user only sees this Activity if it's started from a
        //      notification. In a sense, the Activity extends the notification by providing
        //      information that would be hard to display in the notification itself.

        // Even though this sample's MainActivity doesn't link to the Activity this Notification
        // launches directly, i.e., it isn't part of the normal workflow, a social app generally
        // always links to individual posts as part of the app flow, so we will follow option 1.

        // For an example of option 2, check out the BIG_TEXT_STYLE example.

        // For more information, check out our dev article:
        // https://developer.android.com/training/notify-user/navigation.html

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        // Adds the back stack.
        stackBuilder.addParentStack(SignUp.class);
        // Adds the Intent to the top of the stack.
        stackBuilder.addNextIntent(mainIntent);
        // Gets a PendingIntent containing the entire back stack.
        PendingIntent mainPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        mainIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        final NotificationCompat.Builder notificationCompatBuilder =
                new NotificationCompat.Builder(mContext, CHANNEL_ID);

        GlobalNotificationBuilder.setNotificationCompatBuilderInstance(notificationCompatBuilder);
       // Glide.with(mContext).load(url).into(StoreImg)
        notificationCompatBuilder
                // BIG_PICTURE_STYLE sets title and content for API 16 (4.1 and after).
                .setStyle(bigPictureStyle)
                // Title for API <16 (4.0 and below) devices.
                .setContentTitle(title)
                // Content for API <24 (7.0 and below) devices.
                .setContentText(desc)
                .setSmallIcon(R.drawable.cart_64)
                //.setLargeIcon("")
                .setContentIntent(mainPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                // Set primary color (important for Wear 2.0 Notifications).
                .setColor(ContextCompat.getColor(mContext, R.color.M_colorPrimary))

                // SIDE NOTE: Auto-bundling is enabled for 4 or more notifications on API 24+ (N+)
                // devices and all Wear devices. If you have more than one notification and
                // you prefer a different summary notification, set a group key and create a
                // summary notification via
                // .setGroupSummary(true)
                // .setGroup(GROUP_KEY_YOUR_NAME_HERE)

                .setSubText(StoreName)
                .setCategory(Notification.CATEGORY_SOCIAL)

                // Sets priority for 25 and below. For 26 and above, 'priority' is deprecated for
                // 'importance' which is set in the NotificationChannel. The integers representing
                // 'priority' are different from 'importance', so make sure you don't mix them.
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
                // visibility is set in the NotificationChannel.
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE);

        // If the phone is in "Do not disturb mode, the user will still be notified if
        // the sender(s) is starred as a favorite.


        Notification notification = notificationCompatBuilder.build();


         notificationManager = mContext.getSystemService(NotificationManager.class);
        notificationManager.notify(Notif_ID, notification);

    }

    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output;
        Rect srcRect, dstRect;
        float r;
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        if (width > height){
            output = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
            int left = (width - height) / 2;
            int right = left + height;
            srcRect = new Rect(left, 0, right, height);
            dstRect = new Rect(0, 0, height, height);
            r = height / 2;
        }else{
            output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
            int top = (height - width)/2;
            int bottom = top + width;
            srcRect = new Rect(0, top, width, bottom);
            dstRect = new Rect(0, 0, width, width);
            r = width / 2;
        }

        Canvas canvas = new Canvas(output);

        //final int color = 0xff424242;
        final int color = 0xff000000;
        final Paint paint = new Paint();

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint);

        bitmap.recycle();

        return output;
    }









}