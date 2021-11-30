package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.adapters.ParamedicDetailsAdapter
import com.guelphwellingtonparamedicsapp.databinding.FragmentParamedicDetailsBinding
import com.guelphwellingtonparamedicsapp.models.ParamedicModel

class ParamedicDetailsFragment : Fragment(), View.OnClickListener {

    lateinit var paramedic : ParamedicModel
    lateinit var fragmentParamedicDetailsBinding : FragmentParamedicDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            paramedic = it?.getSerializable("model") as ParamedicModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentParamedicDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(inflater.context), R.layout.fragment_paramedic_details, container, false)

        fragmentParamedicDetailsBinding.emailTextTv.text = paramedic.email
        fragmentParamedicDetailsBinding.extensionTextTv.text = paramedic.extension
        fragmentParamedicDetailsBinding.personalPhoneTextTv.text = paramedic.personalPhone
        fragmentParamedicDetailsBinding.workPhoneTextTv.text = paramedic.workPhone

        fragmentParamedicDetailsBinding.back.setOnClickListener(this)

        var adapter = ParamedicDetailsAdapter(requireContext(), paramedic.availableServices)
        var mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        fragmentParamedicDetailsBinding.certificatesRv.layoutManager = mLayoutManager
        fragmentParamedicDetailsBinding.certificatesRv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        fragmentParamedicDetailsBinding.certificatesRv.setHasFixedSize(true)
        fragmentParamedicDetailsBinding.certificatesRv.adapter = adapter


        // Inflate the layout for this fragment
        return fragmentParamedicDetailsBinding.root
    }

    override fun onClick(v: View?) {
       when(v){
           fragmentParamedicDetailsBinding.back -> backButton()
       }
    }

    private fun backButton(){
        fragmentManager?.popBackStack()
    }
}