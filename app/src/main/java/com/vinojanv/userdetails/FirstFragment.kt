package com.vinojanv.userdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vinojanv.userdetails.api.UserAPIService
import com.vinojanv.userdetails.databinding.FragmentFirstBinding
import com.vinojanv.userdetails.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val userAPIService = UserAPIService.create()


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.buttonFirst.setOnClickListener {
            val id = binding.editTextNumberDecimal.editableText

            val user = userAPIService.getUser(id.toString());
            user.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val body = response.body()
                    body?.let {
                        binding.textviewName.text = it.name
                        binding.textViewEmail.text = it.email
                        binding.textViewUsername.text = it.username
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                        binding.textviewName.text = "User"
                        binding.textViewEmail.text = "Does not"
                        binding.textViewUsername.text = "exist!"
                    }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}