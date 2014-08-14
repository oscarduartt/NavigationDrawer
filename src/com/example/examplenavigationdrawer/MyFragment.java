package com.example.examplenavigationdrawer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragment extends Fragment{
	
	public final static String KEY_TEXT = "key_text";
	private String mText;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mText = getArguments().getString(KEY_TEXT);
		
		View v = inflater.inflate(R.layout.fragment, container, false);
		return v;
	}

}
