package com.example.ex10_listfromdb.networking;

import com.example.ex10_listfromdb.networking.api.GithubApiService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class qui instancie une instance de API service a travers une instance de retrofit
 * Permet de gerer les requests Http
 *
 * Pour effectuer une request http ne pas oublier d'ajouter la permission dans le manifest   <uses-permission android:name="android.permission.INTERNET" />
 * Pour utiliser retrofit, son GsonParser et son httpLogging ne pas oublier d'importer les library dans le gradle
 *
 * Gradle app:
 * dans  dependencies{
 * //library necessaires a retrofit
 *     implementation 'com.squareup.retrofit2:retrofit:2.4.0'
 *     implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
 *     //pour le logging interceptor
 *     implementation 'com.squareup.okhttp3:logging-interceptor:4.9.0'
 *     }
 */
public class RetrofitHelper {

    /**
     * est ajoute par le okkHttpClient a toute les request et permet de logger toutes les etapes de la request
     */
    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    /**
     * Ajoute par le httpClient un Header a toute les request effectue par le okHttpClient (donc toutes les request)
     */
    private static final Interceptor headerInterceptor = new Interceptor() {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            //ici on recupere la request juste avant qu'elle soit execute et lui rajoute un header
            Request request = chain.request().newBuilder().addHeader("key", "value").build();
            return chain.proceed(request);
        }
    };

    /**
     * Objet qui realise la request Http
     */
    private static final OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build();

    /**
     * Objet qui gere la realisation des request http
     * baseUrl est l'API de base de toute nos request auquel s'ajoutera la terminaison propre a chaque request dans l'APIService
     * client est l'instance de okHttpClient qui realise concretement la request
     * converter est le converter GSON qui permet de passer en parametre et attendre en reponse
     * des instance d'objet Java alors que les request emettent et recoivent du text au format Json (il effectue donc les conversion Objet Java <-> text Json)
     * de plus en appelant la fonction enqueu pour executer nos request
     */
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     * Instance de l'APIService cree via notre objet retrofit et qui donc permetra d'executer les request a l'aide de retrofit
     */
    private static final GithubApiService service = retrofit.create(GithubApiService.class);

    /**
     * @return retourne l'instance de l"APIService implemente par retrofit, c'est donc via service
     * et donc l'appel de cette fonction qu'on appelera nops differentes request declarees
     * dans l'APIService pour les executer avec retrofit
     */
    public static GithubApiService getGithubApiService() {
        return service;
    }
}
