package co.pvitor.instagram.add.view

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import co.pvitor.instagram.R
import co.pvitor.instagram.add.Add
import co.pvitor.instagram.add.presentation.AddPresenter
import co.pvitor.instagram.add.view.AddFragment.Companion.URI_KEY
import co.pvitor.instagram.common.base.DependencyInjector
import co.pvitor.instagram.databinding.ActivityAddBinding
import com.google.android.material.snackbar.Snackbar

class AddActivity: AppCompatActivity(), Add.View {

    override lateinit var presenter: Add.Presenter

    private lateinit var binding: ActivityAddBinding

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        presenter = AddPresenter(this, DependencyInjector.addRepository())

        uri = intent.getParcelableExtra<Uri>(URI_KEY)

        if(uri != null) {

            binding.apply {

                setSupportActionBar(toolbarAdd)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                imageViewPost.setImageURI(uri)

            }

        } else {
            finish()
        }

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.share_post) {
           uri?.let {
               presenter.createPost(it, binding.textInputEditTextCaption.text.toString())
           }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun displayRequestFailure(message: Int?) {
        message?.let {
            Snackbar.make(binding.root, getText(it), Snackbar.LENGTH_SHORT).show()
        }
        finish()
    }

    override fun displayRequestSuccess(message: Int?) {
        message?.let {
            Snackbar.make(binding.root, getText(it), Snackbar.LENGTH_SHORT).show()
        }
        finish()
    }

    override fun showProgress(enabled: Boolean) {
        binding.progressBarAdd.visibility = if(enabled) View.VISIBLE else View.GONE
    }

}