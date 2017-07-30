package Config;

/**
 * Created by LENOVO on 4/20/2016.
 */
public class ConstValue {
    public  static String BASE_URL = "http://iclauncher.com/education/";
   // public  static String BASE_URL = "http://192.168.0.102:89/education/";
    public  static  String LOGIN_URL = BASE_URL+"/index.php/api/login";
    public static String STUDENT_PROFILE_URL = BASE_URL+"/index.php/api/get_student_profile";
    public static String STUDENT_ATTENDENCE_URL = BASE_URL+"/index.php/api/get_student_attendence";
    public static String EXAMS_URL = BASE_URL+"/index.php/api/get_exams";
    public static String RESULTS_URL = BASE_URL+"/index.php/api/get_results";
    public static String EVENT_URL = BASE_URL+"/index.php/api/get_school_event";
    public static String TEACHERS_URL = BASE_URL+"/index.php/api/get_school_teacher";
    public static String TOP_STUDENT_URL = BASE_URL+"/index.php/api/get_top_student";
    public static String HOLIDAY_URL = BASE_URL+"/index.php/api/get_holidays";
    public static String NOTICEBOARD_URL = BASE_URL+"/index.php/api/get_school_noticeboard";
    public static String GROWTH_URL = BASE_URL+"/index.php/api/get_student_growth";
    public static String RESULTS_REPORT_URL = BASE_URL+"/index.php/api/get_result_report";
    public static String ENQUIRY_URL = BASE_URL+"/index.php/api/get_enquiry";
    public static String SEND_ENQUIRY_URL = BASE_URL+"/index.php/api/send_enquiry";
    public static String GCM_REGISTER_URL = BASE_URL+"/index.php/api/register_gcm";

    public  static  String PREF_NAME = "education.pref";
    public static  String COMMON_KEY = "student_id";

    public static String GCM_SENDER_ID = "881835876590";
}
