package pl.edu.agh.mobilecodereviewer.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.controllers.utilities.ChangesFilter;

public class PreferencesAccessor {

    public static final String PREFERENCE_SAVED_CONFIGURATIONS = "PREFERENCE_SAVED_CONFIGURATIONS";

    public static final String PREFERENCE_LAST_USED_CONFIGURATION = "PREFERENCE_LAST_USED_CONFIGURATION";

    public static final String PREFERENCE_SAVED_CHANGE_FILTERS = "PREFERENCE_SAVED_CHANGE_FILTERS";

    private static final String PREFERENCES_FILE_NAME = "MCR.prefs";

    private static SharedPreferences preferences;

    public static void initialize(Context context){
        PreferencesAccessor.preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
    }

    public static ConfigurationInfo getLastUsedConfiguration(){
        String json = PreferencesAccessor.preferences.getString(PREFERENCE_LAST_USED_CONFIGURATION, null);
        if(json != null){
            return new Gson().fromJson(json, ConfigurationInfo.class);
        }

        return null;
    }

    public static void saveLastUsedConfiguration(ConfigurationInfo configurationInfo){
        SharedPreferences.Editor prefsEditor = PreferencesAccessor.preferences.edit();
        String json  = new Gson().toJson(configurationInfo);
        prefsEditor.putString(PREFERENCE_LAST_USED_CONFIGURATION, json);
        prefsEditor.commit();
    }

    public static void deleteLastUsedConfiguration(){
        SharedPreferences.Editor prefsEditor = PreferencesAccessor.preferences.edit();
        prefsEditor.remove(PREFERENCE_LAST_USED_CONFIGURATION);
        prefsEditor.commit();
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

    public static void saveChangeFilter(ChangesFilter filter) {
        SharedPreferences.Editor prefsEditor = PreferencesAccessor.preferences.edit();
        List<ChangesFilter> currentSavedChangeFilters = getChangesFilters();
        currentSavedChangeFilters.add(filter);
        String json = new Gson().toJson(currentSavedChangeFilters);
        prefsEditor.putString(PREFERENCE_SAVED_CHANGE_FILTERS, json);
        prefsEditor.commit();
    }

    public static void saveAllChangeFilter(List<ChangesFilter> filters) {
        SharedPreferences.Editor prefsEditor = PreferencesAccessor.preferences.edit();
        String json = new Gson().toJson(filters);
        prefsEditor.putString(PREFERENCE_SAVED_CHANGE_FILTERS, json);
        prefsEditor.commit();
    }

    public static void removeChangeFilter(ChangesFilter filter) {
        List<ChangesFilter> allChangesFilter = getChangesFilters();
        allChangesFilter.remove(filter);
        saveAllChangeFilter(allChangesFilter);
    }

    public static List<ChangesFilter> getChangesFilters() {
        String json = PreferencesAccessor.preferences.getString(PREFERENCE_SAVED_CHANGE_FILTERS, null);

        if(json != null){
            Type typeToken = new TypeToken< List <ChangesFilter>>() {}.getType();
            return new Gson().fromJson(json, typeToken);
        }

        return new LinkedList<ChangesFilter>();
    }
}
















