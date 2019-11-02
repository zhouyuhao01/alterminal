package aiterminal.android.chdmc.com.aiterminal.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import aiterminal.android.chdmc.com.aiterminal.MainApplication;

/**
 * Created by zhouyuhao on 2019/10/30.
 */

public class Utils {
    public static int dip2px(float dipValue) {
        float scale = MainApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static void showDialogAndFinish(Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        builder.show();

    }

    public static void showDialogAndDoNothing(Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                
            }
        });
        builder.show();

    }


    public static void takePhoto(int requestCode, Activity activity) {
        int hasCamera = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasCamera == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activity.startActivityForResult(captureIntent, requestCode);
        }else{
            //没有权限，向用户请求权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }


}
