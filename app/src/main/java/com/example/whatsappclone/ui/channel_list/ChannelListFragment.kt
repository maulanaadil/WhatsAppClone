package com.example.whatsappclone.ui.channel_list


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.whatsappclone.databinding.FragmentChannelListBinding
import com.example.whatsappclone.ui.home.HomeFragmentDirections
import com.getstream.sdk.chat.StreamChat
import com.getstream.sdk.chat.adapter.ChannelListItemAdapter
import com.getstream.sdk.chat.enums.FilterObject
import com.getstream.sdk.chat.enums.Filters
import com.getstream.sdk.chat.rest.User
import com.getstream.sdk.chat.viewmodel.ChannelListViewModel

const val API_KEY = "s2dxdhpxd94g"
const val USER_ID = "empty-queen-5"
const val USER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZW1wdHktcXVlZW4tNSJ9.RJw-XeaPnUBKbbh71rV1bYAKXp6YaPARh68O08oRnOU"

class ChannelListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val configuration = StreamChat.Config(activity!!.applicationContext, API_KEY)
        StreamChat.init(configuration)

        val client = StreamChat.getInstance(activity!!.application)
        val extraData = HashMap<String, Any>()
        extraData["name"] = "Kim Jennie"
        extraData["image"] = "https://i.pinimg.com/736x/f6/6e/2e/f66e2e8f1795672ee9d0ace2760178c6.jpg"

        val currentUser = User(USER_ID, extraData)

        // Membuktikan Token User
        client.setUser(
            currentUser,
            USER_TOKEN
        )

        val binding = FragmentChannelListBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        val viewModel: ChannelListViewModel by viewModels()
        binding.viewModel = viewModel

        val adapter = ChannelListItemAdapter(activity)

        binding.channelList.setViewModel(viewModel, this, adapter)

        val filter =
            Filters.and(Filters.eq("type", "messaging"), Filters.`in`("members", "empty-queen-5"))
        viewModel.setChannelFilter(filter)

        binding.channelList.setOnChannelClickListener { channel ->
            findNavController().navigate(
                HomeFragmentDirections.navHomeToChannel(channel.type, channel.id)
            )
        }
        return binding.root
    }

}


