package pl.chemik.ubiapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import pl.chemik.ubiapp.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AddLocationActivity : AppCompatActivity() {

    lateinit var fieldName : TextView;
    var typeOperation: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location);
        fieldName = findViewById(R.id.editTextLocationName);
        val extras =  intent.extras;
        typeOperation = extras?.getString("typeOperation");
        fieldName.text = extras?.getString("name");

    }

    fun onAcceptClick(view: View) {
        val intent = Intent(this, LocationsListActivity::class.java);
        intent.putExtra("locationName", fieldName.text);
        startActivity(intent);
    }

    fun clickCancel(view: View) {
        super.onBackPressed();
    }
}