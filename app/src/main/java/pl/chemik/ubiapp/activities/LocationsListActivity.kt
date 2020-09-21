package pl.chemik.ubiapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.chemik.ubiapp.R

class LocationsListActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null;
    lateinit var s1: Array<String>;
    lateinit var s2: Array<String>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_list);
        recyclerView = findViewById(R.id.recyclerLocations)
        s1 = resources.getStringArray(R.array.programming_languages);
        s2 = resources.getStringArray(R.array.description);

        val locationAdapter = LocationAdapter(this, s1, s2);
        recyclerView?.adapter = locationAdapter;
        recyclerView?.layoutManager = LinearLayoutManager(this);
    }


}
