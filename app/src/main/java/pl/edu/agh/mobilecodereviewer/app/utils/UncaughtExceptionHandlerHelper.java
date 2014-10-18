package pl.edu.agh.mobilecodereviewer.app.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

public class UncaughtExceptionHandlerHelper {

    public static final String SEND_EMAIL_DIALOG_TITLE = "Sending crash report";

    public static final String CRASH_REPORT_EMAIL_ADDRESS = "mobilecodereviewer@gmail.com";

    public static final String CRASH_REPORT_EMAIL_SUBJECT = "[MobileCodeReviewer] crash log report";

    public static final String CRASH_REPORT_EMAIL_BODY = "Something went wrong with application. See attached information for details.";

    public static final String CRASH_LOG_RELATIVE_FILE_PATH = "/vm/";

    public static final String CRASH_LOG_FILE_NAME = ".crash_report.txt";

    public static final String CRASH_LOG_TMP_FILE_NAME = ".crash_report_tmp.txt";

    public static final String CRASH_LOG_ERROR_TAG = "[MobileCodeReviewer] CrashLog";

    public static boolean checkIfPendingReportsExist(){
        File crashLogFile = new File(UncaughtExceptionHandlerHelper.getCrashLogTmpFilePath());
        return crashLogFile.exists();
    }

    public static void prepareFileToSend() {

        File src = new File(getCrashLogTmpFilePath());
        File dst = new File(getCrashLogFilePath());

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void prepareCrashLogFile(Context context, Throwable exception){
        String fileContent = "";
        fileContent += prepareReportHeader() + "\n\n";
        fileContent += prepareEnvironmentInformation(context) + "\n\n";
        fileContent += prepareExceptionInformation(exception) + "\n\n";
        fileContent += prepareFullApplicationLog();
        fileContent += prepareReportEnd();
        saveAsFile(fileContent);
    }

    public static String getCrashLogTmpFilePath(){
        return getCrashLogFileDirectory() + UncaughtExceptionHandlerHelper.CRASH_LOG_TMP_FILE_NAME;
    }

    public static String getCrashLogFilePath() {
        return getCrashLogFileDirectory() + UncaughtExceptionHandlerHelper.CRASH_LOG_FILE_NAME;
    }

    private static String getCrashLogFileDirectory(){
        return Environment.getExternalStorageDirectory().getAbsolutePath() + UncaughtExceptionHandlerHelper.CRASH_LOG_RELATIVE_FILE_PATH;

    }

    private static String prepareReportHeader(){
        Date CurDate = new Date();
        return "Error Report collected on : " + CurDate.toString();
    }

    private static String prepareReportEnd() {
        return "****  End of current Report ***";
    }


    private static String prepareEnvironmentInformation(Context context){

        String informationData = "";

        informationData += "Informations :\n==============\n";

        PackageManager pm = context.getPackageManager();

        try {
            PackageInfo pi;
            pi = pm.getPackageInfo(context.getPackageName(), 0);

            informationData += prepareInformation("Version", pi.versionName);
            informationData += prepareInformation("Version code", String.valueOf(pi.versionCode));
            informationData += prepareInformation("Package", pi.packageName);

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(CRASH_LOG_ERROR_TAG, "prepare Version or Package informationa failed", e);
        }

        informationData += prepareInformation("File path", context.getFilesDir().getAbsolutePath());
        informationData += prepareInformation("Phone model", Build.MODEL);
        informationData += prepareInformation("Android version", Build.VERSION.RELEASE);
        informationData += prepareInformation("Board", Build.BOARD);
        informationData += prepareInformation("Brand", Build.BRAND);
        informationData += prepareInformation("Device", Build.DEVICE);
        informationData += prepareInformation("Display", Build.DISPLAY);
        informationData += prepareInformation("Finger print", Build.FINGERPRINT);
        informationData += prepareInformation("Host", Build.HOST);
        informationData += prepareInformation("ID", Build.ID);
        informationData += prepareInformation("Model", Build.MODEL);
        informationData += prepareInformation("Product", Build.PRODUCT);
        informationData += prepareInformation("Tags", Build.TAGS);
        informationData += prepareInformation("Time", String.valueOf(Build.TIME));
        informationData += prepareInformation("Type", Build.TYPE);
        informationData += prepareInformation("User", Build.USER);

        return informationData;
    }

    private static String prepareFullApplicationLog(){

        String logData = "";

        logData +=  "Application log :\n==============\n";

        try {
            java.lang.Process process = Runtime.getRuntime().exec("logcat -d");

            String processId = String.valueOf(android.os.Process.myPid());

            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(processId)) {
                    printWriter.write(line + "\n");
                }
            }

            logData += result.toString();

            return logData;
        }catch(Exception e) {
            Log.e(CRASH_LOG_ERROR_TAG,"preparing full application log failed",e);
        }

        return  logData;
    }


    private static String prepareExceptionInformation(Throwable exception){

        String exceptionInformation = "";

        exceptionInformation +=  "Stack :\n==============\n";

        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        exception.printStackTrace(printWriter);
        String stackTrace = result.toString();

        exceptionInformation += stackTrace;

        Throwable cause = exception.getCause();
        if(cause != null ) {
            exceptionInformation += "\nCause :\n==============\n";
        }

        while (cause != null) {
            cause.printStackTrace( printWriter );
            exceptionInformation += result.toString();
            cause = cause.getCause();
        }

        printWriter.close();

        return exceptionInformation;
    }

    public static void saveAsFile(String crashFileContent)
    {
        try {
            String dirPath = getCrashLogFileDirectory();
            File dir = new File(dirPath);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(getCrashLogTmpFilePath());
            if(!file.exists()) {
                file.createNewFile();

                FileOutputStream trace = new FileOutputStream(file);
                PrintStream printStream;
                printStream = new PrintStream(trace);
                printStream.println(crashFileContent);
                printStream.close();

                trace.close();
            }
        }
        catch(Exception e) {
            Log.e(CRASH_LOG_ERROR_TAG,"savings crash file content to file failed",e);
        }
    }

    private static String prepareInformation(String key, String value){
        return key + " : " + value + "\n";
    }
}
