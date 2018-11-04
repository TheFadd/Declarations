package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
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
import com.example.myapplication.Human;
import com.example.myapplication.HumanAdapter;
import com.example.myapplication.L;
import com.example.myapplication.R;
import com.example.myapplication.data.HumanDAO;
import com.example.myapplication.data.HumanEntity;
import com.example.myapplication.network.ApiService;
import com.example.myapplication.network.HumanList;
import com.example.myapplication.network.RetroClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class FragmentSearch extends Fragment {

    private final HumanDAO humanDAO = App.db.humanDAO();

    @BindView(R.id.listHumanView)
    RecyclerView recListHumanView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar loadingIndicator;

    @BindView(R.id.no_internet)
    RelativeLayout connection;

    @BindView(R.id.no_search_view)
    RelativeLayout noSearchResults;

    private final BehaviorSubject<String> query = BehaviorSubject.create();
    private Disposable subscription;
    private HumanAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, root);

        LinearLayoutManager ll = new LinearLayoutManager(getActivity(), VERTICAL, false);
        recListHumanView.setLayoutManager(ll);

        adapter = new HumanAdapter(Collections.emptyList(), human -> {
            if (human.getFavorite()) {
                human.setFavourite(false);
                humanDAO.update(human);
            } else {
                showAddToFavoriteDialog(human);
            }
        }, null, false);
        recListHumanView.setAdapter(adapter);

        subscription = query.debounce(300, MILLISECONDS)
                .switchMap(this::filter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processData, e -> L.d("LOH " + e));

        query.onNext("");
        refresh();
        return root;
    }

    private void processData(List<HumanEntity> items) {
        adapter.swapData(items);
    }

    private Observable<List<HumanEntity>> filter(String query) {
        L.d(query);
        return humanDAO.allHumansRx().doOnNext(L::d).toObservable().flatMap(list ->
                Observable.fromIterable(list).filter(it -> TextUtils.isEmpty(query) || contains(it.getSurname(), query) || contains(it.getNameFatherName(), query))
                        .toList().toObservable());
    }

    private boolean contains(String text1, String text2) {
        return text1.toLowerCase().contains(text2.toLowerCase());
    }

    private void refresh() {
        ApiService api = RetroClient.getApiService();
        api.getHumans().enqueue(new Callback<HumanList>() {
            @Override
            public void onResponse(@NonNull Call<HumanList> call, @NonNull Response<HumanList> response) {
                List<HumanEntity> humans = humanDAO.allHumansBlocking();
                Map<String, HumanEntity> previous = new HashMap<>();
                for (HumanEntity item : humans) {
                    if (item.getFavorite()) {
                        previous.put(item.getServerId(), item);
                    }
                }
                List<HumanEntity> entities = new ArrayList<>();
                for (Human human : response.body().getHumans()) {
                    entities.add(HumanEntity.from(human));
                }
                for (HumanEntity item : entities) {
                    HumanEntity entity = previous.get(item.getServerId());
                    if (entity != null) {
                        item.setFavourite(true);
                        item.setComment(entity.getComment());
                    }
                }
                humanDAO.clearHumans();
                humanDAO.insert(entities);
            }

            @Override
            public void onFailure(@NonNull Call<HumanList> call, Throwable e) {
            }
        });
    }

    public void showAddToFavoriteDialog(final HumanEntity human) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage(R.string.add_comment);
        final EditText input = new EditText(getActivity());
        alert.setView(input);
        alert.setPositiveButton(R.string.save, (dialog, whichButton) -> {
            String comment = input.getEditableText().toString();
            if (!TextUtils.isEmpty(comment)) {
                human.setComment(comment);
            }
            human.setFavourite(true);
            humanDAO.update(human);
        });
        alert.setNegativeButton(R.string.cancel, null);
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

//    public void showLoading() {
//        recListHumanView.setVisibility(View.GONE);
//        loadingIndicator.setVisibility(View.VISIBLE);
//        connection.setVisibility(View.GONE);
//        noSearchResults.setVisibility(View.GONE);
//    }
//
//    public void endLoading() {
//        recListHumanView.setVisibility(View.VISIBLE);
//        loadingIndicator.setVisibility(View.GONE);
//        connection.setVisibility(View.GONE);
//        noSearchResults.setVisibility(View.GONE);
//    }
//
//    public void noInternetConnection() {
//        recListHumanView.setVisibility(View.GONE);
//        loadingIndicator.setVisibility(View.GONE);
//        connection.setVisibility(View.VISIBLE);
//        noSearchResults.setVisibility(View.GONE);
//    }
//
//    public void noSearchResults() {
//        recListHumanView.setVisibility(View.GONE);
//        loadingIndicator.setVisibility(View.GONE);
//        connection.setVisibility(View.GONE);
//        noSearchResults.setVisibility(View.VISIBLE);
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.item_search);
        SearchView sv = (SearchView) item.getActionView();

        sv.setOnCloseListener(() -> {
            query.onNext("");
            return true;
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {
                query.onNext(newQuery);
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.dispose();
    }
}
