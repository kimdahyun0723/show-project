package com.keduit.show.constant;

public enum State {

    COMPLETE("공연완료"),
    ING("공연중"),
    EXPECT("공연예정");

    //한글명 포기
    final private String name;
    private State(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    //한글명 받아서 enum 으로 반환
    public static State nameOf(String name) {
        for (State state : State.values()) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }
}
