package uk.co.nickhu.phuzei.presentation.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import uk.co.nickhu.phuzei.App
import uk.co.nickhu.phuzei.MUZEI_PACKAGE_NAME
import uk.co.nickhu.phuzei.R
import uk.co.nickhu.phuzei.databinding.FragmentSettingsBinding
import uk.co.nickhu.phuzei.presentation.muzei.PhotosWorker
import uk.co.nickhu.phuzei.util.openInPlayStore
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
                binding.imagesCountDescription.minValue = 1
                binding.imagesCountDescription.maxValue = 100
                binding.imagesCountDescription.wrapSelectorWheel = false
                binding.imagesCountDescription.value = it
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

        binding.order.setOnCheckedChangeListener { _, id ->
            viewModel.onShuffleOrder(id == R.id.shuffle)
        }

        binding.imagesCount.setOnClickListener { binding.imagesCountDescription.performClick() }
        binding.imagesCountDescription.setOnValueChangedListener { _, _, new -> viewModel.onImagesCount(new) }

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
