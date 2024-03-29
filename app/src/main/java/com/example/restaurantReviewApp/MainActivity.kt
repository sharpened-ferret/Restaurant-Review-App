package com.example.restaurantReviewApp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.restaurantReviewApp.adapters.MainTabAdapter
import com.example.restaurantReviewApp.fragments.RestaurantsFragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var menu : Menu
    lateinit var filter : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mainToolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(mainToolbar)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        //Disables swiping between views to prevent accidental navigation from the map tab
        viewPager.isUserInputEnabled = false;

        val tabTitles = resources.getStringArray(R.array.mainTabTitles)
        viewPager.adapter = MainTabAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = tabTitles[0]
                1 -> tab.text = tabTitles[1]
            }
        }.attach()

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Log.d("SEARCHRESULT", query)
                doMySearch(query)
            }
        }

        createSignInIntent()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.toolbar_layout), menu)
        if (menu != null) {
            this.menu = menu
            menu.findItem(R.id.account).isVisible = false
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.search)?.actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(true)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                finish()
                overridePendingTransition(0, 0);
                startActivity(intent)
                overridePendingTransition(0, 0);
                return true
            }
            R.id.account -> {
                val accountIntent = Intent(this, AccountActivity::class.java)
                startActivity(accountIntent)
                return true
            }
            R.id.logout -> {
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser != null) {
                    signOut()
                }
                else {
                    createSignInIntent()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }



    private fun createSignInIntent() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            menu.findItem(R.id.logout).title = resources.getString(R.string.logout_title)
            menu.findItem(R.id.account).isVisible = true
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            menu.findItem(R.id.logout).title = resources.getString(R.string.login_title)
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
                menu.findItem(R.id.logout).title = resources.getString(R.string.login_title)
                menu.findItem(R.id.account).isVisible = false
                val view : View = findViewById(R.id.view_pager)
                val snackbar = Snackbar.make(view,resources.getString(R.string.logout_message), LENGTH_SHORT )
                snackbar.show()
            }
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun doMySearch(query : String) {

    }
}