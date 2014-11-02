package pl.edu.agh.mobilecodereviewer.exceptions.handlers;
import android.content.Context;

import pl.edu.agh.mobilecodereviewer.utilities.PreferencesAccessor;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Context context;

    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    public UncaughtExceptionHandler(Context context, Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler) {
        this.context = context;
        this.defaultUncaughtExceptionHandler = defaultUncaughtExceptionHandler;
    }

    public void uncaughtException(Thread thread, Throwable exception) {

        PreferencesAccessor.deleteLastUsedConfiguration();

        if(!UncaughtExceptionHandlerHelper.checkIfPendingReportsExist()) {
            UncaughtExceptionHandlerHelper.prepareCrashLogFile(context, exception);
        }
        defaultUncaughtExceptionHandler.uncaughtException(thread, exception);

	}
}
