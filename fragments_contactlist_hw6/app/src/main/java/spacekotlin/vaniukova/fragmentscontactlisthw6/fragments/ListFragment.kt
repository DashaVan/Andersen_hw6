package spacekotlin.vaniukova.fragmentscontactlisthw6.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import spacekotlin.vaniukova.fragmentscontactlisthw6.*
import spacekotlin.vaniukova.fragmentscontactlisthw6.databinding.FragmentListBinding

class ListFragment : Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var deleteDialog: AlertDialog? = null
    private var contacts = ContactsList.list
    private var contactAdapter: ContactAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        deleteDialog?.dismiss()
        deleteDialog = null
        contactAdapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList(contacts)

        binding.btnSearch.setOnClickListener {
            searchContact()
        }

        contactAdapter?.updateContact(contacts)
        contactAdapter?.notifyDataSetChanged()
    }

    private fun searchContact() {
        val searchName = binding.editTextSearchName.text.toString()
        val searchSurname = binding.editTextSearchSurname.text.toString()

        val listSearchResult =
            if (binding.editTextSearchName.text.isNotEmpty() && binding.editTextSearchSurname.text.isNotEmpty()) {
                contacts.filter { it.name.contains(searchName) }
                    .filter { it.surname.contains(searchSurname) }
                    .toMutableList()
            } else {
                contacts.filter {
                    if (binding.editTextSearchName.text.isNotEmpty()) {
                        it.name.contains(searchName)
                    } else {
                        it.surname.contains(searchSurname)
                    }
                }.toMutableList()
            }

        initList(listSearchResult)
    }

    private fun initList(list: MutableList<Contact>) {
        contactAdapter = ContactAdapter(list, { id -> openDetailFragment(id) },
            { id -> showDeleteDialog(id, list) })
        with(binding.contactList) {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun deleteContact(id: Long, list: MutableList<Contact>) {
        list.removeAt(id.toInt())
        contactAdapter?.updateContact(list)
        contactAdapter?.notifyItemRemoved(id.toInt())
    }

    private fun showDeleteDialog(id: Long, list: MutableList<Contact>) {
        deleteDialog = AlertDialog.Builder(requireContext())
            .setMessage(R.string.delete_this_contact)
            .setPositiveButton("OK") { _, _ -> deleteContact(id, list) }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun openDetailFragment(id: Long) {
        if (isTableMode()) {
            (activity as Navigator).navigateToTabletDetail(
                DetailFragment.newInstance(id),
                "detailFragment"
            )
        } else {
            (activity as Navigator).navigateTo(DetailFragment.newInstance(id), "detailFragment")
        }
    }

    private fun isTableMode() = context?.resources?.configuration?.smallestScreenWidthDp!! >= 600
}