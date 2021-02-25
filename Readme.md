# zoop-offline-aadhar-android-sdk

## 1. INTRODUCTION
AadhaarAPI provide WEB and Mobile gateway for fetching Aadhaar Information of Users. Using these gateways any organization onboarded with us can get User's aadhaar data from their masked E-aadhaar PDF’s, Offline Aadhaar XML and mAadhaar QR image.

## 2. PROCESS FLOW
1. At your backend server, Initiate the offline aadhaar transaction using Rest API [POST] call. Details of these are available in the documents later. You will require API key and Agency Id for accessing this API which can be generated from the Dashboard. 
2. This gateway transaction id then needs to be communicated back to the frontend where SDK functions are to be called. 
3. After including the SDK files (JS & CSS) at frontend and a small HTML snippet, client has to pass above generated transaction id to an SDK function to create a new gateway object and then open the gateway using another function call. 
4. This will open the gateway and the rest of the process till response will be handled by the gateway itself. 
5. Once the transaction is successful or failed, appropriate handler function will be called with response JSON, that can be used by the client to process the flow further. 
6. Result data will be sent to the responseUrl requested via INIT call. 
7. Client will also have a REST API available to pull the status of a gateway transaction from backend and reason of failure. 
 
## 3. INITIATING A GATEWAY TRANSACTION FOR OFFLINE AADHAAR
To initiate a gateway transaction a REST API call has to be made to backend. This call will generate a Gateway Transaction Id which needs to be passed to the frontend web-sdk to launch the gateway.

### 3.1 INIT URL:

    URL: POST {{base_url}}/gateway/offline-aadhaar/init
    
### BASE URL: 

For Pre-Production Environment: https://preprod.aadhaarapi.com

For Production Environment: https://prod.aadhaarapi.com

Example Url: https://preprod.aadhaarapi.com/gateway/offline-aadhaar/init

### 3.2 REQUEST HEADERS: [All Mandatory]
QT_API_KEY: <<your api key value – available via Dashboard>>

QT_AGENCY_ID: <<your agency id value – available via Dashboard>>

Content-Type: application/json

### 3.3 REQUEST BODY PARAMS: [All Mandatory]
 
    {
      "response_url" : "<<response url>>",
      "purpose" : "<<Purpose of transaction>>",
      "face_match" : "<<Y or N>>"
    }

### 3.4 RESPONSE PARAMS:

    {
        "id": "<<transaction id>>",
        "env": 1,
        "webhook_security_key": "<<webhook id>>",
        "face_match": "N",
        "response_url": "<<response url>>"
    }

The above generated gateway transactionId has to be made available in your android project to open the Offline Aadhaar SDK.

Note: A transaction is valid only for 30 mins after generation.

## 4. ADDING SDK TO YOUR PROJECT

### USING GRADLE
   
   Implement below line in your build.gradle file at app level under dependency section
   
    implementation 'sdk.zoop.one.offline_aadhaar:offline_aadhaar:1.1.0'
    
### USING AAR FILE    
To add SDK file as library in your Project, Perform the following Steps:

1. Right click on your project and choose “Open Module Settings”.
2. Click the “+” button in the top left to add a new module.
3. Choose “Import .JAR or .AAR Package” and click the “Next” button.
4. Find the AAR file using the ellipsis button (“…”) beside the “File name” field.
5. Keep the app’s module selected and click on the Dependencies pane to add the new module as a dependency.
6. Use the “+” button of the dependencies screen and choose “Module dependency”.
7. Choose the module and click “OK”.

## 5. CONFIGURING AND LAUNCHING THE OFFLINE AADHAAR SDK

### 5.1 IMPORT FILES
Import following files in your Activity:

    import sdk.zoop.one.offline_aadhaar.zoopActivity.ZoopConsentActivity;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.OFFLINE_AADHAAR;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.OFFLINE_AADHAAR_ERROR;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.OFFLINE_AADHAAR_SUCCESS;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.REQUEST_AADHAARAPI;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_EMAIL;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_ENV;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_PHONE;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_REQUEST_TYPE;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_RESULT;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_TAG;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_TRANSACTION_ID;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_UID;
    import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_IS_ASSIST_MODE_ONLY;

### 5.2 BUILD GRADLE(app)

    dependencies {
        implementation fileTree(include: ['*.jar'], dir: 'libs')
        implementation 'com.android.support:appcompat-v7:28.0.0'
        implementation 'com.android.support.constraint:constraint-layout:1.1.3'
        testImplementation 'junit:junit:4.12'
        androidTestImplementation 'com.android.support.test:runner:1.0.2'
        androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
        implementation 'com.android.support:cardview-v7:28.0.0'
        implementation 'sdk.zoop.one.offline_aadhaar:offline_aadhaar:1.1.0'// only if you are using gradle dependency in this 
       // implementation project(':offline_aadhaar-releaseV1.1.0')// only if you are using AAR file then
        implementation 'com.android.volley:volley:1.1.0'
        implementation 'io.sentry:sentry-android:1.7.27'   //COPY SENTRY TO LOG ERROR MESSAGES

    }
    
### 5.3 MANIFEST FILE

     <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        package="one.zoop.gatewaysdk.sampleapp">
    <uses-permission android:name="android.permission.INTERNET"></uses-permission>
        <application
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/AppTheme">
            <activity android:name=".MainActivity">
                <intent-filter>
                    <action android:name="android.intent.action.MAIN" />

                    <category android:name="android.intent.category.LAUNCHER" />
                </intent-filter>
            </activity>
            <provider
                android:name="android.support.v4.content.FileProvider"
                android:authorities="<<YOUR PACKAGE NAME>>"
                android:exported="false"
                android:grantUriPermissions="true">
                <meta-data
                    android:name="android.support.FILE_PROVIDER_PATHS"
                    android:resource="@xml/provider_paths" />
            </provider>
        </application>

    </manifest>
    
### 5.4 CALL OFFLINE AADHAAR SDK FROM THE ACTIVITY
Use the Intent Function to call the Offline Aadhaar SDK from your Activity as shown below:

    String GatewayId, Email, baseUrl, phone
    boolean isShareCodePreFill;
    Intent gatewayIntent = new Intent(MainActivity.this, ZoopConsentActivity.class);
    gatewayIntent.putExtra(ZOOP_TRANSACTION_ID, "222c21aa-2fff-4ec6-94cb-04d68174324a");
    gatewayIntent.putExtra(ZOOP_BASE_URL, baseUrl);
    gatewayIntent.putExtra(ZOOP_EMAIL, Email); //not mandatory
    //gatewayIntent.putExtra(ZOOP_UID, uid); //not mandatory
    gatewayIntent.putExtra(ZOOP_PHONE, phone); //not mandatory
    gatewayIntent.putExtra(ZOOP_IS_ASSIST_MODE_ONLY, false); //not mandatory
    gatewayIntent.putExtra(ZOOP_REQUEST_TYPE, OFFLINE_AADHAAR);
    startActivityForResult(gatewayIntent, REQUEST_AADHAARAPI);
    
    
Params: GatewayId: “Transaction Id generated from your backend must be passed here”

environment: for pre-prod use "preprod.aadhaarapi.com" and for prod use "prod.aadhaarapi.com"

isShareCodePreFill: if it is true then 4-digit share code will be filled randomly otherwise you need to fill it manually.

Example:

GatewayId = "a051231e-ddc7-449d-8635-bb823485a20d";

Email = “youremail@gmail.com";

baseUrl = "preprod.aadhaarapi.com";

### 5.5 HANDLE SDK RESPONSE

The responses incase of successful transaction as well as response in case of error will be sent to your activity & can be handled via onActivityResult( ) method as shown below.

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String responseString0 = "No Data Received";
        llInitLayout.setVisibility(View.GONE);
        resultDisplayLayout.setVisibility(View.VISIBLE);

        if (null != data) {
            responseString0 = data.toString();
        }
        Log.d(ZOOP_TAG, " res 0" + responseString0);

        if (requestCode == REQUEST_AADHAARAPI && null != data) {
            String requestType = "null";
            if (data.hasExtra(ZOOP_REQUEST_TYPE)) {
                requestType = data.getStringExtra(ZOOP_REQUEST_TYPE);
                Log.d(ZOOP_TAG, " res 1" + requestType);
            } else {
                Log.d(ZOOP_TAG, " res 1" + requestType);
            }

            if (resultCode == RESULT_CANCELED) {
                String responseString = data.getStringExtra(ZOOP_RESULT);
                tvResult.setText(responseString);
                Log.e("SDK test error ", requestType + " err " + responseString + resultCode);
            }

            if (requestType.equalsIgnoreCase(OFFLINE_AADHAAR)) {
                //String responseString1 = data.getStringExtra(ZOOP_RESULT);
                if (resultCode == OFFLINE_AADHAAR_SUCCESS) {
                    String responseString = data.getStringExtra(ZOOP_RESULT);
                    tvResult.setVisibility(View.VISIBLE);
                    resultDisplayLayout.setVisibility(View.VISIBLE);
                    llResultBg.setVisibility(View.VISIBLE);

                    try {
                        JSONObject jsonObject = new JSONObject(responseString);
                        parseResultJson(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tvResult.setText(String.format("complete Response: %s", responseString));
                    Log.d("SDK test result ", requestType + " res " + responseString);
                }

                if (resultCode == OFFLINE_AADHAAR_ERROR) {
                    String errorString = data.getStringExtra(ZOOP_RESULT);
                    // tvResult.setText(errorString);
                    tvResult.setVisibility(View.VISIBLE);
                    resultDisplayLayout.setVisibility(View.GONE);
                    llResultBg.setVisibility(View.GONE);
                    tvResult.setText(errorString);
                    Log.d("SDK test error ", requestType + " err " + errorString);
                }
            } else {
                Log.d(ZOOP_TAG, " res 1" + requestType);
            }
        }
    }
    
 ### 5.6 STRINGS.XML FILE
 
 The instructions message can be modify from strings.xml file 
 
    <string name="zoopTimerInstr">waiting for OTP confirmation</string>
    <string name="zoopTimerInstrShare">waiting for OTP confirmation before please enter share code</string>
    <string name="tv_timer_finish">OTP wait time finished</string>
    
    
 ## 6. RESPONSE FORMAT SENT ON MOBILE  
 
 ### 6.1 SUCCESS JSON RESPONSE FORMAT FOR OFFLINE AADHAAR SUCCESS
 
    {
    "id":"21284528-0006-4b16-b63e-38d78b613f19",
    "env":2,
    "mode":"xml",
    "transaction_status":5,
    "internal_status":"",
    "transaction_attempts":0,
    "public_ip":null,
    "request_medium":null,
    "response_url":"<<response url>>",
    "response_to_agency":"N",
    "face_match":"Y",
    "face_match_resp":"Y",
    "face_match_remark":null,
    "face_match_txn":"99a6058a-419d-4501-a9bf-17d58f7e05ea",
    "request_consent":"N",
    "purpose":"Testing aadhaar",
    "request_timestamp":"2021-02-15 14:34:05:538 +05:30",
    "response_timestamp":"2021-02-15 14:34:05:545 +05:30",
    "face_match_type":"S",
    "transaction_data":{
       "BasicInfo":{
          "Name":"<<user name>>",
          "DOB":"<<DOB>>",
          "Gender":"<<gender>>"
       },
       "FileCreatedAt":"2021-02-15T14:00:36.278Z",
       "Image":"<<Base 64 of user image>>",
       "AadhaarInfo":"XXXXXXXX3488",
       "AddressEnglish":"<<Base 64 of user image>>",
       "DetailedAddress":{
          "house":"<<house number>>",
          "postoffice":"<<post office>>",
          "district":"<<district>>",
          "state":"<<state>>",
          "postalcode":"<<pin code>>"
       },
       "EmailInfo":"Not Verified",
       "PhoneInfo":"Verified",
       "Xml":"<<xml file response>>",
       "pdf_url":"<<downloaded zip file url>>",
       "password":"<<share code>>",
       "FaceMatchScore":"<<Facematch Score out of 100>>",
       "UserSelfie":"<<base 64 of user selfie image>>"
    }
 }
    
 ### 6.2 ERROR JSON RESPONSE FORMAT FOR OFFLINE AADHAAR ERROR
   
    {
      "id": "<<transaction id>>",
      "env": <<environment code>>,
      "mode": "<<mode>>",
      "transaction_status": 6,
      "internal_status": <<internal status for failure (refer below)>>,
      "transaction_attempts": <<number of attempts>>,
      "public_ip": "<<user ip address>>",
      "request_medium": "<<what was origin of request web or mobile>>",
      "response_url": "<<response url of agency>>",
      "face_match": "<<Y or N>>",
      "face_match_resp": "<<Y,N or S (if face match got a response or not) S stands for skipped>>",
      "face_match_remark": "<<in case of face_match_resp = s , Reason why face match was skipped>>",
      "face_match_txn": "<<face match transaction id>>",
      "request_consent": "<<user consent Y or N>>",
      "request_timestamp": "2019-01-22 18:33:09:602 +05:30",
      "response_timestamp": "2019-01-22 18:33:09:626 +05:30"
    }
    
   ### 6.3 INTERNAL STATUS
   
    {
     "ZOOP_004": {
       "error_code": "ZOOP_004",
       "sdk_response": "Download Manager is disabled",
       "statusCode": 6
     },
     "ZOOP_005": {
       "error_code": "ZOOP_005",
       "sdk_response": "Permissions not granted by the user",
       "statusCode": 6
     },
     "ZOOP_006": {
       "error_code": "ZOOP_006",
       "sdk_response": "Go to settings and enable required permissions",
       "statusCode": 6
     },
     "ZOOP_007": {
       "error_code": "ZOOP_007",
       "sdk_response": "Unknown Error Occured",
       "statusCode": 6
     },
     "ZOOP_008": {
       "error_code": "ZOOP_008",
       "sdk_response": "KYC XML download failed"
     },
     "ZOOP_009": {
       "error_code": "ZOOP_009",
       "sdk_response": "Share code is invalid."
     },
     "ZOOP_010": {
       "error_code": "ZOOP_010",
       "sdk_response": "File not found",
       "statusCode": 6
     },
     "ZOOP_011": {
       "error_code": "ZOOP_011",
       "sdk_response": "Camera error",
       "statusCode": 5
     },
     "ZOOP_012": {
       "error_code": "ZOOP_012",
       "sdk_response": "Camera error",
       "statusCode": 5
     },
     "ZOOP_013": {
       "error_code": "ZOOP_013",
       "sdk_response": "User cancelled image capture",
       "statusCode": 5
     },
     "ZOOP_014": {
       "error_code": "ZOOP_014",
       "sdk_response": "Failed to capture image",
       "statusCode": 5
     },
     "ZOOP_015": {
       "error_code": "ZOOP_015",
       "sdk_response": "Error downloading XML on phone"
     },
     "ZOOP_016": {
       "error_code": "ZOOP_016",
       "sdk_response": "Error Downloading XML on phone, no storage directory was found",
       "statusCode": 6
     },
     "ZOOP_017": {
       "error_code": "ZOOP_017",
       "sdk_response": "KYC ZIP download failed"
     },
     "ZOOP_018": {
       "error_code": "ZOOP_018",
       "sdk_response": "Failed to capture image",
       "statusCode": 5
     },
     "ZOOP_020": {
       "error_code": "ZOOP_020",
       "sdk_response": "Aadhaar number does not have mobile number",
       "statusCode": 6
     },
     "ZOOP_021": {
       "error_code": "ZOOP_021",
       "sdk_response": "Please Enter Valid Captcha"
     },
     "ZOOP_022": {
       "error_code": "ZOOP_022",
       "sdk_response": "Technical issue please try after some time",
       "statusCode": 6
     },
     "ZOOP_023": {
       "error_code": "ZOOP_023",
       "sdk_response": "Technical issue please try after some time",
       "statusCode": 6
     },
     "ZOOP_024": {
       "error_code": "ZOOP_024",
       "sdk_response": "Transaction terminated by the user",
       "statusCode": 11
     },
     "ZOOP_026": {
       "error_code": "ZOOP_026",
       "sdk_response": "OTP Wait time expired"
     }
    }
