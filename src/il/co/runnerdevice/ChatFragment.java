package il.co.runnerdevice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

	//	return (LinearLayout) inflater.inflate(R.layout.tab1, container, false);
		  View v = inflater.inflate(R.layout.chat_fragment, container, false);
		   TextView txt=(TextView)v.findViewById(R.id.chatView);//
		    
		
		 txt.setText("lior chat_fragment");
		 
		    //	return (LinearLayout) inflater.inflate(R.layout.tab2, container, false);
		    return  (LinearLayout)v;
	}

}
