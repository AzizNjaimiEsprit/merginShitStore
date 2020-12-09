package Beans;

public class WishList {
	private User user ;
	private Book book;
	
	public WishList(User user, Book book) {
		this.user = user;
		this.book = book;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WishList other = (WishList) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WishList [user=" + user + ", book=" + book + "]";
	}

	public String getBookTitle(){
		return book.getTitle();
	}
	public double getBookPrice(){
		return book.getPrice();
	}

	
	
	
	
	

}
