
package simple.resource.cs.data.foo.model;


public class FooGETHeader {

    private String _id;

    public FooGETHeader() {
    }

    public FooGETHeader withId(String id) {
        _id = id;
        return this;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
