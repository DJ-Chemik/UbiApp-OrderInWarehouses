package pl.chemik.ubiapp.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.chemik.ubiapp.R
import pl.chemik.ubiapp.database.entities.Box
import java.util.*
import kotlin.collections.ArrayList

interface BoxClickListener {
    fun onItemClick(view: View, position: Int);
}

interface RecycledListBoxClickListener {
    fun onItemClickListener(box: Box)
}

class BoxAdapter(context: Context, boxes: List<Box>) :
    RecyclerView.Adapter<BoxAdapter.BoxViewHolder>(), BoxClickListener, Filterable {

    val boxes: List<Box> = boxes;
    var boxFilterList: List<Box> = boxes;
    val context: Context = context;
    private var listener: RecycledListBoxClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxAdapter.BoxViewHolder {
        val inflater = LayoutInflater.from(context);
        val view = inflater.inflate(R.layout.box_row, parent, false);
        val viewHolder = BoxAdapter.BoxViewHolder(view);
        viewHolder.setItemClickListener(this);
        return viewHolder;
    }

    fun filterByBox(locationId: Int): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                if (locationId == -1) {
                    boxFilterList = boxes
                } else {
                    val resultList = ArrayList<Box>()
                    for (row in boxes) {
                        if (row.locationId == locationId) {
                            resultList.add(row)
                        }
                    }
                    boxFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = boxFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                boxFilterList = results?.values as ArrayList<Box>
                notifyDataSetChanged()
            }
        };
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    boxFilterList = boxes
                } else {
                    val resultList = ArrayList<Box>()
                    for (row in boxes) {
                        if (row.name.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    boxFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = boxFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                boxFilterList = results?.values as ArrayList<Box>
                notifyDataSetChanged()
            }
        }
    }

    override fun onBindViewHolder(holder: BoxAdapter.BoxViewHolder, position: Int) {
        holder.textView1.text = boxFilterList[position].name;
    }

    override fun onItemClick(view: View, position: Int) {
        val selectedBox = boxFilterList.get(position);
        listener?.onItemClickListener(selectedBox);
    }

    fun setItemClickListener(listener: RecycledListBoxClickListener?) {
        this.listener = listener;
    }


    override fun getItemCount(): Int {
        return boxFilterList.size;
    }


    class BoxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var textView1: TextView;
        protected var mBoxClickListener: BoxClickListener? = null;

        init {
            textView1 = itemView.findViewById(R.id.textViewBoxName);
            super.itemView.setOnClickListener(this);
        }

        fun setItemClickListener(boxClickListener: BoxClickListener) {
            mBoxClickListener = boxClickListener
        }

        override fun onClick(view: View) {
            if (mBoxClickListener != null) {
                val adapterPosition = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    mBoxClickListener!!.onItemClick(view, adapterPosition)
                }
            }

        }

    }
}