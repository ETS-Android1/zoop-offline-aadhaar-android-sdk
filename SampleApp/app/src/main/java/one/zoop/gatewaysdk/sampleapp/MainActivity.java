package one.zoop.gatewaysdk.sampleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import androidx.core.content.FileProvider;
import sdk.zoop.one.offline_aadhaar.zoopActivity.ZoopConsentActivity;

import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.OFFLINE_AADHAAR;
import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.OFFLINE_AADHAAR_ERROR;
import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.OFFLINE_AADHAAR_SUCCESS;
import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.REQUEST_AADHAARAPI;
import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_BASE_URL;
import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_EMAIL;
import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_IS_ASSIST_MODE_ONLY;
import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_IS_SHARE_CODE_PREFILL;
import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_PHONE;
import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_REQUEST_TYPE;
import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_RESULT;
import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_TAG;
import static sdk.zoop.one.offline_aadhaar.zoopUtils.ZoopConstantUtils.ZOOP_TRANSACTION_ID;

public class MainActivity extends AppCompatActivity {
    TextView tvResult;
    LinearLayout llResultBg, llInitLayout, llEmailStatus, llPhoneStatus, llFacematchScore, llDetailedAddress;
    EditText etEmail, etPhone, etUid;
    RelativeLayout resultDisplayLayout;
    private TextView tvUid, tvName, tvPhoneStatus, tvEmailStatus, tvAddress, tvGender, tvDob, tvFMScore;
    private TextView tvHouse, tvStreet, tvLocality, tvDistrict, tvPostOffice, tvState, tvPostalcode;
    private String phoneStatus = "not found";
    private String emailStatus = "not found";
    private ImageView ivAadhaarImage, ivCapturedImage;
    private Button btnInitXMLKYC;
    String env = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    void initUi() {
        ivAadhaarImage = findViewById(R.id.ivEkycFace);
        ivCapturedImage = findViewById(R.id.ivCapturedFace);
        tvName = findViewById(R.id.tvName);
        llEmailStatus = findViewById(R.id.llEmailStatus);
        tvEmailStatus = findViewById(R.id.tvEmailStatus);
        llPhoneStatus = findViewById(R.id.llPhoneStatus);
        tvPhoneStatus = findViewById(R.id.tvPhoneStatus);
        tvGender = findViewById(R.id.tvGender);
        tvDob = findViewById(R.id.tvDob);
        tvAddress = findViewById(R.id.tvAddress);
        tvUid = findViewById(R.id.tvAadhaarNumber);
        llFacematchScore = findViewById(R.id.llFMScore);
        tvFMScore = findViewById(R.id.tvFaceMatchScore);

        etEmail = findViewById(R.id.etEmail);
        etUid = findViewById(R.id.etUID);
        etPhone = findViewById(R.id.etPhone);

        tvResult = findViewById(R.id.tvResult);
        llResultBg = findViewById(R.id.llBackground);
        resultDisplayLayout = findViewById(R.id.ResultDisplayLayout);


        llInitLayout = findViewById(R.id.llInitLayout);
        llDetailedAddress = findViewById(R.id.llDetailedAddress);
        tvHouse = findViewById(R.id.tvHouse);
        tvStreet = findViewById(R.id.tvStreet);
        tvLocality = findViewById(R.id.tvLocality);
        tvPostOffice = findViewById(R.id.tvPostOffice);
        tvDistrict = findViewById(R.id.tvDistrict);
        tvState = findViewById(R.id.tvState);
        tvPostalcode = findViewById(R.id.tvPostalCode);
        btnInitXMLKYC = findViewById(R.id.btnInitXMLKYC);
        llInitLayout.setVisibility(View.VISIBLE);
        resultDisplayLayout.setVisibility(View.GONE);
        btnInitXMLKYC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llInitLayout.getVisibility() == View.VISIBLE) {
//                    File mFolder = new File(Arrays.toString(getExternalFilesDirs(Environment.DIRECTORY_DOWNLOADS))
//                            + "/" + "123567");
//                    //File f = new File(mFolder.getAbsolutePath() + "/" + "noName12");
//
//                    try {
//                        FileProvider.getUriForFile()
//
//                        File.createTempFile("11hhjhhk", ".jps", mFolder);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

//                    if (!f.exists()) {
//                        try {
//                            f.createNewFile();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    env = "preprod.aadhaarapi.com";
                    Intent gatewayIntent = new Intent(MainActivity.this, ZoopConsentActivity.class);
                    gatewayIntent.putExtra(ZOOP_EMAIL, "divyankvijay.96@gmail.com"); //not mandatory
                    gatewayIntent.putExtra(ZOOP_TRANSACTION_ID, "2b3e4af7-21fd-45ec-9f6b-2454c2de8433");
                    gatewayIntent.putExtra(ZOOP_BASE_URL, "preprod.aadhaarapi.com");
                    gatewayIntent.putExtra(ZOOP_IS_ASSIST_MODE_ONLY, false);
//                    gatewayIntent.putExtra(ZOOP_EMAIL, email); //not mandatory
//                    gatewayIntent.putExtra(ZOOP_UID, uid); //not mandatory
                    gatewayIntent.putExtra(ZOOP_PHONE, "8087337240"); //not mandatory
                    gatewayIntent.putExtra(ZOOP_REQUEST_TYPE, OFFLINE_AADHAAR);
                    startActivityForResult(gatewayIntent, REQUEST_AADHAARAPI);
                } else {
                    reset();
                }
            }
        });
    }

    public void reset() {
        llInitLayout.setVisibility(View.VISIBLE);
        llFacematchScore.setVisibility(View.GONE);
        llEmailStatus.setVisibility(View.GONE);
        llPhoneStatus.setVisibility(View.GONE);
        ivCapturedImage.setVisibility(View.GONE);
        tvResult.setVisibility(View.GONE);
        resultDisplayLayout.setVisibility(View.GONE);
        llDetailedAddress.setVisibility(View.GONE);
        tvHouse.setVisibility(View.GONE);
        tvPostalcode.setVisibility(View.GONE);
        tvStreet.setVisibility(View.GONE);
        tvLocality.setVisibility(View.GONE);
        tvDistrict.setVisibility(View.GONE);
        tvPostOffice.setVisibility(View.GONE);
        tvStreet.setVisibility(View.GONE);
    }

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

    public void parseResultJson(JSONObject parsedXmlJson) {
        try {
            if (parsedXmlJson.has("transaction_data")) {
                JSONObject transactionDataJson = parsedXmlJson.getJSONObject("transaction_data");
                if (transactionDataJson.has("AddressEnglish")) {
                    String address = transactionDataJson.getString("AddressEnglish");
                    tvAddress.setText(address);
                }
                if (transactionDataJson.has("AadhaarInfo")) {
                    String uid = transactionDataJson.getString("AadhaarInfo");
                    tvUid.setText(uid);
                }
                if (transactionDataJson.has("FaceMatchScore")) {
                    String fmScore = transactionDataJson.getString("FaceMatchScore");
                    if (!fmScore.equalsIgnoreCase(null)) {
                        llFacematchScore.setVisibility(View.VISIBLE);
                        tvFMScore.setText(fmScore);
                        Float fmValue = Float.parseFloat(fmScore);
                        if (fmValue >= 75 && fmValue <= 85) {
                            tvFMScore.setTextColor(getResources().getColor(R.color.orangeDark));
                        } else if (fmValue > 85 && fmValue <= 90) {
                            tvFMScore.setTextColor(getResources().getColor(R.color.orange));
                        } else if (fmValue > 90 && fmValue <= 95) {
                            tvFMScore.setTextColor(getResources().getColor(R.color.green));
                        } else if (fmValue > 95) {
                            tvFMScore.setTextColor(getResources().getColor(R.color.greenDark));
                        } else {
                            tvFMScore.setTextColor(getResources().getColor(R.color.red));
                        }
                    }
                }
                if (transactionDataJson.has("EmailInfo")) {
                    emailStatus = transactionDataJson.getString("EmailInfo");
                    Log.i(ZOOP_TAG, emailStatus + "<== email");
                    llEmailStatus.setVisibility(View.VISIBLE);
                    tvEmailStatus.setText(emailStatus);
                } else {
                    Log.i(ZOOP_TAG, emailStatus + "<== email");

                }
                if (transactionDataJson.has("PhoneInfo")) {
                    phoneStatus = transactionDataJson.getString("PhoneInfo");
                    Log.i(ZOOP_TAG, phoneStatus + "<== phone");
                    llPhoneStatus.setVisibility(View.VISIBLE);
                    tvPhoneStatus.setText(phoneStatus);
                } else {
                    Log.i(ZOOP_TAG, phoneStatus + "<== email");
                }
                if (transactionDataJson.has("Image")) {
                    String aadhaarImage = transactionDataJson.getString("Image");
                    byte[] decodedString = Base64.decode(aadhaarImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    ivAadhaarImage.setImageBitmap(decodedByte);
                }

                if (transactionDataJson.has("UserSelfie")) {
                    String capturedImage = transactionDataJson.getString("UserSelfie");
                    byte[] decodedString = Base64.decode(capturedImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    ivCapturedImage.setVisibility(View.VISIBLE);
                    ivCapturedImage.setImageBitmap(decodedByte);
                }
                if (transactionDataJson.has("BasicInfo")) {
                    JSONObject basicInfoJson = transactionDataJson.getJSONObject("BasicInfo");
                    String name = basicInfoJson.getString("Name");
                    String dob = basicInfoJson.getString("DOB");
                    String gender = basicInfoJson.getString("Gender");
                    tvName.setText(name);
                    tvGender.setText(gender);
                    tvDob.setText(dob);
                }

                if (transactionDataJson.has("DetailedAddress")) {
                    llDetailedAddress.setVisibility(View.VISIBLE);
                    JSONObject detailedAddress = transactionDataJson.getJSONObject("DetailedAddress");
                    if (detailedAddress.has("street")) {
                        tvStreet.setVisibility(View.VISIBLE);
                        tvStreet.setText(String.format("street: %s", detailedAddress.getString("street")));
                    }
                    if (detailedAddress.has("house")) {
                        tvHouse.setVisibility(View.VISIBLE);
                        tvHouse.setText(String.format("house: %s", detailedAddress.getString("house")));
                    }
                    if (detailedAddress.has("locality")) {
                        tvLocality.setVisibility(View.VISIBLE);
                        tvLocality.setText(String.format("locality: %s", detailedAddress.getString("locality")));
                    }
                    if (detailedAddress.has("postoffice")) {
                        tvPostOffice.setVisibility(View.VISIBLE);
                        tvPostOffice.setText(String.format("post office: %s", detailedAddress.getString("postoffice")));
                    }
                    if (detailedAddress.has("street")) {
                        tvStreet.setVisibility(View.VISIBLE);
                        tvStreet.setText(String.format("Street: %s", detailedAddress.getString("street")));
                    }
                    if (detailedAddress.has("district")) {
                        tvDistrict.setVisibility(View.VISIBLE);
                        tvDistrict.setText(String.format("district: %s", detailedAddress.getString("district")));
                    }
                    if (detailedAddress.has("state")) {
                        tvState.setVisibility(View.VISIBLE);
                        tvState.setText(String.format("state: %s", detailedAddress.getString("state")));
                    }
                    if (detailedAddress.has("postalcode")) {
                        tvPostalcode.setVisibility(View.VISIBLE);
                        tvPostalcode.setText(String.format("postalcode: %s", detailedAddress.getString("postalcode")));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
