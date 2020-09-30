package pl.chemik.ubiapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.chemik.ubiapp.R
import pl.chemik.ubiapp.database.UbiApp
import pl.chemik.ubiapp.database.entities.Box
import pl.chemik.ubiapp.database.entities.Location

class BoxesListActivity : AppCompatActivity(), RecycledListBoxClickListener {

    var recyclerView: RecyclerView? = null;
    var boxes: List<Box>? = null;
    lateinit var spinnerLocations: Spinner;
    var boxAdapter: BoxAdapter? = null;
    var locations: List<Location>? = null;
    val locationNames: MutableList<String> = mutableListOf("Wszystkie lokacje");
    val locationIds: MutableList<Int> = mutableListOf(-1);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boxes_list)
        recyclerView = findViewById(R.id.recyclerBoxes);
//        initializeView();
        spinnerLocations = findViewById(R.id.spinnerBoxesListLocations)
        setListeners()
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
        findViewById<EditText>(R.id.etSearchBoxes).setText("")
        loadBoxesFromDatabase();
        loadLocationsFromDatabase();
        while (boxes == null) {
            Log.d("WAITING", "Waiting for get boxes")
        }
        boxAdapter = BoxAdapter(this, boxes!!);
        recyclerView?.adapter = boxAdapter;
        recyclerView?.layoutManager = LinearLayoutManager(this);
        boxAdapter!!.setItemClickListener(this);
        while (locations == null) {
            Log.d("WAITING", "Waiting for get locations")
        }
        for (location in locations!!) {
            if (locationIds.contains(location.id)) {
                continue
            }
            locationIds.add(location.id)
            locationNames.add(location.name)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locationNames!!)
        spinnerLocations.adapter = adapter;
        spinnerLocations.setSelection(0);
    }

    fun setListeners() {
        spinnerLocations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onClickSpinnerBoxes(view, position, id);
            }
        }
        findViewById<EditText>(R.id.etSearchBoxes).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                boxAdapter?.filter?.filter(s.toString());
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        });
    }

    fun loadBoxesFromDatabase() {
        GlobalScope.launch {
            val boxDao = UbiApp.database?.boxDao();
            boxes = boxDao?.getAll();
        }
    }

    fun loadLocationsFromDatabase() {
        GlobalScope.launch {
            val locationDao = UbiApp.database?.locationDao();
            locations = locationDao?.getAll();
        }
    }

    fun onClickSpinnerBoxes(view: View?, position: Int, id: Long) {
        val boxId = locationIds[position];
        findViewById<EditText>(R.id.etSearchBoxes).setText("");
        boxAdapter?.filterByBox(boxId)?.filter("");

    }

    fun onAddClick(view: View) {
        val intent = Intent(this, BoxModActivity::class.java);
        intent.putExtra("typeOperation", "add");
        intent.putExtra("name", "");
        intent.putExtra("qrCode", "");
        intent.putExtra("description", "");
        intent.putExtra("locationId", "");
        startActivity(intent);
    }

    override fun onItemClickListener(box: Box) {
        val intent = Intent(this, BoxModActivity::class.java);
        intent.putExtra("typeOperation", "edit");
        intent.putExtra("id", box.id);
        intent.putExtra("name", box.name);
        intent.putExtra("qrCode", box.qrCode);
        intent.putExtra("description", box.description);
        intent.putExtra("locationId", box.locationId);
        startActivity(intent);
    }

    fun onBackkClick(view: View) {
        val intent = Intent(this, StartActivity::class.java);
        startActivity(intent);
    }
}
