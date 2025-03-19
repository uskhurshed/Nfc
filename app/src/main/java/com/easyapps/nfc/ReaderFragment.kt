package com.easyapps.nfc

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.easyapps.nfc.databinding.FragmentEmulatorTerminalBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ReaderFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentEmulatorTerminalBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEmulatorTerminalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private var statusRead: Boolean = false

    private fun initView() {
        if (statusRead) showStopRead()
        binding.tvLogs.text = Html.fromHtml("<p> ${(activity as MainActivity).log} <p> ")
        binding. btnRead.setOnClickListener {
            binding. txDescription.visibility = View.VISIBLE
            listener?.onClickReadCard(statusRead)
        }
        binding.checkboxChip.setOnClickListener {
            (activity as MainActivity).mChip =    binding.checkboxChip.isChecked
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    val text = "Name of file:"

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    @SuppressLint("SetTextI18n")
    fun showStartRead() {
        if (   binding.btnRead != null) {
            statusRead = !statusRead
            binding. btnRead.text = "STOP READ"
        }
    }

    @SuppressLint("SetTextI18n")
    fun showStopRead() {
        statusRead = !statusRead
        binding. btnRead.text = "READ"
    }

    fun showLogs(logText: String, filename: String) {
        binding.  txtFileSave.text = "$text $filename"
        binding.  tvLogs.text = Html.fromHtml("<p> $logText <p> ")
    }

    fun showData(cardnumber: String, cardexpiration: String) {
        binding. txDescription.text = "$cardnumber \n$cardexpiration"
    }

    companion object {
        val TAG = "READER"
        @JvmStatic
        fun newInstance(param1: String = "", param2: String = "") =
                ReaderFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
