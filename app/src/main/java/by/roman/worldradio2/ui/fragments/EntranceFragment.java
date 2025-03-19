package by.roman.worldradio2.ui.fragments;

import static android.view.View.VISIBLE;

import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import by.roman.worldradio2.R;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.UserRepository;


public class EntranceFragment extends Fragment {
    private Button enter;
    private Button create;
    private EditText loginText;
    private EditText passwordText;
    private TextInputLayout loginHolder;
    private TextInputLayout passwordHolder;
    private TextView error;
    private String login;
    private String password;
    private TextView forgot;
    private UserRepository userRepository;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrance, container, false);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        userRepository = new UserRepository(db);
        findAll(view);
        enter.setOnClickListener(v -> {
            login = loginText.getText().toString();
            password = passwordText.getText().toString();
            if(userRepository.entranceUser(login,password)){
                requireActivity().finish();
            } else {
                int redColor = ContextCompat.getColor(requireContext(), R.color.red);
                ColorStateList errorColorStateList = new ColorStateList(
                        new int[][] {new int[] { android.R.attr.state_enabled }, new int[] { -android.R.attr.state_enabled }},
                        new int[] {redColor, redColor}
                );
                error.setVisibility(VISIBLE);
                forgot.setVisibility(VISIBLE);
                loginText.setText("");
                passwordText.setText("");
                loginHolder.setBoxStrokeColorStateList(errorColorStateList);
                passwordHolder.setBoxStrokeColorStateList(errorColorStateList);
                error.setTextColor(redColor);
            }
        });
        create.setOnClickListener(v -> {
            change(new RegistrationFragment());
        });
        forgot.setOnClickListener(v -> {

        });
        return view;
    }
    private void findAll(View view){
        enter = view.findViewById(R.id.enterButton_Entrance);
        create = view.findViewById(R.id.createButton_Entrance);
        loginText = view.findViewById(R.id.loginInput_Entrance);
        passwordText = view.findViewById(R.id.passwordInput_Entrance);
        error = view.findViewById(R.id.errorText_Entrance);
        loginHolder = view.findViewById(R.id.loginHolderView_Entrance);
        passwordHolder = view.findViewById(R.id.passwordHolderView_Entrance);
        forgot = view.findViewById(R.id.forgotPass_Entrance);
    }
    private void change(Fragment f){
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        ft.replace(R.id.accountEntranceView,f);
        ft.commit();
    }
}