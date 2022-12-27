package uk.co.nickhu.phuzei.presentation.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.radiobutton.MaterialRadioButton
import uk.co.nickhu.phuzei.databinding.ItemCategoryBinding

/**
 * Created by Alireza Afkar on 16/9/2018AD.
 */
class SettingsAdapter(
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    private var category = ""
    private val items = listOf(
        "All",
        "Favorites",
        "Landscapes",
        "Receipts",
        "Cityscapes",
        "Landmarks",
        "Selfies",
        "People",
        "Pets",
        "Weddings",
        "Birthdays",
        "Documents",
        "Travel",
        "Animals",
        "Food",
        "Sport",
        "Night",
        "Performances",
        "Whiteboards",
        "Screenshots",
        "Utility",
        "Arts",
        "Crafts",
        "Fashion",
        "Houses",
        "Gardens",
        "Flowers",
        "Holidays"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.root.run {
                    text = this@with
                    isChecked = this@with == category
                    setOnClickListener { listener(this@with) }
                }
            }
        }
    }

    fun setCategory(category: String) {
        this.category = if (category.isEmpty()) {
            items.first()
        } else {
            category
        }
        notifyDataSetChanged()
    }
}
