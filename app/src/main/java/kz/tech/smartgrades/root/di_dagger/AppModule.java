package kz.tech.smartgrades.root.di_dagger;

import android.content.Context;

import androidx.room.Room;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.tech.smartgrades.root.alert.AlertWindow;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.db.IFamilyDao;
import kz.tech.smartgrades.root.family_room.FamilyRoom;
import kz.tech.smartgrades.root.family_room.IFamily;
import kz.tech.smartgrades.root.firebase.FireBasePath;
import kz.tech.smartgrades.root.firebase.IFireBase;
import kz.tech.smartgrades.root.hardware_access.HardwareAccess;
import kz.tech.smartgrades.root.hardware_access.IHardwareAccess;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.language.LocalLanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginUser;
import kz.tech.smartgrades.root.date.DateCustomPicker;
import kz.tech.smartgrades.root.date.IDateCustomPicker;
import kz.tech.smartgrades.root.db.AppDatabase;
import kz.tech.smartgrades.root.device.DeviceConfiguration;
import kz.tech.smartgrades.root.device.IDevice;
import kz.tech.smartgrades.root.fragments.FragmentReplace;
import kz.tech.smartgrades.root.fragments.IFragments;
import kz.tech.smartgrades.root.prefs.IPreferences;
import kz.tech.smartgrades.root.prefs.Preferences;


@Module
public class AppModule {
    private Context c;
    public AppModule(Context c) {
        this.c = c;
    }
    @Singleton @Provides
    public Context provideContext(){
        return c;
    }

    ////////////////////       LOGIN USER       ////////////////////
    @Singleton
    @Provides
    ILogin providesLogin(LoginUser loginUser) {
        return loginUser;
    }
    @Singleton
    @Provides
    LoginUser providesLoginUser(Context c) {
        return new LoginUser(c);
    }

    ////////////////////       FRAGMENTS       ////////////////////
    @Singleton
    @Provides
    IFragments providesFragments(FragmentReplace fragmentReplace) {
        return fragmentReplace;
    }
    @Singleton
    @Provides
    FragmentReplace providesFragmentReplace() {
        return new FragmentReplace();
    }

    ////////////////////       HARDWARE ACCESS       ////////////////////
    @Singleton
    @Provides
    IHardwareAccess providesHardware(HardwareAccess hardwareAccess) {
        return hardwareAccess;
    }
    @Singleton
    @Provides
    HardwareAccess providesHardwareAccess(Context c) {
        return new HardwareAccess(c);
    }

    ////////////////////       ALERT       ////////////////////
    @Singleton
    @Provides
    IAlert providesAlert(AlertWindow alertWindow) {
        return alertWindow;
    }
    @Singleton
    @Provides
    AlertWindow providesAlertWindow(Context c) {
        return new AlertWindow(c);
    }

    ////////////////////       FAMILY ROOM       ////////////////////
    @Singleton
    @Provides
    IFamily providesFamily(FamilyRoom familyRoom) {
        return familyRoom;
    }
    @Singleton
    @Provides
    FamilyRoom providesFamilyRoom() {
        return new FamilyRoom();
    }

    ////////////////////       FIRE BASE       ////////////////////
    @Singleton
    @Provides
    IFireBase providesFireBase(FireBasePath fireBaseQuery) {
        return fireBaseQuery;
    }
    @Singleton
    @Provides
    FireBasePath providesFireBaseQuery() {
        return new FireBasePath();
    }

    ////////////////////       ROOM SQL        ////////////////////////////////
    @Singleton
    @Provides
    public AppDatabase provideDatabase(Context c) {
        return Room.databaseBuilder(c, AppDatabase.class, "database")
                .build();
    }
    @Singleton @Provides
    public IFamilyDao provideNewsDao(AppDatabase appDatabase){
        return appDatabase.tasksDao();
    }
    /////////////////////////////////////////////////////////////////////////////

    //////////////       SHARED PREFERENCES         ////////////////////////////
    @Provides
    @Singleton
    IPreferences providesPreferences(Preferences preferences) {
        return preferences;
    }

    @Provides
    @Singleton
    Preferences providesSharedPreferences(Context c) {
        return new Preferences(c);
    }
    /////////////////////////////////////////////////////////////////////////////

    ////////////           LOCALITY (LANGUAGE)             ////////////////////
    @Provides
    @Singleton
    ILanguage providesIRes(LocalLanguage localeLanguage) {
        return localeLanguage;
    }

    @Provides
    @Singleton
    LocalLanguage providesLocaleLanguage(Context c) {
        return new LocalLanguage(c);
    }
    //////////////////////////////////////////////////////////////////////////////



    /////////////            DATE CUSTOM PICKER            ////////////////////
    @Provides
    @Singleton
    IDateCustomPicker providesDate(DateCustomPicker dateCustomPicker) {
        return dateCustomPicker;
    }
    @Provides
    @Singleton
    DateCustomPicker providesDateCustomPicker(Context c) {
        return new DateCustomPicker(c);
    }

    //////////////                 DEVICE GENERATION NUMBER                //////////////
    @Provides
    @Singleton
    IDevice providesDevice(DeviceConfiguration deviceConfiguration) {
        return deviceConfiguration;
    }
    @Provides
    @Singleton
    DeviceConfiguration providesDeviceConfiguration() {
        return new DeviceConfiguration();
    }

}
