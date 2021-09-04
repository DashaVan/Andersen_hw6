package spacekotlin.vaniukova.fragmentscontactlisthw6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import spacekotlin.vaniukova.fragmentscontactlisthw6.fragments.DetailFragment
import spacekotlin.vaniukova.fragmentscontactlisthw6.fragments.ListFragment

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateTo(ListFragment(), "listFragment")
    }

    override fun navigateTo(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(name)
            .commit()
    }

    override fun navigateToTabletDetail(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_detail_tablet, fragment)
            .commit()
    }

    override fun onBackPressed() {
        var currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (isTableMode()) {
            currentFragment =
                supportFragmentManager.findFragmentById(R.id.container_detail_tablet)!!
        }

        if (currentFragment is DetailFragment) {
            supportFragmentManager.popBackStack("listFragment", 0)
        } else {
            finish()
        }
    }

    private fun isTableMode() =
        applicationContext?.resources?.configuration?.smallestScreenWidthDp!! >= 600
}