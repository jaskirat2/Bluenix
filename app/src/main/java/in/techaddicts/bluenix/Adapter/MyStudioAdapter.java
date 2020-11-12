package in.techaddicts.bluenix.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import in.techaddicts.bluenix.Common.Common;
import in.techaddicts.bluenix.Interface.IRecyclerItemSelectedListener;
import in.techaddicts.bluenix.Model.EventBus.EnableNextButton;
import in.techaddicts.bluenix.Model.Studio;
import in.techaddicts.bluenix.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MyStudioAdapter extends RecyclerView.Adapter<MyStudioAdapter.MyViewHolder> {   //MySalonAdapter

    Context context;
    List<Studio> studioList;
    List<CardView> cardViewList;
    //LocalBroadcastManager localBroadcastManager;

    public MyStudioAdapter(Context context, List<Studio> studioList) {
        this.context = context;
        this.studioList = studioList;
        cardViewList = new ArrayList<>();
        // localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_studio,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_studio_name.setText(studioList.get(i).getName());
        myViewHolder.txt_studio_address.setText(studioList.get(i).getAddress());
        if (!cardViewList.contains(myViewHolder.card_studio))
            cardViewList.add(myViewHolder.card_studio);

        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                //Set white background for all card not be selected
                for (CardView cardView:cardViewList)
                    cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                //Set selected BG for only selected item
                myViewHolder.card_studio.setCardBackgroundColor(context.getResources()
                        .getColor(android.R.color.holo_green_dark));

                /*
                //Send Broadcast to tell BookingActivity enable Button next
                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_STUDIO_STORE,studioList.get(pos));
                intent.putExtra(Common.KEY_STEP,1);
                localBroadcastManager.sendBroadcast(intent);

                 */

                //Event Bus
                EventBus.getDefault().postSticky(new EnableNextButton(1,studioList.get(pos)));

            }
        });
    }

    @Override
    public int getItemCount() {
        return studioList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_studio_name,txt_studio_address;
        CardView card_studio;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_studio = (CardView)itemView.findViewById(R.id.card_studio);
            txt_studio_address = (TextView)itemView.findViewById(R.id.txt_studio_address);
            txt_studio_name = (TextView)itemView.findViewById(R.id.txt_studio_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view,getAdapterPosition());
        }
    }
}
