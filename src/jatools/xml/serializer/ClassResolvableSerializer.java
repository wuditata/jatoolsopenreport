package jatools.xml.serializer;

import jatools.xml.XmlSerializer;
import jatools.xml.XmlSerializerFactory;

import org.w3c.dom.Element;


/**
 *
 *  DOCUMENT ME!
 * 
 *  @version $Revision: 1.2 $
 *  @author $author$
 * 
 */

public class ClassResolvableSerializer extends ContainerSerializer {

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 * @param type DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Object read(Element e, Class type) {
		Class innerClass = null;

		try {
			innerClass = getClass(e);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		try {
			XmlSerializer delegate = XmlSerializerFactory.createInstance(innerClass);

			// 还是返回 ZAutoObject 对象，容易维护理解
			return new ClassResolvable(delegate.read(e, innerClass)).getObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 * @param object DOCUMENT ME!
	 */
	public void write(Element e, Object object) {
		object = ((ClassResolvable) object).getObject();

		setClass(e, object); // 保存的是内部类信息，而不是 ZAutoObject 类信息

		try {
			XmlSerializer delegate = XmlSerializerFactory.createInstance(object
				.getClass());
			delegate.write(e, object);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
