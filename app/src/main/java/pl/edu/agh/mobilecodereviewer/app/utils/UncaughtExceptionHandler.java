package pl.edu.agh.mobilecodereviewer.app.utils;
import android.content.Context;
import android.content.Intent;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Context context;

    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    public UncaughtExceptionHandler(Context context, Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler) {
        this.defaultUncaughtExceptionHandler = defaultUncaughtExceptionHandler;
        this.context = context;
    }

    public void uncaughtException(Thread thread, Throwable exception) {

		String report = UncaughtExceptionHandlerHelper.prepareCrashLogFile(context, exception);
		  
		Intent intent = new Intent(context, CrashActivity.class);
		intent.putExtra(UncaughtExceptionHandlerHelper.CRASH_INFORMATION_INTENT_EXTRA, report);

		UncaughtExceptionHandlerHelper.saveAsFile(report, context);

        context.startActivity(intent);

        defaultUncaughtExceptionHandler.uncaughtException(thread, exception);

	}
}
