package in.techaddicts.bluenix.Retrofit;

import in.techaddicts.bluenix.Model.FCMResponse;
import in.techaddicts.bluenix.Model.FCMSendData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi  {
        @Headers({
                "Content-Type:application/json",
                "Authorization:key=AAAAT-CEDUk:APA91bFoXhR0rK3qb6TGHnHRWJl6pr33JqCm2crrNKerta0Vxxcwe_Sos-AeiiN-VAEgIgF4oiUjwf-PWHbvRpswLNkCFopQB1bdmThSt4O9Sd4gY_B5mMi6MmmsQprrCU6KeyZxO358"
        })
        @POST("fcm/send")
        Observable<FCMResponse> sendNotification (@Body FCMSendData body);
    }


    //  AAAAT-CEDUk:APA91bFoXhR0rK3qb6TGHnHRWJl6pr33JqCm2crrNKerta0Vxxcwe_Sos-AeiiN-VAEgIgF4oiUjwf-PWHbvRpswLNkCFopQB1bdmThSt4O9Sd4gY_B5mMi6MmmsQprrCU6KeyZxO358