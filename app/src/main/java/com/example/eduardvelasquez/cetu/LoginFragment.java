package com.example.eduardvelasquez.cetu;


import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import entidades.Usuarios;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    ProgressDialog progreso;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjRequest;
    TextView showUser;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        showUser=view.findViewById(R.id.show_user);

        //Inicializacion de las variables
        requestQueue= Volley.newRequestQueue(getContext());

        final TextInputLayout passwordTextInput=view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText=view.findViewById(R.id.password_edit_text);

        final TextInputLayout usernameTextInput=view.findViewById(R.id.username_text_input);
        final TextInputEditText usernameEditText=view.findViewById(R.id.username_edit_text);


        MaterialButton loginButton=view.findViewById(R.id.login_button);

        //Enviar un error si el password es menor que 8 caracteres

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass=passwordEditText.getText().toString().trim();
                String user=usernameEditText.getText().toString().trim();


                if(TextUtils.isEmpty(user)){
                    usernameTextInput.setError(getString(R.string.login_error_fields));
                    usernameEditText.requestFocus();
                }
                else if (TextUtils.isEmpty(pass)){
                    passwordTextInput.setError(getString(R.string.login_error_fields));
                    passwordEditText.requestFocus();
                }else {
                    passwordTextInput.setError(null);
                    cargarWebService(user,pass);

                }
            }
        });

        usernameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String userN=usernameEditText.getText().toString().trim();

                if(isAccessValid(usernameEditText.getText())){
                    usernameTextInput.setError(null);
                }

                return false;
            }
        });

        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String passU=passwordEditText.getText().toString().trim();

                if(isAccessValid(usernameEditText.getText())){
                    passwordTextInput.setError(null);
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();



        Usuarios usuario=new Usuarios();

        JSONArray jsonArray=response.optJSONArray("usuario");

        JSONObject jsonObject=null;
        try {
            jsonObject=jsonArray.getJSONObject(0);
            usuario.setNombre(jsonObject.optString("nombre"));
            usuario.setPassword(jsonObject.getString("password"));
        }catch (Exception e){
            e.printStackTrace();
        }

        //showUser.setText(usuario.getNombre()+usuario.getPassword());
        showUser.setText(usuario.getNombre() +" Password: "+ usuario.getPassword());


        setDefaults("sessionUsuario",usuario.getNombre(),getContext());

        ((NavigationHost) getActivity()).navigateTo(new MakeRequest(), false); // Navigate to the next Fragment

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"Usuario o contraseña invalido, vuelve a intentar",Toast.LENGTH_LONG).show();
        Log.i("ERROR", error.toString());
    }

    private void leerDatos(){

    }

    private boolean isAccessValid(@Nullable Editable text){
        return text != null;
    }

    private void cargarWebService(String user, String pass){
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("consultando...");
        progreso.show();


        String url="http://ctrlcanchas.000webhostapp.com/webservice/ws_loginapp.php?Lusername="+user+"&Lpassword="+pass+"";

        url=url.replace(" ", "%20");

        jsonObjRequest=new JsonObjectRequest(Request.Method.GET,url,null, this,this);
        requestQueue.add(jsonObjRequest);
    }


    public static void setDefaults(String key, String value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
}
