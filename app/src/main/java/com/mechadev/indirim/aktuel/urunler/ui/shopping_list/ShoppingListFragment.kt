package com.mechadev.indirim.aktuel.urunler.ui.shopping_list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mechadev.indirim.aktuel.urunler.adapter.ShoppingListAdapter
import com.mechadev.indirim.aktuel.urunler.databinding.FragmentShoppingListBinding
import com.mechadev.indirim.aktuel.urunler.ui.bottomsheet.ShoppingListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class ShoppingListFragment : Fragment() {

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    private val shoppingListViewModel: ShoppingListViewModel by activityViewModels()
    private lateinit var shoppingListAdapter: ShoppingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { safeContext ->
            binding.shoppingListRecyclerView.layoutManager = LinearLayoutManager(safeContext)
        }
        shoppingListAdapter = ShoppingListAdapter(shoppingListViewModel, viewLifecycleOwner)

        binding.shoppingListRecyclerView.adapter = shoppingListAdapter

        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        shoppingListViewModel.getAllShoppingList(currentDate)

     //   shoppingListViewModel.getOrCreateShoppingList(currentDate)

        shoppingListViewModel.shoppingListModel.observe(viewLifecycleOwner) {
            shoppingListAdapter.updateList(it)
        }

    }
}