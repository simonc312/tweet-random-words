package com.simonc312.apps.tweetrandomwords.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.helpers.ItemClickListener;
import com.simonc312.apps.tweetrandomwords.models.Tweet;
import com.simonc312.apps.tweetrandomwords.rest.RestApplication;
import com.simonc312.apps.tweetrandomwords.viewholders.TweetViewHolder;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Simon on 3/2/2016.
 */
public class UpdateStatusActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 123;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_status)
    EditText et_status;
    @Bind(R.id.tv_char_count)
    TextView tv_char_count;
    @Bind(R.id.btn_send)
    Button btn_send;

    @Bind(R.id.item_timeline)
    View itemView;

    @BindString(R.string.compose_tweet)
    String title;

    TweetViewHolder viewHolder;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);
        ButterKnife.bind(this);
        handleIntent(getIntent());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handleTextChange(et_status.getText().toString());
    }

    /**
     * Sets up viewholder and edit text when replying to tweet
     * @param intent
     */
    private void handleIntent(Intent intent) {
        if(intent == null) return;
        Long tweetId = intent.getLongExtra("tweetId",-1L);
        ItemClickListener.TYPE type = (ItemClickListener.TYPE) intent.getSerializableExtra("type");
        viewHolder = new TweetViewHolder(itemView);
        et_status.requestFocus();
        showSoftKeyboard(et_status);

        if(tweetId != -1L){

            String titlePrefix;
            String statusText;
            tweet = Tweet.getTweetById(tweetId);
            if(type == ItemClickListener.TYPE.REPLY){
                titlePrefix = "Reply to ";
                statusText = tweet.getDisplayUsername();
                viewHolder.setTweetOnly(tweet);
            } else{
                titlePrefix = "Retweet ";
                statusText = String.format("RT %s : %s",tweet.getDisplayUsername(),tweet.getStatus());
            }
            et_status.setText(statusText);
            et_status.setSelection(et_status.getText().length());
            title = titlePrefix + tweet.getDisplayUsername();
        }
    }

    @OnTextChanged(R.id.tv_status)
    public void handleTextChange(CharSequence text){
        tv_char_count.setText(String.format("%d/140", text.length()));
        btn_send.setEnabled(text.length() > 0);
    }

    @OnClick(R.id.btn_send)
    public void handleSend(){
        sendPostRequest(et_status.getText().toString());
    }

    private void sendPostRequest(String status) {
        String replyTo = tweet != null ? String.valueOf(tweet.getTweetId()) : null;
        RestApplication.getRestClient().postStatus(status, replyTo, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                setResult(RESULT_OK);
                Toast.makeText(UpdateStatusActivity.this, R.string.update_status_success, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                setResult(RESULT_CANCELED);
                Toast.makeText(UpdateStatusActivity.this, R.string.update_status_error, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
