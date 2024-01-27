package ai.lazero.lazero;

import android.util.Log;


import java.io.ByteArrayInputStream;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Buffer;
import okio.Source;

public class HttpPostBytes {
    private String url_self;
    public byte[] payload_self;

    public HttpPostBytes(String url, byte[] payload) {
        this.url_self = url;
        this.payload_self = payload;
// you've cracked the code! congrats!
    }

    public <T> RequestBody createProgressRequestBody(final MediaType contentType, final byte[] file) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {

                return (long) file.length;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                String TAG = "tagger>>> ";
                try {
                    source = Okio.source(new ByteArrayInputStream(file));
                    Buffer buf = new Buffer();
                    long remaining = contentLength();
                    long current = 0;
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        current += readCount;
                        Log.e(TAG, "current------>" + current);
                        Log.e(TAG, "remaining------>" + remaining);
//                        progressCallBack(remaining, current, callBack);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 上传文件
     *
     * @param actionUrl 接口地址
     * @param filePath  本地文件地址
     */
    public Boolean testPostBytes(String name, String value) {
        try {
            OkHttpClient client = new OkHttpClient();
            System.out.println(">> 3");
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", "randomFile",
                            this.createProgressRequestBody(MediaType.parse("application/octet-stream"), this.payload_self))
                    .addFormDataPart(name, value)
                    .build();
            System.out.println(">> 4");
            Request request = new Request.Builder()
                    .url(this.url_self)
                    .post(requestBody)
                    .build();
            System.out.println(">> 5");
            client.newCall(request).enqueue(new Callback() {
                // so what?
                // time to find a router that will always work. the lazero router!
                // so that we can hard-code localhost everywhere to get response back. pre-register some ports, or use ipv6 instead?
                @Override
                public void onFailure(final Call call, final IOException e) {
                    // Handle the error
//                    Log.e("sed")
                    // what the heck?
                    System.out.println("failed to post data: " + e.toString());
//                    System.out.println("failed to post data"+);
                }

                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        // Handle the error
                        System.out.println("failed to get response");
                    }
                    // Upload successful
                    response.close();
                    // nothing to do with response.
                }
            });
//            bis.close();
//            ois.close();
//            fs.close();
            return true;
        } catch (Exception ex) {
            // Handle the error
            System.out.println("unknown error");
            System.out.println(ex.toString());
        }
        return false;
    }
    //
}
