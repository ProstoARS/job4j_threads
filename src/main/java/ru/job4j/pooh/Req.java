package ru.job4j.pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] parseContent = content.split(System.lineSeparator());
        String firstStr = parseContent[0].substring(0, parseContent[0].indexOf('H'));
        String[] firstSplit = firstStr.split("/");
        for (int i = 0; i < firstSplit.length; i++) {
            firstSplit[i] = firstSplit[i].contains(" ") ? firstSplit[i].split(" ")[0] : firstSplit[i];
        }
        String param = "";
        if (firstSplit.length > 3) {
            param = firstSplit[3];
        } else if (parseContent.length > 4) {
            param = parseContent[parseContent.length - 1];
        }
        return new Req(firstSplit[0], firstSplit[1], firstSplit[2], param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
