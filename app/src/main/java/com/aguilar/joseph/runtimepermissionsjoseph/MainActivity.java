package com.aguilar.joseph.runtimepermissionsjoseph;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.marcoscg.dialogsheet.DialogSheet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private final int PERMISSION_ALL = 501;
    private final int PERMISSION_CAMERA = 502;
    private final String[] PERMISSIONS={Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @BindView(R.id.message) TextView mTextMessage;
    @BindView(R.id.button_dialogsheet) Button mDialogButton;
    @BindView(R.id.button_bottomsheet) Button mBottomButton;
    @BindView(R.id.navigation) BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.DialogSheet:
                    mTextMessage.setText(R.string.dialogsheet);
                    mDialogButton.setVisibility(View.VISIBLE);
                    mBottomButton.setVisibility(View.GONE);
                    return true;
                case R.id.BottomSheetDialogCreator:
                    mTextMessage.setText(R.string.bottomsheet);
                    mDialogButton.setVisibility(View.GONE);
                    mBottomButton.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBottomButton.setOnClickListener(this);
        mDialogButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_dialogsheet:
                new DialogSheet(this)
                        .setTitle(R.string.title_camera_location_permission)
                        .setMessage(R.string.message_camera_location_permission)
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, new DialogSheet.OnPositiveClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("PermissionTAG", " On permission click");
                                if(!hasPermissions(getApplication(), PERMISSIONS)){
                                    requestPermissions(PERMISSIONS, PERMISSION_ALL);
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogSheet.OnNegativeClickListener() {
                            @Override
                            public void onClick(View v) {
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.custom_toast,
                                        (ViewGroup) findViewById(R.id.custom_toast_container));

                                TextView text = (TextView) layout.findViewById(R.id.text);
                                text.setText(getText(R.string.sad_cat));

                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.BOTTOM, 0, 90);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();

                            }
                        })
                        .setBackgroundColor(Color.WHITE) // Your custom background color
                        .setButtonsColorRes(R.color.sexy_blue)  // Default color is accent
                        .show();
                break;
            case R.id.button_bottomsheet:
                new BottomSheetDialogCreator(this, this, R.drawable.baseline_whatshot_white_48dp , R.string.title_camera_location_permission, R.string.message_camera_location_permission, PERMISSIONS, PERMISSION_CAMERA);
                break;
        }
    }

}
