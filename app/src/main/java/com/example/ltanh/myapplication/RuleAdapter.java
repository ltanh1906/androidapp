package com.example.ltanh.myapplication;

/**
 * Created by ltanh on 05/12/2024.
 */
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class RuleAdapter extends RecyclerView.Adapter<RuleAdapter.RuleViewHolder> {

    private List<Rule> ruleList;
    private Context context;

    public RuleAdapter(Context context, List<Rule> ruleList) {
        this.context = context;
        this.ruleList = ruleList;
    }

    @NonNull
    @Override
    public RuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rule_item, parent, false);
        return new RuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RuleViewHolder holder, final int position) {
        final Rule rule = ruleList.get(position);

        holder.packageName.setText("Package: " + rule.getPackageName());
        holder.regex.setText("Regex: " + rule.getRegex());
        holder.status.setText(rule.isEnabled() ? "Enabled" : "Disabled");

        // Xử lý nút Disable
        holder.disableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rule.setEnabled(!rule.isEnabled());
                notifyItemChanged(position);
                Toast.makeText(context, "Rule " + (rule.isEnabled() ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý nút Delete
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruleList.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Rule Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ruleList.size();
    }

    public static class RuleViewHolder extends RecyclerView.ViewHolder {
        TextView packageName, regex, status;
        Button deleteBtn, disableBtn;

        public RuleViewHolder(@NonNull View itemView) {
            super(itemView);
            packageName = (TextView) itemView.findViewById(R.id.package_name);
            regex = (TextView) itemView.findViewById(R.id.regex);
            status = (TextView) itemView.findViewById(R.id.status);
            deleteBtn = (Button) itemView.findViewById(R.id.delete_button);
            disableBtn = (Button) itemView.findViewById(R.id.disable_button);
        }
    }
}
