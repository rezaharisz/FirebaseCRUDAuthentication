package com.alfikri.authenticationfirebase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {


    private List<Mahasiswa> mList = new ArrayList<>();
    private Activity activity;

    String url = "https://authenticationfirebase-33434-default-rtdb.firebaseio.com";
    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl(url);


    public RecyclerAdapter(List<Mahasiswa> mList, Activity activity) {
        this.mList = mList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        final Mahasiswa msiswa = mList.get(position);
        holder.tv_nama.setText("Nama = "+msiswa.getNama());
        holder.tv_fakultas.setText("Fakultas = "+msiswa.getFakultas());
        holder.tv_jurusan.setText("Jurusan = "+msiswa.getJurusan());
        holder.tv_semester.setText("Semester = "+msiswa.getSemester());

        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.child("data").child(msiswa.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity,"Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity,"Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                }).setMessage("Apakah Anda Yakin Ingin Menghapus "+ msiswa.getNama() + " ?");
                builder.show();
            }
        });

        holder.updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogForm dialogForm = new DialogForm(msiswa.getNama(), msiswa.getFakultas(), msiswa.getJurusan(), msiswa.getSemester(), msiswa.getKey(), "Ubah");
                dialogForm.show(manager, "form");

            }
        });




    }


    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama;
        TextView tv_fakultas;
        TextView tv_jurusan;
        TextView tv_semester;
        ImageView hapus;
        ImageView updatedata;

        CardView card_view;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_fakultas = itemView.findViewById(R.id.tv_fakultas);
            tv_jurusan = itemView.findViewById(R.id.tv_jurusan);
            tv_semester = itemView.findViewById(R.id.tv_semester);
            card_view = itemView.findViewById(R.id.card_view);
            hapus = itemView.findViewById(R.id.hapus);
            updatedata = itemView.findViewById(R.id.updatedata);
        }
    }
}
