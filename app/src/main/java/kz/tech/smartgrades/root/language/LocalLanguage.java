package kz.tech.smartgrades.root.language;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocalLanguage implements ILanguage {
    private static final String SELECTED_LANGUAGE = "PREFS_LOCALE_LANGUAGE";
    private Context context;
    public LocalLanguage(Context context) {
        this.context = context;
        if (getLocalLanguage() == null) {
            setLocalLanguage("en");
        } if (getLocalLanguage() != null && getLocalLanguage().equals("")) {
            setLocalLanguage("en");
        }
   //     setLocaleString(getStringLocale());
  //    String a = getStringLocale();
   //     android.util.Log.e("Tag", " Msg");

    }
    @Override
    public Resources getLanguage() {
        Locale locale = new Locale(getLocalLanguage());
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
    @Override
    public String getLocalLanguage() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, Locale.getDefault().getLanguage());
    }
    @Override
    public void setLocalLanguage(String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration configuration = context.getResources().getConfiguration();
            configuration.setLocale(locale);
            configuration.setLayoutDirection(locale);
            context.createConfigurationContext(configuration);
        } else {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Resources resources = context.getResources();
            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLayoutDirection(locale);
            }
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
    }

}
