package com.mehta.android.todo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehta.android.todo.model.ToDoData;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

	Context context;
	List<ToDoData> ToDoAllRecords;

	CustomAdapter(Context context, List<ToDoData> ToDoAllRecords) {
		this.context = context;
		this.ToDoAllRecords = ToDoAllRecords;
	}

	@Override
	public int getCount() {
		return ToDoAllRecords.size();
	}

	@Override
	public Object getItem(int position) {
		return ToDoAllRecords.get(position);
	}

	@Override
	public long getItemId(int position) {
		return ToDoAllRecords.indexOf(getItem(position));
	}

	/* private view holder class */
	private class ViewHolder {
		TextView toDoTitleDate;
		TextView toDoName;
		TextView toDoDescription;
		TextView toDoDate;
		ImageView toDoStatus;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);

			holder = new ViewHolder();

			holder.toDoTitleDate = (TextView) convertView
					.findViewById(R.id.toDoTitleDate);

			holder.toDoName = (TextView) convertView
					.findViewById(R.id.toDoName);

			holder.toDoDescription = (TextView) convertView
					.findViewById(R.id.toDoDescription);

			holder.toDoDate = (TextView) convertView
					.findViewById(R.id.toDoDate);

			holder.toDoStatus = (ImageView) convertView
					.findViewById(R.id.toDoStatus);



			ToDoData row_pos = ToDoAllRecords.get(position);


			holder.toDoTitleDate.setText(row_pos.getToDoDate());
			holder.toDoName.setText(row_pos.getToDoName());
			holder.toDoDescription.setText(row_pos.getToDoDescription());
			holder.toDoDate.setText(row_pos.getToDoDate());
			if(row_pos.getToDoStatus()==1)
				holder.toDoStatus.setImageResource(R.drawable.complete);
			else
				holder.toDoStatus.setImageResource(R.drawable.incomplete);


			convertView.setTag(holder);


		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

}
