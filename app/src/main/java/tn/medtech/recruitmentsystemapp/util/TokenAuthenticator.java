package tn.medtech.recruitmentsystemapp.util;

import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import tn.medtech.recruitmentsystemapp.api.models.AccessToken;
import tn.medtech.recruitmentsystemapp.api.services.ServiceGenerator;
import tn.medtech.recruitmentsystemapp.api.services.UserClient;

public class TokenAuthenticator implements Authenticator {

    private static TokenAuthenticator INSTANCE;
    private TokenService tokenService;

    private TokenAuthenticator(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public static synchronized TokenAuthenticator getInstance(TokenService tokenService) {
        if (INSTANCE == null) {
            INSTANCE = new TokenAuthenticator(tokenService);
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        AccessToken token = tokenService.getToken();
        UserClient userClient = ServiceGenerator.createService(UserClient.class);

        Call<AccessToken> call = userClient.refreshToken(token.getRefreshToken());
        retrofit2.Response<AccessToken> res = call.execute();

        if (res.isSuccessful()) {
            AccessToken newToken = res.body();
            tokenService.saveToken(newToken);

            return response.request().newBuilder().header("Authorization", "Bearer " + res.body().getAccessToken()).build();
        } else {
            return null;
        }
    }
}