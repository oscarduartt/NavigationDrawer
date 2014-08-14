package com.example.examplenavigationdrawer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private DrawerLayout drawerLayout;
	private ListView navList;
	private CharSequence mTitle;
	private ActionBarDrawerToggle drawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get current title
		mTitle = getTitle(); 
		this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		this.navList = (ListView) findViewById(R.id.left_drawer);
		
		// Carga el arreglo de opciones
		final String[] names = getResources().getStringArray(
				R.array.nav_options);
		
		//Se crea el adapter y se carga a la lista
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, names);
		navList.setAdapter(adapter);
		navList.setOnItemClickListener(new DrawerItemClickListener());
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_launcher, R.string.open_drawer,
				R.string.close_drawer) {
			
			/**
			 * Es llamado cuando el Drawer esta completamente cerrado.
			 */
			
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				// creates call to onPrepareOptionsMenu()
				supportInvalidateOptionsMenu();
			}

			/**
			 * Es llamado cuando el Drawer esta completamente abierto
			 */
			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle("Selecciona opción");
				// creates call to onPrepareOptionsMenu()
				supportInvalidateOptionsMenu();
			}
		};
		
		// Manda el drawerToggle para el escuchador.
		drawerLayout.setDrawerListener(drawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		//Sicronica el estado del toggle despues de que el onRestoreInstanceState ocurre.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		
		// Es llamado por el sistema cuando la configuracion del dispositivo cambia mientras la actividad esta corriendo.
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//Pasa el evento a ActionBarDrawerToggle, si regresa verdadero.
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		//Se infla el menu
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Es llamado siempre que llamamos a invalidateOptionsMenu()
	 
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		//Si el navigationDrawer esta abierto se enconden los items de accion relacionado con la vista de contenido.
		boolean drawerOpen = drawerLayout.isDrawerOpen(navList);
		menu.findItem(R.id.action_search).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		
		//Sacamos el texto de los recursos.
		mTitle = getResources().getStringArray(R.array.nav_options)[position];
		
		//Creamos un nuevo fragmento y mandamos el texto a mostrar en el.
		Fragment fragment = new MyFragment();
		Bundle args = new Bundle();
		args.putString(MyFragment.KEY_TEXT, mTitle.toString());
		fragment.setArguments(args);
		
		//Insertamos el fragmento o lo remplazamos en caso de que exista uno existente.
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		
		//Subrayamos el item seleccionado, actualizamos el titulo y cerramos el Drawer.
		navList.setItemChecked(position, true);
		getSupportActionBar().setTitle(mTitle);
		drawerLayout.closeDrawer(navList);
	}
}