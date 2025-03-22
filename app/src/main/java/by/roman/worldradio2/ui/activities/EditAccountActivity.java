package by.roman.worldradio2.ui.activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import by.roman.worldradio2.R;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.UserRepository;

public class EditAccountActivity extends AppCompatActivity {
    private ImageView back;
    private Button delete;
    private EditText loginPlace;
    private EditText passwordPlace;
    private UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            findAllId();
            getData();

            delete.setOnClickListener(v1 -> {
                userRepository.deleteUser(userRepository.getUserIdInSystem());
                //TODO: reboot
            });
            back.setOnClickListener(v1 -> {
                finish();
            });
            return insets;
        });
    }
    private void findAllId(){
        delete.findViewById(R.id.delEditButton);
        back.findViewById(R.id.backButtonEditView);
        loginPlace.findViewById(R.id.loginEditView);
        passwordPlace.findViewById(R.id.passwordEditView);
    }
    private void getData(){
        DatabaseHelper dHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dHelper.getWritableDatabase();
        userRepository = new UserRepository(db);
        loginPlace.setText(userRepository.getUserLogin(userRepository.getUserIdInSystem()));
        passwordPlace.setText(userRepository.getUserPassword(userRepository.getUserIdInSystem()));
    }
}