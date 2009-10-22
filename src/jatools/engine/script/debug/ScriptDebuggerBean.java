package jatools.engine.script.debug;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class ScriptDebuggerBean {
    private String name;
    private Object value;

    /**
     * Creates a new ScriptDebuggerBean object.
     */
    public ScriptDebuggerBean() {
    }

    /**
     * Creates a new ScriptDebuggerBean object.
     *
     * @param name DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public ScriptDebuggerBean(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    
    public String toString() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object getValue() {
        return value;
    }

    /**
     * DOCUMENT ME!
     *
     * @param value DOCUMENT ME!
     */
    public void setValue(Object value) {
        this.value = value;
    }
}
