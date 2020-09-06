package com.rutulkotak.githubdemo.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rutulkotak.githubdemo.R
import com.rutulkotak.githubdemo.data.model.GitHubRepo
import kotlinx.android.synthetic.main.item_layout.view.*

class GitHubRepoListAdapter(
    private var gitHubRepos: List<GitHubRepo>
): RecyclerView.Adapter<GitHubRepoListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(gitHubRepo: GitHubRepo) {
            itemView.textViewTitle.text = gitHubRepo.name
            itemView.textViewDescription.text = gitHubRepo.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_layout, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(gitHubRepos[position])

    override fun getItemCount() = gitHubRepos.size

    fun refresh(newData: List<GitHubRepo>) {
        gitHubRepos = newData
        notifyDataSetChanged()
    }

}