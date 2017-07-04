package app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.login2.R;

public class Register extends Activity{

	ProgressDialog pDialog;
	
	public void onCreate(Bundle savedInstanceState) {

		
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.signup);

		final EditText  name=(EditText)findViewById(R.id.editText1);
		final EditText  tp=(EditText)findViewById(R.id.editText3);
		final EditText  address=(EditText)findViewById(R.id.editText4);
		final EditText  uname=(EditText)findViewById(R.id.editText5);
		final EditText  pass=(EditText)findViewById(R.id.editText2);
		final EditText  cpass=(EditText)findViewById(R.id.editText6);
		
		 
		Button reg=(Button)findViewById(R.id.button1);
		
		 // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
		
		reg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!pass.getText().toString().equals(cpass.getText().toString())){
					cpass.setText("");
					Toast.makeText(Register.this, "password confirmation failed", Toast.LENGTH_SHORT).show();
				}else{
					
					register(name.getText().toString(),tp.getText().toString(),address.getText().toString(),uname.getText().toString(),pass.getText().toString());
										
				}
				
			}
		});
	}
	 private void register(final String name,final String tp,final String address,final String username, final String password) {
	        // Tag used to cancel the request
	        String tag_string_req = "req_login";
	 
	        pDialog.setMessage("Registering in ...");
	        showDialog();
	 
	         StringRequest strReq = new StringRequest(Method.POST,
	                AppConfig.URL_REGISTER, new Response.Listener<String>() {
	 
	                    @Override
	                    public void onResponse(String response) {
	                       
	                        hideDialog();
	                        
	                        Toast.makeText(Register.this,response , Toast.LENGTH_SHORT).show();
	                      
	                        try {
	                            JSONObject jObj = new JSONObject(response);
	                            boolean error = jObj.getBoolean("error");
	 
	                            // Check for error node in json
	                            if (!error) {
	                                // user successfully signed in
	                            	Toast.makeText(getApplicationContext(),
	                                        "Registration Successful", Toast.LENGTH_LONG).show();
	                            	onBackPressed();
	                            } else {
	                                // Error in login. Get the error message
	                                String errorMsg = jObj.getString("error_msg");
	                                Toast.makeText(getApplicationContext(),
	                                        "Registration Failed", Toast.LENGTH_LONG).show();
	                            }
	                        } catch (JSONException e) {
	                            // JSON error
	                            e.printStackTrace();
	                        }
	                        
	 
	                    }
	                }, new Response.ErrorListener() {
	 
	                    @Override
	                    public void onErrorResponse(VolleyError error) {
	                       // Log.e(TAG, "Login Error: " + error.getMessage());
	                        Toast.makeText(getApplicationContext(),
	                                "ERROR OCCURED", Toast.LENGTH_SHORT).show();
	                        hideDialog();
	                    }
	                }) {
	 
	            @Override
	            protected Map<String, String> getParams() {
	                // Posting parameters to login url
	                Map<String, String> params = new HashMap<String, String>();
	                params.put("tag", "register");
	                params.put("username", username);
	                params.put("password", password);
	                params.put("name", name);
	                params.put("tp", tp);
	                params.put("address", address);
	                
	 
	                return params;
	            }
	 
	        };
	 
	        // Adding request to request queue
	        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
	    }
	 private void showDialog() {
	        if (!pDialog.isShowing())
	            pDialog.show();
	    }
	 
	    private void hideDialog() {
	        if (pDialog.isShowing())
	            pDialog.dismiss();
	    }
}
