package spacekotlin.vaniukova.fragmentscontactlisthw6.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import spacekotlin.vaniukova.fragmentscontactlisthw6.ContactsList
import spacekotlin.vaniukova.fragmentscontactlisthw6.Navigator
import spacekotlin.vaniukova.fragmentscontactlisthw6.R
import spacekotlin.vaniukova.fragmentscontactlisthw6.databinding.FragmentDetailBinding
import spacekotlin.vaniukova.fragmentscontactlisthw6.withArguments

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val KEY = "key"
        fun newInstance(id: Long): DetailFragment {
            return DetailFragment().withArguments {
                putLong(KEY, id)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = requireArguments().getLong(KEY)
        addInfo(id)

        binding.buttonSave.setOnClickListener {
            saveInfo(id)
        }
    }

    private fun addInfo(id: Long) {
        val id = id.toInt()
        with(binding) {
            textName.setText(ContactsList.list[id].name)
            textSurname.setText(ContactsList.list[id].surname)
            textPhone.setText(ContactsList.list[id].phoneNumber)
        }
    }

    private fun saveInfo(id: Long) {
        val id = id.toInt()
        with(ContactsList.list[id]) {
            name = binding.textName.text.toString()
            surname = binding.textSurname.text.toString()
            phoneNumber = binding.textPhone.text.toString()
        }
        (activity as Navigator).navigateTo(ListFragment(), "listFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}