package project.delivery.domain;

public enum Bank {
    NH("농협"), KB("국민"), KAKAOBANK("카카오뱅크"), SINHAN("신한"), IBK("기업"),
    WOORI("우리"), HANA("하나"), EPOST("우체국"), KBANK("케이뱅크"), SH("수협"),
    TOSS("토스뱅크");

    private final String description;

    Bank(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
