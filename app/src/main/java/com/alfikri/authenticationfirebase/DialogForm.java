package com.alfikri.authenticationfirebase;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogForm extends DialogFragment {
    String nama,fakultas,jurusan,semester, key, pilih;

    String url = "https://authenticationfirebase-33434-default-rtdb.firebaseio.com";
    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl(url);

    public DialogForm(String nama, String fakultas, String jurusan, String semester, String key, String pilih) {
        this.nama = nama;
        this.fakultas = fakultas;
        this.jurusan = jurusan;
        this.semester = semester;
        this.key = key;
        this.pilih = pilih;
    }

    TextView et_nama;
    TextView et_fakultas;
    TextView et_jurusan;
    TextView et_semester;

    Button btn_simpan;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.input_form,container,false);
        et_nama = view.findViewById(R.id.et_nama);
        et_fakultas = view.findViewById(R.id.et_fakultas);
        et_jurusan = view.findViewById(R.id.et_jurusan);
        et_semester = view.findViewById(R.id.et_semester);
        btn_simpan= view.findViewById(R.id.btn_simpan);

        et_nama.setText(nama);
        et_fakultas.setText(fakultas);
        et_jurusan.setText(jurusan);
        et_semester.setText(semester);


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = et_nama.getText().toString();
                String fakultas = et_fakultas.getText().toString();
                String jurusan = et_jurusan.getText().toString();
                String semester = et_semester.getText().toString();

                if(TextUtils.isEmpty(nama)){
                    input((EditText) et_nama, "nama");
                }
                else if(TextUtils.isEmpty(fakultas)) {
                    input((EditText) et_fakultas, "fakultas");
                }
                else if(TextUtils.isEmpty(jurusan)) {
                    input((EditText) et_jurusan, "jurusan");
                }
                else if(TextUtils.isEmpty(semester)) {
                    input((EditText) et_semester, "semester");
                }
                else {
                    if(pilih.equals("Tambah")){
                        database.child("data").push().setValue(new Mahasiswa(nama,fakultas,jurusan,semester)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(view.getContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
                            }
                            }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Data Gagal Tersimpan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if(pilih.equals("Ubah")){
                        database.child("data").child(key).setValue(new Mahasiswa(nama,fakultas,jurusan,semester)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(view.getContext(), "Data Berhasil di Ubah", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Data Gagal di Ubah", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }

            }
        });
        return view;
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog!= null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void input(EditText txt, String s){
    txt.setError(s+" Tidak Boleh Kosong !");
    txt.requestFocus();
}
}

