package com.keduit.show.constant;

public enum Genre {

    POPULAR_MUSIC("대중음악"),
    CLASSIC("서양음악(클래식)"),
    GUGAK("한국음악(국악)"),
    ACT("연극"),
    MUSICAL("뮤지컬"),
    CIRCUS_MAGIC("서커스/마술"),
    COMPLEX("복합"),
    DANCE("무용");

    //한글명 표기
    final private String name;
    private Genre(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    //한글명을 받아서 enum 으로 반환
    public static Genre nameOf(String name) {
        for (Genre genre : Genre.values()) {
            if (genre.getName().equals(name)) {
                return genre;
            }
        }
        return null;
    }
}
