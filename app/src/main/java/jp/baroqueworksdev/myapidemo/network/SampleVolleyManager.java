package jp.baroqueworksdev.myapidemo.network;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;

public class SampleVolleyManager {

    public interface ResponseListener {

        public void onResponse(String response);

        public void onErrorResponse(VolleyError volleyError);
    }

    private static SampleVolleyManager sInstance;

    /** Object for synchronized */
    private static final Object sLock = new Object();

    private RequestQueue mRequestQueue;

    private SampleVolleyManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        mRequestQueue.start();
    }

    public static synchronized SampleVolleyManager getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = new SampleVolleyManager(context);
        }
        return sInstance;
    }

    /**
     * @note create a new GET request and add queue
     */
    public void getForStringRequest(String url,final ResponseListener listener) {

        if(mRequestQueue != null){
            StringRequest request = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            listener.onResponse(s);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            listener.onErrorResponse(volleyError);
                        }
                    }
            );

            mRequestQueue.add(request);
        } else {
        }
    }

}
