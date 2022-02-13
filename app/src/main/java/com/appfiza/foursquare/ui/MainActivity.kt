package com.appfiza.foursquare.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.appfiza.foursquare.R
import com.appfiza.foursquare.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)

        handleStatusBar()
    }

    /**
     *  Draw under the status bar
     */
    private fun handleStatusBar() {
        //Set fullScreen (draw under statusBar and NavigationBar )
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding.container.setOnApplyWindowInsetsListener { view, insets ->
            val navigationBarHeight = WindowInsetsCompat.toWindowInsetsCompat(insets)
                .getInsets(WindowInsetsCompat.Type.navigationBars()).bottom

            // Put the content above the navigation bar
            view.updatePadding(bottom = navigationBarHeight)
            WindowInsetsCompat.CONSUMED.toWindowInsets()
        }
    }

}