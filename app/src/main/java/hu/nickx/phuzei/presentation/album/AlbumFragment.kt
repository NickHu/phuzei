package hu.nickx.phuzei.presentation.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.apps.muzei.api.MuzeiContract
import com.google.android.material.snackbar.Snackbar
import hu.nickx.phuzei.App
import hu.nickx.phuzei.BuildConfig
import hu.nickx.phuzei.MUZEI_PACKAGE_NAME
import hu.nickx.phuzei.R
import hu.nickx.phuzei.databinding.FragmentAlbumsBinding
import hu.nickx.phuzei.presentation.main.AlbumAdapter
import hu.nickx.phuzei.presentation.muzei.PhotosWorker
import hu.nickx.phuzei.util.InfiniteScrollListener
import hu.nickx.phuzei.util.isMuzeiInstalled
import hu.nickx.phuzei.util.openInPlayStore
import hu.nickx.phuzei.util.toast
import javax.inject.Inject

/**
 * Created by Alireza Afkar on 6/12/2018AD.
 */
class AlbumFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: AlbumViewModel by viewModels { viewModelFactory }

    private lateinit var adapter: AlbumAdapter

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).component?.inject(this)

        super.onCreate(savedInstanceState)

        with(viewModel) {
            val owner = this@AlbumFragment
            selectAlbumObservable.observe(owner) {
                if (isPhuzeiSelected()) {
                    showExitSnackBar(binding.swipe, it)
                } else {
                    showActivateSnackBar(binding.swipe)
                }
            }

            albumsObservable.observe(owner) {
                if (it.isNotEmpty()) {
                    adapter.addItems(it)
                }
            }

            loadingObservable.observe(owner) {
                binding.swipe.isRefreshing = it
            }

            errorObservable.observe(owner) {
                requireContext().toast(it)
            }

            enqueueImages.observe(owner) {
                PhotosWorker.enqueueLoad(requireContext(), true)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipe.setOnRefreshListener { refresh() }
        setupRecycler(binding.recyclerView)
        viewModel.subscribe(arguments?.getInt(KEY_TYPE, TYPE_ALBUMS) ?: TYPE_ALBUMS)
    }

    private fun setupRecycler(recyclerView: RecyclerView) {
        adapter = AlbumAdapter(viewModel.currentAlbum) {
            viewModel.onSelectAlbum(it)
            adapter.setAlbum(it.id)
        }

        with(recyclerView) {
            adapter = this@AlbumFragment.adapter
            addOnScrollListener(InfiniteScrollListener { viewModel.onLoadMore() })
        }
    }

    private fun refresh() {
        adapter.clearItems()
        viewModel.onRefresh()
    }

    private fun isPhuzeiSelected(): Boolean {
        return MuzeiContract.Sources.isProviderSelected(
            requireContext(),
            BuildConfig.PHUZEI_AUTHORITY
        )
    }

    private fun showActivateSnackBar(swipe: SwipeRefreshLayout) {
        Snackbar.make(
            swipe,
            R.string.source_not_activated,
            Snackbar.LENGTH_LONG
        ).setAction(R.string.activate) {
            val intent = MuzeiContract.Sources.createChooseProviderIntent(BuildConfig.PHUZEI_AUTHORITY)
            if (isMuzeiInstalled()) {
                startActivity(intent)
            } else {
                openInPlayStore(MUZEI_PACKAGE_NAME)
            }
        }.show()
    }

    private fun showExitSnackBar(swipe: SwipeRefreshLayout, title: String) {
        Snackbar.make(
            swipe,
            getString(R.string.selected_album_, title),
            Snackbar.LENGTH_LONG
        ).setAction(R.string.exit) {
            requireActivity().finish()
        }.show()
    }

    companion object {
        private const val KEY_TYPE = "type"

        const val TYPE_ALBUMS = 0
        const val TYPE_SHARED_ALBUMS = 1

        fun newInstance(type: Int): AlbumFragment {
            return AlbumFragment().apply {
                arguments = bundleOf(KEY_TYPE to type)
            }
        }
    }
}
