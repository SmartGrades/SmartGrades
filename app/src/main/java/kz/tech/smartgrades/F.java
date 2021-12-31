package kz.tech.smartgrades;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class F {
    public static final String FAMILY_MEMBER_LIST = "FamilyMemberListFragment";
    public static final String APP_LOCK_LIST = "AppLockListFragment";
    public static final String ACCESS = "AccessFragment";
    public static final String QUICK_ACCESS_SIGN_IN = "QuickAccessSignInFragment";
    public static final String CHILD_ACCESS = "ChildAccessFragment";
    public static final String REGISTRATION_SELECT = "RegistrationSelectFragment";
    public static final String FORGOT_PASSWORD = "ForgotPasswordFragment";
    public static final String SIGN_IN = "SignInFragment";
    public static final String TargetParentId = "TargetParentId";

    public static final String Index = "Index";
    public static final String CardName = "CardName";

    public static final String MessageType  = "MessageType";
    public static final String ChildLessonId  = "ChildLessonId";

    public static final String Code = "Code";
    public static final String VerificationСode = "VerificationСode";
    public static final String UserId = "UserId";
    public static final String MentorId = "MentorId";
    public static final String ColumnId = "ColumnId";
    public static final String Name = "Name";
    public static final String UserName = "UserName";
    public static final String AboutMe = "AboutMe";
    public static final String SchoolId = "SchoolId";
    public static final String TeacherId = "TeacherId";
    public static final String FirstName = "FirstName";
    public static final String LastName = "LastName";
    public static final String Description = "Description";
    public static final String SourceId = "SourceId";
    public static final String TargetId = "TargetId";
    public static final String ChildId = "ChildId";
    public static final String FamilyGroupId = "FamilyGroupId";
    public static final String ClassId = "ClassId";
    public static final String ParentId = "ParentId";
    public static final String SponsorId = "SponsorId";
    public static final String Password = "Password";
    public static final String StudentId = "StudentId";
    public static final String Grade = "Grade";

    public static final String Avatar = "Avatar";

    public static final String Message = "Message";
    public static final String MessageId = "MessageId";

    public static final String GroupName = "GroupName";
    public static final String GroupId = "GroupId";
    public static final String LessonId = "LessonId";
    public static final String Phone = "Phone";
    public static final String Lesson = "Lesson";
    public static final String LessonName = "LessonName";
    public static final String Type = "Type";
    public static final String Data = "Data";
    public static final String ChatId = "ChatId";

    public static final String EnKey = "EnKey";
    public static final String AuthKey = "AuthKey";
    public static final String CardNumber = "CardNumber";
    public static final String Date = "Date";
    public static final String FullName = "FullName";
    public static final String Cryptogram = "Cryptogram";
    public static final String Bank = "Bank";
    public static final String Sum = "Sum";
    public static final String AccountId = "AccountId";


    public static final String InterForm = "InterForm";
    public static final String PrivateChat = "PrivateChat";
    public static final String MentorList = "MentorList";

    public static final String Id = "Id";
    public static final String CardId = "CardId";
    public static final String InterFormId = "InterFormId";

    private static final String USERNAME_PATTERN = "^[a-z0-9]([._](?![._])|[a-z0-9]){1,18}[a-z0-9]$";
    //private static final String NAME_PATTERN = "^[a-zA-Zа-яА-Я]([-](?![-])|[a-zA-Zа-яА-Я]){0,62}[a-zA-Zа-яА-Я]$";
    private static final String NAME_PATTERN = "^[\\p{Alpha}]([-](?![-])|[\\p{Alpha}]{0,62}[\\p{Alpha}])$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,64}$";

    private static final Pattern username_pattern = Pattern.compile(USERNAME_PATTERN);
    private static final Pattern name_pattern = Pattern.compile(NAME_PATTERN);
    private static final Pattern password_pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isUsernameValid(final String username) {
        Matcher matcher = username_pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean isPasswordValid(final String password) {
        Matcher matcher = password_pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isNameValid(final String name) {
        Matcher matcher = name_pattern.matcher(name);
        return matcher.matches();
    }
}
