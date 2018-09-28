package com.aguilar.joseph.runtimepermissionsjoseph;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.support.v4.app.ActivityCompat.requestPermissions;

public class BottomSheetDialogCreator {

    private BottomSheetDialog mBottomSheetDialog;
    private View sheetView;
    private Button okButton;
    private ImageView imgDialog;
    private TextView titleDialog;
    private TextView messageDialog;


    public BottomSheetDialogCreator(Activity act, Context context, int id_img, int id_title, int id_message, final String[] PERMISSIONS, final int permission_code) {

        //		Bottom permission dialog
        final Activity act_final = act;
        mBottomSheetDialog = new BottomSheetDialog(context);
        sheetView = act.getLayoutInflater().inflate(R.layout.bottom_sheet_permission_dialog, null);
        okButton = sheetView.findViewById(R.id.button_accept_permission);
        imgDialog = sheetView.findViewById(R.id.img_dialog_permission);
        titleDialog = sheetView.findViewById(R.id.title_dialog_permission);
        messageDialog = sheetView.findViewById(R.id.message_dialog_permission);

        imgDialog.setImageResource(id_img);
        titleDialog.setText(id_title);
        messageDialog.setText(id_message);

        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                mBottomSheetDialog.dismiss();
                requestPermissions(act_final, PERMISSIONS, permission_code);
                Log.d("PermissionCancelTAG", " On click");
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }


    private static void RequestPermissionRuntime(Activity act_final,String[] PERMISSIONS,int permission_code) {
        requestPermissions( act_final, PERMISSIONS, permission_code);
    }


}
