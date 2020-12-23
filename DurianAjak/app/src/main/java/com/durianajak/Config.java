package com.durianajak;

public class Config {

    //your admin panel url
    public static String ADMIN_PANEL_URL = "http://wh747939.ispot.cc";

    //your applicationId or package name
    public static String PACKAGE_NAME = "com.durianajak";

    //*============================================================================*//

    //database path configuration
    public static String DB_PATH_START = "/data/data/";
    public static String DB_PATH_END = "/databases/";
    public static String DB_PATH = DB_PATH_START + PACKAGE_NAME + DB_PATH_END;

}
