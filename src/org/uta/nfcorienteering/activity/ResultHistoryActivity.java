package org.uta.nfcorienteering.activity;

import java.util.ArrayList;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Track;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_result_history);

		ArrayList<Track> data = initDummyData();

		ListView listView = (ListView) findViewById(R.id.histor_list_view);
		HistoryListAdapter adapter = new HistoryListAdapter(this);
		adapter.setData(data);

		listView.setAdapter(adapter);
	}

	private ArrayList<Track> initDummyData() {
		ArrayList<Track> data = new ArrayList<Track>();

		int dataAmount = 10;
		for (int i = 0; i < dataAmount; i++) {
			Track track = new Track();
			OrienteeringEvent event = new OrienteeringEvent();
			OrienteeringRecord record = new OrienteeringRecord();

			track.setDistance((i + 2) + " km");

			event.setEventName("Orienteering event" + (i + 1));
			event.setLocation("Location" + (i + 1));
			record.setFinishDate((i + 10) + " Nov 2014");

			track.setParentEvent(event);
			event.setRecord(record);

			data.add(track);
		}

		return data;
	}

	public class HistoryListAdapter extends BaseAdapter {
		private LayoutInflater inflater = null;

		private ArrayList<Track> data = null;

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

			holder.button.setOnClickListener(buttonClickLitsener);
			holder.button.setTag(record);
		}

		private ButtonClickLitsener buttonClickLitsener = new ButtonClickLitsener();

		public class ButtonClickLitsener implements OnClickListener {

			@Override
			public void onClick(View v) {
				OrienteeringRecord record = (OrienteeringRecord) v.getTag();
				Intent intent = new Intent(ResultHistoryActivity.this,
						TrackResultsActivity.class);
				intent.putExtra("record", record);
				intent.putExtra("HistoryMode", true);

				startActivity(intent);
			}
		}

		public ArrayList<Track> getData() {
			return data;
		}

		public void setData(ArrayList<Track> data) {
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
