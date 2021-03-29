package kz.tech.smartgrades;

import android.app.Application;

import kz.tech.smartgrades.root.di_dagger.AppComponent;
import kz.tech.smartgrades.root.di_dagger.AppModule;
import kz.tech.smartgrades.root.di_dagger.DaggerAppComponent;

public class App extends Application {
    //      APP        //
    private static App app;
    public static App app(){
        return app;
    }

    private AppComponent appComponent;
    public AppComponent getComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }
}


