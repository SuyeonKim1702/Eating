package desla.aos.eating.ui.map

import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.network.KakaoMapApi
import desla.aos.eating.data.repositories.MapRepository
import desla.aos.eating.databinding.ActivityMapBinding
import desla.aos.eating.ui.base.BaseActivity


class MapActivity : BaseActivity<ActivityMapBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_map

    private lateinit var factory: MapViewModelFactory
    private lateinit var viewModel: MapViewModel

    private val mapFragment: MapFragment = MapFragment()

    override fun initStartView() {

        val api = KakaoMapApi()
        val repository = MapRepository(api)
        factory = MapViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MapViewModel::class.java)

        replaceFragment(mapFragment)

    }

    override fun initDataBinding() {


    }

    override fun initAfterBinding() {




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mapFragment.onActivityResult(requestCode, resultCode, data)
    }


    private fun replaceFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.map_frame_layout, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.commit()
    }



}