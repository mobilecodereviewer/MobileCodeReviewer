package pl.edu.agh.mobilecodereviewer.commons;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.ConfigurationInfo;

public class PreferencesAccessor {

    public static final String PREFERENCE_SAVED_CONFIGURATIONS = "PREFERENCE_SAVED_CONFIGURATIONS";

    private static final String PREFERENCES_FILE_NAME = "MCR.prefs";

    private static SharedPreferences preferences;

    public static void initialize(Context context){
        PreferencesAccessor.preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
    }

    public static List<ConfigurationInfo> getConfigurations(){
        String json = PreferencesAccessor.preferences.getString(PREFERENCE_SAVED_CONFIGURATIONS, null);

        if(json != null){
            Type typeToken = new TypeToken< List <ConfigurationInfo>>() {}.getType();
            return new Gson().fromJson(json, typeToken);
        }

        return null;
    }

    public static void saveConfigurations(List<ConfigurationInfo> configurationInfos){
        SharedPreferences.Editor prefsEditor = PreferencesAccessor.preferences.edit();
        String json = new Gson().toJson(configurationInfos);
        prefsEditor.putString(PREFERENCE_SAVED_CONFIGURATIONS, json);
        prefsEditor.commit();
    }

}
