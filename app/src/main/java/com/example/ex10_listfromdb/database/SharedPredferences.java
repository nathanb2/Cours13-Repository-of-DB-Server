package com.example.ex10_listfromdb.database;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.ex10_listfromdb.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Class permettant de gerer l'ecriture et la lecture dasn le fichier de local storage dedie a l'application
 * Class construite avec le design pattern singleton donc on ne peut en creer q'une instance
 */
public class SharedPredferences {

    /**
     * nom du fichier de local storage de notre app
     */
    private static final String MY_STORAGE_FILE_NAME = "MY_STORAGE_FILE_NAME";
    /**
     * les differentes cles sous lequels on sauvegarde nos valeures dasn le fichier
     */
    private static final String LAST_USERS_FETCH_KEY = "LAST_USERS_FETCH_KEY";
    private static final String USER_KEY = "USER_KEY";
    private static final String USERS_KEY = "USERS_KEY";
    /**
     * variable static contenant la seul est unique instance que l'on cree de cette class
     */
    private static SharedPredferences instance;
    /**
     * outil d'ecriture et de lecture dans le local storage que nous fournit Android (importer plus haut : import android.content.SharedPreferences;)
     */
    private final SharedPreferences mSharedPref;

    /**
     * @param context constructeur private, on ne peut l'appeler de l'exteieure de la class
     *                et ne pouvons donc creer de nouvelles instance de celle ci
     */
    private SharedPredferences(Context context){
        mSharedPref = context.getSharedPreferences(MY_STORAGE_FILE_NAME, MODE_PRIVATE);
    }

    /**
     * @return SharedPref instance , can be null
     */
    public static SharedPredferences getInstance(){
        return instance;
    }


    /**
     * @param context necessaire pour creer l'instance de la classe donc doit pas etre null
     *                car si premiere appeel de la fonction ne pourras creer l'instance
     *                si la classe qui l'appel n'a pas de context, utiliser getInstance() mais attention peut retourner null
     *
     * @return
     */
    public static SharedPredferences getInstance(@NonNull Context context){
        if (instance == null) {
            //si l'instance est null alors creation de l'instance
            instance = new SharedPredferences(context);
        }
        return instance;
    }

    public void setLastGithubUsersFetch(long timestamp){
        mSharedPref.edit().putLong(LAST_USERS_FETCH_KEY, timestamp).apply();
    }

    public long getLastGithubUsersFetch(){
        return mSharedPref.getLong(LAST_USERS_FETCH_KEY, 0);
    }

    public void setUser(User user) {
        //Gson convertit un objet en String strucure sous forme de key/value
        String userString = new Gson().toJson(user);
        mSharedPref.edit().putString(USER_KEY, userString).apply();
    }

    public User getUser(){
        String userString = mSharedPref.getString(USER_KEY, "");
        //Gson reconvertit le Json precedement cree en Objet User
        // dans lequel il sait inserer les donnees car les keys du Json correspondent au nom des variables de la class User
        User user = new Gson().fromJson(userString, User.class);
        return user;
    }

    public void setUsers(ArrayList<User> users){
        String usersString = new Gson().toJson(users);
        mSharedPref.edit().putString(USERS_KEY, usersString).apply();
    }

    public ArrayList<User> getUsers(){
        //definition d'un objet type pour indiquer a Gson sous quel format lire le Json recuperer en String pour le convertire en une liste de Users
        Type listType = new TypeToken<ArrayList<User>>() {}.getType();
        String usersString = mSharedPref.getString(USERS_KEY, "");
        ArrayList<User> users = new Gson().fromJson(usersString, listType);
        return users;
    }

}
