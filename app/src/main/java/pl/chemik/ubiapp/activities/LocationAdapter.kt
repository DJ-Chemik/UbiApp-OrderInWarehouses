package pl.chemik.ubiapp.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.chemik.ubiapp.R
import pl.chemik.ubiapp.database.entities.Location


interface LocationClickListener {
    fun onItemClick(view: View, position: Int);
}

interface RecycledListLocationClickListener {
    fun onItemClickListener(location: Location)
}

class LocationAdapter(context: Context, locations: List<Location>) :
    RecyclerView.Adapter<LocationAdapter.LocationViewHolder>(), LocationClickListener {

    val locations: List<Location> = locations;
    val context: Context = context;
    private var listener: RecycledListLocationClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(context);
        val view = inflater.inflate(R.layout.location_row, parent, false);
        val viewHolder = LocationViewHolder(view);
        viewHolder.setItemClickListener(this);
        return viewHolder;
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.textView1.text = locations[position].name;
    }

    override fun onItemClick(view: View, position: Int) {
        val selectedLocation = locations.get(position);
        listener?.onItemClickListener(selectedLocation);
    }

    fun setItemClickListener(listener: RecycledListLocationClickListener?) {
        this.listener = listener;
    }


    override fun getItemCount(): Int {
        return locations.size;
    }

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var textView1: TextView;
        protected var mLocationClickListener: LocationClickListener? = null;

        init {
            textView1 = itemView.findViewById(R.id.textViewLocationName);
            super.itemView.setOnClickListener(this);
        }

        fun setItemClickListener(locationClickListener: LocationClickListener) {
            mLocationClickListener = locationClickListener
        }

        override fun onClick(view: View) {
            if (mLocationClickListener != null) {
                val adapterPosition = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    mLocationClickListener!!.onItemClick(view, adapterPosition)
                }
            }

        }

    }

}