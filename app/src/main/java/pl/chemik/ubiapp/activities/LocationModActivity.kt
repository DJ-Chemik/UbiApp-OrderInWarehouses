package pl.chemik.ubiapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.chemik.ubiapp.R
import pl.chemik.ubiapp.database.UbiApp
import pl.chemik.ubiapp.database.entities.Location


class LocationModActivity : AppCompatActivity() {

    lateinit var fieldName: TextView;
    var typeOperation: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_mod);
        fieldName = findViewById(R.id.editTextLocationName);
        typeOperation = intent.extras?.getString("typeOperation");
        fieldName.text = intent.extras?.getString("name");
        if (typeOperation == "add") {
            findViewById<Button>(R.id.deleteLocationButton).isVisible = false;
        }
        if (typeOperation == "edit") {

        }

    }

    fun onAcceptClick(view: View) {
        val newIntent = Intent(this, LocationsListActivity::class.java);
        if (typeOperation == "add") {
            GlobalScope.launch {
                val locationDao = UbiApp.database?.locationDao();
                locationDao?.create(Location(fieldName.text.toString()));
            }
        }
        if (typeOperation == "edit") {
            GlobalScope.launch {
                val id = intent.extras?.getInt("id");
                val locationDao = UbiApp.database?.locationDao();
                if (id != null) {
                    val locationToEdit = locationDao?.getOneById(id)
                    while (locationToEdit == null) {
                    }
                    locationToEdit.name = fieldName.text.toString();
                    locationDao?.update(locationToEdit);
                }
            }
        }
        startActivity(newIntent);
    }

    fun clickDelete(view: View) {
        GlobalScope.launch {
            val locationDao = UbiApp.database?.locationDao();
            val id = intent.extras?.getInt("id");
            if (id != null) {
                val locationToDelete = locationDao?.getOneById(id);
                while (locationToDelete == null) {
                }
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