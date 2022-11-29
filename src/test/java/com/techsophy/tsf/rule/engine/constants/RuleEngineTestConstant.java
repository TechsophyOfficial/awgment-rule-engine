package com.techsophy.tsf.rule.engine.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RuleEngineTestConstant
{
    public final static  String ID= "1";
    public final static  Map<String,Object> VARIABLES =null;
    public final static Integer VERSION =1;
    public final static  String TENANT ="tenant1";
    public static final String TEST_ACTIVE_PROFILE = "test";

    //FORMCONTROLLERTESTConstants
    public static final String RULE_ENGINE_CONTENT_1 = "testdata/rule-engine-content1.json";
    public static final String RULE_UPDATE = "testdata/rule-update.json";
    public static final String RULE_MODEL_CONTENT = "testdata/rule-engine-model.json";
    public static final String REQUEST = "testdata/request-dto.json";
    public static final String REQUEST2 = "testdata/request-dto2.json";
    public static final String REQUEST3 = "testdata/request-dto3.json";
    public static final String CONTENT_1="testdata/rule-engine-content2.json";
    public static final String CONTENT_VALUE="content";
    public static final String TEST_NAME_VALUE="test";
    public static final String TEST_VERSION_VALUE="version";
    public static final String TRUE_VALUE="true";
    public static final String CIBIL="cibil";
    public static final Integer CIBIL_SCORE=900;
    public static final String TICKET_TYPE="ticketType";
    public static final String  VARIABLES_KEY="variables";
    public static final String INSTALL="install";
    public static final String TEST_KEY="key";

    //INITILIZATION CONSTANTS
    public static final String DEPARTMENT="department";
    public static final String  NULL=null;
    public static final String EMAIL_ID="emailId";
    public static final String MOBILE_NUMBER="mobileNumber";
    public static final String LAST_NAME="lastName";
    public static final String  FIRST_NAME="firstName";
    public static final String USER_NAME="userName";
    public static final String USER_ID="id";
    public static final String UPDATED_ON="updatedOn";
    public static final String UPDATED_BY_ID="updatedById";
    public static final String CREATED_ON="createdOn";
    public static final String CREATED_BY_ID="createdById";
    public static final String NUMBER="1234567890";
    public static final String BIGINTEGER_ID = "847117072898674688";
    public static final String USER_FIRST_NAME ="tejaswini";
    public static final String USER_LAST_NAME ="Kaza";
    public static final String MAIL_ID ="tejaswini.k@techsophy.com";
    public static final String TEST_TOKEN="testdata/token.txt";
    public static final String EMPTY_STRING="";
    public static final String TEST_ID="id";
    public static final String ID_VALUE="1";

    //UserDetailsTestConstants
    public static final String  USER_DETAILS_RETRIEVED_SUCCESS="User details retrieved successfully";
    public static final String INITIALIZATION_DATA="{\"data\":[{\"id\":\"847117072898674688\",\"userName\":\"tejaswini\",\"firstName\":\"Kaza\",\"lastName\":\"Tejaswini\",\"mobileNumber\":\"1234567890\",\"emailId\":\"tejaswini.k@techsophy.com\",\"department\":null,\"createdById\":null,\"createdByName\":null,\"createdOn\":null,\"updatedById\":null,\"updatedByName\":null,\"updatedOn\":null}],\"success\":true,\"message\":\"User details retrieved successfully\"}\n";
    public final static String GET="GET";
    public static final String ABC="abc";
}
