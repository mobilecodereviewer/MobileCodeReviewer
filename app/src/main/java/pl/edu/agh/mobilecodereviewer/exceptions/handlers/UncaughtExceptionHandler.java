package pl.edu.agh.mobilecodereviewer.exceptions.handlers;
import android.content.Context;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Context context;

    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    public UncaughtExceptionHandler(Context context, Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler) {
        this.context = context;
        this.defaultUncaughtExceptionHandler = defaultUncaughtExceptionHandler;
    }

    public void uncaughtException(Thread thread, Throwable exception) {

        if(!UncaughtExceptionHandlerHelper.checkIfPendingReportsExist()) {
            UncaughtExceptionHandlerHelper.prepareCrashLogFile(context, exception);
        }
        defaultUncaughtExceptionHandler.uncaughtException(thread, exception);

	}
}
