package project.delivery.domain.pay;

public enum Card {
    SINHAN("신한"), HANA("하나"), HYUNDAI("현대"), SAMSUNG("삼성"), LOTTE("롯데"), BC("비씨"), KB("국민"),
    NH("NH농협"), CITI("씨티"), KAKAO("카카오뱅크"), TOSS("토스뱅크");

    private final String description;

    Card(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description + "카드";
    }
}
