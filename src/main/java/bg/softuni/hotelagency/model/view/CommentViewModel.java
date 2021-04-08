package bg.softuni.hotelagency.model.view;

public class CommentViewModel {
    private String userNames;
    private String userPic;
    private String content;
    private String postedOn;

    public CommentViewModel() {
    }

    public String getUserNames() {
        return userNames;
    }

    public CommentViewModel setUserNames(String userNames) {
        this.userNames = userNames;
        return this;
    }

    public String getUserPic() {
        return userPic;
    }

    public CommentViewModel setUserPic(String userPic) {
        this.userPic = userPic;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentViewModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getPostedOn() {
        return postedOn;
    }

    public CommentViewModel setPostedOn(String postedOn) {
        this.postedOn = postedOn;
        return this;
    }
}
