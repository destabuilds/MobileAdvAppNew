package com.example.mobileadvappnew.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobileadvappnew.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddToCart.setOnClickListener {
            val name = binding.edtProductName.text.toString()
            val qtyString = binding.edtQuantity.text.toString()
            val qty = if (qtyString.isNotEmpty()) qtyString.toInt() else 1

            // Mengirimkan argument ke CartFragment lewat SafeArgs
            val action = ProductDetailFragmentDirections
                .actionProductDetailFragmentToCartFragment(
                    productName = name,
                    quantity = qty
                )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}