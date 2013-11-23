package the.helper;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class StringHelper {

	private final String str;

	public StringHelper(String str) {
		this.str = str;
	}
	
	public List<Integer> splitToInt (String separator) {
		if (str == null) {
			return null;
		}
		String[] strs = str.split(separator);
		List<Integer> ints = newArrayList();
		for (String str : strs) {
			if (!str.isEmpty()) ints.add(Integer.parseInt(str));
		}
		return ints;
	}

}
