package ru.open.api.models;

public class UsersPostResponse {
    public int page;
    public int per_page;
    public int total;
    public int total_pages;

    public RegisteredUser[] data;

    public UsersPostResponse(int page, int per_page, int total, int total_pages, RegisteredUser[] data) {
        this.page = page;
        this.per_page = per_page;
        this.total = total;
        this.total_pages = total_pages;
        this.data = data;
    }
}
