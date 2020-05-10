package shiful.android.healthconsult;

public class Constant {

    public static final String MAIN_URL = "http://finalproject.ml/health_consult/android";

    public static final String SIGNUP_URL = MAIN_URL+"/register.php";
    public static final String LOGIN_URL = MAIN_URL+"/login.php";
    public static final String USER_VIEW_URL = MAIN_URL+"/view_user.php?cell=";



    //Keys for server communications

    public static final String KEY_NAME = "name";
    public static final String KEY_CELL = "cell";
    public static final String KEY_AC_TYPE = "account_type";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //share preference
    //We will use this to store the user cell number into shared preference
    public static final String SHARED_PREF_NAME = "shiful.android.healthconsult"; //pcakage name+ id

    //This would be used to store the cell of current logged in user
    public static final String CELL_SHARED_PREF = "cell";

    //json array name.We will received data in this array
    public static final String JSON_ARRAY = "result";
}