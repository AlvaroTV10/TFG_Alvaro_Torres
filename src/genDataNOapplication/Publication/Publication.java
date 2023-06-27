package genDataNOapplication.Publication;
import java.util.*;

import genDataNOapplication.User.User;


//class added 
public class Publication {
	
	private Integer id;
	private int user; 
	private String type;
	private String topic;
	private String publication_description;                 
	private Double quality;
	private boolean fakenew; //True if fake new 
	private boolean prediction; //True if fake new
	private String politicalorientation;
	
	private Integer num_likes=0;
	private Integer num_comments=0;
	private Integer num_share=0;
	private Integer num_ignored = 0;
	public List<Integer> like_users = new LinkedList <Integer> ();
	public List<Integer> comment_users = new LinkedList <Integer> ();
	
	public List<Integer> share_users = new LinkedList <Integer> ();
	public List<Integer> users_tagged = new LinkedList <Integer> ();
	public List<Integer> ignored_users = new LinkedList <Integer> ();
	int date;
	
	//private String emotion;
	
	
	
	
	public void setId(Integer id_) {
		this.id = id_;
	}
	public Integer getId() {
		return this.id;
	}
	public void setDate(Integer date_) {
		this.date = date_;
	}
	public Integer getDate() {
		return this.date;
	}
		
	public void loadUser(int user_) {
		this.user = user_;
	}
	public int getUser() {
		return this.user;
	}
	
	public void loadType(String type_) {
		this.type = type_;
	}
	public String getType() {
		return this.type;
	}
	
	public void loadTopic(String topic_) {
		this.topic = topic_;
	}
	public String getTopic() {
		return this.topic;
	}
	
	public void loadPublicationDescription(String publication_des) {
		this.publication_description = publication_des;
	}
	public String getPublicationDescription() {
		return this.publication_description;
	}
	
	public void loadQuality(Double quality_) {
		this.quality = quality_;
	}
	public Double getQuality() {
		return this.quality;
	}
	
	public void setNumLikes() {
		this.num_likes = this.like_users.size();
	}
	public Integer getNumLikes() {
		return this.num_likes;
	}
	public void setNumComments() {
		this.num_comments = this.comment_users.size();
	}
	public Integer getNumComments() {
		return this.num_comments;
	}
	
	public void setNumShare() {
		this.num_share = this.share_users.size();
	}
	public Integer getNumShare() {
		return this.num_share;
	}
	
	public void addUserLike(Integer useri) {
		if (this.like_users.contains(useri) == false) {
			this.like_users.add(useri);
			setNumLikes();
		}
	}
	
	public void addUserComment(Integer useri) {
		if (this.comment_users.contains(useri) == false) {
			this.comment_users.add(useri);
			setNumComments();
		}
	}
	
	public void addUserShare(Integer useri) {
		if (this.share_users.contains(useri) == false) {
			this.share_users.add(useri);
			setNumShare();
		}
	}
	
	public void addUserIgnored(Integer useri) {
		if (this.ignored_users.contains(useri) == false) {
			this.ignored_users.add(useri);
			setNumIgnored();
		}
	}
	/*public void loadEmotion(String emotion_) {
		this.emotion = emotion_;
	}
	public String getEmotion() {
		return emotion;
	}
	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}*/
	public Integer getNumIgnored() {
		return this.num_ignored;
	}
	public void setNumIgnored() {
		this.num_ignored = this.ignored_users.size();	}
	public boolean isFakenew() {
		return fakenew;
	}
	public void setFakenew(boolean fakenew) {
		this.fakenew = fakenew;
	}
	public String getPoliticalOrientation() {
		return politicalorientation;
	}
	public void setPoliticalOrientation(String politicalorientation) {
		this.politicalorientation = politicalorientation;
	}
	public boolean isPrediction() {
		return prediction;
	}
	public void setPrediction(boolean prediction) {
		this.prediction = prediction;
	}
	

	
	
}
