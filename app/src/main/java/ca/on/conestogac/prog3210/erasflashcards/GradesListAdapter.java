package ca.on.conestogac.prog3210.erasflashcards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GradesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Score> glist;

    public GradesListAdapter(List<Score> glist, Context context){
        super();
        this.glist = glist;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        //decleares the textview variables
        public TextView gFname;
        public TextView gLname;
        public TextView gGrade;
        public TextView gCategory;
        public TextView gDate;

        public ViewHolder(View itemView){

            super(itemView);
            //initializes the declared variables
            gFname = (TextView) itemView.findViewById(R.id.fname);
            gLname = (TextView) itemView.findViewById(R.id.lname);
            gGrade = (TextView) itemView.findViewById(R.id.grade);
            gCategory = (TextView) itemView.findViewById(R.id.category);
            gDate = (TextView) itemView.findViewById(R.id.date);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Score scoreAdapter = glist.get(position);
        ((ViewHolder) holder).gFname.setText((scoreAdapter.getFname()));
        ((ViewHolder) holder).gLname.setText(scoreAdapter.getLname());
        ((ViewHolder) holder).gGrade.setText(Double.toString(scoreAdapter.getGrade()));
        ((ViewHolder) holder).gCategory.setText(scoreAdapter.getCategory());
        Date date = new Date((scoreAdapter.getDate()*1000L));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String dateString = dateFormat.format(date);
        ((ViewHolder) holder).gDate.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return glist.size();
    }
}
