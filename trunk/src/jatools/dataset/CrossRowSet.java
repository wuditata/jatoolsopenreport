package jatools.dataset;

import java.util.Iterator;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.3 $
  */
public class CrossRowSet extends RandomRowSet {
    private Map keysCache;
    private int[] cols2;

    /**
     * Creates a new CrossRowSet object.
     *
     * @param data DOCUMENT ME!
     * @param key DOCUMENT ME!
     * @param rows DOCUMENT ME!
     */
    public CrossRowSet(Dataset data, Key key, int[] rows, int[] cols2) {
        super(data, key, rows);
        this.cols2 = cols2;
    }
    
    Iterator keys() {
        validate();
        return this.keysCache.keySet().iterator();
    }

    /**
     * DOCUMENT ME!
     *
     * @param key DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DatasetException DOCUMENT ME!
     */
    public RowSet locate(Key key) throws DatasetException {
        validate();

        return (RowSet) keysCache.get(key);
    }

	private void validate() {
		if (keysCache == null) {
            this.keysCache = new IndexBuilder(this, cols2).build();
         
        }
	}
}
