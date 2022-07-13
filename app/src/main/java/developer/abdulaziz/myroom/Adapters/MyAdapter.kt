package developer.abdulaziz.myroom.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import developer.abdulaziz.myroom.RoomDatabase.Entity.User
import developer.abdulaziz.myroom.databinding.ItemBinding

class MyAdapter(
    private val list: ArrayList<User>,
    private val myClick: MyClick
) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User, position: Int) {
            binding.name.text = user.name
            binding.root.setOnClickListener { myClick.onClick(user, position) }
            binding.root.setOnLongClickListener { myClick.onLongClick(user, position); true }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(hol: ViewHolder, pos: Int) = hol.onBind(list[pos], pos)
    override fun getItemCount(): Int = list.size
    interface MyClick {
        fun onClick(user: User, position: Int)
        fun onLongClick(user: User, position: Int)
    }
}