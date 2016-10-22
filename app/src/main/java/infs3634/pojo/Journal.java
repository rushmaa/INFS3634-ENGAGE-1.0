package infs3634.pojo;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Journal extends BaseObservable implements Serializable {

    @Bindable
    @SerializedName("journalId")
    @Expose
    private String journalId;
    @Bindable
    @SerializedName("journalTitle")
    @Expose
    private String journalTitle;
    @Bindable
    @SerializedName("journalContent")
    @Expose
    private String journalContent;
    @Bindable
    @SerializedName("journalCreated")
    @Expose
    private String journalCreated;
    @Bindable
    @SerializedName("userId")
    @Expose
    private String userId;

    /**
     * @return The journalId
     */
    public String getJournalId() {
        return journalId;
    }

    /**
     * @param journalId The journalId
     */
    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    /**
     * @return The journalTitle
     */
    public String getJournalTitle() {
        return journalTitle;
    }

    /**
     * @param journalTitle The journalTitle
     */
    public void setJournalTitle(String journalTitle) {
        this.journalTitle = journalTitle;
    }

    /**
     * @return The journalContent
     */
    public String getJournalContent() {
        return journalContent;
    }

    /**
     * @param journalContent The journalContent
     */
    public void setJournalContent(String journalContent) {
        this.journalContent = journalContent;
    }

    /**
     * @return The journalCreated
     */
    public String getJournalCreated() {
        return journalCreated;
    }

    /**
     * @param journalCreated The journalCreated
     */
    public void setJournalCreated(String journalCreated) {
        this.journalCreated = journalCreated;
    }

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
    }

}