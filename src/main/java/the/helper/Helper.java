package the.helper;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Helper
{
	private static final Logger logger =
	        LoggerFactory.getLogger(Helper.class);
	
    public static <T> T with(T value)
    {
        return value;
    }

    public static boolean not(boolean value)
    {
        return !value;
    }
    
    public static Integer parseInt (String number) {
    	Integer parsedNumber = null;
    	try {
    		parsedNumber = Integer.parseInt(number);
    	} catch (Exception ex) {
    		logger.error("String must be number.", ex);
    	}
    	return parsedNumber;
    }
    
	public static boolean isBlank(String obj) {
		return obj == null || "".equals(obj);
	}

	public static <T> boolean isNull(T obj) {
		return obj == null;
	}

	public static <T> boolean isSet(T item) {
		return not(isNull(item));
	}

    public static boolean isEmpty(Collection<?> collection)
    {
        return isNull(collection) || collection.isEmpty();
    }
    
    public static StringHelper str (String str) {
    	return new StringHelper(str);
    }
}
