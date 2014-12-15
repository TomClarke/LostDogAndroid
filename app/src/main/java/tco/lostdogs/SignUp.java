package tco.lostdogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up, menu);
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
    public void create(View view){


        EditText usernameView = (EditText) findViewById(R.id.username);
        EditText emailView = (EditText) findViewById(R.id.email);
        EditText password1View = (EditText) findViewById(R.id.password1);
        EditText password2View = (EditText) findViewById(R.id.password2);
        boolean validationError = false;


        StringBuilder validationErrorMessage = new StringBuilder("Error:  ");

        if (isEmpty(usernameView)) {
            validationError = true;
            validationErrorMessage.append("Please Enter A Username");
        }

        if (isEmpty(password1View)) {
            if (validationError) {
                validationErrorMessage.append("Please Enter a Password");
            }
            validationError = true;
            validationErrorMessage.append("Please Enter a Password");
        }
        if (!isMatching(password1View, password2View)) {
            if (validationError) {
                validationErrorMessage.append("Passwords do not match");
            }
            validationError = true;
            validationErrorMessage.append("Passwords do not match");
        }


        if (validationError) {
            Toast.makeText(SignUp.this,
                    validationErrorMessage.toString(),
                    Toast.LENGTH_LONG).show();

            return;
        }

        // Set up a new Parse user
        ParseUser user = new ParseUser();
        user.setUsername(usernameView.getText().toString());
        user.setPassword(password1View.getText().toString());
        user.setEmail(emailView.getText().toString());
        // Call the sign up method

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                System.out.println("HERE");
                next(e);
            }

        });

    }

    public void next(ParseException e) {
        // Dismiss the dialog
        StringBuilder validationErrorMessage = new StringBuilder("Attempting to send... ");
        if (e != null) {
            // Show the error message
            validationErrorMessage.toString();
            Toast.makeText(SignUp.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } else {

            validationErrorMessage.append(" Sent!");
            validationErrorMessage.toString();
            // Start an intent for the dispatch activity
            Intent intent = new Intent(SignUp.this, MainMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
		/*
			      user.signUpInBackground(new SignUpCallback() {
			        @Override
			        public void done(ParseException e) {
			        	Dialog diolog = null;
			    		diolog.dismiss();
			    	  if (e != null) {
			    		  System.out.println("WE GOT HERE ");
			    	    // Show the error message
			    	    Toast.makeText(SignUp.this, e.getMessage(),
			    	      Toast.LENGTH_LONG).show();
			    	  } else {
			    		  System.out.println("OR GOT HERE");
			    	    // Start an intent for the dispatch activity
			    	    Intent intent = new Intent(SignUp.this, MenuActivity.class);
			    	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
			    	      Intent.FLAG_ACTIVITY_NEW_TASK);
			    	    startActivity(intent);
			    		//Intent menu = new Intent(this, MenuActivity.class);
			    		//startActivity(menu);
			    	  }
			        }
			      });
	}

	*/




    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isMatching(EditText etText1, EditText etText2) {
        if (etText1.getText().toString().equals(etText2.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
