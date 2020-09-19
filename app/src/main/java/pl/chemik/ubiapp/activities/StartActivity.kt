package pl.chemik.ubiapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import pl.chemik.ubiapp.R

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickGoToLocationsList(view: View) {
        val intent = Intent(this, LocationsListActivity::class.java);
        startActivity(intent);
    }

    fun clickGoToItemsList(view: View) {
        val intent = Intent(this, ItemsListActivity::class.java);
        startActivity(intent);
    }

    fun clickGoToSettings(view: View) {
        val intent = Intent(this, SettingsActivity::class.java);
        startActivity(intent);
    }

    fun clickExit(view: View) {
        super.onBackPressed();
    }
}
