package ru.akirakozov.sd.refactoring.html;

public class HtmlWriter {
    private final StringBuilder builder = new StringBuilder();

    public void addH1Header(final String content) {
        builder.append("<h1>").append(content).append("</h1>").append("\n");
    }

    public void addBreakLine() {
        builder.append("</br>").append("\n");
    }

    public HtmlWriter print(final String content) {
        builder.append(content);
        return this;
    }

    public void println(final String content) {
        builder.append(content).append("\n");
    }

    public String toString() {
        return "<html><body>\n" + builder.toString() + "</body></html>";
    }
}
