package com.android.adybelliservice.SmsApplication.Activity;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adybelliservice.SmsApplication.Adapter.SmsAdapter;
import com.android.adybelliservice.SmsApplication.DataBase.SMS;
import com.android.adybelliservice.SmsApplication.DataBase.SmSDB;
import com.android.adybelliservice.SmsApplication.Common.AppFont;
import com.android.adybelliservice.SmsApplication.Common.CONSTANT;
import com.android.adybelliservice.SmsApplication.Common.Utils;
import com.android.adybelliservice.R;
import com.android.adybelliservice.databinding.SmsActivityMainBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;


public class MainActivity extends AppCompatActivity {
    private Socket mSocket;
    private String SENT = "SMS_SENT";
    private String DELIVERED = "SMS_DELIVERED";
    PendingIntent piSent, piDelivered;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    private SmSDB db;
    private ArrayList<SMS> smsList = new ArrayList<>();
    private RecyclerView rec;
    private Context context = this;
    private SmsActivityMainBinding binding;
    private static MainActivity INSTANCE;
    private ArrayList<BroadcastReceiver> broadcastReceivers = new ArrayList<>();
    public static final String CHANNEL_ID = "com.sms.sender.android";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SmsActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        INSTANCE = this;
        try {
            IO.Options options = IO.Options.builder()
                    // IO factory options
                    .setForceNew(false)
                    .setMultiplex(true)
                    // low-level engine options
                    .setTransports(new String[]{WebSocket.NAME})
                    .setUpgrade(true)
                    .setRememberUpgrade(false)
                    .setQuery(null)
                    .setExtraHeaders(null)
                    .setReconnection(true)
                    .setReconnectionAttempts(Integer.MAX_VALUE)
                    .setReconnectionDelay(1_000)
                    .setReconnectionDelayMax(5_000)
                    .setRandomizationFactor(0.5)
                    .setTimeout(20_000)
                    .setAuth(null)
                    .build();
            String host="http://sha.adybelli.com";
            if(!Utils.getSharedPreference(context,"host").isEmpty()){
                host=Utils.getSharedPreference(context,"host");
            } else {
                Utils.setPreference("host","http://sha.adybelli.com",context);
            }

            mSocket = IO.socket(URI.create(host), options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        db = new SmSDB(this);
        initComponents();
        setFonts();
        askPermission();

    }

    public static MainActivity get() {
        return INSTANCE;
    }

    private void setFonts() {
        binding.title.setTypeface(AppFont.getSemiBoldFont(context));
        binding.status.setTypeface(AppFont.getRegularFont(context));
        binding.settings.setTypeface(AppFont.getSemiBoldFont(context));
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.SEND_SMS},
                1);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startSentListener();
        startDeliveryListener();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startTask();
                } else {
                    Toast.makeText(MainActivity.this, "You must access sms send permission!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void startTask() {
        connectWebSocket();
        getAllSmsHistory(0);
        setListener();

    }

    private void setListener() {
        binding.retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    disconnect();
                    connectWebSocket();
                    setStatus("Connecting...");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                EditText edittext = new EditText(context);

                alert.setMessage("Enter new host");
                alert.setTitle("Change host name");

                alert.setView(edittext);
                if(Utils.getSharedPreference(context,"host").isEmpty()){
                    edittext.setText("http://sha.adybelli.com");
                } else {
                    edittext.setText(Utils.getSharedPreference(context,"host"));
                }

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String host = edittext.getText().toString();
                        if(host.equals(Utils.getSharedPreference(context,"host"))){
                            dialog.dismiss();
                        } else {
                            Utils.setPreference("host",host,context);
                            finish();
                            startActivity(new Intent(context,MainActivity.class));
                        }
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        });
    }

    private void disconnect() {
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off("onMessage", onNewMessage);
    }

    private void initComponents() {
        rec = findViewById(R.id.rec);
    }

    private void getAllSmsHistory(int page) {
        MainActivity.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = db.getAllDate();
                if (cursor.getCount() == 0) {
                    // No data
                } else {
                    smsList.clear();
                    while (cursor.moveToNext()) {
                        smsList.add(new SMS(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5)));
                    }
                    if (page == 0) {
                        rec.setLayoutManager(new LinearLayoutManager(context));
                        rec.setAdapter(new SmsAdapter(smsList, context));
                    } else {
                        try {
                            rec.getAdapter().notifyDataSetChanged();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            rec.setLayoutManager(new LinearLayoutManager(context));
                            rec.setAdapter(new SmsAdapter(smsList, context));
                        }
                    }
                }
            }
        });


    }

    private void connectWebSocket() {
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on("onMessage", onNewMessage);
        mSocket.connect();
    }

    public Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("TAG", "Socket Connected!");
            setStatus("Connected");
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            setStatus("Connection error!");
            showNotification("Connection error!", "Sms sender not working", args[0].toString());
        }
    };
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            setStatus("Disconnected");
            showNotification("Disconnected", "Sms sender disconnected", "Please check sms sender app connection status or restart app!");
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            try {
                JSONObject obj = (JSONObject) args[0];
                Gson gson = new Gson();
                sendSms(obj.getString("phone_number"),obj.getString("message"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    private void showNotification(String title, String body, String bigText) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.sms_ic_baseline_sms_24)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(bigText))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Sms sender";
            String description = "To error display";
            Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/disconnected.mp3");
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.setSound(sound, audioAttributes);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            builder.setChannelId(CHANNEL_ID);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }

    private void setStatus(String s) {
        MainActivity.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.status.setText(s);
            }
        });
    }

    public void sendSms(String phone, String code) {
        String message = code;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        long sms_id = System.currentTimeMillis();
        SMS sms = new SMS(1, phone, message, CONSTANT.PENDING, sms_id + "",date);
        int flag = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flag = PendingIntent.FLAG_MUTABLE;
        }
        Intent sentIntent = new Intent(SENT), deliveredIntent = new Intent(DELIVERED);
        sentIntent.putExtra("sms_id", sms_id + "");
        deliveredIntent.putExtra("sms_id", sms_id + "");
        piSent = PendingIntent.getBroadcast(context, 0, sentIntent, PendingIntent.FLAG_CANCEL_CURRENT | flag);
        piDelivered = PendingIntent.getBroadcast(context, 0, deliveredIntent, PendingIntent.FLAG_CANCEL_CURRENT | flag);

        SmsManager smsMgrVar = SmsManager.getDefault();
        smsMgrVar.sendTextMessage(phone, null, message, piSent, piDelivered);

        db.insertSms(sms);

        getAllSmsHistory(3);

    }

    private void startDeliveryListener() {
        // ---when the SMS has been delivered---
        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {

                    case Activity.RESULT_OK:
                        db.updateStatus(CONSTANT.DELIVERY, arg1.getExtras().getString("sms_id"));
                        getAllSmsHistory(1);
                        break;

                    case Activity.RESULT_CANCELED:
                        db.updateStatus(CONSTANT.NOT_DELIVERY, arg1.getExtras().getString("sms_id"));
                        getAllSmsHistory(1);
                        break;
                    default:
                        db.updateStatus(CONSTANT.FAILED, arg1.getExtras().getString("sms_id"));
                        getAllSmsHistory(5);
                        break;
                }
            }
        };
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
        broadcastReceivers.add(smsDeliveredReceiver);
    }

    private void startSentListener() {
        // ---when the SMS has been sent---
        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {

                    case Activity.RESULT_OK:
                        db.updateStatus(CONSTANT.SENT, arg1.getExtras().getString("sms_id"));
                        getAllSmsHistory(1);
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        db.updateStatus(CONSTANT.FAILED, arg1.getExtras().getString("sms_id"));
                        getAllSmsHistory(1);
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        db.updateStatus(CONSTANT.FAILED, arg1.getExtras().getString("sms_id"));
                        getAllSmsHistory(2);
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        db.updateStatus(CONSTANT.FAILED, arg1.getExtras().getString("sms_id"));
                        getAllSmsHistory(3);
                        break;

                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        db.updateStatus(CONSTANT.FAILED, arg1.getExtras().getString("sms_id"));
                        getAllSmsHistory(4);
                        break;
                    default:
                        db.updateStatus(CONSTANT.FAILED, arg1.getExtras().getString("sms_id"));
                        getAllSmsHistory(6);
                        break;
                }
            }
        };
        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
        broadcastReceivers.add(smsSentReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for (BroadcastReceiver broadcastReceiver : broadcastReceivers) {
            try {
                unregisterReceiver(broadcastReceiver);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}