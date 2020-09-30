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
import pl.chemik.ubiapp.database.entities.Item

class ItemModActivity : AppCompatActivity() {

    lateinit var fieldName: TextView;
    lateinit var fieldCategories: TextView;
    lateinit var fieldEanUpcCode: TextView;
    lateinit var fieldDescription: TextView;
    lateinit var fieldBoxes: Spinner;
    var boxes: List<Box>? = null
    lateinit var boxNames: List<String>
    lateinit var boxIds: List<Int>
    var typeOperation: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_mod)
        fieldName = findViewById(R.id.editTextItemName);
        fieldCategories = findViewById(R.id.editTextCategories)
        fieldEanUpcCode = findViewById(R.id.editTextItemQrCode);
        fieldDescription = findViewById(R.id.editTextItemDescription);
        fieldBoxes = findViewById(R.id.spinnerItemBoxes);
        typeOperation = intent.extras?.getString("typeOperation");
        fieldName.text = intent.extras?.getString("name");
        fieldCategories.text = intent.extras?.getString("categories");
        fieldEanUpcCode.text = intent.extras?.getString("eanUpcCode");
        fieldDescription.text = intent.extras?.getString("description");
        loadBoxesFromDatabase();
        while (boxes == null) {
            Log.d("WAITING", "Waiting for get boxes")
        }
        boxNames = boxes?.map { box -> box.name }!!;
        boxIds = boxes?.map { box -> box.id }!!;
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, boxNames!!)
        fieldBoxes.adapter = adapter;
        val spinnerId = boxIds.indexOf(intent.extras?.getInt("boxId"));
        fieldBoxes.setSelection(spinnerId);
        if (typeOperation == "add") {
            findViewById<Button>(R.id.deleteItemButton).isVisible = false;
            findViewById<Button>(R.id.acceptItemButton).text = "Dodaj";
        }
        if (typeOperation == "edit") {
            findViewById<Button>(R.id.acceptItemButton).text = "Edytuj";
        }
    }

    fun loadBoxesFromDatabase() {
        GlobalScope.launch {
            val boxDao = UbiApp.database?.boxDao();
            boxes = boxDao?.getAll();
        }
    }

    fun onAcceptClick(view: View) {
        val newIntent = Intent(this, ItemsListActivity::class.java);
        if (typeOperation == "add") {
            GlobalScope.launch {
                val itemDao = UbiApp.database?.itemDao();
                itemDao?.create(
                    Item(
                        fieldName.text.toString(),
                        fieldDescription.text.toString(),
                        fieldEanUpcCode.text.toString(),
                        fieldCategories.text.toString(),
                        boxIds[fieldBoxes.selectedItemPosition]
                    )
                );
            }
        }
        if (typeOperation == "edit") {
            GlobalScope.launch {
                val id = intent.extras?.getInt("id");
                val itemDao = UbiApp.database?.itemDao();
                if (id != null) {
                    val itemToEdit = itemDao?.getOneById(id)
                    while (itemToEdit == null) {
                    }
                    itemToEdit.name = fieldName.text.toString();
                    itemToEdit.categories = fieldCategories.text.toString();
                    itemToEdit.description = fieldDescription.text.toString();
                    itemToEdit.eanUpcCode = fieldEanUpcCode.text.toString();
                    itemToEdit.boxId = boxIds[fieldBoxes.selectedItemPosition];
                    itemDao?.update(itemToEdit);
                }
            }
        }
        startActivity(newIntent);
    }

    fun clickDelete(view: View) {
        GlobalScope.launch {
            val itemDao = UbiApp.database?.itemDao();
            val id = intent.extras?.getInt("id");
            if (id != null) {
                val itemToDelete = itemDao?.getOneById(id);
                while (itemToDelete == null) {
                }
                itemDao?.delete(itemToDelete);
            }
        }
        val intent = Intent(this, ItemsListActivity::class.java);
        startActivity(intent);
    }

    fun clickCancel(view: View) {
        super.onBackPressed();
    }


}