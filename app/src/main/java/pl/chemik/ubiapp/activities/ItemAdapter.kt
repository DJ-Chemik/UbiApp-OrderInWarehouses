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
import pl.chemik.ubiapp.database.entities.Item
import java.util.*
import kotlin.collections.ArrayList

interface ItemClickListener {
    fun onItemClick(view: View, position: Int);
}

interface RecycledListItemClickListener {
    fun onItemClickListener(item: Item)
}

class ItemAdapter(context: Context, items: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(), ItemClickListener, Filterable {

    val items: List<Item> = items;
    var itemFilterList: List<Item> = items;
    val context: Context = context;
    private var listener: RecycledListItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemViewHolder {
        val inflater = LayoutInflater.from(context);
        val view = inflater.inflate(R.layout.item_row, parent, false);
        val viewHolder = ItemAdapter.ItemViewHolder(view);
        viewHolder.setItemClickListener(this);
        return viewHolder;
    }

    fun filterByBox(boxId: Int): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                if (boxId == -1) {
                    itemFilterList = items
                } else {
                    val resultList = ArrayList<Item>()
                    for (row in items) {
                        if (row.boxId == boxId) {
                            resultList.add(row)
                        }
                    }
                    itemFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemFilterList = results?.values as ArrayList<Item>
                notifyDataSetChanged()
            }
        };
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemFilterList = items
                } else {
                    val resultList = ArrayList<Item>()
                    for (row in items) {
                        if (row.name.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    itemFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemFilterList = results?.values as ArrayList<Item>
                notifyDataSetChanged()
            }
        }
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        holder.textView1.text = itemFilterList[position].name;
    }

    override fun onItemClick(view: View, position: Int) {
        val selectedItem = itemFilterList.get(position);
        listener?.onItemClickListener(selectedItem);
    }

    fun setItemClickListener(listener: RecycledListItemClickListener?) {
        this.listener = listener;
    }


    override fun getItemCount(): Int {
        return itemFilterList.size;
    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var textView1: TextView;
        protected var mItemClickListener: ItemClickListener? = null;

        init {
            textView1 = itemView.findViewById(R.id.textViewItemName);
            super.itemView.setOnClickListener(this);
        }

        fun setItemClickListener(itemClickListener: ItemClickListener) {
            mItemClickListener = itemClickListener
        }

        override fun onClick(view: View) {
            if (mItemClickListener != null) {
                val adapterPosition = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    mItemClickListener!!.onItemClick(view, adapterPosition)
                }
            }

        }

    }
}