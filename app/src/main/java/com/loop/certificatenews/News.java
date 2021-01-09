package com.loop.certificatenews;

public class News {
    private String newTitle;
    private String newCategory;
    private String newDate;
    private String newUrl;
    private String newAuthor;

    public News(String Title, String Category, String Date, String Url, String Author) {
        newTitle = Title;
        newCategory = Category;
        newDate = Date;
        newUrl = Url;
        newAuthor = Author;
    }

    public String getTitle() {
        return newTitle;
    }

    public String getCategory() {
        return newCategory;
    }

    public String getDate() {
        return newDate;
    }

    public String getUrl() {
        return newUrl;
    }

    public String getAuthor() {
        return newAuthor;
    }
}
