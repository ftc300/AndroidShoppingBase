package com.shoping.mall.study.imagelettericon;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shoping.mall.R;

public class ImageLetterIconActivity extends Activity {
	
    private static final String[] desuNoto = {
            "Alane Avey", "Belen Brewster", "Brandon Brochu", "Carli Carrol", "Della Delrio",
            "Esther Echavarria", "Etha Edinger", "Felipe Flecha", "Ilse Island", "Kecia Keltz",
            "Lourie Lucas", "Lucille Leachman", "Mandi Mcqueeney", "Murray Matchett", "Nadia Nero",
            "Nannie Nipp", "Ozella Otis", "Pauletta Poehler", "Roderick Rippy", "Sherril Sager",
            "Taneka Tenorio", "Treena Trentham", "Ulrike Uhlman", "Virgina Viau", "Willis Wysocki"
    };
    private static final String[] countries = {
            "Albania", "Australia", "Belgium", "Canada", "China", "Dominica", "Egypt", "Estonia",
            "Finland", "France", "Germany", "Honduras", "Italy", "Japan", "Madagascar", "Netherlands",
            "Norway", "Panama", "Portugal", "Romania", "Russia", "Slovakia", "Vatican", "Zimbabwe"
    };
    private static final int CONTACTS = 0;
    private static final int COUNTRIES = 1;
    private static final int ALTERNATECOUNTRIES = 2;
    private static final int ALTERNATECONTACTS = 3;
    private static final Random RANDOM = new Random();

    private ListView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_letter_main);
        recyclerView = (ListView) findViewById(R.id.recyclerview);
        setupRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.image_letter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contacts:
                setContactsAdapter(desuNoto);
                return true;
            case R.id.countries:
                setCountriesAdapter(countries);
                return true;
            case R.id.alternate_countries:
                setAlternateCountriesAdapter(countries);
                return true;
            case R.id.alternate_contacts:
                setAlternateContactsAdapter(countries);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView() {
        setContactsAdapter(desuNoto);
    }

    private void setContactsAdapter(String[] array) {
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(this, Arrays.asList(array), CONTACTS));
    }

    private void setCountriesAdapter(String[] array) {
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(this, Arrays.asList(array), COUNTRIES));
    }


    private void setAlternateCountriesAdapter(String[] array) {
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(this, Arrays.asList(array), ALTERNATECOUNTRIES));
    }

    private void setAlternateContactsAdapter(String[] array) {
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(this, Arrays.asList(array), ALTERNATECONTACTS));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends BaseAdapter {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<String> mValues;
        private int[] mMaterialColors;
        private int[] mImageResource;
        private int mType;

        public SimpleStringRecyclerViewAdapter(Context context, List<String> items, int type) {
          // context.getTheme().resolveAttribute(R.attr.selectable_ItemBackground, mTypedValue, true);
            mMaterialColors = context.getResources().getIntArray(R.array.colors);
            mImageResource = new int[]{R.drawable.gran, R.drawable.john, R.drawable.mechanic, R.drawable.ruth,
                    R.drawable.stefan, R.drawable.teacher, R.drawable.turtle, R.drawable.walter, R.drawable.yeats};
            mBackground = mTypedValue.resourceId;
            mValues = items;
            mType = type;
        }


        public static class ViewHolder{
            public final View mView;
            public final MaterialLetterIcon mIcon;
            public final TextView mTextView;
            public String mBoundString;

            public ViewHolder(View view) {
                mView = view;
                mIcon = (MaterialLetterIcon) view.findViewById(R.id.icon);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

		@Override
		public int getCount() {
			return mValues.size();
		}

		@Override
		public Object getItem(int position) {
			return mValues.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image_letter, parent, false);
				convertView.setBackgroundResource(mBackground);
			} 
	            ViewHolder holder = new ViewHolder(convertView);
	            switch (mType) {
                case CONTACTS:
                    holder.mIcon.setInitials(true);
                    holder.mIcon.setInitialsNumber(2);
                    holder.mIcon.setLetterSize(18);
                    holder.mIcon.setLetter(mValues.get(position));
                    holder.mIcon.setShapeType(MaterialLetterIcon.SHAPE_RECT);
                    holder.mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
                    break;
                case COUNTRIES:
                    holder.mIcon.setLettersNumber(3);
                    holder.mIcon.setLetterSize(16);
                    holder.mIcon.setShapeType(MaterialLetterIcon.SHAPE_CIRCLE);
                    holder.mIcon.setLetter(mValues.get(position));
                    holder.mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
                    break;
                case ALTERNATECOUNTRIES:
                    if (position % 2 == 0) {
                        holder.mIcon.setShapeType(MaterialLetterIcon.SHAPE_NORMAL);
                        holder.mIcon.setImageResource(mImageResource[RANDOM.nextInt(mImageResource.length)]);
                    } else {
                        holder.mIcon.setShapeType(MaterialLetterIcon.SHAPE_CIRCLE);
                        holder.mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
                        holder.mIcon.setLetter(mValues.get(position));
                        holder.mIcon.setInitials(true);
                        holder.mIcon.setInitialsNumber(2);
                        holder.mIcon.setLetterSize(18);
                    }
                    break;
                case ALTERNATECONTACTS:
                    if (position % 2 == 0) {
                        holder.mIcon.setShapeType(MaterialLetterIcon.SHAPE_NORMAL);
                        holder.mIcon.setOval(false);
                        holder.mIcon.setImageResource(mImageResource[RANDOM.nextInt(mImageResource.length)]);
                    } else {
                        holder.mIcon.setShapeType(MaterialLetterIcon.SHAPE_RECT);
                        holder.mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
                        holder.mIcon.setLetter(mValues.get(position));
                        holder.mIcon.setInitials(true);
                        holder.mIcon.setInitialsNumber(2);
                        holder.mIcon.setLetterSize(18);
                    }
                    break;
            }
            holder.mBoundString = mValues.get(position);
            holder.mTextView.setText(mValues.get(position));
            
			return convertView;
		}
    }
}
