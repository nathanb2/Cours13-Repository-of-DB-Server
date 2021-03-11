package com.example.ex10_listfromdb.injection;

import android.content.Context;


import com.example.ex10_listfromdb.database.DataBase;
import com.example.ex10_listfromdb.repository.UserRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * class qui cree les instances de class necessaires aux differents viewModel du projet
 * L'objectif et que chaque class recoive directement tout ce dont elle a besoin et n'ai pas a recevoir de parametre qui lui serait necessaire a dans un deuxieme temps pouvoir construire les instance de ce dont elle a reeelement besoin
 * CHAQUE CLASS RECOIT CE DONT ELLE A BESOIN (EN PARAMETRE DE CONSTRUCTOR TOUTE LES VARIABLES QU'ELLE DECLARE AU NIVEAU DE LA CLASS)
 */
public class Injection {

    public static UserRepository provideUserRepository(Context context, Executor executor) {
        DataBase database = DataBase.getDatabase(context);
        return new UserRepository(database.userDao(), executor);
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        Executor executor = provideExecutor();
        UserRepository userRepository = provideUserRepository(context, executor);
        return new ViewModelFactory(userRepository, executor);
    }
}