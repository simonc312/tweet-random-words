package com.simonc312.apps.tweetrandomwords.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.simonc312.apps.tweetrandomwords.R;
import com.simonc312.apps.tweetrandomwords.rest.RestApplication;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.compose_tweet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handleTextChange(et_status.getText().toString());
    }

    @OnTextChanged(R.id.tv_status)
    public void handleTextChange(CharSequence text){
        tv_char_count.setText(String.format("%d/140",text.length()));
        btn_send.setEnabled(text.length() > 0);
    }

    @OnClick(R.id.btn_send)
    public void handleSend(){
        sendPostRequest(et_status.getText().toString());
    }

    private void sendPostRequest(String status) {
        RestApplication.getRestClient().postStatus(status,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                setResult(RESULT_OK);
                Toast.makeText(UpdateStatusActivity.this,R.string.update_status_success,Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                setResult(RESULT_CANCELED);
                Toast.makeText(UpdateStatusActivity.this,R.string.update_status_error,Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
