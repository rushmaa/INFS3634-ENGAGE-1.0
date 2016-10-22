package infs3634.journalapp;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import infs3634.pojo.Journal;
import infs3634.pojo.User;
import infs3634.service.ApiClient;
import retrofit2.Call;
import retrofit2.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JournalRecyclerViewAdapter extends RecyclerView.Adapter<JournalRecyclerViewAdapter.ViewHolder> {

    private final List<Journal> mValues;
    private FetchJournalsTask fetchJournalsTask;
    private User user;
    private ViewGroup parent;

    public JournalRecyclerViewAdapter(User user) {
        mValues = new ArrayList<Journal>();
        this.user = user;

        Call<List<Journal>> call = ApiClient.getInstance().getUserJounrals(this.user.getUserId());
        fetchJournalsTask = new FetchJournalsTask();
        fetchJournalsTask.execute(call);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent= parent;
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.fragment_journal, parent, false);
        return new ViewHolder(viewDataBinding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ViewDataBinding viewDataBinding = holder.getViewDataBinding();
        viewDataBinding.setVariable(BR.journal, mValues.get(position));
        viewDataBinding.setVariable(BR.context, parent.getContext());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mViewDataBinding;

        public ViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
        }

        public ViewDataBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }

    public class FetchJournalsTask extends AsyncTask<Call, Void, List<Journal>> {

        @Override
        protected List<Journal> doInBackground(Call... params) {

            try {
                Call<List<Journal>> call = params[0];
                Response<List<Journal>> response = call.execute();
                List<Journal> Journals = response.body();
                return Journals;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(final List<Journal> journals) {
            fetchJournalsTask = null;

            if (journals != null) {
                mValues.addAll(journals);
                notifyDataSetChanged();
            } else {
                System.out.println("No result");
            }
        }

        @Override
        protected void onCancelled() {
            fetchJournalsTask = null;
        }
    }
}
