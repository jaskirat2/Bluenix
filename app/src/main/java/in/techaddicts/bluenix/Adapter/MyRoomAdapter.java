package in.techaddicts.bluenix.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import in.techaddicts.bluenix.Common.Common;
import in.techaddicts.bluenix.Interface.IRecyclerItemSelectedListener;
import in.techaddicts.bluenix.Model.EventBus.EnableNextButton;
import in.techaddicts.bluenix.Model.Room;
import in.techaddicts.bluenix.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MyRoomAdapter extends RecyclerView.Adapter<MyRoomAdapter.MyViewHolder> {         //MyBarberAdapter

    Context context;
    List<Room> roomList;
    List<CardView> cardViewList;
    //LocalBroadcastManager localBroadcastManager;

    public MyRoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
        cardViewList = new ArrayList<>();
        // localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_room,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.txt_room_name.setText(roomList.get(i).getName());
        if(roomList.get(i).getRatingTimes()!=null)
        {
            myViewHolder.ratingBar.setRating(roomList.get(i).getRating().floatValue() / roomList.get(i).getRatingTimes());
        }else
        {
            myViewHolder.ratingBar.setRating(0);
        }
        if (!cardViewList.contains(myViewHolder.card_room))
            cardViewList.add(myViewHolder.card_room);

        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                //Set background for all item not choice
                for (CardView cardView : cardViewList)
                {
                    cardView.setCardBackgroundColor(context.getResources()
                            .getColor(android.R.color.white));
                }

                //Set background for choice
                myViewHolder.card_room.setCardBackgroundColor(
                        context.getResources()
                                .getColor(android.R.color.holo_green_dark)
                );

                /*
                //Sent Local broadcast to enable button next
                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_ROOM_SELECTED,roomList.get(pos));
                intent.putExtra(Common.KEY_STEP,2);
                localBroadcastManager.sendBroadcast(intent);

                 */

                //Eventbus
                EventBus.getDefault().postSticky(new EnableNextButton(2,roomList.get(i)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_room_name;
        RatingBar ratingBar;
        CardView card_room;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_room = (CardView)itemView.findViewById(R.id.card_room);
            txt_room_name = (TextView)itemView.findViewById(R.id.txt_room_name);
            ratingBar = (RatingBar)itemView.findViewById(R.id.rtb_room);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view,getAdapterPosition());
        }
    }
}

