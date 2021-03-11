package com.example.ex10_listfromdb.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ex10_listfromdb.model.User;
import com.example.ex10_listfromdb.repository.UserRepository;


public class UserDetailsViewModel extends ViewModel {
    private UserRepository mRepository;
    /**
     * permet de conserver le user une fois recupere depuis la database
     * (Ici utilise pour affiche l'age dans AgeFragment ou l'on recupere le meme viewModel que dans userDetailsFragment
     * car le lifecycleowner du viewmodel est labas declare comme etant l'activity donc on a bien la meme instance de viewModel pour nos 2 fragments)
     */
    private User mUser;

    public UserDetailsViewModel(UserRepository userRepository) {
        mRepository = userRepository;
    }

    public LiveData<User> getUserById(long id) {
        return mRepository.getUserById(id);
    }

    public void setUser(User user) {
        mUser = user;
    }

    public User getUser() {
        return mUser;
    }
}