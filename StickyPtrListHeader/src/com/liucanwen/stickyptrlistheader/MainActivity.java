package com.liucanwen.stickyptrlistheader;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liucanwen.stickyptrlistheader.view.StickyListHeaderView;

public class MainActivity extends Activity
{
	private StickyListHeaderView stickyListHeaderView;
	
	private PullToRefreshListView listView;
	
	private ListViewAdapter adapter;
	
	private boolean isRefreshing = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeViews();
		
		initializeListeners();
	}

	private void initializeViews()
	{
		stickyListHeaderView = buildStickyListHeaderView(this);
		listView = (PullToRefreshListView) findViewById(R.id.listView1);
		listView.getRefreshableView().addHeaderView(stickyListHeaderView);
		adapter = new ListViewAdapter();
		listView.setAdapter(adapter);
	}
	
	private void initializeListeners()
	{
		listView.setOnScrollListener(new AbsListView.OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				view.refreshDrawableState();
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount)
			{
				//header View no scroll while ptr is refreshing
				if(!isRefreshing)
					stickyListHeaderView.onScroll(view, firstVisibleItem,
							visibleItemCount, totalItemCount);
			}
		});
		
		listView.setOnRefreshListener(new OnRefreshListener<ListView>()
		{
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView)
			{
				//refresh
				new GetDataTask().execute();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				Intent intent = new Intent(Intent.ACTION_VIEW);   
		        Uri uri = Uri.parse("https://github.com/kk-java/StickyPtrListHeader");   
		        intent.setData(uri);   
		        Intent chooseIntent = Intent.createChooser(intent, "view source");   
		        startActivity(chooseIntent);   
			}
		});
		
	}

	private class ListViewAdapter extends BaseAdapter
	{
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getLayoutInflater().inflate(
						android.R.layout.simple_list_item_1, null);
			}
			((TextView) convertView.findViewById(android.R.id.text1))
					.setText(getItem(position));
			return convertView;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public String getItem(int position)
		{
			return "view source item>" + position;
		}

		@Override
		public int getCount()
		{
			return 70;
		}
	}

	//Simulation data manipulation
	private class GetDataTask extends AsyncTask<Void, Void, String[]>
	{
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			isRefreshing = true;
		}
		
		@Override
		protected String[] doInBackground(Void... params)
		{
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result)
		{
			//change head
			stickyListHeaderView.updateView(getRandomResId());
			adapter.notifyDataSetChanged();

			listView.onRefreshComplete();

			super.onPostExecute(result);
			
			isRefreshing = false;
		}
	}

	//stick list head
	public StickyListHeaderView buildStickyListHeaderView(Context context)
	{
		StickyListHeaderView instance = new StickyListHeaderView(context);
		instance.onFinishInflate();
		return instance;
	}
	
	//get random image
	public int getRandomResId()
	{
		int[] resArr = {R.drawable.gz1, R.drawable.gz2, R.drawable.gz3, R.drawable.gz4, R.drawable.gz6, R.drawable.gz7, R.drawable.gz8, R.drawable.gz9, R.drawable.gz10};
		
		int random = new Random().nextInt(resArr.length);
		
		return resArr[random];
	}
}
