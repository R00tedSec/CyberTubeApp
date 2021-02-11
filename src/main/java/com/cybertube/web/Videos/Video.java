package com.cybertube.web.Videos;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.*;

import com.cybertube.web.Comments.Comment;
import com.cybertube.web.Users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.*;

@Entity
@Table(name = "Videos")
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long videoID;

	@Column(unique = true, nullable = false)
	private String videoTitle;

	@Column(unique = true, nullable = false)
	private String videoURL;

	@Column(unique = true, nullable = false)
	private String videoThumbnail;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String videoDescription;

	@Column(nullable = false)
	private String videoDate;

	@Column(nullable = false)
	private String videoAuthor;

	@ElementCollection
	private List<String> videoCategories;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<Comment> commentaries;

	@JsonIgnore
	@ManyToOne
	private User authorVideoEntity;

	@JsonIgnore
	@ManyToMany
	private List<User> LikeVideoEntity;

	public Video() {
		this.videoCategories = new ArrayList<String>();
		this.commentaries = new ArrayList<Comment>();
		this.LikeVideoEntity = new ArrayList<User>();
	}

	public Video(String videoTitle, String videoURL, String videoDescription, User videoAuthor,
			List<String> videoCategories) {
		this.videoTitle = videoTitle;
		this.setVideoURL(videoURL);
		this.videoDescription = videoDescription;
		this.videoAuthor = videoAuthor.getUsername();
		this.videoDate = getCurrentTime();
		this.videoCategories = videoCategories;
		this.authorVideoEntity = videoAuthor;
	}

	public List<User> getLikeVideoEntity() {
		return this.LikeVideoEntity;
	}

	public void setLikeVideoEntity(List<User> LikeVideoEntity) {
		this.LikeVideoEntity = LikeVideoEntity;
	}

	public void addLikeVideoEntity(User user) {
		this.LikeVideoEntity.add(user);
	}

	public void removeLikeVideoEntity(User user) {
		this.LikeVideoEntity.remove(user);
	}

	public List<Comment> getCommentaries() {
		return this.commentaries;
	}

	public void setCommentaries(List<Comment> commentaries) {
		this.commentaries = commentaries;
	}

	public void addCommetary(Comment commentary) {
		this.commentaries.add(commentary);
	}

	public void deleteCommetary(Comment commentary) {
		// this.commentaries.remove(commentary);
	}

	public String getVideoThumbnail() {
		return this.videoThumbnail;
	}

	public void setVideoThumbnail(String videoThumbnail) {
		this.videoThumbnail = videoThumbnail;
	}

	public long getVideoID() {
		return this.videoID;
	}

	public void setVideoID(long videoID) {
		this.videoID = videoID;
	}

	public String getVideoTitle() {
		return this.videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public String getVideoURL() {
		return this.videoURL;
	}

	public void setVideoURL(String videoURL) {

		// Get the thumbnail of the video
		Pattern compiledPattern = Pattern.compile(
				"(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = compiledPattern.matcher(videoURL);
		if (matcher.find()) {
			this.setVideoThumbnail(matcher.group());
		}

		this.videoURL = videoURL;
	}

	public String getVideoDescription() {
		return this.videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	public String getVideoDate() {
		return this.videoDate;
	}

	public void setVideoDate(String videoDate) {
		this.videoDate = videoDate;
	}

	public String getVideoAuthor() {
		return this.videoAuthor;
	}

	public void setVideoAuthor(String videoAuthor) {
		this.videoAuthor = videoAuthor;
	}

	public List<String> getVideoCategories() {
		return this.videoCategories;
	}

	public void setVideoCategories(List<String> videoCategories) {
		this.videoCategories = videoCategories;
	}

	public static String getCurrentTime() {
		Date date = new Date();
		String strDateFormat = "dd MMMM, YYYY";
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		String formattedDate = dateFormat.format(date);
		return formattedDate;
	}
}
