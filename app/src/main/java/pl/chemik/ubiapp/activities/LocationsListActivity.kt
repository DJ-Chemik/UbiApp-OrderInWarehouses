package pl.chemik.ubiapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.chemik.ubiapp.R
import pl.chemik.ubiapp.database.UbiApp
import pl.chemik.ubiapp.database.entities.Location

class LocationsListActivity : AppCompatActivity(), RecycledListItemClickListener {

    var recyclerView: RecyclerView? = null;
    var locations: List<Location>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_list);
        recyclerView = findViewById(R.id.recyclerLocations);
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
        loadLocationsFromDatabase();
        while (locations == null) {
            Log.d("WAITING", "Waiting for get locations")
        }
        val locationAdapter = LocationAdapter(this, locations!!);
        recyclerView?.adapter = locationAdapter;
        recyclerView?.layoutManager = LinearLayoutManager(this);
        locationAdapter.setItemClickListener(this);
    }

    fun loadLocationsFromDatabase() {
        GlobalScope.launch {
            val locationDao = UbiApp.database?.locationDao();
            locations = locationDao?.getAll();
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


}
