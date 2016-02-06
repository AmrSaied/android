package com.example.amrsaid.isksecurity.adater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.amrsaid.isksecurity.R;
import com.example.amrsaid.isksecurity.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CustomUserAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Movie> movieItems;
//	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomUserAdapter(Activity activity, List<Movie> movieItems) {
		this.activity = activity;
		this.movieItems = movieItems;
		inflater = LayoutInflater.from(activity);

	}

	@Override
	public int getCount() {
		return movieItems.size();
	}

	@Override
	public Object getItem(int location) {
		return movieItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row_log, null);

//		if (imageLoader == null)
//			imageLoader = AppController.getInstance().getImageLoader();
		CircleImageView thumbNail = (CircleImageView) convertView
				.findViewById(R.id.thumbnail);
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView rating = (TextView) convertView.findViewById(R.id.rating);
		TextView genre = (TextView) convertView.findViewById(R.id.genre);


		// getting movie data for the row
		Movie m = movieItems.get(position);


//		// thumbnail image
		Picasso.with(activity)
				.load(m.getThumbnailUrl())
				.into(thumbNail);
//	thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

		// title
		title.setText(m.getTitle());

		// rating
		rating.setText("Status: " + String.valueOf(m.getRating()));

		// genre
		String genreStr = "";

		genre.setText("Last Enter :"+ m.getGenre());


		return convertView;
	}

}