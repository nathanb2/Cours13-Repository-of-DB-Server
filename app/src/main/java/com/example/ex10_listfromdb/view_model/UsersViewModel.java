package com.example.ex10_listfromdb.view_model;


import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ex10_listfromdb.model.DataResponse;
import com.example.ex10_listfromdb.model.User;
import com.example.ex10_listfromdb.repository.UserRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class UsersViewModel extends ViewModel {
    private final Executor mExecutor;
    private UserRepository mRepository;
    //permet de garder la position visible de la liste de users lors de la rotation
    //le fragment est detruit et recree avec une nouvelle instance mais l'instance du viewModel recupere est la emem donc on y garde la valeur la position visible de la recyclerView
    //ainsi on peut a la reouverture du fragment lui dir de scrolle automatiquement jusqu'a la derniere position visile et non de ramener l'utilisateur en haut de la liste
    private int recyclerViewVisiblePosition;

    public UsersViewModel(UserRepository userRepository, Executor executor) {
        mRepository = userRepository;
        mExecutor = executor;
    }

    public MediatorLiveData<DataResponse<List<User>>> getAllUsers() {
        return mRepository.getAllUsers();
    }

    public void createUser(User user) {
        mExecutor.execute(() -> mRepository.createUser(user));
    }

    public void removeUser(User user) {
        mExecutor.execute(() -> mRepository.removeUser(user));
    }

    public void updateUser(User user) {
        mExecutor.execute(() -> mRepository.updateUser(user));
    }

    public void setRecyclerViewVisiblePosition(int recyclerViewVisiblePosition) {
        this.recyclerViewVisiblePosition = recyclerViewVisiblePosition;
    }

    public int getRecyclerViewVisiblePosition() {
        return recyclerViewVisiblePosition;
    }
}