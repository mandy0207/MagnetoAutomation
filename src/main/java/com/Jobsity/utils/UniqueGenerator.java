package com.Jobsity.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.openqa.selenium.By;

import com.github.javafaker.Faker;

public class UniqueGenerator {

	public static String generateUniqueEmail() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyssMMSSss");
		String timestamp = dateFormat.format(new Date());
		// Combine the base email with the timestamp
		String uniqueEmail = "webautomation" + timestamp + "@yopmail.com";

		return uniqueEmail;
	}

	public static String generateRandomInvalidEmail() {
		// Logic to generate a random invalid email
		return RandomStringUtils.randomAlphabetic(8);
	}

	public static String getUniqueString() {
		Faker faker = new Faker();
		return faker.name().firstName();
	
	}

	public static String getPassword() {
		return "Testing@12345";
	}

	public static String getCurrentYear() {
		Year currentYear = Year.now();
		// Format the year as a string with the "yyyy" pattern
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
		String formattedYear = currentYear.format(formatter);
		return formattedYear;
	}
	
	public static String getCurrentDateWithRequiredFormat(String format) {
		DateFormat dateformat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateformat.format(date).toUpperCase();
	}

	public static String calculateYear(int monthsToSubtract) {
		// Get the current year and month
		YearMonth currentYearMonth = YearMonth.now();

		// Subtract the specified number of months
		YearMonth resultYearMonth = currentYearMonth.minusMonths(monthsToSubtract);

		// Return the year of the result as a string
		return String.valueOf(resultYearMonth.getYear());
	}

	public static long getUniqueNumber() {
		Random random = new Random();
		int min = 1000; // Minimum 4-digit number
		int max = 9999; // Maximum 4-digit number
		int uniqueNumber = random.nextInt(max - min + 1) + min;
		return uniqueNumber;
	}

	public static int RandomNumber() {
		Random random = new Random();
		int randomNumber = random.nextInt(4) + 1;
		return randomNumber;
	}
	
	public static int randomNumber() {
		Random random = new Random();
		int min = 10000;
		int max = 99999;
		return random.nextInt(max - min + 1) + min;
	}

	public static int getRandomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}
	public static String getRandomMonth() {
		Random random = new Random();
		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun" };
		int randomIndex = random.nextInt(months.length);
		String randomMonth = months[randomIndex];
		return randomMonth;
	}
	
	

	public static String generatePetDate(boolean monthLess, String less) {
		Calendar calendar = Calendar.getInstance();

		if (monthLess) {
			calendar.add(Calendar.MONTH, -Integer.parseInt(less));
		} else {
			calendar.add(Calendar.YEAR, 0);
		}

		Date date = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
		return dateFormat.format(date);
	}

	public static String getWrongGotchaDayYear(String yearsToSubtract) {

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		return Integer.toString(currentYear - Integer.parseInt(yearsToSubtract) - 3);
	}

	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		return sdf.format(now);
	}

	public static String generateRandomName(int length) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int randomIndex = new Random().nextInt(alphabet.length());
			sb.append(alphabet.charAt(randomIndex));

			// Generate space after five character
			if ((i + 1) % 5 == 0 && i + 1 < length) {
				sb.append(' ');
			}
		}
		return sb.toString();
	}

	public static String genarateFutureDate(String formate) {
		LocalDate now = LocalDate.now();
		Random random = new Random();
		int randomYear = now.getYear() + random.nextInt(2, 10);
		int randomMonth = random.nextInt(1, 13);
		LocalDate futureDate = now.withYear(randomYear).withMonth(randomMonth);
		while (futureDate.isBefore(now) || futureDate.isEqual(now)) {
			futureDate = futureDate.plusYears(3);
		}
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(formate);
		return dateFormat.format(futureDate);
	}

	public static String uniqueDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date now = new Date();
		return sdf.format(now);
	}

	public static List<String> getLocatorString(By locator) {
		String value = locator.toString();
		String[] subString = value.split(":");
		return List.of(subString);
	}

	public static List<String> getLastPathComponent(List<String> urls) {
		List<String> urlPaths = new ArrayList<>();
		String stage = "https://www-staging.adoptapet.com";
		URL url;
		for (String urlString : urls) {
			if (!urlString.contains("http"))
				urlString = stage + urlString;
			String path = null;
			try {
				url = new URL(urlString);
				path = url.getPath();
			} catch (MalformedURLException e) {
			}
			StringTokenizer st = new StringTokenizer(path, "/");
			int count = 0;
			String lastToken = null;
			while (st.hasMoreTokens()) {
				lastToken = st.nextToken();
				count++;
			}
			if (count > 0) {
				urlPaths.add(lastToken);
			} else {
				urlPaths.add(urlString.toString());
			}
		}
		return urlPaths;
	}
	
    public static String generateSentence(int length) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('a', 'z')
            .build();
        return generator.generate(length);
    }
    
    public static String emailWritter() {
    	return "This email has been sent for testing purposes; please do not respond to this email, as it is intended to verify the system's functionality. Kindly ignore this message, as no action is required from your end. Thank you for your cooperation in this matter";
    }
    
    public static String decodeHexString(String input) {
        // Regex to find JavaScript hexadecimal escape sequences like \xHH
        Pattern hexPattern = Pattern.compile("\\\\x([0-9A-Fa-f]{2})");
        Matcher matcher = hexPattern.matcher(input);
        StringBuffer decodedString = new StringBuffer();

        // Iterate through all found matches of \xHH
        while (matcher.find()) {
            // Convert the hex value to its corresponding character
            String hexCode = matcher.group(1);  // Get the hex digits (HH)
            int charCode = Integer.parseInt(hexCode, 16);  // Parse hex to integer
            matcher.appendReplacement(decodedString, Character.toString((char) charCode));
        }
        matcher.appendTail(decodedString);  // Append remaining text

        return decodedString.toString();
    }

}
