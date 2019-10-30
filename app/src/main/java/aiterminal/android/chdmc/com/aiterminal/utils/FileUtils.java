package aiterminal.android.chdmc.com.aiterminal.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.EventLog;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhouyuhao on 2019/10/28.
 */

public class FileUtils {
    public static String saveBitmap(Bitmap bitmap, String fileName) {
        File filePic;

        String savePath = Environment.getExternalStorageDirectory().getAbsolutePath();

        try {
            filePic = new File(savePath + "/" +fileName  + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return filePic.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("xxx", "saveBitmap: 2return");
            return null;
        }
    }

}
