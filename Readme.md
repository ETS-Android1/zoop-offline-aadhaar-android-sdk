# zoop-offline-aadhar-android-sdk

## 1. INTRODUCTION
TODO-

## 2. PROCESS FLOW
1. This gateway transaction id then needs to be communicated back to the frontend(in Android Project) where SDK is to be called.
2. After adding the SDK (.aar file) in your android project, client has to pass the above generated transaction id to an SDK Intent Function, which will open the SDK.
3. SDK will open with a uidai login after which and the rest of the process till response will be handled by the gateway itself.
4. Once the transaction is successful or failed, appropriate handler function will be called with response JSON, that can be used by the client to process the flow further.
 
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

### 4. ADDING SDK (.AAR FILE) TO YOUR PROJECT
To add SDK file as library in your Project, Perform the following Steps:

1. Right click on your project and choose “Open Module Settings”.
2. Click the “+” button in the top left to add a new module.
3. Choose “Import .JAR or .AAR Package” and click the “Next” button.
4. Find the AAR file using the ellipsis button (“…”) beside the “File name” field.
5. Keep the app’s module selected and click on the Dependencies pane to add the new module as a dependency.
6. Use the “+” button of the dependencies screen and choose “Module dependency”.
7. Choose the module and click “OK”.
