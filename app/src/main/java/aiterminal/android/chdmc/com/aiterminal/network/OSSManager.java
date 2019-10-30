package aiterminal.android.chdmc.com.aiterminal.network;

import android.graphics.Bitmap;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.CannedAccessControlList;
import com.alibaba.sdk.android.oss.model.CreateBucketRequest;
import com.alibaba.sdk.android.oss.model.CreateBucketResult;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;

import aiterminal.android.chdmc.com.aiterminal.Config;
import aiterminal.android.chdmc.com.aiterminal.MainApplication;

/**
 * Created by zhouyuhao on 2019/10/27.
 */

public class OSSManager {

    private OSS mOSS;

    private static OSSManager mInstance = new OSSManager();
    private String mBucket = "alterminal";
    private String endpoint = "oss-cn-beijing.aliyuncs.com";

    private OSSManager() {

//if null , default will be init
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // connction time out default 15s
        conf.setSocketTimeout(15 * 1000); // socket timeout，default 15s
        conf.setMaxConcurrentRequest(5); // synchronous request number，default 5
        conf.setMaxErrorRetry(2); // retry，default 2
        OSSLog.enableLog(); //write local log file ,path is SDCard_path\OSSLog\logs.csv

        OSSCredentialProvider credentialProvider =
                new OSSPlainTextAKSKCredentialProvider(Config.OSS_ACCESS_KEY_ID,Config.OSS_ACCESS_KEY_SECRET);

        OSS oss = new OSSClient(MainApplication.getContext(), endpoint, credentialProvider, conf);

        CreateBucketRequest createBucketRequest = new CreateBucketRequest(mBucket);
        // 设置存储空间的访问权限为公共读，默认为私有读写。
        createBucketRequest.setBucketACL(CannedAccessControlList.PublicRead);
        // 指定存储空间所在的地域。
        createBucketRequest.setLocationConstraint("oss-cn-hangzhou");
        OSSAsyncTask createTask = oss.asyncCreateBucket(createBucketRequest, new OSSCompletedCallback<CreateBucketRequest, CreateBucketResult>() {
            @Override
            public void onSuccess(CreateBucketRequest request, CreateBucketResult result) {
                Log.d("locationConstraint", request.getLocationConstraint());
            }
            @Override
            public void onFailure(CreateBucketRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常。
                if (clientException != null) {
                    // 本地异常，如网络异常等。
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });

        mOSS = new OSSClient(MainApplication.getContext(), endpoint, credentialProvider, conf);

    }

    public static OSSManager getInstance() {
        return mInstance;
    }

    public String getPicHost() {
        return "https://" +mBucket+ "." +endpoint;
    }

    public void uploadFile(String object, String localFile, OSSCompletedCallback callback) {

        final long upload_start = System.currentTimeMillis();
        OSSLog.logDebug("upload start");

        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return;
        }

        // 构造上传请求
        OSSLog.logDebug("create PutObjectRequest ");
        PutObjectRequest put = new PutObjectRequest(mBucket, object, localFile);
        put.setCRC64(OSSRequest.CRC64Config.YES);

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                int progress = (int) (100 * currentSize / totalSize);
            }
        });

        OSSLog.logDebug(" asyncPutObject ");
        mOSS.asyncPutObject(put, callback);
    }

}
