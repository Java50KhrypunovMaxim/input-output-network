package telran.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public interface InputOutput {
	String readString(String promt);

	void writeString(String string);

	default void writeLine(String string) {
		writeString(string + "\n");
	}

	default void writeObject(Object object) {
		writeString(object.toString());
	}

	default void writeObjectLine(Object object) {
		writeLine(object + "\n");
	}

	default <T> T readObject(String prompt, String errorPrompt, Function<String, T> mapper) {
		boolean running = false;
		T res = null;
		do {
			running = false;
			String string = readString(prompt);
			try {
				res = mapper.apply(string);

			} catch (RuntimeException e) {
				writeLine(errorPrompt + ": " + e.getMessage());
				running = true;
			}
		} while (running);
		return res;
	}

	default Integer readInt(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Integer::parseInt);
	}

	static Integer readInt(String prompt, String errorPrompt, int min, int max) {
		int res = Integer.parseInt(prompt);
		if (res < min && res > max) {
			throw new InputMismatchException("number must be less max, and more min");
		}
		return res;
	}

	default Long readLong(String prompt, String errorPrompt) {
		Long res = readObject(prompt, errorPrompt, Long::parseLong);

		return res;
	}

	static Long readLong(String prompt, String errorPrompt, long min, long max) {
		long res = Long.parseLong(prompt);
		if (res < min || res > max) {
			throw new RuntimeException(errorPrompt);
		}
		return res;
	}

	default Double readDouble(String prompt, String errorPrompt) {

		return readObject(prompt, errorPrompt, Double::parseDouble);
	}

	default String readString(String prompt, String errorPrompt, Predicate<String> predicate) {
		return readObject(prompt, errorPrompt, string -> {
			if (!predicate.test(string)) {
				throw new IllegalArgumentException("");
			}
			return string;
		});
	}

	default String readString(String prompt, String errorPrompt, HashSet<String> options) {

		return readString(prompt, errorPrompt, options::contains);
	}

	default LocalDate readDate(String prompt, String errorPrompt) {

		return readObject(prompt, errorPrompt, LocalDate::parse);
	}

	default LocalDate readDate(String prompt, String errorPrompt, LocalDate min, LocalDate max) {

		return readObject(prompt, errorPrompt, string -> {
			LocalDate res = LocalDate.parse(string);
			if (res.isBefore(min) || res.isAfter(max))
				throw new IllegalAccessError(String.format("Date should be in the range from %s to %s", min, max));
			return res;
		});
	}
	default char readOperator(String prompt, String errorPrompt) {
	    char operator;
	    do {
	        operator = readString(prompt).charAt(0);
	        if (operator != '+' && operator != '-' && operator != '*' && operator != '/') {
	            writeLine(errorPrompt);
	        }
	    } while (operator != '+' && operator != '-' && operator != '*' && operator != '/');
	    return operator;
	}
}
