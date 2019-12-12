package com.klimaatmobiel.ui

import android.graphics.Color
import android.util.TypedValue
import android.opengl.Visibility
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.marginLeft
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.projecten3android.R
import com.klimaatmobiel.domain.OrderItem
import com.klimaatmobiel.domain.Product
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import com.klimaatmobiel.ui.adapters.OrderPreviewListAdapter
import com.klimaatmobiel.ui.adapters.ProductListAdapter
import timber.log.Timber


@BindingAdapter("listDataProducts")
fun listDataProductsBinding(recyclerView: RecyclerView, data: List<Product>) {
    val adapter = recyclerView.adapter as ProductListAdapter
}
@BindingAdapter("listDataOrderPreview")
fun listDataOrderPreviewBinding(recyclerView: RecyclerView, data: List<OrderItem>?) {
    val adapter = recyclerView.adapter as OrderPreviewListAdapter
    adapter.submitList(if (data != null) ArrayList(data!!) else null)
}

@BindingAdapter("projectBudgetBinding")
fun projectBudgetBinding(txtView: TextView,  budget: Double) {
    txtView.text = "Winkelmandje - €" + budget.toString() +" budget"
}

@BindingAdapter("groupSpentBinding")
fun groupSpentBinding(txtView: TextView,  totalOrderPrice: Double) {
    txtView.text = "Totaal: €" + totalOrderPrice.toString()
}

@BindingAdapter("productNameBinding")
fun productNameBinding(txtView: TextView, name: String?) {
 txtView.text = name
}

@BindingAdapter("productNameAndCategoryBinding")
fun productNameAndCategoryBinding(txtView: TextView, product: Product?) {
    if(product != null) txtView.text = "${product.productName} (${product.category?.categoryName})"
}

@BindingAdapter("productPriceBinding")
fun productPriceBinding(txtView: TextView, price: Double) {
    txtView.text = "Prijs: €" + price.toString()
}


@BindingAdapter("orderItemAmountBinding")
fun orderItemAmountBinding(txtView: TextView, amount: Int) {
    txtView.text = amount.toString()
}

@BindingAdapter("btnCheckoutOrderBinding")
fun btnCheckoutOrderBinding(btn: Button, totalPrice: Double) {
    btn.text = "Voltooi bestelling van €" + totalPrice.toString()
}

@BindingAdapter("orderItemTotalPriceBinding")
fun orderItemTotalPriceBinding(txtView: TextView, oi: OrderItem) {
    txtView.text = "€" + (oi.amount * oi.product!!.price).toString()
}

/**
 * Show the appropriate amount of flowers and dots to visualize the climate score of the project.
 *
 * @param parent The [LinearLayout] that holds the climate score.
 * @param score The score of the project.
 */
@BindingAdapter("orderTotalScoreBinding")
fun orderTotalScoreBinding(parent: LinearLayout, score: Double) {
    parent.removeAllViews()

    val lp20 = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)

    lp20.setMargins(8, 0, 8, 8)

    val tv = TextView(parent.context)
    tv.text = ("Klimaatscore: ")
    tv.setTextColor(Color.parseColor("#C3004A"))
    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,40.toFloat())

    parent.addView(tv)

    val aantalDraws = (score/2).toInt()

    for (i in 1..aantalDraws) {
        val iv = ImageView(parent.context)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)

        lp.setMargins(8, 0, 8, 0)

        when(i) {
            1 -> iv.setBackgroundResource(R.drawable.score_bloem)
            in 2..3 -> iv.setBackgroundResource(R.drawable.score_bloem_oranje)
            else -> iv.setBackgroundResource(R.drawable.score_bloem_groen)
        }
        parent.addView(iv, lp)
    }

    for (i in 1..(5-aantalDraws)) {
        val iv = ImageView(parent.context)

        iv.setBackgroundResource(R.drawable.score_dot)

        parent.addView(iv, lp20)
    }
}

/**
 * Bind the categoryName to the [TextView]
 */
@BindingAdapter("categorieNameBinding")
fun categoryNameBinding(txtView: TextView, name: String) {
    txtView.text = name
}

/**
 * Convert imgUrl to a URI with the https scheme.
 * Use Glide to download the image display it in imgView or show a default image if the action fails.
 *
 * @param imgView The [ImageView] that will hold the image.
 * @param imgUrl The url of the image that should be displayed.
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_image))
            .into(imgView)
    }
}

/**
 * Show a spinner based on the API status.
 *
 * @param statusImageView The [ImageView] that will hold the animation.
 * @param status The curren [KlimaatMobielApiStatus].
 */
@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: KlimaatMobielApiStatus?) {
    if(status != null) {
        when (status) {
            KlimaatMobielApiStatus.LOADING -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.loading_animation)
            }
            KlimaatMobielApiStatus.DONE -> statusImageView.visibility = View.GONE
            KlimaatMobielApiStatus.ERROR -> statusImageView.visibility = View.GONE
        }
    }
}