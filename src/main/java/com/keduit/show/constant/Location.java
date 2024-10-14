package com.keduit.show.constant;

public enum Location {

    SEOUL("서울특별시"),
    GYEONGGI("경기도"),
    BUSAN("부산광역시"),
    DAEGU("대구광역시"),
    GWANGJU("광주광역시"),
    ULSAN("울산광역시"),
    GYEONGBUK("경상북도"),
    GANGWON("강원도"),
    GANGWON_S("강원특별자치도"),
    INCHEON("인천광역시"),
    JEOLBUK("전라북도"),
    JEOLNAM("전라남도"),
    DAEJEON("대전광역시"),
    SEJONG("세종특별자치시"),
    GYEONGNAM("경상남도"),
    CHUNGNAM("충청남도"),
    JEJU("제주특별자치도"),
    CHUNGBUK("충청북도");

    final private String name;
    private Location(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public static Location nameOf(String name) {
        for (Location location : Location.values()) {
            if (location.getName().equals(name)) {
                return location;
            }
        }
        return null;
    }
}
