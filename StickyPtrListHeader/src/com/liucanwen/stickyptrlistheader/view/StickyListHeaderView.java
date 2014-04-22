package com.liucanwen.stickyptrlistheader.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liucanwen.stickyptrlistheader.R;

/**
 * @author ck (liucanwen517@gmail.com)
 * update at 2014.04.23
 */
public class StickyListHeaderView extends LinearLayout implements
		AbsListView.OnScrollListener
{

	private boolean mAlreadyInflated = false;

	private View imageView1;

	public StickyListHeaderView(Context context)
	{
		super(context);
	}
	
	@Override
	public void onFinishInflate()
	{
		if (!mAlreadyInflated)
		{
			mAlreadyInflated = true;
			inflate(getContext(), R.layout.view_sticky_list_header, this);
			imageView1 = findViewById(R.id.imageView1);
		}
		super.onFinishInflate();
	}
	
	public void updateView(int resId)
	{
		((ImageView)imageView1).setImageResource(resId);
	}

	@SuppressLint("NewApi")
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{
		View localView = view.getChildAt(0);

		if (localView == this || firstVisibleItem == 0)
		{
			int i = (int) (-localView.getTop() / 1.5f);
			this.imageView1.setTranslationY(i);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
	}

}
