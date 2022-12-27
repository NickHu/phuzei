package com.alirezaafkar.phuzei.presentation.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.alirezaafkar.phuzei.App
import com.alirezaafkar.phuzei.BuildConfig
import com.alirezaafkar.phuzei.MUZEI_PACKAGE_NAME
import com.alirezaafkar.phuzei.R
import com.alirezaafkar.phuzei.databinding.FragmentSettingsBinding
import com.alirezaafkar.phuzei.presentation.muzei.PhotosWorker
import com.alirezaafkar.phuzei.presentation.pro.ProDialog
import com.alirezaafkar.phuzei.util.openInPlayStore
import javax.inject.Inject

/**
 * Created by Alireza Afkar on 5/21/20.
 */
class SettingsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SettingsViewModel by activityViewModels { viewModelFactory }

    private lateinit var adapter: SettingsAdapter

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).component?.inject(this)
        super.onCreate(savedInstanceState)

        with(viewModel) {
            val owner = this@SettingsFragment

            categoryObservable.observe(owner) {
                adapter.setCategory(it)
            }

            intentObservable.observe(owner) {
                startActivity(it)
            }

            isShuffleObservable.observe(owner) {
                if (it) {
                    binding.shuffle.isChecked = true
                } else {
                    binding.sequence.isChecked = true
                }
            }

            logoutObservable.observe(owner) {
                App.restart(requireActivity())
            }

            imagesCountObservable.observe(owner) {
                binding.imagesCountDescription.setSelection(it)
            }

            enqueueImages.observe(owner) {
                PhotosWorker.enqueueLoad(requireContext(), true)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contact.setOnClickListener { viewModel.onContact() }
        binding.contactDescription.setOnClickListener { viewModel.onContact() }

        binding.muzei.setOnClickListener { openInPlayStore(MUZEI_PACKAGE_NAME) }
        binding.muzeiDescription.setOnClickListener { binding.muzei.performClick() }

        binding.logout.setOnClickListener { viewModel.onLogout() }
        binding.logoutDescription.setOnClickListener { viewModel.onLogout() }

        binding.pro.isVisible = !BuildConfig.IS_PRO
        binding.proDescription.isVisible = binding.pro.isVisible

        binding.proDescription.setOnClickListener { binding.pro.performClick() }
        binding.pro.setOnClickListener {
            ProDialog.show(parentFragmentManager)
        }

        binding.order.setOnCheckedChangeListener { _, id ->
            viewModel.onShuffleOrder(id == R.id.shuffle)
        }

        binding.imagesCount.setOnClickListener { binding.imagesCountDescription.performClick() }
        binding.imagesCountDescription.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                viewModel.onImagesCount(position)
            }

        }

        setupRecycler(binding.categories)

        viewModel.subscribe()
    }

    private fun setupRecycler(categories: RecyclerView) {
        adapter = SettingsAdapter {
            viewModel.onSelectCategory(it)
        }
        categories.adapter = adapter
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

}
