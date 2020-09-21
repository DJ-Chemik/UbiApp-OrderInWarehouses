package pl.chemik.ubiapp.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.chemik.ubiapp.R

class LocationAdapter(ct: Context, s1: Array<String>, s2: Array<String>) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    val data1: Array<String> = s1;
    val data2: Array<String> = s2;
    val context: Context = ct;

    init {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(context);
        val view = inflater.inflate(R.layout.location_row, parent, false);
        return LocationViewHolder(view);
    }

        override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.textView1.text = data1[position];
        holder.textView2.text = data2[position];
    }

    override fun getItemCount(): Int {
        return data1.size;
    }

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textView1: TextView;
        var textView2: TextView;

        init {
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);

        }

    }

}