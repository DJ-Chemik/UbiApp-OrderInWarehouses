package pl.chemik.ubiapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.chemik.ubiapp.R
import pl.chemik.ubiapp.database.UbiApp
import pl.chemik.ubiapp.database.entities.Item

class ItemsListActivity : AppCompatActivity(), RecycledListItemClickListener {

    var recyclerView: RecyclerView? = null;
    var items: List<Item>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_list)
        recyclerView = findViewById(R.id.recyclerItems);
        initializeView();
    }

    override fun onRestart() {
        super.onRestart();
        initializeView();
    }

    override fun onResume() {
        super.onResume();
        initializeView();
    }

    fun initializeView() {
        loadItemsFromDatabase();
        while (items == null) {
            Log.d("WAITING", "Waiting for get items")
        }
        val itemAdapter = ItemAdapter(this, items!!);
        recyclerView?.adapter = itemAdapter;
        recyclerView?.layoutManager = LinearLayoutManager(this);
        itemAdapter.setItemClickListener(this);
    }

    fun loadItemsFromDatabase() {
        GlobalScope.launch {
            val itemDao = UbiApp.database?.itemDao();
            items = itemDao?.getAll();
        }
    }

    fun onAddClick(view: View) {
        val intent = Intent(this, ItemModActivity::class.java);
        intent.putExtra("typeOperation", "add");
        intent.putExtra("id", "");
        intent.putExtra("name", "");
        intent.putExtra("description", "");
        intent.putExtra("eanUpcCode", "");
        intent.putExtra("categories", "");
        intent.putExtra("boxId", "");
        startActivity(intent);
    }

    override fun onItemClickListener(item: Item) {
        val intent = Intent(this, ItemModActivity::class.java);
        intent.putExtra("typeOperation", "edit");
        intent.putExtra("id", item.id);
        intent.putExtra("name", item.name);
        intent.putExtra("description", item.description);
        intent.putExtra("eanUpcCode", item.eanUpcCode);
        intent.putExtra("categories", item.categories);
        intent.putExtra("boxId", item.boxId);
        startActivity(intent);
    }

    fun onBackkClick(view: View) {
        val intent = Intent(this, StartActivity::class.java);
        startActivity(intent);
    }
}
