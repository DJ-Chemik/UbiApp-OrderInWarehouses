package pl.chemik.ubiapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.chemik.ubiapp.R
import pl.chemik.ubiapp.database.UbiApp
import pl.chemik.ubiapp.database.entities.Location


class LocationModActivity : AppCompatActivity() {

    lateinit var fieldName : TextView;
    var typeOperation: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_mod);
        fieldName = findViewById(R.id.editTextLocationName);
        typeOperation = intent.extras?.getString("typeOperation");
        fieldName.text = intent.extras?.getString("name");
        if (typeOperation==="add") {

        }
        if (typeOperation==="edit") {

        }

    }

    fun onAcceptClick(view: View) {
        val intent = Intent(this, LocationsListActivity::class.java);
        GlobalScope.launch {
            val locationDao = UbiApp.database?.locationDao();
            locationDao?.create(Location(fieldName.text.toString()));
        }
        startActivity(intent);
    }

    fun clickDelete(view: View) {
        GlobalScope.launch {
            val locationDao = UbiApp.database?.locationDao();
            val id = intent.extras?.getInt("id");
            Log.d("ID", id.toString())
            if (id != null) {
                val locationToDelete = locationDao?.getOneById(id);
                while (locationToDelete == null) {

                }
                Log.d("id to delete", locationToDelete.id.toString())
                Log.d("Name", locationToDelete.name)
                locationDao?.delete(locationToDelete);
            }
        }
        val intent = Intent(this, LocationsListActivity::class.java);
        startActivity(intent);
    }

    fun clickCancel(view: View) {
        super.onBackPressed();
    }
}