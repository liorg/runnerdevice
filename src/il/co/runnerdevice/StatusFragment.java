package il.co.runnerdevice; 

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatusFragment extends Fragment  implements OnClickListener{
	ShipItemView shipItem;
	SessionManager session;
	//public Tab2Fragment(ShipItemView shipItemView,SessionManager sessionManager)
	//{
	//	super();
		//shipItem=shipItemView;
		//session=sessionManager;
	//}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	    View v = inflater.inflate(R.layout.status_fragment, container, false);
	    Button button=(Button)v.findViewById(R.id.btn_frag2);//
	   TextView txt=(TextView)v.findViewById(R.id.textView2);//
	    
	 Bundle args=   getArguments();  
	 String status=args.getString("status", "");
	 txt.setText(status);
	 
	    button.setOnClickListener(this);
	    //	return (LinearLayout) inflater.inflate(R.layout.tab2, container, false);
	    return  (LinearLayout)v;
	}
	
	
	
	public void onClick(View view) {
	    MyShip_Fragment fragment2 = new MyShip_Fragment();
	    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
	    FragmentTransaction fragmentTransaction =        fragmentManager.beginTransaction();
	    fragmentTransaction.replace(R.id.frame_container, fragment2);
	    fragmentTransaction.addToBackStack(null);
	    fragmentTransaction.commit();
	}

}
