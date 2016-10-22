package infs3634.service;

import android.net.Uri;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DropboxApi {

    private static final String ACCESS_TOKEN = "7gjVfwgQidAAAAAAAAAAD9t91nOOGyKMQzonuqkEGUyVWIIgOW42rJuYG5g3nxuk";

    private static DbxClientV2 getClient() {
        // Create Dropbox client
        DbxRequestConfig config = new DbxRequestConfig("infs3634/journalApp");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        return client;
    }

    public static void main(String args[]) throws DbxException, IOException {
        DbxClientV2 client = getClient();


        for (Metadata f : getDropboxFiles()) {
            System.out.println(f.getPathLower());
        }


    }

    public static Metadata deleteFile(String dropboxFilePath) throws DbxException, IOException {
        return getClient().files().delete(dropboxFilePath);
    }


    public static Metadata downloadFile(String dropboxFilePath, String localFilePath) throws DbxException, IOException {
        FileOutputStream outputStream = new FileOutputStream(localFilePath);
        try {
            return getClient().files().download(dropboxFilePath).download(outputStream);
        } finally {
            outputStream.close();
        }
    }

    public static List<Metadata> getDropboxFiles() throws DbxException {
        List<Metadata> results = new ArrayList<Metadata>();
        ListFolderResult result = getClient().files().listFolder("");

        while (true) {
            for (Metadata metadata : result.getEntries()) {
                results.add(metadata);
            }

            if (!result.getHasMore()) {
                break;
            }

            result = getClient().files().listFolderContinue(result.getCursor());
        }
        return results;
    }

    public static FileMetadata uploadToDropbox(InputStream in, String filename) throws DbxException, IOException {

        FileMetadata metadata = getClient().files().uploadBuilder(filename)
                .uploadAndFinish(in);
        return metadata;

    }
}
