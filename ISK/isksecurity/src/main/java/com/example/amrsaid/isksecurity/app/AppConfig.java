package com.example.amrsaid.isksecurity.app;

public class AppConfig {
	public static final String url_User = "http://10toon.com/isk/User.json";
	public static final String url_Log = "http://10toon.com/isk/Log.json";


	public static String URL ="http://smartkey-smartkey.rhcloud.com/SmartKeyWebService";

	// Server user login url
	public static String URL_LOGIN = "http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/login";


	// Server user register url
	public static String URL_REGISTER = "http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/register";
	public static final String get_Log = "http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/userLogs/get";

	//Url For Message
	public static final String get_user_massage = "http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/message/group/get";
	public static final String get_group_massage = "http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/message/user/get";
	public static final String send_group_massage = "http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/message/group/send";
	public static final String send_user_message = "http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/message/user/send";

	//Url For Groups
	public static final String url_get_users = "http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/group/getUsers";
	public static final String create_group = "http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/group/create";
	public static final String add_user_group = "http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/group/addUser";
	public static final String remove_user_group = "http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/group/removeUser";

	//URL FOR batteryLogs

	public static final String update_userLogs ="http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/batteryLogs/update";
	public static final String get_userLogs ="http://smartkey-smartkey.rhcloud.com/SmartKeyWebService/batteryLogs/get";

}
