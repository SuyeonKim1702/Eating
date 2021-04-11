package desla.aos.eating.ui.post

import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.network.ServerApi
import desla.aos.eating.data.repositories.PostRepository
import desla.aos.eating.databinding.ActivityPostBinding
import desla.aos.eating.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*


class PostActivity :  BaseActivity<ActivityPostBinding>() {


    override val layoutResourceId: Int
        get() = R.layout.activity_post

    private lateinit var factory: PostViewModelFactory
    private lateinit var viewModel: PostViewModel

    private var isFirstSpinner = true

    override fun initStartView() {

        val server = ServerApi()
        val repository = PostRepository(server)
        factory = PostViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)


        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {
        val items = arrayOf("2명", "3명", "4명", "5명")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, items)
        viewDataBinding.spinner.adapter = adapter
        viewDataBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                viewModel.data.memberCountLimit = position + 1
               if(isFirstSpinner){
                   isFirstSpinner = false
               }else{
                   viewDataBinding.spinnerLayout.isSelected = true
                   (view as TextView).setTextColor(Color.RED) //Change selected text color

                   viewDataBinding.spinner.isSelected = true
               }

            }

        }


        viewDataBinding.tvDv0.setOnClickListener {
            it.isSelected = true
            viewDataBinding.tvDv1.isSelected = false
            viewDataBinding.tvDv2.isSelected = false
            viewModel.data.deliveryFeeByHost = 0
        }
        viewDataBinding.tvDv1.setOnClickListener {
            it.isSelected = true
            viewDataBinding.tvDv0.isSelected = false
            viewDataBinding.tvDv2.isSelected = false
            viewModel.data.deliveryFeeByHost = 1
        }
        viewDataBinding.tvDv2.setOnClickListener {
            it.isSelected = true
            viewDataBinding.tvDv0.isSelected = false
            viewDataBinding.tvDv1.isSelected = false
            viewModel.data.deliveryFeeByHost = 2
        }

        viewDataBinding.tvPlace0.setOnClickListener {
            it.isSelected = true
            viewDataBinding.tvPlace1.isSelected = false
            viewDataBinding.tvPlace2.isSelected = false
            viewModel.data.meetPlace = 0
        }
        viewDataBinding.tvPlace1.setOnClickListener {
            it.isSelected = true
            viewDataBinding.tvPlace0.isSelected = false
            viewDataBinding.tvPlace2.isSelected = false
            viewModel.data.meetPlace = 0
        }
        viewDataBinding.tvPlace2.setOnClickListener {
            it.isSelected = true
            viewDataBinding.tvPlace0.isSelected = false
            viewDataBinding.tvPlace1.isSelected = false
            viewModel.data.meetPlace = 0
        }

    }

    override fun initAfterBinding() {

        viewModel.category_name.observe(this, Observer {
            viewDataBinding.tvPostCategory.text = it
        })

        //오늘 날짜 설정
        SimpleDateFormat("yyyy/MM/dd a HH:mm").let {
            viewDataBinding.tvDate.text = it.format(Date())
        }

        viewModel.date.observe(this, Observer {
            viewDataBinding.tvDate.text = it
            viewDataBinding.tvDate.isSelected = true
        })




    }



}