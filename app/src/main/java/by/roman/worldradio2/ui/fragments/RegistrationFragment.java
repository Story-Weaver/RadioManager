package by.roman.worldradio2.ui.fragments;

import static android.view.View.VISIBLE;

import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import by.roman.worldradio2.R;
import by.roman.worldradio2.data.dto.FilterDTO;
import by.roman.worldradio2.data.dto.SettingsDTO;
import by.roman.worldradio2.data.dto.UserDTO;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.FilterRepository;
import by.roman.worldradio2.data.repository.SettingsRepository;
import by.roman.worldradio2.data.repository.UserRepository;


public class RegistrationFragment extends Fragment {

    private ImageView create;
    private EditText loginText;
    private EditText passwordText;
    private EditText passwordRepText;
    private String login = null;
    private String password = null;
    private UserRepository userRepository;

    private ImageView passStatus1;
    private ImageView passStatus2;
    private boolean status1 = false;
    private boolean status2 = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        userRepository = new UserRepository(db);
        findAll(view);
        create.setOnClickListener(v -> {
            login = loginText.getText().toString();
            password = passwordText.getText().toString();
            if(!login.isEmpty() && !password.isEmpty()){
                if(!userRepository.checkUserData(login)){
                    UserDTO dto = new UserDTO(login,password,1);
                    userRepository.addUser(dto);
                    FilterDTO dto2 = new FilterDTO(userRepository.getUserIdInSystem(),null,null,null,0);
                    SettingsDTO dto3 = new SettingsDTO(userRepository.getUserIdInSystem(),0,0,1,0,0);
                    FilterRepository filterRepository = new FilterRepository(db);
                    filterRepository.addFilter(dto2);
                    SettingsRepository settingsRepository = new SettingsRepository(db);
                    settingsRepository.addSettings(dto3);
                    requireActivity().finish();
                } else {
                    //error.setText("Уже существует");
                }
            } else {
                //error.setText("Пустое поле");
            }
        });
        passStatus1.setOnClickListener(v -> {
            if(status1){
                passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                passStatus1.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.eye));
            } else {
                passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                passStatus1.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.eye_closed));
            }
            status1 = !status1;
        });
        passStatus2.setOnClickListener(v -> {
            if(status2){
                passwordRepText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                passStatus2.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.eye));
            } else {
                passwordRepText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                passStatus2.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.eye_closed));
            }
            status2 = !status2;
        });
        return view;
    }
    private void findAll(View view){
        create = view.findViewById(R.id.regButton);
        loginText = view.findViewById(R.id.loginInput_Registration);
        passwordText = view.findViewById(R.id.password1Input_Registration);
        passwordRepText = view.findViewById(R.id.password2Input_Registration);
        passStatus1 = view.findViewById(R.id.pass1Status_Registration);
        passStatus2 = view.findViewById(R.id.pass2Status_Registration);
    }
}