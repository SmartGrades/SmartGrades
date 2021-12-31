package kz.tech.smartgrades;

public class MSG_TYPE {
    public static final int Message = 0;//"Message";
    public static final int Grade = 1;
    public static final int SKIP_LATE = 2;//""Late";
    public static final int SKIP_WAC = 3;//"WasAbsentCause";
    public static final int SKIP_WANC = 4;//"WasAbsentNonCause";
    public static final int SKIP_SICK = 5;//"Sick";
    public static final int REMOTE_GRADE = 6;//"Sick";

    public static final String SKIP_LATE_MSG = "Опаздание";
    public static final String SKIP_WAC_MSG = "Отсутствие (по уваж. причине)";
    public static final String SKIP_WANC_MSG = "Отсутствие (без уваж. причины)";
    public static final String SKIP_SICK_MSG = "Болен";
    public static final String SKIP_REMOTE_GRADE = "Оценка отменена";

}
