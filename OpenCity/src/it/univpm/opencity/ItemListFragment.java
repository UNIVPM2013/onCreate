package it.univpm.opencity;

<<<<<<< HEAD
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
=======
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.Adapter;
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
<<<<<<< HEAD
=======
import android.widget.Toast;
>>>>>>> 92286ab773427861121001619e8409a157f4bca5

import it.univpm.opencity.dummy.SondaggiContent;
import it.univpm.opencity.dummy.SondaggiContent.DummyItem;

/**
 * A list fragment representing a list of Items. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link ItemDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ItemListFragment extends ListFragment {
	static ListAdapter la;
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(String id);
	}

<<<<<<< HEAD
	public static void refreshListAdapter() {
		((BaseAdapter) la).notifyDataSetChanged();
	}

=======
	public static void refreshListAdapter(){
		((BaseAdapter) la).notifyDataSetChanged();
	}
	
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemListFragment() {
<<<<<<< HEAD
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new GetSondaggioTask(getActivity())
		.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		la = new ArrayAdapter<SondaggiContent.DummyItem>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				SondaggiContent.ITEMS);

		setListAdapter(la);

		

		// setListAdapter(new
		// ArrayAdapter<SondaggiContent.DummyItem>(getActivity(),
		// android.R.layout.simple_list_item_activated_1,
		// android.R.id.text1, SondaggiContent.ITEMS));
=======
	}	
	
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 		

		la = new ArrayAdapter<SondaggiContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1, SondaggiContent.ITEMS);

        setListAdapter(la);
		
		
		//setListAdapter(new ArrayAdapter<SondaggiContent.DummyItem>(getActivity(),
			//	android.R.layout.simple_list_item_activated_1,
				//android.R.id.text1, SondaggiContent.ITEMS));
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		mCallbacks.onItemSelected(SondaggiContent.ITEMS.get(position).id);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
<<<<<<< HEAD

	private class GetSondaggioTask extends
			AsyncTask<Void, Void, ArrayList<DummyItem>> {
		Context mContext;

		public ArrayList<DummyItem> sondaggio = null;

		public GetSondaggioTask(Context context) {
			super();
			mContext = context;
		}

		@Override
		protected ArrayList<DummyItem> doInBackground(Void... params) {
			try {

				sondaggio = SondaggiData.getSondaggi();

			} catch (IllegalArgumentException e2) {
				// Error message to post in the log
			}
			return sondaggio;
		}

		@Override
		protected void onPostExecute(ArrayList<DummyItem> sondaggio) {
			// Set activity indicator visibility to "gone"
			ItemListFragment.refreshListAdapter();

		}

	}
=======
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
}
