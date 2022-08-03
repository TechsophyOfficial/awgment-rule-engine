package com.techsophy.tsf.rule.engine.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RuleEngineConstants
{
    /*CustomFilterConstants*/
    public static final String AUTHORIZATION="Authorization";

    /*LocaleConfigConstants*/
    public static final String ACCEPT_LANGUAGE ="Accept-Language";
    public static final String BASENAME_ERROR_MESSAGES ="classpath:errorMessages";
    public static final String BASENAME_MESSAGES="classpath:messages";
    public static final Long CACHEMILLIS = 3600L;
    public static final Boolean USEDEFAULTCODEMESSAGE =true;
    public static final String AWGMENT_ROLES_MISSING_IN_CLIENT_ROLES ="AwgmentRoles are missing in clientRoles";
    public static final String CLIENT_ROLES_MISSING_IN_USER_INFORMATION="ClientRoles are missing in the userInformation";
    public static final String AWGMENT ="awgment";

    //JWTRoleConverter
    public static final String CLIENT_ROLES="clientRoles";
    public static final String USER_INFO_URL= "techsophy-platform/protocol/openid-connect/userinfo";
    public static final String TOKEN_VERIFICATION_FAILED="Token verification failed";

    // Roles
    public static final String HAS_ANY_AUTHORITY="hasAnyAuthority('";
    public static final String HAS_ANY_AUTHORITY_ENDING="')";
    public static final String AWGMENT_RULE_ENGINE_CREATE_OR_UPDATE ="awgment-rule-engine-create-or-update";
    public static final String AWGMENT_RULE_ENGINE_READ = "awgment-rule-engine-read";
    public static final String AWGMENT_RULE_ENGINE_DELETE = "awgment-rule-engine-delete";

    public static final String AWGMENT_RULE_ENGINE_ALL = "awgment-rule-engine-all";
    public static final String OR=" or ";
    public static final String CREATE_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_RULE_ENGINE_CREATE_OR_UPDATE +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+ AWGMENT_RULE_ENGINE_ALL +HAS_ANY_AUTHORITY_ENDING;
    public static final String READ_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_RULE_ENGINE_READ +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+ AWGMENT_RULE_ENGINE_ALL +HAS_ANY_AUTHORITY_ENDING;
    public static final String DELETE_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_RULE_ENGINE_DELETE +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+ AWGMENT_RULE_ENGINE_ALL +HAS_ANY_AUTHORITY_ENDING;



    //TokenUtilsTest
    public static final String TOKEN_TXT_PATH = "testdata/token.txt";
    public static final String TECHSOPHY_PLATFORM="techsophy-platform";

    /*TenantAuthenticationManagerResource*/
    public static final String KEYCLOAK_ISSUER_URI="${keycloak.issuer-uri}";

    /*RuleEngineControllerConstants*/
    public static final String BASE_URL="/rules";
    public static final String VERSION_V1 ="/v1";
    public static final String DRD_URL ="/dmn";
    public static final String MEDIA_TYPE_APPLICATION_JSON ="application/json";
    public static final String SAVE_DRD_REQUESTPARAM_ID="id";
    public static final String SAVE_DRD_REQUESTPARAM_NAME="name";
    public static final String SAVE_DRD_REQUESTPARAM_VERSION="version";
    public static final String SAVE_DRD_REQUESTPARAM_CONTENT="content";
    public static final String SAVE_DRD_REQUESTPARAM_DEPLOYMENTNAME ="deploymentName";
    public static final String EXECUTE_DMN_URL ="/execute-dmn";
    public static final String DMN_BY_ID_URL ="/dmn/{id}";
    public static final String EVALUATE = "/evaluate";
    public static final String EMPTY_STRING="";
    public static final String INCLUDE_CONTENT = "include-content";
    public static final String SAVE_DRD_SUCCESS="SAVE.DRD.SUCCESS";
    public static final String EXECUTE_DMN_SUCCESS="EXECUTE.DMN.SUCCESS";
    public static final String GET_DMN_SUCCESS="GET.DMN.SUCCESS";
    public static final String UPDATE_DMN_SUCCESS="UPDATE.DMN.SUCCESS";
    public static final String DRD_DELETED_SUCCESSFULLY="DRD.DELETED.SUCCESSFULLY";
    public static final String DRD_EVALUATION_SUCCESS="DRD.EVALUATION.SUCCESS";
    public static final String OUTPUT="output";
    public  static final String CREATED_BY_ID_VALUE ="1";
    public static final Instant CREATED_ON_INSTANT =Instant.now();
    public static final String UPDATED_BY_ID_VALUE ="1";
    public static final Instant UPDATED_ON_INSTANT =Instant.now();
    public static final String CREATED_BY_NAME="tejaswini";
    public static final String UPDATED_BY_NAME="tejaswini";
    public static final String TEST_ID="1";
    public static final String TEST_NAME="abc";
    public static final String TEST_CONTENT="abc";
    public static final Integer TEST_VERSION=1;
    public static final String TEST_DEPLOYMENT_NAME="abc";

    //RuleEngineServiceConstants
    public static final String SPACE=" ";
    public static final String USER_DEFINITION_FIRST_NAME="firstName";
    public static final String USER_DEFINITION_LAST_NAME ="lastName";

    //ViewControllerConstants
    public static final String BASE="/";
    public static final String INDEX_URL ="index";
    public static final String MODELER_URL ="modeler";

    //ApiErrorResponseConstants
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String TIME_ZONE = "UTC";
    public static final String PREFERED_USERNAME="preferred_username";
    public static final int SEVEN=7;
    public static final int ONE=1;
    public static final String UNABLE_TO_GET_TOKEN ="Unable to get token";

    /*ViewControllerConstants*/
    public static final String CREATED_BY_ID_NOT_NULL="createdById may not be null";
    public static final String CREATED_ON_NOT_NULL="createdOn may not be null";

    /*EntityConstants*/
    public static final String TP_RULE_ENGINE_COLLECTION ="tp_rule_engine";

    //UtilityServiceConstants
    public static final String BEARER= "Bearer ";
    public static final String REGEX_SPLIT="\\.";
    public static final String ISS="iss";
    public static final String URL_SEPERATOR="/";
    public static final String CONTENT_TYPE="Content-Type";
    public static final String APPLICATION_JSON ="application/json";
    public static final String GET="GET";
    public static final String PUT="PUT";
    public static final String DELETE="DELETE";

    //MainMethodConstants
    public static final String CURRENT_PROJECT_NAME="com.techsophy.tsf.rule.engine.*";
    public static final String MULTI_TENANCY_PROJECT="com.techsophy.multitenancy.mongo.*";

    /*MainMethodConstants*/
    public static final String GATEWAY_URL ="${gateway.uri}";
    public static final String RULE_ENGINE_MODELER ="tp-rule-engine";
    public static final String VERSION_1="v1";
    public static final String RULE_ENGINE_MODELER_API_VERSION_1="Rule engine Modeler API v1";

    //WEBCLIENTWrapperTestConstants
    public static final String LOCAL_HOST_URL="http://localhost:8080";
    public static final String TOKEN="token";
    public static final String TEST="test";

    /*UserDetailsConstants*/
    public static final String GATEWAY_URI="${gateway.uri}";
    public static final String LOGGED_USER="loggeduser";
    public static final String FILTER_COLUMN="?filter-column=loginId&filter-value=";
    public static final String MANDATORY_FIELDS="&only-mandatory-fields=true";
    public static final String RESPONSE="response";
    public static final String DATA ="data";
    public static final String GATEWAY="gateway";
    public static final String ACCOUNT_URL = "/accounts/v1/users";
    public static final String ID = "id";

    //GlobalMessageSourceConstants
    public static final String  KEY="key";
    public static final String ARGS="args";

    //LoggingHandler
    public static final String CONTROLLER_CLASS_PATH = "execution(* com.techsophy.tsf.rule.engine.controller..*(..))";
    public static final String SERVICE_CLASS_PATH= "execution(* com.techsophy.tsf.rule.engine.service..*(..))";
    public static final String EXCEPTION = "ex";
    public static final String IS_INVOKED_IN_CONTROLLER= "() is invoked in controller ";
    public static final String IS_INVOKED_IN_SERVICE= "() is invoked in service ";
    public static final String EXECUTION_IS_COMPLETED_IN_CONTROLLER="() execution is completed  in controller";
    public static final String EXECUTION_IS_COMPLETED_IN_SERVICE="() execution is completed  in service";
    public static final String EXCEPTION_THROWN="An exception has been thrown in ";
    public static final String CAUSE="Cause : ";
    public static final String BRACKETS_IN_CONTROLLER="() in controller";
    public static final String BRACKETS_IN_SERVICE="() in service";
}
