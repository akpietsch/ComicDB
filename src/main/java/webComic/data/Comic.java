package webComic.data;

public class Comic {

	public String title;
	public int id;
	public int issue;
	public int box;
	public String publisher;
	public String comment;
	public String imgurl;

	public Comic() {

	}

	public Comic(int id, String title, int issue, int box, String publisher, String comment) {
		super();
		this.id = id;
		this.title = title;
		this.title = title;
		this.issue = issue;
		this.box = box;
		this.comment = comment;
		this.publisher = publisher;
		this.setImgurl("");
	}

	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIssue() {
		return issue;
	}

	public void setIssue(int issue) {
		this.issue = issue;
	}

	public int getBox() {
		return box;
	}

	public void setBox(int box) {
		this.box = box;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

}
