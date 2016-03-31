package il.co.runnerdevice.Authentication;
//https://github.com/Udinic/AccountAuthenticator/blob/master/src/com/udinic/accounts_authenticator_example/authentication/AccountGeneral.java


public class AccountGeneral {

    /**
     * Account type id
     */
    public static final String ACCOUNT_TYPE = "il.co.runnerdevice.account";

    /**
     * Account name
     */
    public static final String ACCOUNT_NAME = "Runner";

    /**
     * Auth token types
     */
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an Runner account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an Runner account";
   
    public static final String  GRANT_TYPE_REFRESH_TOKEN =  "refresh_token";
    public static final String  CLIENT_ID = "ngAutoApp";
    public static final String  GRANT_TYPE_PWS = "password";
    public static  final String PARAM_EXPIRED= "PEXPIRED";
    public static  final String PARAM_USER_ID= "PUSER_ID";
    public static  final String PARAM_ROLES= "PROLES";
    public static  final String PARAM_PWS= "PWS";
}