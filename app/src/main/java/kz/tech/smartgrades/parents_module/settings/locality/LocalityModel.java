package kz.tech.smartgrades.parents_module.settings.locality;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalityModel {

    @SerializedName("image")
    @Expose
    private int image;
    @Nullable
    @SerializedName("text")
    @Expose
    private String text;
    @Nullable
    @SerializedName("language")
    @Expose
    private String language;
    public LocalityModel() {}

    public LocalityModel(int image, @Nullable String text, @Nullable String language) {
        this.image = image;
        this.text = text;
        this.language = language;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Nullable
    public String getText() {
        return text;
    }

    public void setText(@Nullable String text) {
        this.text = text;
    }

    @Nullable
    public String getLanguage() {
        return language;
    }

    public void setLanguage(@Nullable String language) {
        this.language = language;
    }
}
