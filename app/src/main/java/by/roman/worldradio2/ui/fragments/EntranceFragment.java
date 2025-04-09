package by.roman.worldradio2.ui.fragments;

import static android.view.View.VISIBLE;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import by.roman.worldradio2.R;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.UserRepository;


public class EntranceFragment extends Fragment {
    private ImageView enter;
    private EditText loginText;
    private EditText passwordText;
    private TextView error;
    private String login;
    private String password;
    private TextView forgot;
    private UserRepository userRepository;
    private ImageView passStatus;
    private boolean status = false;
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
                error.setVisibility(VISIBLE);
                forgot.setVisibility(VISIBLE);
                loginText.setText("");
                passwordText.setText("");
                error.setTextColor(redColor);
            }
        });
        passStatus.setOnClickListener(v -> {
            if(status){
                passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                passStatus.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.eye));
            } else {
                passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                passStatus.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.eye_closed));
            }
            status = !status;
        });
        forgot.setOnClickListener(v -> {

        });
        return view;
    }
    private void findAll(View view){
        enter = view.findViewById(R.id.enterButton);
        loginText = view.findViewById(R.id.loginInput_Entrance);
        passwordText = view.findViewById(R.id.passwordInput_Entrance);
        error = view.findViewById(R.id.errorText_Entrance);
        forgot = view.findViewById(R.id.forgotPass_Entrance);
        passStatus = view.findViewById(R.id.passStatus_Entrance);
    }
}