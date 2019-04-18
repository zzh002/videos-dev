package com.zzh.pojo.vo;


public class UsersVO {
    
    private String id;
	
	private String userToken;
	
	private boolean isFollow;

    /**
     * 用户唯一标识
     */
    private String openid;

    /**
     * 性别 0：未知、1：男、2：女
     */
    private Integer gender;

    /**
     * 所在国家
     */
    private String country;

    /**
     * 所在省份
     */
    private String province;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 我的头像，如果没有默认给一张
     */
    private String faceImage;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 我的粉丝数量
     */
    private Integer fansCounts;

    /**
     * 我关注的人总数
     */
    private Integer followCounts;

    /**
     * 我接受到的赞美/收藏 的数量
     */
    private Integer receiveLikeCounts;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取openid
     *
     * @return openid - 用户唯一标识
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * 设置openid
     *
     * @param openid 用户唯一标识
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取我的头像，如果没有默认给一张
     *
     * @return face_image - 我的头像，如果没有默认给一张
     */
    public String getFaceImage() {
        return faceImage;
    }

    /**
     * 设置我的头像，如果没有默认给一张
     *
     * @param faceImage 我的头像，如果没有默认给一张
     */
    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取我的粉丝数量
     *
     * @return fans_counts - 我的粉丝数量
     */
    public Integer getFansCounts() {
        return fansCounts;
    }

    /**
     * 设置我的粉丝数量
     *
     * @param fansCounts 我的粉丝数量
     */
    public void setFansCounts(Integer fansCounts) {
        this.fansCounts = fansCounts;
    }

    /**
     * 获取我关注的人总数
     *
     * @return follow_counts - 我关注的人总数
     */
    public Integer getFollowCounts() {
        return followCounts;
    }

    /**
     * 设置我关注的人总数
     *
     * @param followCounts 我关注的人总数
     */
    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    /**
     * 获取我接受到的赞美/收藏 的数量
     *
     * @return receive_like_counts - 我接受到的赞美/收藏 的数量
     */
    public Integer getReceiveLikeCounts() {
        return receiveLikeCounts;
    }

    /**
     * 设置我接受到的赞美/收藏 的数量
     *
     * @param receiveLikeCounts 我接受到的赞美/收藏 的数量
     */
    public void setReceiveLikeCounts(Integer receiveLikeCounts) {
        this.receiveLikeCounts = receiveLikeCounts;
    }

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public boolean isFollow() {
		return isFollow;
	}

	public void setFollow(boolean isFollow) {
		this.isFollow = isFollow;
	}
}