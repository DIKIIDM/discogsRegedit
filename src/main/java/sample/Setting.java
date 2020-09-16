package sample;

public class Setting {
    public static boolean bTest = true;
    //-------------------------------------------------------------------------
    public static String getdbUrl() {
        String sRes = "";
        if (bTest)
            sRes = "jdbc:mysql://localhost/papavinyl?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
        else
            sRes = "";
        return sRes;
    }
    //-------------------------------------------------------------------------
    public static String getdbPass() {
        String sRes = "";
        if (bTest)
            sRes = "root";
        else
            sRes = "";
        return sRes;
    }
    //-------------------------------------------------------------------------
    public static String getdbLogin() {
        String sRes = "";
        if (bTest)
            sRes = "root";
        else
            sRes = "";
        return sRes;
    }
}
