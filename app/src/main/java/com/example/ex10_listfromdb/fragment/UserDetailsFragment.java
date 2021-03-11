package com.example.ex10_listfromdb.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ex10_listfromdb.model.User;
import com.example.ex10_listfromdb.databinding.FragmentUserDetailsBinding;
import com.example.ex10_listfromdb.injection.Injection;
import com.example.ex10_listfromdb.injection.ViewModelFactory;
import com.example.ex10_listfromdb.view_model.UserDetailsViewModel;

public class UserDetailsFragment extends Fragment {

    private @NonNull FragmentUserDetailsBinding binding;
    private UserDetailsViewModel userDetailsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // et cree une instance de FragmentUserDetailsBinding (class generee) par laquelle on peut directeemnt acceder a toutes les vues du layout sans passer par findviewbyid
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Le fragment ayant etait ouvert par l'action action_usersFragment_to_userDetailsFragment
        //et le fragment UsersDetailsFragment ayant pour argument long id (voir main_navigation)
        //on recupere l'argument passe lors de lexecution de l'action grace la class generee UserDetailsFragmentArgs
        // (qui en verite utilise un bundle (variable argument de la class mere)) comme nous le faisions manuelleemnt au paravant
        long userId = UserDetailsFragmentArgs.fromBundle(getArguments()).getUserId();

        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        //on passe getActivity en lifeCycleOwner du viewModelProvider afin que l'instance de notre viewModel soit la meme pour tout les fragments de cette activity
        //le lifeCycleOwner de definit pas que le viewModel est le meme uniquement pour l'instance de owner que l'on lui passe mais pour son scope
        //CAD par exemple pour l'activity et donc ces fragment ou pour un type de fragment
        //Ainsi meme en lui passant le fragment (this) lorsque apres rotation le fragment est detruit et reouvert avec une nouvelle instance c'est bien la meme instance de viewModel qui est recupere
        userDetailsViewModel = new ViewModelProvider(getActivity(), viewModelFactory).get(UserDetailsViewModel.class);

        userDetailsViewModel.getUserById(userId).observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                //on indique au binding (class manipulant notre layout XML) que la variable user que l'on lui a declare a pour valeur notre objet user
                binding.setUser(user);
                //une fois recupere via le onChanged du live data on passe notre user au viewModel (on le recuperera directement dans NameFragment)
                userDetailsViewModel.setUser(user);
            }
        });

        binding.name.setOnClickListener(v -> {
            //Grace au differentes class generees suite a la declaration de l'action action_userDetailsFragment_to_nameFragment dans le main_navigation.xml
            //l'action recoit en parametre le name car le fragment que l'action ouvre NameFragment attends en argument String name voir le tag <argument de Namefragment dans le main_navigation
            UserDetailsFragmentDirections.ActionUserDetailsFragmentToNameFragment action
                    = UserDetailsFragmentDirections.actionUserDetailsFragmentToNameFragment(userDetailsViewModel.getUser().getLogin());
            //on dit au navigation controller d'executer l'action
            Navigation.findNavController(binding.getRoot()).navigate(action);
        });
    }
}