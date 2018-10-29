import java.io.Serializable;

public class Book implements Serializable{
        private String id = null;
        private String author = null;
        private String title = null;
        private String isbn = null;
        private Integer price = null;
        private String ISO = null;


        public Book(String id, String author, String title, String isbn, Integer price, String ISO) {
            this.id = id;
            this.author = author;
            this.title = title;
            this.isbn = isbn;
            this.price = price;
            this.ISO = ISO;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String  getISO() {
            return ISO;
        }

        public void setISO(String ISO) {
            this.ISO = ISO;
        }
}

