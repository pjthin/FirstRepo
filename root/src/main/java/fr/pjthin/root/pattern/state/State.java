package fr.pjthin.root.pattern.state;

public interface State {

    void validate();

    void cancel();

    void refuse();

    void init();
}
