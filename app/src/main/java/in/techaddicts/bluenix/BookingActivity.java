package in.techaddicts.bluenix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import in.techaddicts.bluenix.Model.EventBus.BarberDoneEvent;
import in.techaddicts.bluenix.Model.EventBus.ConfirmBookingEvent;
import in.techaddicts.bluenix.Model.EventBus.DisplayTimeSlotEvent;
import in.techaddicts.bluenix.Model.EventBus.EnableNextButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import in.techaddicts.bluenix.Adapter.MyViewPagerAdapter;
import in.techaddicts.bluenix.Common.Common;
import in.techaddicts.bluenix.Common.NonSwipeViewPager;
import in.techaddicts.bluenix.Model.Room;
import com.shuhart.stepview.StepView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class BookingActivity extends AppCompatActivity {
    AlertDialog dialog;
    CollectionReference roomRef;

    @BindView(R.id.step_view)
    StepView stepView;
    @BindView(R.id.view_pager)
    NonSwipeViewPager viewPager;
    @BindView(R.id.btn_previous_step)
    Button btn_previous_step;
    @BindView(R.id.btn_next_step)
    Button btn_next_step;

    //Event
    @OnClick(R.id.btn_previous_step)
    void previousStep() {
        if (Common.step == 3 || Common.step > 0)
        {
            Common.step--;
            viewPager.setCurrentItem(Common.step);
            if (Common.step < 3) //Always enable NEXT when step 3
            {
                btn_next_step.setEnabled(true);
                setColorButton();
            }
        }
    }
    @OnClick(R.id.btn_next_step)
    void nextClick() {
        if (Common.step < 3 || Common.step == 0)
        {
            Common.step++; //Increase
            if (Common.step == 1) //After choose studio
            {
                if (Common.currentStudio != null)
                    loadRoomByStudio(Common.currentStudio.getStudioId());
            }
            else if (Common.step == 2) //Pick time slot
            {
                if (Common.currentRoom != null)
                    loadTimeSlotOfRoom(Common.currentRoom.getRoomId());
            }
            else if (Common.step == 3) //Confirm
            {
                if (Common.currentTimeSlot != -1)
                    confirmBooking();
            }
            viewPager.setCurrentItem(Common.step);
        }
    }

    private void confirmBooking() {
        //Send broadcast to fragment step four
      /*  Intent intent = new Intent(Common.KEY_CONFIRM_BOOKING);
        localBroadcastManager.sendBroadcast(intent);*/

        EventBus.getDefault().postSticky(new ConfirmBookingEvent(true));
    }

    private void loadTimeSlotOfRoom(String roomId) {
//Send Local Broadcast to Fragment step 3
      /*  Intent intent = new Intent(Common.KEY_DISPLAY_TIME_SLOT);
        localBroadcastManager.sendBroadcast(intent);*/

        EventBus.getDefault().postSticky(new DisplayTimeSlotEvent(true));
    }

    private void loadRoomByStudio(String studioId) {
        dialog.show();

        //Select all Room of Studio
        // /AllRental/Cikupa/StudioBand/9qREOUipdtsPtGIRHu6k/Room
        ///AllSalon/NewYork/Branch/AX7yo2j7lDEzFMx5LZvG/Barber/CC66BQ44G0uuWuNzSPCQ
        if (!TextUtils.isEmpty(Common.city))
        {
            roomRef = FirebaseFirestore.getInstance()
                    .collection("AllSalon")
                    .document(Common.city)
                    .collection("Branch")
                    .document(studioId)
                    .collection("Barber");

            roomRef.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            ArrayList<Room> rooms = new ArrayList<>();
                            for (QueryDocumentSnapshot roomSnapShot:task.getResult())
                            {
                                Room room = roomSnapShot.toObject(Room.class);
                                room.setPassword(""); //Remove password in client app
                                room.setRoomId(roomSnapShot.getId()); //Get Id of room

                                rooms.add(room);
                            }
                            //Send Broadcast to BookingStep2Fragment to load Recycler
                           /* Intent intent = new Intent(Common.KEY_ROOM_LOAD_DONE);
                            intent.putParcelableArrayListExtra(Common.KEY_ROOM_LOAD_DONE,rooms);
                            localBroadcastManager.sendBroadcast(intent);*/

                            EventBus.getDefault().postSticky(new BarberDoneEvent(rooms));

                            dialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                        }
                    });
        }

    }

    /*
    //Broadcast Receiver
    private BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int step = intent.getIntExtra(Common.KEY_STEP,0);
            if (step == 1)
                Common.currentStudio = intent.getParcelableExtra(Common.KEY_STUDIO_STORE);
            else if (step == 2)
                Common.currentRoom = intent.getParcelableExtra(Common.KEY_ROOM_SELECTED);
            else if (step == 3)
                Common.currentTimeSlot = intent.getIntExtra(Common.KEY_TIME_SLOT, -1);

            btn_next_step.setEnabled(true);
            setColorButton();
        }
    };*/

    //Event Bus convert
    @Subscribe(sticky = true , threadMode = ThreadMode.MAIN)
    public void buttonNextReceiver(EnableNextButton event)
    {
        int step = event.getStep();
        if (step == 1)
            Common.currentStudio = event.getSalon();
        else if (step == 2)
            Common.currentRoom = event.getRoom();
        else if (step == 3)
            Common.currentTimeSlot = event.getTimeSlot();

        btn_next_step.setEnabled(true);
        setColorButton();
    }

    /*
    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(BookingActivity.this);

        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        /*
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver,new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));
         */

        setupStepView();
        setColorButton();

        //View
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4); //4 fragment need keep state of this 4 screen page
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                //Show step
                stepView.go(i,true);
                if ( i == 0)
                    btn_previous_step.setEnabled(false);
                else
                    btn_previous_step.setEnabled(true);

                //Set Button Disable here
                btn_next_step.setEnabled(false);
                setColorButton();

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setColorButton() {
        if (btn_next_step.isEnabled())
        {
            btn_next_step.setBackgroundResource(R.color.colorButton);
        }
        else
        {
            btn_next_step.setBackgroundResource(android.R.color.darker_gray);
        }
        if (btn_previous_step.isEnabled())
        {
            btn_previous_step.setBackgroundResource(R.color.colorButton);
        }
        else
        {
            btn_previous_step.setBackgroundResource(android.R.color.darker_gray);
        }
    }

    private void setupStepView() {
        List<String> stepList = new ArrayList<>();
        stepList.add("Salon");
        stepList.add("Barber");
        stepList.add("Time");
        stepList.add("Confirm");
        stepView.setSteps(stepList);
    }

    //Event Bus

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}