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
import by.roman.worldradio2.data.dto.UserDTO;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.UserRepository;


public class FragmentRegistration extends Fragment {

    private Button enter;
    private Button create;
    private EditText loginText;
    private EditText passwordText;
    private TextView error;
    private TextInputLayout loginHolder;
    private TextInputLayout passwordHolder;
    private String login = null;
    private String password = null;
    private UserRepository userRepository;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        userRepository = new UserRepository(db);
        findAll(view);
        enter.setOnClickListener(v -> {
            change(new FragmentEntrance());
        });
        create.setOnClickListener(v -> {
            login = loginText.getText().toString();
            password = passwordText.getText().toString();
            if(!login.isEmpty() && !password.isEmpty()){
                if(!userRepository.checkUserData(login)){
                    UserDTO dto = new UserDTO(2,login,password,1);
                    userRepository.addUser(dto);
                    requireActivity().finish();
                } else {
                    error.setText("same");
                    err();
                }
            } else {
                error.setText("null");
                err();
            }
        });
        return view;
    }
    private void change(Fragment f){
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_fade_in,R.anim.fragment_fade_out);
        ft.replace(R.id.accountEntranceView,f);
        ft.commit();
    }
    private void err(){
        int redColor = ContextCompat.getColor(requireContext(), R.color.red);
        ColorStateList errorColorStateList = new ColorStateList(
                new int[][] {new int[] { android.R.attr.state_enabled }, new int[] { -android.R.attr.state_enabled }},
                new int[] {redColor, redColor}
        );
        error.setVisibility(VISIBLE);
        loginText.setText("");
        passwordText.setText("");
        loginHolder.setBoxStrokeColorStateList(errorColorStateList);
        passwordHolder.setBoxStrokeColorStateList(errorColorStateList);
        error.setTextColor(redColor);
    }
    private void findAll(View view){
        enter = view.findViewById(R.id.enterButton_Registration);
        create = view.findViewById(R.id.createButton_Registration);
        loginText = view.findViewById(R.id.loginInput_Registration);
        passwordText = view.findViewById(R.id.passwordInput_Registration);
        loginHolder = view.findViewById(R.id.loginHolderView_Registration);
        passwordHolder = view.findViewById(R.id.passwordHolderView_Registration);
        error = view.findViewById(R.id.errorText_Registration);
    }
}