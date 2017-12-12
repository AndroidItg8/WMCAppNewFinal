package itg8.com.wmcapp.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import itg8.com.wmcapp.board.model.DeleteNoticeModel;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.complaint.model.ComplaintCategoryModel;
import itg8.com.wmcapp.complaint.model.LikeModel;
import itg8.com.wmcapp.emergency.model.EmergencyModel;
import itg8.com.wmcapp.news.model.NewsModel;
import itg8.com.wmcapp.prabhag.model.PrabhagModel;
import itg8.com.wmcapp.profile.ProfileModel;
import itg8.com.wmcapp.profile.model.UserLikeModel;
import itg8.com.wmcapp.registration.RegistrationModel;
import itg8.com.wmcapp.torisum.model.SubCatList;
import itg8.com.wmcapp.torisum.model.TorisumModel;
import itg8.com.wmcapp.torisum.model.TourismFilterCategoryModel;
import itg8.com.wmcapp.torisum.model.TourismFilterModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by swapnilmeshram on 31/10/17.
 */

public interface RetroController {
    @GET
    Call<List<CityModel>> getCityFromServer(@Url String url);

    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<ResponseBody> checkAuthentication(@Url String url,
                                           @Field("grant_type") String password,
                                           @Field("username") String emailId,
                                           @Field("password") String pswd);

    //    FullName:Aman Ambarte
//    AddressLine1:sefwe
//    AddressLine2:sefwe
//    ContactNumber:786786786786
//    EmailId:Aman@gmail.com
//    Lid:0
//    UserName:Aman@gmail.com
//    RegisteredDate:2017-11-17 06:28:19.177
//    RoleName:AppUser
//    pkid:19
//    LastModifiedDate:
    @Multipart
    @POST()
    Call<RegistrationModel> sendRegistrationInfoToserver(@Url String url,
                                                         @Part("FullName") RequestBody name,
                                                         @Part("AddressLine1") RequestBody address,
                                                         @Part("AddressLine2") RequestBody city,
                                                         @Part("ContactNumber") RequestBody mobile,
                                                         @Part("EmailId") RequestBody emailId,
//                                                         @Part("Lid") RequestBody lid,
//                                                         @Part("UserName") RequestBody userName,
//                                                         @Part("RegisteredDate") RequestBody regisDate,
//                                                         @Part("RoleName") RequestBody appUser,
                                                         @Part("pkid") RequestBody pid,
                                                         @Part MultipartBody.Part file


    );


//    Email:Aman@gmail.com

//    RegisteredDate:2017-11-17 06:28:19.177

    //    OldPassword:WMC123
//    NewPassword:123456
    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<RegistrationModel> changePassword(@Url String url, @Field("OldPassword") String oldpswd, @Field("NewPassword") String newpswd);


    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<RegistrationModel> forgetPaswd(@Url String url, @FieldMap Map<String, String> parameters);

//    Category_fkid:1
//    ComplaintName:Dash
//    ComplaintDescription:drfgv
//    Longitutde:sdfgv
//    Latitude:rgs
//    City_fkid:1

    //

//    skip:0
//    pageSize:10
//    User_Wise:0
//    cityid:0

    @GET()
    Observable<ResponseBody> loadComplaint(@Url String url, @Query("skip") int page, @Query("pageSize") int limit, @Query("User_Wise") int from, @Query("cityid") int cityId);

    @GET()
    Observable<ResponseBody> loadNoticeBoard(@Url String url, @Query("skip") int page, @Query("pageSize") int limit, @Query("cityid") int cityId);


    @Multipart
    @POST()
    Observable<ResponseBody> addComplaint(@Url String url,
                                          @Part MultipartBody.Part file,
                                          @Part("Latitude") RequestBody lat,
                                          @Part("Longitutde") RequestBody lang,
                                          @Part("ComplaintName") RequestBody addr,
                                          @Part("ComplaintDescription") RequestBody desc,
                                          @Part("City_fkid") RequestBody cityId,
                                          @Part("ShowIdentity") RequestBody identity,
                                          @Part("Category_fkid") RequestBody categoryId);

    @Multipart
    @POST()
    Observable<HashMap<Integer,ResponseBody>> addComplaintOffline(@Url String url,
                                          @Part MultipartBody.Part file,
                                          @Part("Latitude") RequestBody lat,
                                          @Part("Longitutde") RequestBody lang,
                                          @Part("ComplaintName") RequestBody addr,
                                          @Part("ComplaintDescription") RequestBody desc,
                                          @Part("City_fkid") RequestBody cityId,
                                          @Part("ShowIdentity") RequestBody identity);

    @GET()
    Call<List<TorisumModel>> getTorisumList(@Url String loadUrl);

    @GET()
    Call<List<ProfileModel>> getProfile(@Url String url);

    //    Master_fkid:1
//    SubMaster_fkid:5
//    Likes:1
    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<RegistrationModel> sendLike(@Url String url, @Field("Master_fkid") int complaint, @Field("SubMaster_fkid") int subMaster_fkid, @Field("Likes") int i);

    @GET()
    Call<List<TourismFilterModel>> loadCategoryTourism(@Url String loadUrl);

    @Multipart
    @POST()
    Observable<ResponseBody> addSuggestion(@Url String url,
                                           @Part MultipartBody.Part file,
                                           @Part("description") RequestBody title,
                                           @Part("title") RequestBody desc);

    @GET()
    Call<List<EmergencyModel>> getEmergency(@Url String url);

    //    Email:jay@gmail.com
//    Password:123456
//    ConfirmPassword:123456
//    UserRoles:AppUser
//    MobileNumber:9823778532
//    FullName:ayesha
    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<RegistrationModel> registartion(@Url String url,
                                         @Field("Email") String emial,
                                         @Field("Password") String passd,
                                         @Field("ConfirmPassword") String cpassd,
                                         @Field("UserRoles") String AppUser,
                                         @Field("MobileNumber") String mobile,
                                         @Field("FullName") String name
    );

    @GET()
    Call<List<PrabhagModel>> getPragbhagList(@Url String loadUrl);

    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<RegistrationModel> deleteNBFromServer(@Url String url,@Field("Notice_fkid") int pkid);

    @GET()
    Call<List<UserLikeModel>> getUserLikeList(@Url String url);

    @GET()
    Call<List<NewsModel>> getNewsList(@Url String url);

    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<RegistrationModel> submitFeedback(@Url String url,
                                           @Field("rateAmt") int rating,
                                           @Field("description") String description);

    @GET()
    Call<List<DeleteNoticeModel>> getDeleteNBList(@Url String url);

    @POST()
    @Headers("Content-Type:application/json")
    Call<List<TorisumModel>> getFilterTourismList(@Url String url,@Body List<TourismFilterCategoryModel> torismFilterCategory);
    @GET()
    Call<List<ComplaintCategoryModel>> getComplaintCategory(@Url String url);
//    @Field("Title") String title,


}
