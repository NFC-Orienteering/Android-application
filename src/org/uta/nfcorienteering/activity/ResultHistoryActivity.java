package org.uta.nfcorienteering.activity;

import java.util.Collections;
import java.util.List;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.utility.LocalStorage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ResultHistoryActivity extends Activity {
	List<Track> data = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_result_history);

		readHistory();

		ListView listView = (ListView) findViewById(R.id.histor_list_view);
		HistoryListAdapter adapter = new HistoryListAdapter(this);
		adapter.setData(data);

		listView.setAdapter(adapter);
	}

	private void readHistory() {
		LocalStorage localStorage = new LocalStorage(this);
		data = localStorage.readOrienteeringHistory();
		
		if(null != data) {
			Collections.reverse(data);
		}
	}

	public class HistoryListAdapter extends BaseAdapter {
		private LayoutInflater inflater = null;

		private List<Track> data = null;

		public HistoryListAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			if (data == null) {
				return 0;
			}
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.result_list_item, null);
				holder = new ViewHolder();

				holder.eventName = (TextView) convertView
						.findViewById(R.id.result_item_event_name);
				holder.distance = (TextView) convertView
						.findViewById(R.id.result_item_distance);
				holder.location = (TextView) convertView
						.findViewById(R.id.result_item_location);
				holder.date = (TextView) convertView
						.findViewById(R.id.result_item_date);

				holder.button = (Button) convertView
						.findViewById(R.id.result_item_button);

				holder.button.setOnClickListener(buttonClickLitsener);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			adaptDataToView(data.get(position), holder);

			return convertView;
		}

		private void adaptDataToView(Track track, ViewHolder holder) {
			OrienteeringEvent event = track.getParentEvent();
			OrienteeringRecord record = event.getRecord();

			holder.eventName.setText(event.getEventName());
			holder.distance.setText("Track: " + track.getDistance());
			holder.location.setText("Location: " + event.getLocation());
			holder.date.setText("Complete date: " + record.getFinishDate());

			holder.button.setTag(track);
		}

		private ButtonClickLitsener buttonClickLitsener = new ButtonClickLitsener();

		public class ButtonClickLitsener implements OnClickListener {

			@Override
			public void onClick(View v) {
				Track track = (Track) v.getTag();
				Intent intent = new Intent(ResultHistoryActivity.this,
						TrackResultsActivity.class);
				intent.putExtra("track", track);
				intent.putExtra("isFromHistroytActivity", true);

				startActivity(intent);
			}
		}

		public List<Track> getData() {
			return data;
		}

		public void setData(List<Track> data) {
			this.data = data;
		}

	}

	public final class ViewHolder {
		public TextView eventName;
		public TextView distance;
		public TextView location;
		public TextView date;
		public Button button;
	}
}
