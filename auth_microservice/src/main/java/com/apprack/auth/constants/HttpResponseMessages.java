package com.apprack.auth.constants;

public class HttpResponseMessages {
    //Success Messages
    public static final String SUCCESS_MESSAGE = "User successfully created";
    public static final String ALREADY_REGISTERED = "User already registered";
    public static final String USER_DELETED = "User successfully deleted";
    // Error Messages
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "An unexpected error occurred";
    public static final String BAD_GATEWAY_MESSAGE = "Invalid response from upstream server";
    public static final String SERVICE_UNAVAILABLE_MESSAGE = "Service is temporarily unavailable";
    // Add other status codes as needed

    //Login
    public static final String INVALID_CREDENTIALS = "Invalid Credentials";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String LOGIN_SUCCESSFULLY = "Login Successfully";


}
