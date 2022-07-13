package developer.abdulaziz.myroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import developer.abdulaziz.myroom.Adapters.MyAdapter
import developer.abdulaziz.myroom.RoomDatabase.AppDatabase
import developer.abdulaziz.myroom.RoomDatabase.Entity.User
import developer.abdulaziz.myroom.databinding.ActivityMainBinding
import developer.abdulaziz.myroom.databinding.Item2Binding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var list: ArrayList<User>
    private lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            appDatabase = AppDatabase.getInstance(root.context)
            list = ArrayList()
            list.addAll(appDatabase.myDao().read())
            save.setOnClickListener {
                if (name.text.toString().isNotEmpty()) {
                    val user = User(name.text.toString())
                    appDatabase.myDao().create(user)
                    list.add(user)
                    myAdapter.notifyItemInserted(list.indexOf(user))
                } else Toast.makeText(root.context, "Empty", Toast.LENGTH_SHORT).show()
            }
            myAdapter = MyAdapter(list, object : MyAdapter.MyClick {
                override fun onClick(user: User, position: Int) {
                    val alertDialog = AlertDialog.Builder(root.context).create()
                    val item = Item2Binding.inflate(layoutInflater)
                    item.name.setText(user.name)
                    item.save.setOnClickListener {
                        if (item.name.text.toString().isNotEmpty()) {
                            user.name = item.name.text.toString()
                            appDatabase.myDao().update(user)
                            list[position] = user
                            myAdapter.notifyItemChanged(position)
                            alertDialog.cancel()
                        } else Toast.makeText(root.context, "Empty", Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.setView(item.root)
                    alertDialog.show()
                }

                override fun onLongClick(user: User, position: Int) {
                    appDatabase.myDao().delete(user)
                    list.remove(user)
                    myAdapter.notifyItemRemoved(position)
                }
            })
            rv.adapter = myAdapter
        }
    }
}