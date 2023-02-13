package com.dct.androidexam.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dct.util.Constant;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


@SuppressLint("Registered")
public class VolleyRestApi extends AppCompatActivity {
    Object result;

    public void volleyWebservice(Context context, String url, final String apiKey, final VolleyCallback callback) {
        Log.e("Url to server", url);
        Log.e("Request to server", apiKey);
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.e("Response from server", response.toString());
                        result = response;
                        if (!response.toString().equals("")) {
                            callback.onSuccess(result);
                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onFailure(error);
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "text/xml; charset=" +
                        getParamsEncoding();
            }
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("api_key", Constant.apiKey);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public interface VolleyCallback {
        void onSuccess(Object result);

        void onFailure(VolleyError error);
    }


}
