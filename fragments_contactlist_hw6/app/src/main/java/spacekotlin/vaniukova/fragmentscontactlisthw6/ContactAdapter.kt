package spacekotlin.vaniukova.fragmentscontactlisthw6

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ContactAdapter(
    private val onItemClicked: (id: Long) -> Unit,
    private val onItemLongClicked: (id: Long) -> Unit
) : RecyclerView.Adapter<ContactAdapter.Holder>() {

    private val differ = AsyncListDiffer<Contact>(this, ContactDiffUtilCallBack())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_contact), onItemClicked, onItemLongClicked)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val contact = differ.currentList[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun updateContact(newContacts: List<Contact>) {
        differ.submitList(newContacts)
    }

    class ContactDiffUtilCallBack : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }

    class Holder(
        view: View,
        onItemClicked: (id: Long) -> Unit,
        onItemLongClicked: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tvName)
        private val tvSurname: TextView = view.findViewById(R.id.tvSurname)
        private val tvPhone: TextView = view.findViewById(R.id.tvPhone)
        private val imageViewAvatar: ImageView = view.findViewById(R.id.imageViewAvatar)
        private var currentId: Long? = null

        init {
            view.setOnClickListener {
                currentId?.let { onItemClicked(it) }
            }
        }

        init {
            view.setOnLongClickListener {
                currentId?.let { onItemLongClicked(it) }
                return@setOnLongClickListener true
            }
        }

        fun bind(contact: Contact) {
            currentId = contact.id
            tvName.text = contact.name
            tvSurname.text = contact.surname
            tvPhone.text = contact.phoneNumber

            Glide.with(itemView)
                .load(contact.avatarLink)
                .error(R.drawable.cat)
                .into(imageViewAvatar)
        }
    }
}