package com.example.locktest2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;

public class MainActivity extends AppCompatActivity {

    PasscodeView passcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);


        passcodeView = findViewById(R.id.passcode_view);
        passcodeView.setPasscodeLength(3).setLocalPasscode("111").setListener(new PasscodeView.PasscodeViewListener() {
            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(), "WRONG", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String number) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }
        });
    }

    public class ScreenReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){

                Log.w("BAM","Screen went on");

                Intent i = new Intent();
                i.setClassName("com.example.locktest2", "com.example.locktest2.MainActivity");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

            else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
                Log.w("BAM","Screen went off");
            }
        }
    }
}
