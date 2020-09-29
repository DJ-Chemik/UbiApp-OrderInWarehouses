package pl.chemik.ubiapp.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.chemik.ubiapp.R
import pl.chemik.ubiapp.database.entities.Box

interface BoxClickListener {
    fun onItemClick(view: View, position: Int);
}

interface RecycledListBoxClickListener {
    fun onItemClickListener(box: Box)
}

class BoxAdapter(context: Context, boxes: List<Box>) :
    RecyclerView.Adapter<BoxAdapter.BoxViewHolder>(), BoxClickListener {

    val boxes: List<Box> = boxes;
    val context: Context = context;
    private var listener: RecycledListBoxClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxAdapter.BoxViewHolder {
        val inflater = LayoutInflater.from(context);
        val view = inflater.inflate(R.layout.box_row, parent, false);
        val viewHolder = BoxAdapter.BoxViewHolder(view);
        viewHolder.setItemClickListener(this);
        return viewHolder;
    }

    override fun onBindViewHolder(holder: BoxAdapter.BoxViewHolder, position: Int) {
        holder.textView1.text = boxes[position].name;
    }

    override fun onItemClick(view: View, position: Int) {
        val selectedBox = boxes.get(position);
        listener?.onItemClickListener(selectedBox);
    }

    fun setItemClickListener(listener: RecycledListBoxClickListener?) {
        this.listener = listener;
    }


    override fun getItemCount(): Int {
        return boxes.size;
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