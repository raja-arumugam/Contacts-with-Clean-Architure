package com.example.contacts.presentation.ui.fragement


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.contacts.R
import com.example.contacts.databinding.FragmentSplashBinding
import com.example.contacts.di.component.app.Injectable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(), Injectable {

    private lateinit var splashBinding: FragmentSplashBinding
    val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        splashBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)

        splashBinding.lifecycleOwner = viewLifecycleOwner

        Glide.with(this).load(R.drawable.android).into(splashBinding.ivSplash)

        val slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.silde)
        splashBinding.ivSplash.startAnimation(slideAnimation)

        activityScope.launch {
            delay(5000)

            val splashFragmentDirection =
                SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            findNavController().navigate(splashFragmentDirection)
        }

        return splashBinding.root
    }

    override fun onStop() {
        super.onStop()
        activityScope.cancel()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

}