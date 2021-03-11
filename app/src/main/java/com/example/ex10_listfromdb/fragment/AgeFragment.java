package com.example.ex10_listfromdb.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ex10_listfromdb.view_model.UserDetailsViewModel;
import com.example.ex10_listfromdb.databinding.FragmentAgeBinding;
import com.example.ex10_listfromdb.injection.Injection;
import com.example.ex10_listfromdb.injection.ViewModelFactory;


/**
 * Ouvert directement depuis le xml de userDetailsActivity grace au dataBinding qui permet de realiser directement la fonction du onclick
 * soit: l'appel de l'action action_userDetailsFragment_to_ageFragment declare dans le main_navigation
 */
public class AgeFragment extends Fragment {

    private FragmentAgeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAgeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //on recupere le mem viewModel que dans userDetailsfragment (meme instance)
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        UserDetailsViewModel userDetailsViewModel = new ViewModelProvider(getActivity(), viewModelFactory).get(UserDetailsViewModel.class);

        //ainsi on recupere le user qui avait etait insere dans le viewModel dans UserdetailsFragment
        binding.setAge(String.valueOf(userDetailsViewModel.getUser().getId()));
    }
}