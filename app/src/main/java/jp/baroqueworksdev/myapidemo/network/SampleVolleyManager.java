package jp.baroqueworksdev.myapidemo.network;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageLoader.ImageContainer;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import java.util.Map;

public class SampleVolleyManager {

    public interface ResponseListener {

        public void onResponse(Object response);

        public void onErrorResponse(VolleyError volleyError);
    }

    private static SampleVolleyManager sInstance;

    /**
     * Object for synchronized
     */
    private static final Object sLock = new Object();

    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;

    private SampleVolleyManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(32 * 1024 * 1024));
        mRequestQueue.start();
    }

    public static synchronized SampleVolleyManager getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = new SampleVolleyManager(context);
        }
        return sInstance;
    }

    /**
     * Request(StringRequest)
     *
     * @param url
     * @param listener
     * @note create a new GET request and add queue
     */
    public void get(String url, final ResponseListener listener) {

        if (mRequestQueue != null) {
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

    /**
     * Request(StringRequest) with params
     *
     * @param url
     * @param listener
     * @param params
     * @param headers
     * @note create a new GET request and add queue
     */
    public void get(String url, final ResponseListener listener, final Map<String, String> params
            , final Map<String, String> headers) {

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
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };

        mRequestQueue.add(request);
    }


    public ImageListener getImageListener(final ResponseListener listener, final ImageView view,
                                          final int defaultImageResId, final int errorImageResId) {

        ImageListener imageListener = new ImageListener() {
            @Override
            public void onResponse(ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    view.setImageBitmap(response.getBitmap());
                    if (listener != null) {
                        listener.onResponse(response.getBitmap());
                    }

                } else if (defaultImageResId != 0) {
                    view.setImageResource(defaultImageResId);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorImageResId != 0) {
                    view.setImageResource(errorImageResId);
                    listener.onErrorResponse(error);
                }
            }
        };

        return imageListener;
    }

    public void get(String url, ImageListener listener) {
        mImageLoader.get(url, listener);
    }

    public void get(String url, ImageListener listener, int maxWidth, int maxHeight) {
        mImageLoader.get(url, listener, maxWidth, maxHeight);
    }

    public void get(String url, final ResponseListener listener, final ImageView view,
                    final int defaultImageResId, final int errorImageResId) {
        ImageListener imageListener = getImageListener(listener, view, defaultImageResId, errorImageResId);
        mImageLoader.get(url, imageListener);
    }


    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
