package it.univpm.opencity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerAdapter extends ArrayAdapter<String> {
	public DrawerAdapter(Context context, int resource, int textViewResourceId,
			String[] objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		ctx=context;
	}

	private Context ctx;


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// Creiamo il riferimento all'holder
		DrawerHolder drawerHolder = null;
		// Se non presente una istanza della View la creiamo attraverso
		// inflating. Se già presente la riutilizziamo
		if (convertView == null) {
			// Otteniamo il riferimento alla View da parserizzare
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.drawer_list_item, null);
			// Creiamo un Holder associato
			drawerHolder = new DrawerHolder();

			// Assegnamo i riferimenti alle textView
			drawerHolder.title = (TextView) convertView
					.findViewById(R.id.title);
			drawerHolder.icon = (ImageView) convertView.findViewById(R.id.icon);

			// Lo assegnamo come Tag della View
			convertView.setTag(drawerHolder);
		} else {
			// La View esiste già per cui possiede già l'holder
			drawerHolder = (DrawerHolder) convertView.getTag();
		}

		// Otteniamo l'elemento i-esimo
		String titolo = (String)getItem(position);
		int icon_id = 0;
		switch (position) {
		case 0:
			icon_id = R.drawable.collections_view;
			break;
		case 1:
			icon_id = R.drawable.sondaggi;
			break;
		case 2:
			icon_id = R.drawable.segnalazioni;
			break;
		case 3:
			icon_id = R.drawable.map;
			break;
		}
		// Assegnamo i valori
		
		drawerHolder.title.setText(titolo);
		drawerHolder.icon.setImageResource(icon_id);
		return super.getView(position, convertView, parent);
	}

	private class DrawerHolder {
		TextView title;
		ImageView icon;
	}

}
