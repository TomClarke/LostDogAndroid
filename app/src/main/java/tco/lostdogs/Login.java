package tco.lostdogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;


public class Login extends Activity {


    private Dialog progressDialog;
    private Button loginButton;
    static final String TAG = "LostDogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Parse.initialize(this, "NtFAPxv8LmzhKUQ16CgYi62twBNHypYLFfAcLQKc", "U7qhHqNJtJ8jAINa66ldiFCLAyk8BKQIQkwgTgij");

        ParseFacebookUtils.initialize(getString(R.string.app_id));

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);




        loginButton = (Button) findViewById(R.id.authButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authParse();
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((ParseUser.getCurrentUser() != null) && ParseFacebookUtils.isLinked(currentUser)){
            // Start an intent for the logged in activity
            startActivity(new Intent(this, MainMenu.class));
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }


    public void forgotPassword(View view){

        TextView emailReset = (TextView) findViewById(R.id.txtEmailReset);
        emailReset.setVisibility(View.VISIBLE);

        EditText emailInput = (EditText) findViewById(R.id.txtInputEmailReset);
        emailInput.setVisibility(View.VISIBLE);

    }


    public void authParse() {

        Login.this.progressDialog = ProgressDialog.show(
                Login.this, "", "Logging in...", true);
        List<String> permissions = Arrays.asList("public_profile");
        ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                Login.this.progressDialog.dismiss();
                if (user == null) {
                    Log.d(TAG,
                            "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d(TAG,
                            "User signed up and logged in through Facebook!");
                    MainMenuActivity();
                } else {
                    Log.d(TAG,
                            "User logged in through Facebook!");
                    MainMenuActivity();
                }
            }
        });
    }

    private void MainMenuActivity() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }


    public void loginCheck(View view){


        EditText username = (EditText) findViewById(R.id.txtInputUsername);
        EditText password = (EditText) findViewById(R.id.inputPassword);




        if((username.getText().equals("")) || (password.getText().equals(""))){
            StringBuilder validationErrorMessage = new StringBuilder("Please enter both a password and a username");

            Toast.makeText(Login.this,
                    validationErrorMessage.toString(),
                    Toast.LENGTH_LONG).show();
            Intent menu = new Intent();
            startActivity(menu);

        }


        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(),new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException e) {
                // Handle the response
                if (e != null) {
                    // Show the error message
                    Toast.makeText(Login.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();

                } else {
                    // Start an intent for the dispatch activity
                    Intent intent = new Intent(Login.this, MainMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                }
            }

        });
    }

    public void signUp(View view){

        Intent signUp = new Intent(this, SignUp.class);
        startActivity(signUp);
    }




}
