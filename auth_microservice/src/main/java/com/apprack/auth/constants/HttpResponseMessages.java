package com.apprack.auth.constants;

public class HttpResponseMessages {
    // Auth success messages
    public static final String SUCCESS_MESSAGE = "User successfully created"; // can override generic if needed
    public static final String ALREADY_REGISTERED = "User already registered";
    public static final String USER_DELETED = "User successfully deleted";

    // Auth error messages
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
    public static final String INVALID_CREDENTIALS = "Invalid Credentials";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String LOGIN_SUCCESSFULLY = "Login Successfully";

    // Optionally import generic messages from auth_common if you want consistency
}

