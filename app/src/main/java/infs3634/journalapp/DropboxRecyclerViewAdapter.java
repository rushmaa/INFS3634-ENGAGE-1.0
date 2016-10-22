package infs3634.journalapp;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import infs3634.journalapp.databinding.FragmentDropboxItemBinding;
import infs3634.pojo.Journal;
import infs3634.pojo.User;
import infs3634.service.ApiClient;
import infs3634.service.DropboxApi;
import retrofit2.Call;
import retrofit2.Response;


public class DropboxRecyclerViewAdapter extends RecyclerView.Adapter<DropboxRecyclerViewAdapter.ViewHolder> {

    private final List<Metadata> mValues;
    private FetchDropboxTask fetchDropboxTask;
    private ViewGroup parent;
    DropboxFragment fragment;

    public DropboxRecyclerViewAdapter(DropboxFragment fragment) {
        this.fragment = fragment;
        mValues = new ArrayList<Metadata>();
        reload();
    }

    public void reload() {
        fetchDropboxTask = new FetchDropboxTask();
        fetchDropboxTask.execute();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        FragmentDropboxItemBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.fragment_dropbox_item, parent, false);
        return new ViewHolder(viewDataBinding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        FragmentDropboxItemBinding viewDataBinding = holder.getViewDataBinding();
        viewDataBinding.setMetadata(mValues.get(position));
        viewDataBinding.setFragment(fragment);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private FragmentDropboxItemBinding mViewDataBinding;

        public ViewHolder(FragmentDropboxItemBinding viewDataBinding) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
        }

        public FragmentDropboxItemBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }

    public class FetchDropboxTask extends AsyncTask<Void, Void, List<Metadata>> {

        @Override
        protected List<Metadata> doInBackground(Void... params) {

            try {
                List<Metadata> metadatas = DropboxApi.getDropboxFiles();
                return metadatas;
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Metadata> files) {
            fetchDropboxTask = null;

            if (files != null) {
                mValues.clear();
                mValues.addAll(files);
                notifyDataSetChanged();
            } else {
                System.out.println("No result");
            }
        }

        @Override
        protected void onCancelled() {
            fetchDropboxTask = null;
        }
    }



}
