package com.allandroidprojects.buildsmart.payu_de;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


import static com.allandroidprojects.buildsmart.payu_de.Constant.SERVER_main_folder;


public interface ServiceInterface {

    // method,, return type ,, secondary url
    @Multipart
    @POST(SERVER_main_folder+"new_hash.php")
    Call<String> getHashCall(
            @Part("key") RequestBody key,
            @Part("txnid") RequestBody txnid,
            @Part("amount") RequestBody amount,
            @Part("productinfo") RequestBody producinfo,
            @Part("firstname") RequestBody firstname,
            @Part("email") RequestBody email
    );

}
