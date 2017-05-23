package android.ucsc.vaias.Adapter;

import android.ucsc.vaias.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;



public class ListViewAdapter extends BaseAdapter {
    private final ArrayList mData;

    public ListViewAdapter(Map<String, String> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        // Get the data item for this position
        Map.Entry<String, String> item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_adapter_item, parent, false);
        } else {
            result = convertView;
        }
        // Lookup view for data population
        ((TextView) result.findViewById(R.id.text1)).setText(item.getKey());
        ((TextView) result.findViewById(R.id.text2)).setText(item.getValue());

        return result;

    }
}

