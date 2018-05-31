package com.example.eduardvelasquez.cetu;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class MakeRequest extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    ProgressBar progreso;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjRequest;
    String user;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //*****************DECLARANDO LOS INPUT
        requestQueue= Volley.newRequestQueue(getContext());

        View view =inflater.inflate(R.layout.fragment_make_request, container, false);

        final TextInputLayout tipoEventoLayout=view.findViewById(R.id.tipoevento_input_text);
        final TextInputLayout nombreEventoLayout=view.findViewById(R.id.nombreevento_input_text);
        final TextInputLayout telUsuLayout=view.findViewById(R.id.telusu_input_text);
        final TextInputLayout durEventoLayout=view.findViewById(R.id.durevento_input_text);
        final TextInputLayout fechaEventoLayout=view.findViewById(R.id.fechaevento_input_text);
        final TextInputLayout horaEventoLayout=view.findViewById(R.id.horaevento_input_text);
        final TextInputLayout cancha=view.findViewById(R.id.cancha_input_text);
        final TextInputLayout estadoReservaLayout=view.findViewById(R.id.estadoreser_input_text);
        final TextInputLayout usuarioLayout=view.findViewById(R.id.usuario_input_text);
        progreso=view.findViewById(R.id.terminateProgress);


        //*****************DECLARANDO LOS EDIT TEXT


        final TextInputEditText tipoEventoEdit=view.findViewById(R.id.tipoevento_edit_text);
        final TextInputEditText nombreEventoEdit=view.findViewById(R.id.nombreevento_edit_text);
        final TextInputEditText telUsuEdit=view.findViewById(R.id.telusu_edit_text);
        final TextInputEditText durEventoEdit=view.findViewById(R.id.durevento_edit_text);
        final TextInputEditText fechaEventoEdit=view.findViewById(R.id.fechaevento_edit_text);
        final TextInputEditText horaEventoEdit=view.findViewById(R.id.horaevento_edit_text);
        final TextInputEditText canchaEdit=view.findViewById(R.id.cancha_edit_text);
        final TextInputEditText estadoReservaEdit=view.findViewById(R.id.estadoreser_edit_text);
        final TextInputEditText usuarioEdit=view.findViewById(R.id.usuario_edit_text);


        final TextView showNameOfUser=(TextView) view.findViewById(R.id.name_user);
        showNameOfUser.setText(LoginFragment.getDefaults("sessionUsuario", getContext()));
        usuarioEdit.setText(LoginFragment.getDefaults("sessionUsuario", getContext()));
        user=LoginFragment.getDefaults("sessionUsuario", getContext());

        final Button btnGuardarSolicitud=view.findViewById(R.id.save_button);

        btnGuardarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String tevento=tipoEventoEdit.getText().toString().trim();
                    String nomEvento=nombreEventoEdit.getText().toString().trim();
                    String telUsu=telUsuEdit.getText().toString().trim();
                    String durEvento=durEventoEdit.getText().toString().trim();
                    String fechaEvento=fechaEventoEdit.getText().toString().trim();
                    String horaEvento=horaEventoEdit.getText().toString().trim();
                    String cancha=canchaEdit.getText().toString().trim();
                    String estado=estadoReservaEdit.getText().toString().trim();

                    cargarWebService(tevento, nomEvento, telUsu, durEvento, fechaEvento, horaEvento, cancha, estado,user);
                }catch (Exception jsError){
                    Log.i("Trying insert",jsError.getMessage());
                }

            }
        });



        return view;
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(), "Se ha almacenado correctamente", Toast.LENGTH_LONG).show();
        progreso.setVisibility(View.GONE);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.setVisibility(View.GONE);
        Toast.makeText(getContext(), "No se ha almacenado"+error.toString(), Toast.LENGTH_LONG).show();



        error.printStackTrace();
    }

    private void cargarWebService(String tEvento, String nEvento, String telU, String durEvento, String fechaEvento, String horaEvento, String cancha, String estado, String user){
        progreso.setVisibility(View.VISIBLE);

        String url="http://ctrlcanchas.000webhostapp.com/webservice/ws_addevent.php?Mtevento="+
                tEvento+"&Mnombre="+nEvento+"&Mtel="+telU+"&Mdur="+durEvento+"&Mfecha="+fechaEvento
                +"&Mhora="+horaEvento+"&Mcancha="+cancha+"&Mestado="+estado+"&Mipor="+user;

        url=url.replace(" ","%20");

        Log.i("ANTES DE CREAROBJETO", "ERROR1");

        jsonObjRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);

        Log.i("DESPUES DE CREAROBJETO", "ERROR2");

        requestQueue.add(jsonObjRequest);

        Log.i("ADDING EL OBJ AL ARRAY", "errror3");
    }


}
