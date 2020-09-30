package pl.chemik.ubiapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.chemik.ubiapp.R
import pl.chemik.ubiapp.database.UbiApp
import pl.chemik.ubiapp.database.entities.Box
import pl.chemik.ubiapp.database.entities.Item

class ItemsListActivity : AppCompatActivity(), RecycledListItemClickListener {

    var recyclerView: RecyclerView? = null;
    var itemAdapter: ItemAdapter? = null;
    lateinit var spinnerBoxes: Spinner;
    var allItems: List<Item>? = null;
    var items: MutableList<Item>? = null;
    var boxes: List<Box>? = null;
    val boxNames: MutableList<String> = mutableListOf("Wszystkie pudła");
    val boxIds: MutableList<Int> = mutableListOf(-1);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_list)
//        initializeView();
        recyclerView = findViewById(R.id.recyclerItems);
        spinnerBoxes = findViewById(R.id.spinnerItemsListBoxes);
        setListeners();
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
        findViewById<EditText>(R.id.etSearchItems).setText("")
        loadItemsFromDatabase();
        loadBoxesFromDatabase();
        while (items == null) {
            Log.d("WAITING", "Waiting for get items")
        }
        itemAdapter = ItemAdapter(this, items!!);
        recyclerView?.adapter = itemAdapter;
        recyclerView?.layoutManager = LinearLayoutManager(this);
        itemAdapter!!.setItemClickListener(this);
        while (boxes == null) {
            Log.d("WAITING", "Waiting for get boxes")
        }
        for (box in boxes!!) {
            if (boxIds.contains(box.id)) {
                continue
            }
            boxIds.add(box.id)
            boxNames.add(box.name)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, boxNames!!)
        spinnerBoxes.adapter = adapter;
        spinnerBoxes.setSelection(0);
    }

    fun setListeners() {
        spinnerBoxes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onClickSpinnerBoxes(view, position, id);
            }
        }
        findViewById<EditText>(R.id.etSearchItems).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                itemAdapter?.filter?.filter(s.toString());
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        });
    }

    fun loadItemsFromDatabase() {
        GlobalScope.launch {
            val itemDao = UbiApp.database?.itemDao();
            allItems = itemDao?.getAll();
            items = mutableListOf();
            allItems?.let { items!!.addAll(it) }

        }
    }

    fun loadBoxesFromDatabase() {
        GlobalScope.launch {
            val boxDao = UbiApp.database?.boxDao();
            boxes = boxDao?.getAll();
        }
    }

    fun onClickSpinnerBoxes(view: View?, position: Int, id: Long) {
        val boxId = boxIds[position];
        findViewById<EditText>(R.id.etSearchItems).setText("");
        itemAdapter?.filterByBox(boxId)?.filter("");

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
