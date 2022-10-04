package com.android.mismenu.core.util

import com.android.mismenu.features.domain.data.entities.CartEntity
import com.android.mismenu.features.domain.data.entities.WishlistEntity
import com.android.mismenu.features.domain.entities.Product
import com.android.mismenu.features.presentaion.viewmodel.CartViewModel
import com.android.mismenu.features.presentaion.viewmodel.HomeViewModel
import com.android.mismenu.features.presentaion.viewmodel.SearchViewModel
import com.android.mismenu.features.presentaion.viewmodel.WishlistViewModel

interface ClickListener {
    fun <T> onItemClickListener(item: T)

    fun <T>onClickViewInsideViewHolder(item: T)
}

//class onClickListener<T>(val clickListener: (obj: T) -> Unit) {
//    fun onClick(obj: T) = clickListener(obj)
//}

/* Click listener for wishlist adapter*/
class ClickListenerWishlistImp(private val viewModel: WishlistViewModel): ClickListener{
    override fun <T> onItemClickListener(item: T) {
        viewModel.itemSelected(item as WishlistEntity)
    }

    override fun <T> onClickViewInsideViewHolder(item: T) {
        viewModel.removeItemSelected(item as WishlistEntity)
    }
}

/* Click listener for product adapter*/
class ClickListenerNewArrivalImp (private val viewModel: HomeViewModel): ClickListener{
    override fun <T> onItemClickListener(item: T) {
        viewModel.productSelected(item as Product)
    }

    override fun <T> onClickViewInsideViewHolder(item: T) {
        viewModel.wishlistItemSelected(item as Product)
    }
}

class ClickListenerItemInCart(private val viewModel: CartViewModel): ClickListener{
    override fun <T> onItemClickListener(item: T) {
        viewModel.itemSelected(item as CartEntity)
    }

    override fun <T> onClickViewInsideViewHolder(item: T) {
        viewModel.removeItemSelected(item as CartEntity)
    }
}

class ClickListenerItemInSearch(private val viewModel: SearchViewModel): ClickListener{
    override fun <T> onItemClickListener(item: T) {
        viewModel.itemSelected(item as Product)
    }

    override fun <T> onClickViewInsideViewHolder(item: T) {
        TODO("Not yet implemented")
    }
}
