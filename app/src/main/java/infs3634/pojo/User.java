
package infs3634.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import infs3634.journalapp.BR;
import infs3634.journalapp.R;

public class User extends BaseObservable implements Serializable {

    @Bindable
    @SerializedName("userId")
    @Expose
    private String userId;
    @Bindable
    @SerializedName("username")
    @Expose
    private String username;
    @Bindable
    @SerializedName("password")
    @Expose
    private String password;
    @Bindable
    @SerializedName("name")
    @Expose
    private String name;
    @Bindable
    @SerializedName("isAdmin")
    @Expose
    private String isAdmin;

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);

    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);

    }

    /**
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);

    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);

    }

    /**
     * @return The isAdmin
     */
    public String getIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin The isAdmin
     */
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
        notifyPropertyChanged(BR.isAdmin);
    }

}
