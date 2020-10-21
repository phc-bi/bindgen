package org.bindgen.processor.config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bindgen.processor.util.ClassName;

/**
 * A {@link ClassName} scope that can filter packages based on a string expression.
 *
 * The expression specifies all beginning parts of allowed packages
 * in a comma-separated string, eg {@code com.myapp.customers.domain,
 * com.myapp.users.domain} which will allow any ClassName that is in
 * or under the specified packages.
 *
 * @author igor.vaynberg
 */
// TODO unit test
public class PackageExpressionScope implements Scope<ClassName> {

	/**
	 * If any input expression starts with this string,
	 * it will be treated as a regular expression rather than a simple package name prefix.
	 */
	private static final String REGEX_MARKER = "regex:";
	private final List<String> prefixes = new ArrayList<>();
	private final List<Pattern> patterns = new ArrayList<>();

	public PackageExpressionScope(String packageMask) {
		String[] expressions = packageMask.split(",");
		for (String expression : expressions) {
			expression = expression.trim();
			if (expression.startsWith(REGEX_MARKER)) {
				this.patterns.add(Pattern.compile(expression.substring(REGEX_MARKER.length())));
			} else {
				if (!expression.endsWith(".")) {
					expression = expression + ".";
				}
				this.prefixes.add(expression);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean includes(ClassName name) {
		final String packageName = name.getPackageName() + ".";
		for (String prefix : this.prefixes) {
			if (packageName.startsWith(prefix)) {
				return true;
			}
		}
		for (Pattern pattern : this.patterns) {
			if (pattern.matcher(packageName).matches()) {
				return true;
			}
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return new StringBuilder("[")
			.append(this.getClass().getSimpleName())
			.append(" prefixes=")
			.append(String.join(",", this.prefixes))
			.append(" patterns=")
			//.append(String.join(",", this.patterns.stream().map(Pattern::pattern).collect(Collectors.toList())))
			.append("]")
			.toString();
	}

}
