package pl.chemik.ubiapp.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.chemik.ubiapp.R
import pl.chemik.ubiapp.database.entities.Item

interface ItemClickListener {
    fun onItemClick(view: View, position: Int);
}

interface RecycledListItemClickListener {
    fun onItemClickListener(item: Item)
}

class ItemAdapter(context: Context, items: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(), ItemClickListener {

    val items: List<Item> = items;
    val context: Context = context;
    private var listener: RecycledListItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemViewHolder {
        val inflater = LayoutInflater.from(context);
        val view = inflater.inflate(R.layout.item_row, parent, false);
        val viewHolder = ItemAdapter.ItemViewHolder(view);
        viewHolder.setItemClickListener(this);
        return viewHolder;
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        holder.textView1.text = items[position].name;
    }

    override fun onItemClick(view: View, position: Int) {
        val selectedItem = items.get(position);
        listener?.onItemClickListener(selectedItem);
    }

    fun setItemClickListener(listener: RecycledListItemClickListener?) {
        this.listener = listener;
    }


    override fun getItemCount(): Int {
        return items.size;
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