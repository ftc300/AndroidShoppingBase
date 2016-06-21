package com.shoping.mall.study.dialogfragment.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.shoping.mall.R;
import com.shoping.mall.study.dialogfragment.data.Friend;
import com.shoping.mall.study.dialogfragment.widget.DialogFactory;
import com.shoping.mall.study.dialogfragment.widget.ListDialogFragment;


/**
 * Created by niuxiaowei on 2016/1/19.
 */
public class FriendAdapter extends BaseAdapter {

    private List<Friend> mFriends;

    private DialogFactory mDialogFactory;

    private Context mContext;

    private FriendsListener mListener;

    public ListDialogFragment.ListDialogListener listDialogListener = new ListDialogFragment.ListDialogListener() {
        @Override
        public void onItemClick(int position) {

            Toast.makeText(mContext,"点击了 po="+position,Toast.LENGTH_LONG).show();

        }
    };

    public static interface FriendsListener{
        void onFriendItemLongClick(Friend longClickFriend);
    }

    public void setData(List<Friend> datas) {
        this.mFriends = datas;
        this.notifyDataSetChanged();
    }


    public FriendAdapter(Context context, DialogFactory dialogFactory,FriendsListener listener) {
        this.mContext = context;
        this.mDialogFactory = dialogFactory;
        dialogFactory.restoreDialogListener(this);
        this.mListener = listener;
    }





    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class FriendViewHolder{
        TextView nameTv;

        public FriendViewHolder(View itemView) {
            nameTv = (TextView) itemView.findViewById(R.id.name);
        }
    }

	@Override
	public int getCount() {

		 if (mFriends != null) {
	            return mFriends.size();
	        }
	        return 0;

	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_friend_item, parent, false);
		 FriendViewHolder friendViewHolder = new FriendViewHolder(view);
		 
		  final Friend f = mFriends.get(position);
	        friendViewHolder.nameTv.setText(f.mName);

	        if (position % 2 == 0) {
	            friendViewHolder.nameTv.setBackgroundColor(Color.RED);
	        } else {
	            friendViewHolder.nameTv.setBackgroundColor(Color.WHITE);
	        }
	        friendViewHolder.nameTv.setOnLongClickListener(new View.OnLongClickListener() {
	            @Override
	            public boolean onLongClick(View v) {

	                if (mDialogFactory != null) {

	                    mDialogFactory.showListDialog(new String[]{"delete","copy"}, true, listDialogListener);
	                }

	                if (mListener != null) {
	                    mListener.onFriendItemLongClick(f);
	                }
	                return true;
	            }
	        });
		 
		return view;
	}
}
