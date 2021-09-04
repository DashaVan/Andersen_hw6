package spacekotlin.vaniukova.fragmentscontactlisthw6

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private val contacts: List<Contact>,
    private val onItemClicked: (id: Long) -> Unit
) : RecyclerView.Adapter<ContactAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_contact), onItemClicked)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int = contacts.size

    class Holder(
        view: View,
        onItemClicked: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tvName)
        private val tvSurname: TextView = view.findViewById(R.id.tvSurname)
        private val tvPhone: TextView = view.findViewById(R.id.tvPhone)
        private var currentId: Long? = null

        init {
            view.setOnClickListener {
                currentId?.let { onItemClicked(it) }
            }
        }

        fun bind(contact: Contact) {
            currentId = contact.id
            tvName.text = contact.name
            tvSurname.text = contact.surname
            tvPhone.text = contact.phoneNumber
        }
    }
}