package pnj.uts.ti.MasBrillianesaFaydhurrahman.fragment.catatan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;

import pnj.uts.ti.MasBrillianesaFaydhurrahman.DetailCatatanAct;
import pnj.uts.ti.MasBrillianesaFaydhurrahman.R;
import pnj.uts.ti.MasBrillianesaFaydhurrahman.TambahCatatanAct;
import pnj.uts.ti.MasBrillianesaFaydhurrahman.adapter.AdapterCatatan;
import pnj.uts.ti.MasBrillianesaFaydhurrahman.model.ModelCatatan;

public class CatatanFragment extends Fragment {
    ListView listView;
    AdapterCatatan adapterCatatan;
    Button actTambahCatatan;
    TextView edtJudulCatatan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_catatan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actTambahCatatan = view.findViewById(R.id.actTambahCatatan);
        edtJudulCatatan = view.findViewById(R.id.edtJudulCatatan);
        listView = view.findViewById(R.id.listView);
        adapterCatatan = new AdapterCatatan(getActivity(), R.layout.item_list_catatan);
        listView.setAdapter(adapterCatatan);

        actTambahCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TambahCatatanAct.class);
                startActivity(intent);
            }
            
            
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ModelCatatan model = (ModelCatatan) parent.getAdapter().getItem(position);
                edtJudulCatatan.setText(model.getJudul());
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("Ingin mengubah data "+model.getJudul()+" ?");
                alert.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapusData();
                    }
                });
                alert.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ModelCatatan model = (ModelCatatan) parent.getAdapter().getItem(position);
                        Intent intent =new Intent(getActivity(), DetailCatatanAct.class);
                        intent.putExtra("judul", model.getJudul());
                        startActivity(intent);
                    }
                });
                alert.show();
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
    void hapusData() {
        String jdul= (String) edtJudulCatatan.getText();
        File file = new File(Environment.getExternalStorageDirectory()+"/Catatan",jdul );
        if(file.exists()) {
            file.delete();
            Toast.makeText(getActivity(),"Hapus Data Berhasil", Toast.LENGTH_SHORT).show();
            getData();
        }else {
            Toast.makeText(getActivity(),"Hapus Tidak Berhasil :", Toast.LENGTH_SHORT).show();
        }
    }

    void getData(){
        File file;
        adapterCatatan.clear();
        ArrayList<ModelCatatan> myList = new ArrayList<>();

        String root_sd = Environment.getExternalStorageDirectory().toString();
        file = new File( root_sd +"/Catatan") ;
        File list[] = file.listFiles();

        for( int i=0; i< list.length; i++)
        {
            ModelCatatan model= new ModelCatatan();
            model.setJudul(list[i].getName());
//            Log.e("a",""+model.getJudul());
            myList.add(model);
        }

        adapterCatatan.addAll(myList);
        adapterCatatan.notifyDataSetChanged();
    }


}
