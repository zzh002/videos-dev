package com.zzh.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ZZH
 * @date 2019/2/19 16:24
 **/
@Table(name = "users_like_comments")
public class UsersLikeComments {

    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;


    @Column(name = "comment_id")
    private String commentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
