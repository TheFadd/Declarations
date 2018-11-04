package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.data.HumanEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class HumanAdapter extends RecyclerView.Adapter<HumanAdapter.HumanViewHolder> {

    private static final String LOCATION_SEPARATOR = " ";

    public interface HumanFavouriteListener {
        void onHumanFavourite(HumanEntity human);
    }

    public interface HumanCommentListener {
        void onHumanComment(HumanEntity human);
    }

    private List<HumanEntity> items;
    private HumanFavouriteListener listener;
    private HumanCommentListener commentListener;
    private Boolean needComment;

    public HumanAdapter(List<HumanEntity> humans, HumanFavouriteListener listener, HumanCommentListener commentListener, boolean needComment) {
        items = humans;
        this.listener = listener;
        this.commentListener = commentListener;
        this.needComment = needComment;
    }

    @Override
    public HumanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new HumanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HumanViewHolder holder, final int position) {
        final HumanEntity item = items.get(position);

        String fullName = item.getNameFatherName();
        String fatherName;
        String name;
        if (fullName.contains(LOCATION_SEPARATOR)) {
            String[] parts = fullName.split(LOCATION_SEPARATOR);
            name = parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1).toLowerCase() + LOCATION_SEPARATOR;
            fatherName = parts[1].substring(0, 1).toUpperCase() + parts[1].substring(1).toLowerCase();
            fullName = name + fatherName;
        }
        holder.nameView.setText(fullName);

        holder.surnameView.setText(item.getSurname().substring(0, 1).toUpperCase() + item.getSurname().substring(1).toLowerCase());

        holder.placeOfWorkView.setText(item.getPlaceOfWork());
        holder.placeOfWorkView.setVisibility(TextUtils.isEmpty(item.getPlaceOfWork()) ? GONE : VISIBLE);

        String pos = item.getPosition();
        boolean empty = TextUtils.isEmpty(pos) || pos.equalsIgnoreCase("ні");
        holder.positionView.setVisibility(empty ? GONE : VISIBLE);
        holder.positionView.setText(pos);

        if (TextUtils.isEmpty(item.getComment()) && needComment) {
            holder.commentView.setVisibility(VISIBLE);
            holder.viewForCard.setVisibility(VISIBLE);
            holder.commentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commentListener.onHumanComment(item);
                    notifyDataSetChanged();
                }
            });
        } else if (TextUtils.isEmpty(item.getComment()) && !needComment) {
            holder.commentView.setVisibility(View.GONE);
            holder.viewForCard.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(item.getComment()) && !needComment) {
            holder.commentView.setVisibility(View.GONE);
            holder.viewForCard.setVisibility(View.GONE);
        } else {
            holder.commentView.setText(item.getComment());
            holder.commentView.setVisibility(VISIBLE);
            holder.viewForCard.setVisibility(VISIBLE);
            holder.commentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (commentListener != null) commentListener.onHumanComment(item);
                    notifyDataSetChanged();
                }
            });
        }

        if (TextUtils.isEmpty(item.getLinkPDF())) {
            holder.PDFLinkView.setVisibility(View.INVISIBLE);
        } else {
            holder.PDFLinkView.setVisibility(VISIBLE);
            holder.PDFLinkView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri PDFUri = Uri.parse(item.getLinkPDF());
                            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, PDFUri);
                            view.getContext().startActivity(websiteIntent);
                        }
                    });
        }
        holder.starImageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) listener.onHumanFavourite(item);
                        notifyDataSetChanged();
                    }
                });

        holder.starImageView.setImageResource(item.getFavorite() ? R.drawable.ic_star_black_24dp : R.drawable.ic_star_border_black_24dp);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void swapData(List<HumanEntity> data){
        items = data;
        notifyDataSetChanged();
    }

    public static class HumanViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.CardViewHuman)
        CardView cardViewHuman;
        @BindView(R.id.surname)
        TextView surnameView;
        @BindView(R.id.name)
        TextView nameView;
        @BindView(R.id.placeofwork)
        TextView placeOfWorkView;
        @BindView(R.id.position)
        TextView positionView;
        @BindView(R.id.pdffile)
        ImageView PDFLinkView;
        @BindView(R.id.starimage)
        ImageView starImageView;
        @BindView(R.id.comment)
        TextView commentView;
        @BindView(R.id.view_for_card)
        View viewForCard;

        HumanViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
