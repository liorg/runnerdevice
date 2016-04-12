package il.co.runnerdevice.Tutorial;

import il.co.runnerdevice.R;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import il.co.runnerdevice.R;

public class ShipListAdaptor extends BaseAdapter {

	Context context;
	List<ShipItemView> rowItems;

	ShipListAdaptor(Context context, List<ShipItemView> rowItems) {
		this.context = context;
		this.rowItems = rowItems;
	}

	public void updateAdapter(List<ShipItemView> arrylst) {
		this.rowItems = arrylst;

		// and call notifyDataSetChanged
		notifyDataSetChanged();
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
		ImageView profile_pic;
		TextView member_name;
		TextView status;
		TextView contactType;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		holder = new ViewHolder();
		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.ship_item, null);

			holder.member_name = (TextView) convertView
					.findViewById(R.id.member_name);
			holder.profile_pic = (ImageView) convertView
					.findViewById(R.id.profile_pic);
			holder.status = (TextView) convertView.findViewById(R.id.status);
			holder.contactType = (TextView) convertView
					.findViewById(R.id.contact_type);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ShipItemView row_pos = rowItems.get(position);
		// http://stackoverflow.com/questions/29489978/android-set-the-source-of-an-imageview-inside-a-custom-listview-adapter-from-t
		// hold url
		holder.profile_pic.setImageResource(row_pos.getProfile_pic_id());
		holder.member_name.setText(row_pos.getMember_name());
		holder.status.setText(row_pos.getStatus());
		holder.contactType.setText(row_pos.getContactType());

		return convertView;
	}
}
