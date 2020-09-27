package pl.chemik.ubiapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.chemik.ubiapp.R
import pl.chemik.ubiapp.database.UbiApp
import pl.chemik.ubiapp.database.entities.Location
import java.util.*

class LocationsListActivity : AppCompatActivity(), RecycledListItemClickListener {

    var recyclerView: RecyclerView? = null;
    lateinit var s1: Array<String>;
    lateinit var s2: Array<String>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_list);
        recyclerView = findViewById(R.id.recyclerLocations);
        s1 = resources.getStringArray(R.array.programming_languages);
        s2 = resources.getStringArray(R.array.description);
        changeDatabase();
        val locationAdapter = LocationAdapter(this, s1, s2);
        recyclerView?.adapter = locationAdapter;
        recyclerView?.layoutManager = LinearLayoutManager(this);
        locationAdapter.setItemClickListener(this);

        GlobalScope.launch {
            val locationDao = UbiApp.database?.locationDao();
//            locationDao?.create(Location("strych"))
//            locationDao?.create(Location("piwnica"))
            val locations = locationDao?.getAll();
            if (locations!=null) {
                for (location in locations) {
                    Log.d("=============", location.name)
                }

            }
        }

    }

    fun changeDatabase() {

    }

    fun onAddClick(view: View) {
        val intent = Intent(this, AddLocationActivity::class.java);
        intent.putExtra("typeOperation", "add");
        intent.putExtra("name", "");
        startActivity(intent);
    }

    fun onEditClick(view: View) {
        val intent = Intent(this, AddLocationActivity::class.java);
        intent.putExtra("typeOperation", "edit");
        intent.putExtra("name", s1[0]);
        startActivity(intent);
    }

    override fun onItemClickListener(location: Location) {
        val intent = Intent(this, AddLocationActivity::class.java);
        intent.putExtra("typeOperation", "edit");
        intent.putExtra("name", location.name);
        startActivity(intent);
    }


}
