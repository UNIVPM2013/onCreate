package it.univpm.opencity;

import java.util.ArrayList;

import it.univpm.opencity.dummy.OrdiniContent.OrdiniItem;
import it.univpm.opencity.dummy.SondaggiContent.DummyItem;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * An activity representing a list of Ordini. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link OrdineDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link OrdineListFragment} and the item details (if present) is a
 * {@link OrdineDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link OrdineListFragment.Callbacks} interface to listen for item selections.
 */
public class OrdineListActivity extends FragmentActivity implements
		OrdineListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ordine_list);

		 new GetOrdiniTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			
		
		if (findViewById(R.id.ordine_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((OrdineListFragment) getSupportFragmentManager().findFragmentById(
					R.id.ordine_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link OrdineListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(OrdineDetailFragment.ARG_ITEM_ID, id);
			OrdineDetailFragment fragment = new OrdineDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.ordine_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, OrdineDetailActivity.class);
			detailIntent.putExtra(OrdineDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	
	private class GetOrdiniTask extends AsyncTask<Void,Void,ArrayList<OrdiniItem>> {
		Context mContext;
		
		public ArrayList<OrdiniItem> ordini = null;		
		
		public GetOrdiniTask(Context context) {
		    super();
		    mContext = context;
		}
		
		
		
	   


	@Override
	protected ArrayList<OrdiniItem> doInBackground(Void... params) {
		 try {

		    	ordini = OrdiniData.getOrdini();
		        
		    }catch (IllegalArgumentException e2) {
		    // Error message to post in the log 
		    }
			return ordini;
	}
	
	
	@Override
	   protected void onPostExecute(ArrayList<OrdiniItem> ordini) {
	       // Set activity indicator visibility to "gone"
		OrdineListFragment.refreshListAdapter();
		    
	   }
	
	}
}
