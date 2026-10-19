package com.example.mobileadvappnew.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobileadvappnew.databinding.FragmentCartBinding
import java.text.NumberFormat
import java.util.Locale

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    // Membaca argumen yang dikirim
    private val args: CartFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productName = args.productName
        val quantity = args.quantity
        val pricePerUnit = 12000000
        val totalPrice = pricePerUnit * quantity

        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)

        val priceString = numberFormat.format(pricePerUnit).replace(",00", "")
        val totalPriceString = numberFormat.format(totalPrice).replace(",00", "")

        binding.tvOrderDetails.text = """
            Nama Produk: $productName
            Jumlah: $quantity unit
            Harga per Unit: $priceString
            
            Total Estimasi Harga: $totalPriceString
        """.trimIndent()

        // Tombol Checkout dengan navigasi kembali ke CategoryFragment
        binding.btnCheckout.setOnClickListener {
            val action = CartFragmentDirections.actionCartFragmentToCategoryFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}