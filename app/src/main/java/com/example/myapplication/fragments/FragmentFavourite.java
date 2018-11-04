package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.myapplication.App;
import com.example.myapplication.HumanAdapter;
import com.example.myapplication.R;
import com.example.myapplication.data.HumanDAO;
import com.example.myapplication.data.HumanEntity;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;


public class FragmentFavourite extends Fragment {

    @BindView(R.id.listHumanView)
    RecyclerView recListHumanView;

    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar loadingIndicator;

    final HumanDAO humanDAO = App.db.humanDAO();
    private HumanAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, root);

        LinearLayoutManager ll = new LinearLayoutManager(getActivity(), VERTICAL, false);
        recListHumanView.setLayoutManager(ll);
        adapter = new HumanAdapter(Collections.emptyList(), this::showDeleteConfirmationDialog, this::showCommentDialog, true);
        recListHumanView.setAdapter(adapter);

        humanDAO.allFavoriteHumans().observe(this, this::processHumans);
        return root;
    }

    private void processHumans(List<HumanEntity> humans) {
        if (humans.isEmpty()) {
            emptyViewON();
        } else {
            emptyViewOFF();
        }
        adapter.swapData(humans);
    }

    private void showDeleteConfirmationDialog(final HumanEntity human) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, (d, id) -> {
            human.setFavourite(false);
            humanDAO.update(human);
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    private void showCommentDialog(final HumanEntity human) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage(R.string.add_comment);
        final EditText input = new EditText(getActivity());
        alert.setView(input);
        alert.setPositiveButton(R.string.save, (dialog, whichButton) -> {
            String srt = input.getEditableText().toString();
            human.setComment(srt);
            humanDAO.update(human);
        });
        alert.setNegativeButton(R.string.cancel, (dialog, whichButton) -> dialog.cancel());
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void emptyViewON() {
        emptyView.setVisibility(View.VISIBLE);
    }

    public void emptyViewOFF() {
        emptyView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favorite, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_entries:
                humanDAO.removeAllFavorite();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}








