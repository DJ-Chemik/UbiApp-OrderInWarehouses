package pl.chemik.ubiapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.chemik.ubiapp.R
import pl.chemik.ubiapp.database.UbiApp
import pl.chemik.ubiapp.database.entities.Box
import pl.chemik.ubiapp.database.entities.Location

class BoxModActivity : AppCompatActivity() {

    lateinit var fieldName: TextView;
    lateinit var fieldQrCode: TextView;
    lateinit var fieldDescription: TextView;
    lateinit var fieldLocation: Spinner;
    var locations: List<Location>? = null
    lateinit var locationNames: List<String>
    lateinit var locationIds: List<Int>
    var typeOperation: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_box_mod)
        fieldName = findViewById(R.id.editTextBoxName);
        fieldQrCode = findViewById(R.id.editTextBoxQrCode);
        fieldDescription = findViewById(R.id.editTextBoxDescription);
        fieldLocation = findViewById(R.id.spinnerBoxLocation);
        typeOperation = intent.extras?.getString("typeOperation");
        fieldName.text = intent.extras?.getString("name");
        fieldQrCode.text = intent.extras?.getString("qrCode");
        fieldDescription.text = intent.extras?.getString("description");
        loadLocationsFromDatabase();
        while (locations == null) {
            Log.d("WAITING", "Waiting for get locations")
        }
        locationNames = locations?.map { location -> location.name }!!;
        locationIds = locations?.map { location -> location.id }!!;
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locationNames!!)
        fieldLocation.adapter = adapter;
        val spinnerId = locationIds.indexOf(intent.extras?.getInt("locationId"));
        fieldLocation.setSelection(spinnerId);
        if (typeOperation == "add") {
            findViewById<Button>(R.id.deleteBoxButton).isVisible = false;
            findViewById<Button>(R.id.acceptBoxButton).text = "Dodaj";
        }
        if (typeOperation == "edit") {
            findViewById<Button>(R.id.acceptBoxButton).text = "Edytuj";
        }
    }

    fun loadLocationsFromDatabase() {
        GlobalScope.launch {
            val locationDao = UbiApp.database?.locationDao();
            locations = locationDao?.getAll();
        }
    }

    fun onAcceptClick(view: View) {
        val newIntent = Intent(this, BoxesListActivity::class.java);
        if (typeOperation == "add") {
            GlobalScope.launch {
                val boxDao = UbiApp.database?.boxDao();
                boxDao?.create(
                    Box(
                        fieldName.text.toString(),
                        fieldDescription.text.toString(),
                        fieldQrCode.text.toString(),
                        locationIds[fieldLocation.selectedItemPosition]
                    )
                );
            }
        }
        if (typeOperation == "edit") {
            GlobalScope.launch {
                val id = intent.extras?.getInt("id");
                val boxDao = UbiApp.database?.boxDao();
                if (id != null) {
                    val boxToEdit = boxDao?.getOneById(id)
                    while (boxToEdit == null) {
                    }
                    boxToEdit.name = fieldName.text.toString();
                    boxToEdit.description = fieldDescription.text.toString();
                    boxToEdit.qrCode = fieldQrCode.text.toString();
                    boxToEdit.locationId = locationIds[fieldLocation.selectedItemPosition];
                    boxDao?.update(boxToEdit);
                }
            }
        }
        startActivity(newIntent);
    }

    fun clickDelete(view: View) {
        GlobalScope.launch {
            val boxDao = UbiApp.database?.boxDao();
            val id = intent.extras?.getInt("id");
            if (id != null) {
                val boxToDelete = boxDao?.getOneById(id);
                while (boxToDelete == null) {
                }
                boxDao?.delete(boxToDelete);
            }
        }
        val intent = Intent(this, BoxesListActivity::class.java);
        startActivity(intent);
    }

    fun clickCancel(view: View) {
        super.onBackPressed();
    }

}