package pnj.uts.ti.MasBrillianesaFaydhurrahman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import pnj.uts.ti.MasBrillianesaFaydhurrahman.R;
import pnj.uts.ti.MasBrillianesaFaydhurrahman.model.ModelBerita;
import pnj.uts.ti.MasBrillianesaFaydhurrahman.model.ModelCatatan;

public class AdapterCatatan extends ArrayAdapter<ModelCatatan> {
    int resource;
    Context context;
    public AdapterCatatan(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder = null;

        if(convertView==null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(resource,parent,false);
            holder.txtJudulCat = convertView.findViewById(R.id.txtJudulCat);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }

        ModelCatatan model = getItem(position);
        holder.txtJudulCat.setText(model.getJudul());

        return convertView;
    }

    class Holder {

        TextView txtJudulCat;
    }


}
