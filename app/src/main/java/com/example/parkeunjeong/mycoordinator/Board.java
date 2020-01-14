package com.example.parkeunjeong.mycoordinator;

public class Board {
    private String id;
    private String name;
    private String titie;
    private String content;

    Board(){}

    Board(String id, String title, String content, String name){
        this.titie = title;
        this.content = content;
        this.id = id;
        this.name = name;
    }

    public String getTitie() {
        return titie;
    }

    public String getContent() {
        return content;
    }

    public void setTitie(String titie) {
        this.titie = titie;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", titie='" + titie + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
