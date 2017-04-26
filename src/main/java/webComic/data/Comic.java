package webComic.data;

/**
 * Java Comic Objekt 
 * 
 * @param id
 * @param title
 * @param issue
 * @param box
 * @param publisher
 * @param comment
 */

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

	/**
	 * @param id
	 * @param title
	 * @param issue
	 * @param box
	 * @param publisher
	 * @param comment
	 */
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

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return
	 */
	public int getIssue() {
		return issue;
	}

	/**
	 * @param issue
	 */
	public void setIssue(int issue) {
		this.issue = issue;
	}

	/**
	 * @return
	 */
	public int getBox() {
		return box;
	}

	/**
	 * @param box
	 */
	public void setBox(int box) {
		this.box = box;
	}

	/**
	 * @return
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return
	 */
	public String getImgurl() {
		return imgurl;
	}

	/**
	 * @param imgurl
	 */
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

}
