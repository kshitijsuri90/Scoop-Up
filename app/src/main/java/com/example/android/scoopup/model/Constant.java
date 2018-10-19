package com.example.android.scoopup.model;

public class Constant {
    //Night/Day Mode
    public static Boolean STATE = false;

    //Query Parameters
    public static final String REQUEST_URL = "http://content.guardianapis.com/search?";
    public static final String QUERY_ORDER_BY = "order-by";
    public static final String QUERY_FIELDS = "q";
    public static final String KEY_SHOW_FIELD = "show-fields";
    public static final String KEY_ALL = "all";
    public static final String QUERY_PAGES = "page-size";
    public static String SECTION_QUERY = "def";
    public static final String SECTION_NAME = "sectionName";
    public static final String SECTION_ID = "sectionId";
    public static final int LOADER_ID = 1;
    public static final String API_KEY = "api-key";
    public static final String API_KEY_VALUE = "test";
    public static final String SHOW_TAGS = "show-tags";
    public static final String SHOW_TAGS_VALUE ="contributor";
    public static final String NEWS_QUANTITY = "30";

    //Utils Constants
    public static final int READ_TIMEOUT = 10000;
    public static final int CONNECTTION_TIMEOUT = 15000;
    public static final String REQUEST_METHOD = "GET";

    //JSON Parameters
    public static final String RESPONSE = "response";
    public static final String RESULTS = "results";
    public static final String WEB_TITLE = "webTitle";
    public static final String SECTION = "sectionName";
    public static final String WEB_PUBLICATION = "webPublicationDate";
    public static final String WEB_URL = "webUrl";
    public static final String FIELDS = "fields";
    public static final String THUMBNAIL = "thumbnail";
    public static final String TAGS = "tags";

    //Default Image
    public static final String IMAGE = "http://cosmicshambles.com/wp-content/uploads/2016/12/the-guardian-logo-440x440.png";

}
