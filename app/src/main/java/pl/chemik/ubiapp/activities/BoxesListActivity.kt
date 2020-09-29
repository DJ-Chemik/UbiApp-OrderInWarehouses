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
import pl.chemik.ubiapp.database.entities.Box
import pl.chemik.ubiapp.database.entities.Location

class BoxesListActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null;
    var boxes: List<Box>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boxes_list)
        recyclerView = findViewById(R.id.recyclerBoxes);
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
        loadBoxesFromDatabase();
        while (boxes == null) {
            Log.d("WAITING", "Waiting for get boxes")
        }
        val locationAdapter = LocationAdapter(this, boxes!!);
        recyclerView?.adapter = locationAdapter;
        recyclerView?.layoutManager = LinearLayoutManager(this);
//        locationAdapter.setItemClickListener(this);
    }

    fun loadBoxesFromDatabase() {
        GlobalScope.launch {
            val boxDao = UbiApp.database?.boxDao();
            boxes = boxDao?.getAll();
        }
    }

    fun onAddClick(view: View) {
        val intent = Intent(this, LocationModActivity::class.java);
        intent.putExtra("typeOperation", "add");
        intent.putExtra("name", "");
        startActivity(intent);
    }

    override fun onItemClickListener(location: Location) {
        val intent = Intent(this, LocationModActivity::class.java);
        intent.putExtra("typeOperation", "edit");
        intent.putExtra("id", location.id);
        intent.putExtra("name", location.name);
        startActivity(intent);
    }

    fun onBackkClick(view: View) {
        val intent = Intent(this, StartActivity::class.java);
        startActivity(intent);
    }
}
