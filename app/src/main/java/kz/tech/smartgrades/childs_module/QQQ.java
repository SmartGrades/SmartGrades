package kz.tech.smartgrades.childs_module;


/** * Custom Application which can detect application state of whether it enter * background or enter foreground. * * @reference http://www.vardhan-justlikethat.blogspot.sg/2014/02/android-solution-to-detect-when-android.html */
    public abstract class QQQ { /* extends Application implements Application.ActivityLifecycleCallbacks {
        public static final int STATE_UNKNOWN = 0x00;
        public static final int STATE_CREATED = 0x01;
        public static final int STATE_STARTED = 0x02;
        public static final int STATE_RESUMED = 0x03;
        public static final int STATE_PAUSED = 0x04;
        public static final int STATE_STOPPED = 0x05;
        public static final int STATE_DESTROYED = 0x06;
        private static final int FLAG_STATE_FOREGROUND = -1;
        private static final int FLAG_STATE_BACKGROUND = -2;
        private int mCurrentState = STATE_UNKNOWN;
        private int mStateFlag = FLAG_STATE_BACKGROUND;
        @Override
        public void onCreate() {
            super.onCreate();
            mCurrentState = STATE_UNKNOWN; registerActivityLifecycleCallbacks(this);
        }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
         mCurrentState = STATE_CREATED; }
        @Override
        public void onActivityStarted(Activity activity) {
        if (mCurrentState == STATE_UNKNOWN || mCurrentState == STATE_STOPPED) {
            if (mStateFlag == FLAG_STATE_BACKGROUND) { applicationWillEnterForeground();
            mStateFlag = FLAG_STATE_FOREGROUND; } } mCurrentState = STATE_STARTED;
    }
    @Override
    public void onActivityResumed(Activity activity) {
        mCurrentState = STATE_RESUMED;
    } @Override public void onActivityPaused(Activity activity) {
        mCurrentState = STATE_PAUSED;
    }
    @Override public void onActivityStopped(Activity activity) {
        mCurrentState = STATE_STOPPED;
    }
    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    } @Override
    public void onActivityDestroyed(Activity activity) {
        mCurrentState = STATE_DESTROYED;
    } @Override public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (mCurrentState == STATE_STOPPED && level >= TRIM_MEMORY_UI_HIDDEN) {
            if (mStateFlag == FLAG_STATE_FOREGROUND) { applicationDidEnterBackground();
            mStateFlag = FLAG_STATE_BACKGROUND;
            }
        }else if (mCurrentState == STATE_DESTROYED && level >= TRIM_MEMORY_UI_HIDDEN) {
            if (mStateFlag == FLAG_STATE_FOREGROUND) {
                applicationDidDestroyed(); mStateFlag = FLAG_STATE_BACKGROUND;
            }
        }
    } /** * The method be called when the application been destroyed. But when the * device screen off,this method will not invoked. */
   // protected abstract void applicationDidDestroyed(); /** * The method be called when the application enter background. But when the * device screen off,this method will not invoked. */
  //  protected abstract void applicationDidEnterBackground(); /** * The method be called when the application enter foreground. */
  //  protected abstract void applicationWillEnterForeground();
  //  }*/

}