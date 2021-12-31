package kz.tech.smartgrades.root.language;

import android.content.res.Resources;

public interface ILanguage {
    Resources getLanguage();
    String getLocalLanguage();
    void setLocalLanguage(String language);
}
