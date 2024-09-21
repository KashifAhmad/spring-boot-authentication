package prod_mng_microservice.contants;

public class HttpResponseMessages {
    //Success Messages
    public static final String SUCCESS_MESSAGE = "Product successfully created";
    public static final String ALREADY_REGISTERED = "Product already registered";
    public static final String PRODUCT_DELETED = "Product successfully deleted";
    // Error Messages
    public static final String USERNAME_ALREADY_EXISTS = "Product already exists";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "An unexpected error occurred";
    public static final String BAD_GATEWAY_MESSAGE = "Invalid response from upstream server";
    public static final String SERVICE_UNAVAILABLE_MESSAGE = "Service is temporarily unavailable";
    // Add other status codes as needed

    //Login
    public static final String INVALID_CREDENTIALS = "Invalid Product";
    public static final String PRODUCT_NOT_FOUND = "Product not found";


}
