package ceui.pixiv.ui.user.following

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ceui.lisa.R
import ceui.lisa.databinding.FragmentPixivListBinding
import ceui.lisa.view.SpacesItemDecoration
import ceui.loxia.clearItemDecorations
import ceui.pixiv.ui.common.IllustCardHolder
import ceui.pixiv.ui.common.PixivFragment
import ceui.pixiv.ui.common.setUpRefreshState
import ceui.pixiv.ui.list.pixivListViewModel
import ceui.pixiv.ui.user.UserPostHolder
import ceui.refactor.ppppx
import ceui.refactor.setOnClick
import ceui.refactor.viewBinding

class FollowingPostFragment : PixivFragment(R.layout.fragment_pixiv_list) {

    private val binding by viewBinding(FragmentPixivListBinding::bind)
    private val args by navArgs<FollowingPostFragmentArgs>()
    private val viewModel by pixivListViewModel { FollowingPostsDataSource(args) }

    private var type: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listView.layoutManager = LinearLayoutManager(requireContext())
        setUpRefreshState(binding, viewModel)
        val dataSource = viewModel.dataSource<FollowingPostsDataSource>()
        binding.listSetting.setOnClick {
            if (type == 0) {
                type = 1
                binding.listView.layoutManager = null
                dataSource.updateMapper { illust -> listOf(IllustCardHolder(illust)) }
                binding.listView.clearItemDecorations()
                binding.listView.addItemDecoration(SpacesItemDecoration(4.ppppx))
                binding.listView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            } else if (type == 1) {
                type = 0
                binding.listView.layoutManager = null
                dataSource.updateMapper { illust -> listOf(UserPostHolder(illust)) }
                binding.listView.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}