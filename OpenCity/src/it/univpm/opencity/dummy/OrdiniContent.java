package it.univpm.opencity.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class OrdiniContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<OrdiniItem> ITEMS = new ArrayList<OrdiniItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, OrdiniItem> ITEM_MAP = new HashMap<String, OrdiniItem>();

	
	public static void addItem(OrdiniItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class OrdiniItem {
		public String id;
		public String content;

		public OrdiniItem(String id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return content;
		}
	}
}
