package infs3634.journalapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.nononsenseapps.filepicker.FilePickerActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.File;
import java.io.FileInputStream;

import infs3634.journalapp.databinding.FragmentDropboxBinding;
import infs3634.pojo.User;
import infs3634.service.DropboxApi;


public class DropboxFragment extends Fragment {

    private User user;
    private View view;
    private static int FILE_CODE = 555;
    DropboxRecyclerViewAdapter drvd;

    public DropboxFragment() {
        // Required empty public constructor
    }

    public User getUser() {
        return this.user;
    }

    public boolean isUserAdmin(){
        return this.user.getIsAdmin().equals("1");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.user = (User) getArguments().getSerializable("user");
        }
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("Dropbox");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDropboxBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_dropbox, container, false);
        View view = binding.getRoot();
        binding.setFragment(this);
        this.view = view;

        View recView = view.findViewById(R.id.dropbox_file_list);
        // Set the adapter
        if (recView instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) recView;
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).build());

            drvd = new DropboxRecyclerViewAdapter(this);
            recyclerView.setAdapter(drvd);
        }


        return view;
    }

    public void pickFile() {
        // This always works
        Intent i = new Intent(getContext(), FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
        startActivityForResult(i, FILE_CODE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            UploadTask uploadTask = new UploadTask();
            uploadTask.execute(uri);
        }
    }

    public void downloadFile(Metadata file) {
        DownloadFileTask downloadFileTask = new DownloadFileTask();
        downloadFileTask.execute(file);
    }

    public void deleteFile(Metadata file) {
        DeleteFileTask deleteFileTask = new DeleteFileTask();
        deleteFileTask.execute(file);
    }

    public class UploadTask extends AsyncTask<Uri, Void, FileMetadata> {

        protected void onPreExecute() {

        }

        protected FileMetadata doInBackground(Uri... params) {

            FileMetadata response = null;

            try {

                // Define path of file to be upload
                File file = new File(params[0].getPath());
                FileInputStream inputStream = new FileInputStream(file);

                // put the file to dropbox
                response = DropboxApi.uploadToDropbox(inputStream, "/" + file.getName());

            } catch (Exception e) {

                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(FileMetadata fileMetadata) {

            if (fileMetadata != null) {
                Toast.makeText(getContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                drvd.reload();
            }
        }
    }

    public class DownloadFileTask extends AsyncTask<Metadata, Void, Void> {

        protected void onPreExecute() {

        }

        protected Void doInBackground(Metadata... params) {

            try {
                String localFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + params[0].getName();
                DropboxApi.downloadFile(params[0].getPathLower(), localFilePath);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void fileMetadata) {
            Toast.makeText(getContext(), "File Downloaded ", Toast.LENGTH_LONG).show();
        }
    }

    public class DeleteFileTask extends AsyncTask<Metadata, Void, Void> {

        protected void onPreExecute() {

        }

        protected Void doInBackground(Metadata... params) {

            try {
                DropboxApi.deleteFile(params[0].getPathLower());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void fileMetadata) {
            Toast.makeText(getContext(), "File Deleted ", Toast.LENGTH_LONG).show();
            drvd.reload();
        }
    }
}
