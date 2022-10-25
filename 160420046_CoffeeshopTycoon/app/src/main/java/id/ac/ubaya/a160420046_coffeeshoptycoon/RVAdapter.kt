package id.ac.ubaya.a160420046_coffeeshoptycoon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.random_result_item.view.*

class RVAdapter (var mutableList: MutableList<RandomSold>): RecyclerView.Adapter<RVAdapter.Holder>() {
    class Holder (itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItem(randomSold: RandomSold) {
            var strCustomerNum = ""
            if (randomSold.customerNum > 0){
                strCustomerNum = randomSold.customerNum.toString() + " Customers"
            }
            else if (randomSold.customerNum == -1){
                strCustomerNum = "Out of Stock"
            }
            else{
                strCustomerNum = "No Customer"
            }
            var strHour = randomSold.time.toString() + " - " + strCustomerNum.toString()
            itemView.txtHour.setText(strHour.toString())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val x = LayoutInflater.from(parent.context).inflate(R.layout.random_result_item, parent, false)
        return Holder(x)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Holder(holder.itemView).bindItem(mutableList[position])
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}