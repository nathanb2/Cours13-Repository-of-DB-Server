package com.example.ex10_listfromdb.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex10_listfromdb.model.DataResponse;
import com.example.ex10_listfromdb.model.User;
import com.example.ex10_listfromdb.adapter.UserAdapter;
import com.example.ex10_listfromdb.view_model.UsersViewModel;
import com.example.ex10_listfromdb.databinding.FragmentUsersBinding;
import com.example.ex10_listfromdb.injection.Injection;
import com.example.ex10_listfromdb.injection.ViewModelFactory;

import java.util.List;

public class UsersFragment extends Fragment {


    public static final String TAG = UsersFragment.class.getSimpleName();
    private UserAdapter mUserAdapter;
    private FragmentUsersBinding binding;
    private UsersViewModel usersViewModel;

    public static UsersFragment newInstance() {
        UsersFragment usersFragment = new UsersFragment();
        return usersFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        usersViewModel =  new ViewModelProvider(this, viewModelFactory).get(UsersViewModel.class);

        usersViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<DataResponse<List<User>>>() {
            @Override
            public void onChanged(DataResponse<List<User>> response) {
                Toast.makeText(getActivity(), response.getRequestStatus().name(), Toast.LENGTH_SHORT).show();
                if (mUserAdapter == null){
                    mUserAdapter = initAdapter(response.getBody());
                    //on fait scroll automatiqueemnt notre recyclerView jusqu'a la position visible avant la rotation
                    binding.recyclerView.scrollToPosition(usersViewModel.getRecyclerViewVisiblePosition());
                }else {
                    //on actualise notre liste (juste le contenu de la liste)
                    mUserAdapter.setData(response.getBody());
                    //et disons a l'adapter de se rafraichir pour afficher d'eventuels changements
                    mUserAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        //avant la destruction de la vue on garde la position de la liste visible dans notre viewModel
        usersViewModel.setRecyclerViewVisiblePosition(((LinearLayoutManager)binding.recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
        mUserAdapter = null;
        super.onDestroyView();
    }

    private UserAdapter initAdapter(List<User> users) {
        UserAdapter adapter = new UserAdapter(users);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
        return adapter;
    }
}
