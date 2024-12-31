package com.mechadev.indirim.aktuel.urunler.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mechadev.indirim.aktuel.urunler.adapter.ShoppingListAddItemAdapter
import com.mechadev.indirim.aktuel.urunler.databinding.ShoppingListBottomSheetBinding
import com.mechadev.indirim.aktuel.urunler.model.ShoppingItemModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class ShoppingListBottomSheet : BottomSheetDialogFragment() {
    private var _binding: ShoppingListBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val shoppingListViewModel: ShoppingListViewModel by viewModels()
    private var shoppingListId: Int = 0

    // private val shoppingListViewModel: ShoppingListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShoppingListBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // BottomSheet arka planını dimlemeyi sağla
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        shoppingListViewModel.getOrCreateShoppingList(currentDate)

        shoppingListViewModel.shoppingModel.observe(viewLifecycleOwner) {
            binding.titleTextView.text = it.dateText

            shoppingListId = it.id

            shoppingListViewModel.fetchShoppingItems(shoppingListId)
        }

        // RecyclerView için LayoutManager belirliyoruz
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemAdapter = ShoppingListAddItemAdapter { shoppingItemModel: ShoppingItemModel ->
            shoppingListViewModel.deleteShoppingItem(shoppingItemModel)
        }
        binding.itemRecyclerView.adapter = itemAdapter

        shoppingListViewModel.shoppingItemList.observe(viewLifecycleOwner) {
            itemAdapter.setItemList(it)
        }

        binding.addButton.setOnClickListener {
            if (binding.commentInput.text.toString().isNotEmpty()) {
                shoppingListViewModel.addShoppingItem(
                    shoppingListId,
                    binding.commentInput.text.toString()
                )
                shoppingListViewModel.fetchShoppingItems(shoppingListId)

                binding.commentInput.text.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
