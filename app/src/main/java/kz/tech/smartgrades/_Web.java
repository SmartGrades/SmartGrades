package kz.tech.smartgrades;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.StringReader;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.parent.ParentActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class _Web{

    //FUNC NAME FOR REQUEST SOAP
    public static final String func_RegisterUser = "RegisterUser";
    public static final String func_RegisterSchool = "RegisterSchool";
    public static final String func_ParentRegisterChild = "ParentRegisterChild";
    public static final String func_GetUserAccess = "GetUserAccess";
    public static final String func_UserEditPassword = "UserEditPassword";
    public static final String func_SetUserAccess = "SetUserAccess";
    public static final String func_AuthUser = "AuthUser";
    public static final String func_UpdateUserData = "UpdateUserData";
    public static final String func_DeleteUserData = "DeleteUserData";
    public static final String func_GetUser = "GetUser";
    public static final String func_GetMentorRoom = "GetMentorRoom";
    public static final String func_GetSponsorRoom = "GetSponsorRoom";
    public static final String func_AddMentorRoom = "AddMentorRoom";
    public static final String func_AddAboutMe = "AddAboutMe";
    public static final String func_AddSponsorRoom = "AddSponsorRoom";
    public static final String func_GetMentorGroup = "GetMentorGroup";
    public static final String func_AddMentorGroup = "AddMentorGroup";
    public static final String func_GetChildrenList = "GetChildrenList";
    public static final String func_GetParentsListInFamilyGroup = "GetParentsListInFamilyGroup";
    public static final String func_InterFormInFamilyGroup = "InterFormInFamilyGroup";
    public static final String func_CheckInterFormInFamilyGroup = "CheckInterFormInFamilyGroup";
    public static final String func_RejectInterFormInFamilyGroup = "RejectInterFormInFamilyGroup";
    public static final String func_RejectInterFormOfSponsorship = "RejectInterFormOfSponsorship";
    public static final String func_AcceptInterFormOfSponsorship = "AcceptInterFormOfSponsorship";
    public static final String func_GetTimeInterFormInFamilyGroup = "GetTimeInterFormInFamilyGroup";
    public static final String func_AcceptInterFormInFamilyGroup = "AcceptInterFormInFamilyGroup";
    public static final String func_SponsorGetChild = "SponsorGetChild";
    public static final String func_GetChildLessons = "GetChildLessons";
    public static final String func_Chats = "Chats";
    public static final String func_GetSchoolList = "GetSchoolList";
    public static final String func_InterFormParentToSchool = "InterFormParentToSchool";
    public static final String func_DeleteContactFromFamilyGroup = "DeleteContactFromFamilyGroup";
    public static final String func_MentorSetGrade = "MentorSetGrade";
    public static final String func_GetSchools = "GetSchools";
    public static final String func_GetSchoolClassList = "GetSchoolClassList";
    public static final String func_AcceptInterFormOfMentoring = "AcceptInterFormOfMentoring";
    public static final String func_RejectInterFormOfMentoring = "RejectInterFormOfMentoring";
    public static final String func_GetParentsList = "GetParentsList";
    public static final String func_SponsorToChildChats = "SponsorToChildChats";
    public static final String func_RemoveChat = "RemoveChat";
    public static final String func_CancelInterForm = "CancelInterForm";
    public static final String func_RemoveChildFromMentorGroup = "RemoveChildFromMentorGroup";
    public static final String func_ParentRemoveChildFromMentorGroup = "ParentRemoveChildFromMentorGroup";
    public static final String func_GetParentData = "GetParentData";
    public static final String func_SendSMSCode = "SendSMSCode";
    public static final String func_IsFreeLogin = "IsFreeLogin";
    public static final String func_IsFreeEmail = "IsFreeEmail";
    public static final String func_ParentAddLessonForChild = "ParentAddLessonForChild";
    public static final String func_EditNameDiscontCard = "EditNameDiscontCard";
    public static final String func_GetMentorOrSponsorList = "GetMentorOrSponsorList";
    public static final String func_GetMentorData = "GetMentorData";
    public static final String func_GetSponsorData = "GetSponsorData";
    public static final String func_Post3Ds = "Post3Ds";
    public static final String func_GetChildData = "GetChildData";
    public static final String func_AddGrade = "AddGrade";
    public static final String func_ParentAddGrade = "ParentAddGrade";
    public static final String func_ParentAddGradeInsteadOfMentor = "ParentAddGradeInsteadOfMentor";
    public static final String func_SendMessage = "SendMessage";
    public static final String func_RemoveGrade = "RemoveGrade";
    public static final String func_GetLessons = "GetLessons";
    public static final String func_AddLesson = "AddLesson";
    public static final String func_AddDiscontCard = "AddDiscontCard";
    public static final String func_Replenish = "Replenish";
    public static final String func_Extract = "Extract";
    public static final String func_ParentSendMoneyToChild = "ParentSendMoneyToChild";
    public static final String func_ParentSetChildReward = "ParentSetChildReward";

    public static final String func_SponsorAddChild = "SponsorAddChild";
    public static final String func_SendMessageToGroups = "SendMessageToGroups";

    public static final String func_GetMentorList = "GetMentorList";
    public static final String func_GetTransactions = "GetTransactions";
    public static final String func_GetMessages = "GetMessages";
    public static final String func_GetLessonInfo = "GetLessonInfo";
    public static final String func_GetSchoolInfo = "GetSchoolInfo";
    public static final String func_ChildDeleteMentor = "ChildDeleteMentor";
    public static final String func_ChildDeleteLesson = "ChildDeleteLesson";
    public static final String func_SetLessonSmartGrades = "SetLessonSmartGrades";
    public static final String func_DisableSmartGradesForLesson = "DisableSmartGradesForLesson";
    public static final String func_RemoveDiscontCard = "RemoveDiscontCard";
    public static final String func_ChildAddMentor = "ChildAddMentor";

    //INTER FORM
    public static final String func_InterFormParentToChild = "InterFormParentToChild";
    public static final String func_AcceptInterFormParentToChild = "AcceptInterFormParentToChild";
    public static final String func_RejectInterFormParentToChild = "RejectInterFormParentToChild";

    public static final String func_InterFormOfSponsorship = "InterFormOfSponsorship";
    public static final String func_AcceptInterFormParentToSponsor = "AcceptInterFormParentToSponsor";
    public static final String func_RejectInterFormParentToSponsor = "RejectInterFormParentToSponsor";
    public static final String func_AcceptInterFormSponsorToParent = "AcceptInterFormSponsorToParent";
    public static final String func_RejectInterFormSponsorToParent = "RejectInterFormSponsorToParent";

    public static final String func_GetSponsorParentInterFormData = "GetSponsorParentInterFormData";
    public static final String func_AcceptSponsorEditParentInterForm = "AcceptSponsorEditParentInterForm";

    public static final String func_InterFormParentToMentor = "InterFormParentToMentor";
    public static final String func_AcceptInterFormMentorToParent = "AcceptInterFormMentorToParent";
    public static final String func_RejectInterFormMentorToParent = "RejectInterFormMentorToParent";

    public static final String func_InterFormMentorToParent = "InterFormMentorToParent";
    public static final String func_AcceptInterFormParentToMentor = "AcceptInterFormParentToMentor";
    public static final String func_RejectInterFormParentToMentor = "RejectInterFormParentToMentor";

    public static final String func_InterFormParentToParent = "InterFormParentToParent";
    public static final String func_AcceptInterFormParentToParent = "AcceptInterFormParentToParent";
    public static final String func_RejectInterFormParentToParent = "RejectInterFormParentToParent";

    public static final String func_UpdateCheckedSponsored = "UpdateCheckedSponsored";
    public static final String func_GradesTable = "GradesTable";
    public static final String func_ParentSetGrade = "ParentSetGrade";

    public static final String func_GetCountryList = "GetCountryList";
    public static final String func_GetRegionList = "GetRegionList";
    public static final String func_GetCityList = "GetCityList";
    public static final String func_GetSchoolData = "GetSchoolData";

    public static final String func_SchoolAddColumnToClassess = "SchoolAddColumnToClassess";
    public static final String func_SchoolDeleteColumn = "SchoolDeleteColumn";
    public static final String func_SchoolEditColumn = "SchoolEditColumn";
    public static final String func_SchoolAddClass = "SchoolAddClass";
    public static final String func_SchoolDeleteClass = "SchoolDeleteClass";
    public static final String func_SchoolMoveClass = "SchoolMoveClass";
    public static final String func_SchoolMoveClassv2 = "SchoolMoveClassv2";

    public static final String func_SchoolAddStudent = "SchoolAddStudent";
    public static final String func_SchoolAddTeacher = "SchoolAddTeacher";
    public static final String func_SchoolAddLesson = "SchoolAddLesson";
    public static final String func_SchoolGetStudentsList = "SchoolGetStudentsList";
    public static final String func_SchoolGetTeachersList = "SchoolGetTeachersList";
    public static final String func_SchoolGetLessonsList = "SchoolGetLessonsList";
    public static final String func_GetRequestMessages = "GetRequestMessages";

    public static final String func_MentorAddColumnToClassess = "MentorAddColumnToClassess";
    public static final String func_MentorAddClass = "MentorAddClass";
    public static final String func_MentorEditColumn = "MentorEditColumn";
    public static final String func_GetChildrenListForSponsored = "GetChildrenListForSponsored";
    public static final String func_InterFormMentorToSchool = "InterFormMentorToSchool";
    public static final String func_RejectInterFormMentorToSchool = "RejectInterFormMentorToSchool";
    public static final String func_SchoolAddStudentV2 = "SchoolAddStudentV2";
    public static final String func_SchoolAddStudentV3 = "SchoolAddStudentV3";

    //EncryptDecrypt DATA
    private static final String characterEncoding = "UTF8";
    private static final String cipherTransformation = "AES/CBC/PKCS5Padding";
    private static final String aesEncryptionAlgorithm = "AES";

    //OkHTTP CLIENT DATA
    public static OkHttpClient HttpClient = new OkHttpClient();
    public static final MediaType ContentType_XML = MediaType.get("text/xml; charset=utf-8");

    //SERVICE DATA
    public static final String URL = "http://145.249.246.125:81/WebService.asmx";
    private static final String PublicKey = "!Fw?1SaY3PhjIFu0";

    public static String encrypt(String plainText, String key) throws Exception{
        byte[] plainTextbytes = plainText.getBytes(characterEncoding);
        byte[] keyBytes = key.getBytes(characterEncoding);
        return Base64.encodeToString(encrypt(plainTextbytes, keyBytes, keyBytes), Base64.DEFAULT).replace("\n", "");
    }
    public static String encrypt(String plainText) throws Exception{
        byte[] plainTextbytes = plainText.getBytes(characterEncoding);
        byte[] keyBytes = PublicKey.getBytes(characterEncoding);
        return Base64.encodeToString(encrypt(plainTextbytes, keyBytes, keyBytes), Base64.DEFAULT).replace("\n", "");
    }
    private static byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector) throws Exception{
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        plainText = cipher.doFinal(plainText);
        return plainText;
    }
    public static String decrypt(String encryptedText, String key) throws Exception{
        byte[] cipheredBytes = Base64.decode(encryptedText, Base64.DEFAULT);
        byte[] keyBytes = key.getBytes(characterEncoding);
        return new String(decrypt(cipheredBytes, keyBytes, keyBytes), characterEncoding);
    }
    public static String decrypt(String encryptedText) throws Exception{
        byte[] cipheredBytes = Base64.decode(encryptedText, Base64.DEFAULT);
        byte[] keyBytes = PublicKey.getBytes(characterEncoding);
        return new String(decrypt(cipheredBytes, keyBytes, keyBytes), characterEncoding);
    }
    private static byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector) throws Exception{
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpecy = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
        cipherText = cipher.doFinal(cipherText);
        return cipherText;
    }

    public static String XMLReader(String stringXML){
        try{
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            SAXPars saxp = new SAXPars();
            parser.parse(new InputSource(new StringReader(stringXML)), saxp);
            return decrypt(saxp.getResult());
        } catch(Exception e){
            System.out.println(e);
            return "0";
        }
    }
    public static class SAXPars extends DefaultHandler{
        private String thisElement;
        private String result;
        boolean isAccumulating = false;

        public String getResult(){
            return result;
        }
        @Override
        public void startDocument() throws SAXException{
            result = "";
        }
        @Override
        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException{
            thisElement = qName;
            if(thisElement.equals("result")) isAccumulating = true;
        }
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException{
            if(thisElement.equals("result")) result += new String(ch, start, length);
        }
        @Override
        public void endElement(String namespaceURI, String localName, String qName) throws SAXException{
            thisElement = "";
        }
    }

    public boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String SoapRequest(String funcName, String Data){
        try{
            String enData = "";
            if(!stringIsNullOrEmpty(Data)) enData = encrypt(Data);
            String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                    "<soap:Body>" +
                    "<" + funcName + " xmlns=\"http://esparta.kz/webservices\">" +
                    "<_Data>" + enData + "</_Data>" +
                    "</" + funcName + ">" +
                    "</soap:Body>" +
                    "</soap:Envelope>";
            return soap;
        } catch(Exception e){
            return "";
        }
    }
    public static String SoapRequest(String funcName, String Data, String key){
        try{
            String enData = encrypt(Data, key);
            String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                    "<soap:Body>" +
                    "<" + funcName + " xmlns=\"http://esparta.kz/webservices\">" +
                    "<_Data>" + enData + "</_Data>" +
                    "</" + funcName + ">" +
                    "</soap:Body>" +
                    "</soap:Envelope>";
            return soap;
        } catch(Exception e){
            return "";
        }
    }

    public static void SendGrade(MentorActivity activity, String mentorId, String childId, String groupId, String lessonId, int msgType, String data, String ChatId){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.MentorId, mentorId);
        jsonData.addProperty(F.ChildId, childId);
        jsonData.addProperty(F.GroupId, groupId);
        jsonData.addProperty(F.LessonId, lessonId);
        jsonData.addProperty(F.Data, data);
        jsonData.addProperty(F.Type, msgType);
        jsonData.addProperty(F.ChatId, ChatId);

        String SOAP = SoapRequest(func_AddGrade, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            if(answer.isSuccess()) activity.presenter.onStartPresenter();
                            else activity.alert.onToast(answer.getMessage());
                        }
                    });
                }
            }
        });
    }
    public static void ParentSendGradeInsteadOfMentor(ParentActivity activity, String parentId, String childId, String groupId, String lessonId, int msgType, String data, String ChatId){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.ParentId, parentId);
        jsonData.addProperty(F.ChildId, childId);
        jsonData.addProperty(F.GroupId, groupId);
        jsonData.addProperty(F.LessonId, lessonId);
        jsonData.addProperty(F.Data, data);
        jsonData.addProperty(F.Type, msgType);
        jsonData.addProperty(F.ChatId, ChatId);

        String SOAP = SoapRequest(func_ParentAddGradeInsteadOfMentor, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            if(answer.isSuccess()) activity.presenter.onStartPresenter();
                            else activity.alert.onToast(answer.getMessage());
                        }
                    });
                }
            }
        });
    }
    public static void ParentSendGrade(ParentActivity activity, String parentId, String childId, String lessonId, String data, String ChatId){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.ParentId, parentId);
        jsonData.addProperty(F.ChildId, childId);
        jsonData.addProperty(F.LessonId, lessonId);
        jsonData.addProperty(F.Data, data);
        jsonData.addProperty(F.ChatId, ChatId);

        String SOAP = SoapRequest(func_ParentAddGrade, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            if(answer.isSuccess()) activity.presenter.onStartPresenter();
                            else activity.alert.onToast(answer.getMessage());
                        }
                    });
                }
            }
        });
    }
}