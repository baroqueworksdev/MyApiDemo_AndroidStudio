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
     * @param url      request url
     * @param listener listener for Response or Error
     */
    public void get(String url, final ResponseListener listener) {

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
    }

    /**
     * Request(StringRequest) with params
     *
     * @param url      request url
     * @param listener listener for Response or Error
     * @param params   value of setting Http Params
     * @param headers  value of setting Http headers
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


    /**
     * Generate ImageListener
     *
     * @param listener          listener for Response or Error
     * @param view              ImageView
     * @param defaultImageResId display image id before Loading
     * @param errorImageResId   display image id when done error
     * @return ImageListener
     */
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

    /**
     * Request(ImageListener) for Getting Image
     *
     * @param url      url for Request
     * @param listener ImageListener
     */
    public void get(String url, ImageListener listener) {
        mImageLoader.get(url, listener);
    }

    /**
     * Request(ImageListener) for Getting Image
     *
     * @param url       url for Request
     * @param listener  ImageListener
     * @param maxWidth  maximum width for Image file
     * @param maxHeight maximum height for Image file
     */
    public void get(String url, ImageListener listener, int maxWidth, int maxHeight) {
        mImageLoader.get(url, listener, maxWidth, maxHeight);
    }

    /**
     * Request(ImageListener) for Getting Image
     *
     * @param url               url for Request
     * @param listener          listener for Response or Error
     * @param view              ImageView
     * @param defaultImageResId display image id before Loading
     * @param errorImageResId   display image id when done error
     */
    public void get(String url, final ResponseListener listener, final ImageView view,
            final int defaultImageResId, final int errorImageResId) {
        ImageListener imageListener = getImageListener(listener, view, defaultImageResId,
                errorImageResId);
        mImageLoader.get(url, imageListener);
    }


    /**
     * Get ImageLoader
     *
     * @return ImageLoader
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
