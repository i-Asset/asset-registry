package at.srfg.iot.aas.model;
/**
 * 
 * enumeration for the (valid) identifier types.
 *
 */

import java.util.regex.Pattern;
/**
 * Enumeration defining the allowed identifier types. Each
 * type can be detected based on a regular expression.  
 * <p>
 * 
 * @author dglachs
 * @see https://www.regextester.com
 *
 */
public enum IdType {
	// Unquoted: ^(([a-z]+):\/\/(\w+(\.\w+)+)(([\/\#](\w+(\(\w+\))?(\/)?)+)+)?)(\?\S+)?$
	// matches against URI - must start with the protocol! 
	URI("(([a-z]+):\\/\\/(\\w+(\\.\\w+)+)(([\\/\\#](\\w+(\\(\\w+\\))?(\\/)?)+)+)?)(\\?\\S+)?$"),	
	// Unquoted: ^(((\d+)[-\/](\w+))(([-\/](\w+)?)+)?)#(((\w{2})-)?(\w+))(#(\d+))?$
	// matches against a IRDI (eClass, IEC CDD)
	IRDI("^(((\\d+)[-\\/](\\w+))(([-\\/](\\w+)?)+)?)#(((\\w{2})-)?(\\w+))(#(\\d+))?$"),
	// Unquoted: ^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$
	UUID("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$"),
	// matches word characters: [a-zA-Z_0-9]
	IdShort("^(\\w+)$"),
	// NO regex - use only as fallback 
	Custom(""),
	;
	private Pattern pattern;
	private IdType(String regex) {
		this.pattern = Pattern.compile(regex, Pattern.MULTILINE);
	}
	protected Pattern pattern() {
		return pattern;
	}
//	/**
//	 * Helper method for accessing defined parts in an identifier.
//	 * @param part The named id part, such as {@link IdPart#Supplier}, or {@link IdPart#Namespace}
//	 * @param id The id to extract the {@link IdPart}
//	 * @return The result
//	 * @throws IllegalStateException when the requested part is not found
//	 */
//	public String getPart(IdPart part, String id) {
//		if (! part.idType.equals(this)) {
//			throw new IllegalStateException("Wrong IdType - use IdPart directly!");
//		}
//		Matcher matcher = pattern.matcher(id);
//		if ( matcher.find() ) {
//			return part.extractFrom(matcher);
//		}
//		throw new IllegalStateException("Requested Matcher Group not found");
//	}
	public static IdType getType(String id) {
		for (IdType e : values()) {
			if ( e.pattern.asPredicate().test(id)) {
				return e;
			}
		}
		return IdType.Custom;
	}

}
