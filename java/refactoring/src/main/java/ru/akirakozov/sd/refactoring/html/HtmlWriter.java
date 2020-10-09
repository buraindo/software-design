package ru.akirakozov.sd.refactoring.html;

public class HtmlWriter {
    private final StringBuilder builder = new StringBuilder();

    public void addH1Header(final String content) {
        addTag("h1", content);
    }

    public void addParagraph(final String content) {
        addTag("p", content);
    }

    public void addBreakLine() {
        addSingleTag("br");
    }

    public void print(final String content) {
        builder.append(content);
    }

    public void println(final String content) {
        builder.append(content).append("\n");
    }

    public String toString() {
        return "<html><body>\n" + builder.toString() + "</body></html>";
    }

    private void addSingleTag(final String tag) {
        builder.append("</").append(tag).append(">").append("\n");
    }

    private void addTag(final String tag, final String content) {
        builder.append("<").append(tag).append(">").append(content).append("</").append(tag).append(">").append("\n");
    }
}
