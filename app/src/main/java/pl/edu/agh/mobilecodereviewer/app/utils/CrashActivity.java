package pl.edu.agh.mobilecodereviewer.app.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import pl.edu.agh.mobilecodereviewer.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;

public class CrashActivity extends RoboActivity {

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_CrashActivity_message)
    private String crashMessage;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_CrashActivity_show)
    private String showDetailsButtonLabel;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_CrashActivity_hide)
    private String hideDetailsButtonLabel;

    private boolean showDetails = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unexpected_crash);
		
		final TextView textView = (TextView)findViewById(R.id.unexpectedCrashMessageTextView);
		textView.setText(crashMessage);
		
		findViewById(R.id.unexpectedCrashSendButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + UncaughtExceptionHandlerHelper.CRASH_LOG_RELATIVE_FILE_PATH + UncaughtExceptionHandlerHelper.CRASH_LOG_FILE_NAME;
				sendErrorMail(CrashActivity.this, filePath);
				finish();
			}
		});

        findViewById(R.id.unexpectedCrashShowHideDetailsButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Button button = (Button) v;
                TextView crashDetailsTextView = (TextView) findViewById(R.id.unexpectedCrashDetailsTextView);

                if(!showDetails) {
                    String crashDetails = getIntent().getStringExtra(UncaughtExceptionHandlerHelper.CRASH_INFORMATION_INTENT_EXTRA);
                    crashDetailsTextView.setText(crashDetails);
                    button.setText(hideDetailsButtonLabel);
                } else {
                    crashDetailsTextView.setText("");
                    button.setText(showDetailsButtonLabel);
                }

                showDetails = !showDetails;
            }
        });

	}
	
	private void sendErrorMail(Context context , String filePath) {

		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		
		sendIntent.setType("plain/text");
		sendIntent.putExtra(Intent.EXTRA_EMAIL,new String[] {UncaughtExceptionHandlerHelper.CRASH_REPORT_EMAIL_ADDRESS});
		sendIntent.putExtra(Intent.EXTRA_TEXT, UncaughtExceptionHandlerHelper.CRASH_REPORT_EMAIL_BODY);
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, UncaughtExceptionHandlerHelper.CRASH_REPORT_EMAIL_SUBJECT);
		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)) );

		sendIntent.setType("message/rfc822");
		context.startActivity( Intent.createChooser(sendIntent, "Mobile Code Reviewer"));
	}
}
