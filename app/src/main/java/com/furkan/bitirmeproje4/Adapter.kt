package com.furkan.bitirmeproje4

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.username.text = user.username
        holder.phone.text = user.phone
        holder.tolerans.text = user.tolerans
        holder.sifre.text = user.password
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.tvUsername)
        val phone: TextView = itemView.findViewById(R.id.tvPhone)
        val tolerans: TextView = itemView.findViewById(R.id.tvTolerans)
        val sifre: TextView = itemView.findViewById(R.id.tvSifre)
    }
}
class PortfolioAdapter(var portfolios: List<Portfolio>) : RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.portfolio_item, parent, false)
        return PortfolioViewHolder(view)
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        val portfolio = portfolios[position]
        holder.bind(portfolio)
    }

    override fun getItemCount(): Int {
        return portfolios.size
    }

    inner class PortfolioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val portfolioNameTextView: TextView = itemView.findViewById(R.id.portfolioNameTextView)
        private val portfolioInfoTextView: TextView = itemView.findViewById(R.id.txtPorfoy)
        private val portfolioLevelTextView: TextView = itemView.findViewById(R.id.txtRiskLevel)

        fun bind(portfolio: Portfolio) {
            portfolioNameTextView.text = portfolio.name
            portfolioInfoTextView.text = portfolio.assetAllocation
            portfolioLevelTextView.text = portfolio.riskLevel
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun updatePortfolios(newPortfolios: List<Portfolio>) {
        portfolios = newPortfolios
        notifyDataSetChanged()
    }
}



