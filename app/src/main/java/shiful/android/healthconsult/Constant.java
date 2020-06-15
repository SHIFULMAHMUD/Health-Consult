package shiful.android.healthconsult;

public class Constant {

    private static final String MAIN_URL = "http://finalproject.ml/health_consult/android";
    public static final String SIGNUP_URL = MAIN_URL+"/register.php";
    public static final String DOC_SIGNUP_URL = MAIN_URL+"/doc_register.php";
    public static final String LOGIN_URL = MAIN_URL+"/login.php";
    public static final String DOC_LOGIN_URL = MAIN_URL+"/doc_login.php";
    public static final String APPOINTMENT_URL = MAIN_URL+"/appointment.php";
    public static final String USER_VIEW_URL = MAIN_URL+"/view_user.php?cell=";
    public static final String PATIENT_VIEW_URL = MAIN_URL+"/view_patient.php?cell=";
    public static final String DOC_VIEW_URL = MAIN_URL+"/view_doctor.php?cell=";
    public static final String UPDATE_PROFILE_URL = MAIN_URL+"/update_profile.php";
    public static final String CONFIRM_APPOINTMENT_URL = MAIN_URL+"/confirm_appointment.php";
    public static final String REJECT_APPOINTMENT_URL = MAIN_URL+"/reject_appointment.php";
    public static final String UPDATE_DOC_PROFILE_URL = MAIN_URL+"/update_doc_profile.php";
    public static final String CATEGORY_URL = MAIN_URL+"/category.php?";
    public static final String DOCTOR_URL = MAIN_URL+"/doctor.php?category_name=";
    public static final String AMBULANCE_URL = MAIN_URL+"/ambulance.php?";
    public static final String HISTORY_URL = MAIN_URL+"/appointment_history.php?cell=";
    public static final String PATIENT_HISTORY_URL = MAIN_URL+"/patient_history.php?cell=";
    public static final String NOTIFICATION_API_URL = MAIN_URL+"/api.php";

    //Keys for server communications

    public static final String KEY_NAME = "name";
    public static final String KEY_CELL = "cell";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_STATUS = "status";


    public static final String KEY_UPDATE_NAME = "name";
    public static final String KEY_UPDATE_CELL = "cell";
    public static final String KEY_UPDATE_GENDER = "gender";
    public static final String KEY_UPDATE_EMAIL = "email";
    public static final String KEY_UPDATE_PASSWORD = "password";

    public static final String KEY_CAT_ID = "category_id";
    public static final String KEY_CATEGORY_NAME = "category_name";

    public static final String KEY_DOC_ID = "doc_id";
    public static final String KEY_CATEGORY_ID = "cat_id";
    public static final String KEY_DOC_NAME = "doc_name";
    public static final String KEY_DOC_CELL = "cell";
    public static final String KEY_SPECIALITY = "speciality_type";
    public static final String KEY_DOC_GENDER = "gender";
    public static final String KEY_DOC_VISIT = "visit";
    public static final String KEY_QUALIFICATION = "qualification";
    public static final String KEY_DESIGNATION = "designation";
    public static final String KEY_PLACE = "place";
    public static final String KEY_COST = "cost";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_DOC_TOKEN = "doc_token";

    public static final String KEY_AMB_NAME = "ambulance_name";
    public static final String KEY_AMB_CELL = "ambulance_phone";
    public static final String KEY_AMB_PLACE = "location";

    public static final String KEY_PATIENT_NAME = "patient_name";
    public static final String KEY_PATIENT_GENDER = "gender";
    public static final String KEY_PATIENT_CELL = "patient_mobile";
    public static final String KEY_PATIENT_EMAIL = "email";
    public static final String KEY_PATIENT_REQ = "request";
    public static final String KEY_TOKEN = "token";
    //share preference
    //We will use this to store the user cell number into shared preference
    public static final String SHARED_PREF_NAME = "shiful.android.healthconsult"; //pcakage name+ id

    //This would be used to store the cell of current logged in user
    public static final String CELL_SHARED_PREF = "cell";

    //json array name.We will received data in this array
    public static final String JSON_ARRAY = "result";
}