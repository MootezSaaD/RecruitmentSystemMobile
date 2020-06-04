package tn.medtech.recruitmentsystemapp.api.services;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.medtech.recruitmentsystemapp.util.TokenAuthenticator;
import tn.medtech.recruitmentsystemapp.util.TokenService;

public class ServiceGenerator {

    private static final String API_BASE_URL = "http://10.0.2.2:3000/api/";

    private static Retrofit retrofit;

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass) {

        retrofit = builder.client(httpClient.build()).build();

        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceWithAuth(Class<S> serviceClass, TokenService tokenService) {
        OkHttpClient.Builder client = httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                Request.Builder builder = request.newBuilder();

                if (tokenService.getToken().getAccessToken() != null) {
                    builder.addHeader("Authorization", "Bearer " + tokenService.getToken().getAccessToken());
                }
                request = builder.build();
                return chain.proceed(request);
            }
        }).authenticator(TokenAuthenticator.getInstance(tokenService));

        retrofit = builder.client(client.build()).build();

        return retrofit.create(serviceClass);
    }

    /**
     * or Error Handing when non-OK response is received from Server
     */
    @NonNull
    public static Retrofit retrofit() {
        OkHttpClient client = httpClient.build();
        ;
        return builder.client(client).build();
    }
}