package com.simonc312.apps.tweetrandomwords.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.rest.RestApplication;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Simon on 2/28/2016.
 */
public class NavHeaderUser extends LinearLayout {

    @Bind(R.id.iv_picture)
    ImageView iv_picture;
    @Bind(R.id.iv_background)
    ImageView iv_background;
    @Bind(R.id.tv_fullname)
    TextView tv_fullname;
    @Bind(R.id.tv_username)
    TextView tv_username;
    @Bind(R.id.tv_location)
    TextView tv_location;

    public NavHeaderUser(Context context) {
        this(context,null);
    }

    public NavHeaderUser(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavHeaderUser(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setContentView();
    }

    private void setContentView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_profile, this);
        ButterKnife.bind(this);
    }

    @Override
    public void onFinishInflate(){
        super.onFinishInflate();
        if(RestApplication.isLoggedIn())
            fetchProfileData();
    }

    private void updateImage(String url, ImageView imageView) {
        Glide.with(getContext())
                .load(url)
                .error(R.color.colorPrimary)
                .centerCrop()
                .into(imageView);
    }

    private void fetchProfileData() {
        RestApplication.getRestClient().getAccountInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String profileImage = response.getString("profile_image_url");
                    String backgroundImage = response.getString("profile_background_image_url");
                    String fullname = response.getString("name");
                    String username = response.getString("screen_name");
                    String location = response.getString("location");

                    tv_fullname.setText(fullname);
                    tv_username.setText(username);
                    tv_location.setText(location);
                    updateImage(profileImage,iv_picture);
                    updateImage(backgroundImage,iv_background);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("profile fetch error", responseString);
            }

        });
    }
}
