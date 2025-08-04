package com.example.lixinewyear.framework.base.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.lixinewyear.R
import com.example.lixinewyear.databinding.ItemLoadMoreBinding
import com.example.lixinewyear.presentation.custom.LoadingView

abstract class BaseAdapter<T, VB : ViewBinding>(val context: Context) :
    RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    companion object {
        private const val TYPE_PROGRESS_LOAD_MORE = 0x0001
        const val TYPE_ITEM = 0x0002
    }

    open var mIsRegisterLoadMore: Boolean = false
    var mDataList = ArrayList<T>()

    private var mEndlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener? = null
    open var mIsReachEnd: Boolean = false
    var mRecycleView: RecyclerView? = null

    protected abstract fun setLayout(viewType: Int): (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected abstract fun setViewHolder(binding: VB, viewType: Int): BaseRecyclerViewHolder

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder {
        return if (viewType == TYPE_PROGRESS_LOAD_MORE) {
            LoadMoreViewHolder(setLayoutLoadMore(parent))
        } else {
            val binding = setLayout(viewType).invoke(LayoutInflater.from(context), parent, false)
            setViewHolder(binding, viewType)
        }
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder,
        position: Int
    ) {
        (holder as? BaseAdapter<*, *>.LoadMoreViewHolder)?.showHideLoadMore(!mIsReachEnd && (mDataList.isNotEmpty()))
    }

    override fun getItemCount(): Int = if (mIsRegisterLoadMore) calTotal() + 1 else calTotal()

    open fun calTotal() = mDataList.size


    open fun setLayoutLoadMore(viewGroup: ViewGroup): ViewBinding =
        ItemLoadMoreBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

    open fun registerLoadMore(
        layoutManager: RecyclerView.LayoutManager,
        recyclerView: RecyclerView, loadMoreCallback: () -> Unit
    ) {
        mRecycleView = recyclerView
        mIsRegisterLoadMore = true
        mIsReachEnd = false

        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (getItemViewType(position)) {
                        TYPE_PROGRESS_LOAD_MORE -> layoutManager.spanCount
                        else -> 1
                    }
                }
            }
        }

        this.mEndlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore() {
                    if (!mIsReachEnd) {
                        loadMoreCallback()
                    }
                }
            }

        recyclerView.addOnScrollListener(this.mEndlessRecyclerViewScrollListener!!)
    }

    fun isReachEnd() = mIsReachEnd
    open fun onReachEnd() {
        mIsReachEnd = true
        notifyItemChanged(getBottomItemPosition())
    }

    fun openReachEnd() {
        mEndlessRecyclerViewScrollListener?.resetState()
        mIsReachEnd = false
        notifyItemChanged(getBottomItemPosition())
    }

    open fun getBottomItemPosition(): Int {
        return itemCount - 1
    }

    open fun addListItem(itemList: List<T>) {
        val start = itemCount
        mDataList.addAll(itemList)
        this.notifyItemRangeInserted(start, itemList.size)
    }

    open fun addListItemAt(itemList: List<T>, position: Int) {
        mDataList.addAll(position, itemList)
        this.notifyItemRangeInserted(position, itemList.size)
    }

    open fun addItem(item: T) {
        mDataList.add(item)
        this.notifyItemInserted(itemCount)
    }

    open fun addItemAt(item: T, position: Int) {
        mDataList.add(position, item)
        this.notifyItemInserted(position)
    }

    open fun removeItemAt(position: Int) {
        mDataList.removeAt(position)
        this.notifyItemRemoved(position)
    }


    open fun removeItemsRange(start: Int, end: Int? = null) {
        if (start > 0 && start < mDataList.size) {
            val jumpEnd = ((end ?: (mDataList.size - 1)))
            for (i in jumpEnd downTo start) {
                mDataList.removeAt(i)
            }
            this.notifyItemRangeRemoved(start, start + jumpEnd)
        }
    }

    open fun getItem(position: Int) = mDataList[position]

    @SuppressLint("NotifyDataSetChanged")
    fun clearAll() {
        mDataList.clear()
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun setDataList(itemList: List<T>) {
        val clone = ArrayList(itemList)
        mDataList.clear()
        mDataList.addAll(clone)
        this.notifyDataSetChanged()
    }

    fun getData() = mDataList


    inner class LoadMoreViewHolder(binding: ViewBinding) : BaseRecyclerViewHolder(binding) {
        val mLayoutProgress: LoadingView = itemView.findViewById(R.id.layoutProgress)
        fun showHideLoadMore(isShow: Boolean) {
            if (isShow) {
                mLayoutProgress.show()
            } else {
                mLayoutProgress.disappear()
            }
        }
    }
}