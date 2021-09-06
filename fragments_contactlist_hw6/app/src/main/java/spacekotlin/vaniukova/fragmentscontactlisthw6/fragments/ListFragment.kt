package spacekotlin.vaniukova.fragmentscontactlisthw6.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator
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

        if (savedInstanceState != null) {
            contacts = savedInstanceState.getParcelableArrayList<Contact>(KEY_COUNTER)
                ?: error("Unexpected state")
            initList()
        } else {
            initList()
        }

        binding.btnSearch.setOnClickListener {
            searchContact()
        }
        contactAdapter?.updateContact(contacts)
    }

    companion object {
        private const val KEY_COUNTER = "counter"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_COUNTER, contacts)
    }

    private fun searchContact() {
        val searchName = binding.editTextSearchName.text.toString()
        val searchSurname = binding.editTextSearchSurname.text.toString()

        val oldContacts = contacts

        if (binding.editTextSearchName.text.isNotEmpty() && binding.editTextSearchSurname.text.isNotEmpty()) {
           contacts = contacts.filter { it.name.contains(searchName) }
               .filter { it.surname.contains(searchSurname) }
               .toMutableList() as ArrayList<Contact>

        } else{
            contacts = contacts.filter {
                if (binding.editTextSearchName.text.isNotEmpty()) {
                    it.name.contains(searchName)
                } else {
                    it.surname.contains(searchSurname)
                }
            }.toMutableList() as ArrayList<Contact>
        }
        initList()
        contactAdapter?.updateContact(contacts)
        contacts = oldContacts
    }

    private fun initList() {
        contactAdapter = ContactAdapter({ id -> openDetailFragment(id) },
            { id -> showDeleteDialog(id) })
        with(binding.contactList) {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)

            if (itemDecorationCount == 0) {
                addItemDecoration(ItemDec(requireContext()))
                itemAnimator = FlipInTopXAnimator()
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
    }

    private fun deleteContact(idItem: Long) {
        contacts = contacts.filter{
            it.id != idItem
        } as ArrayList<Contact>
        contactAdapter?.updateContact(contacts)
    }

    private fun showDeleteDialog(id: Long) {
        deleteDialog = AlertDialog.Builder(requireContext())
            .setMessage(R.string.delete_this_contact)
            .setPositiveButton("OK") { _, _ -> deleteContact(id) }
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