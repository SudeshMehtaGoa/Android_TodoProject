package com.mehta.android.todo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

	Context context;
	List<RowItem> rowItems;

	CustomAdapter(Context context, List<RowItem> rowItems) {
		this.context = context;
		this.rowItems = rowItems;
	}

	@Override
	public int getCount() {
		return rowItems.size();
	}

	@Override
	public Object getItem(int position) {
		return rowItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItems.indexOf(getItem(position));
	}

	/* private view holder class */
	private class ViewHolder {
		TextView todo_Date;
		TextView todo_Name;
		TextView todo_Description;
		TextView todo_Date_2;
		ImageView todo_Status;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);

			holder = new ViewHolder();

			holder.todo_Date = (TextView) convertView
					.findViewById(R.id.todo_Date);

			holder.todo_Name = (TextView) convertView
					.findViewById(R.id.todo_Name);

			holder.todo_Description = (TextView) convertView
					.findViewById(R.id.todo_Description);

			holder.todo_Date_2 = (TextView) convertView
					.findViewById(R.id.todo_Date_2);

			holder.todo_Status = (ImageView) convertView
					.findViewById(R.id.todo_Status);



			RowItem row_pos = rowItems.get(position);


			holder.todo_Date.setText(row_pos.gettodo_Date());
			holder.todo_Name.setText(row_pos.gettodo_Name());
			holder.todo_Description.setText(row_pos.gettodo_Description());
			holder.todo_Date_2.setText(row_pos.gettodo_Date());
			holder.todo_Status.setImageResource(row_pos.gettodo_Status());


			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

}
