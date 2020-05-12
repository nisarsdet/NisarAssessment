package com.tek.countrydata;

import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class Runner {

	public static void main(String[] args) {

		System.out.println("Welcome to country details search system");
		String cont = "y";
		Scanner scan = new Scanner(System.in);
		CountryAPI obj = new CountryAPI();

		do {
			System.out.println("Press 1 for fetch capital by country code");
			System.out.println("Press 2 for fetch capital by country full/partial name");
			System.out.println("Enter your choice");
			String choice = scan.nextLine();

			if (choice.trim().equalsIgnoreCase("1")) {
				System.out.println("Enter 3 digit country code");
				String countryCode = scan.nextLine();
				String capital = obj.getCapitalByCountryCode(countryCode);

				if (StringUtils.isEmpty(capital)) {
					System.out.println("No matching country found for country code " + countryCode);
				} else {
					System.out.println("Capital of country code " + countryCode + " is : " + capital);
				}
			} else if (choice.trim().equalsIgnoreCase("2")) {
				System.out.println("Enter country name");
				String countryName = scan.nextLine();
				Map<String, String> capitalMap = obj.getCapitalByCountryName(countryName);

				if (capitalMap.size() == 0) {
					System.out.println("No matching country found for name " + countryName);
				} else {
					for (String country : capitalMap.keySet()) {
						System.out.println("Capital for country name " + country + " is : " + capitalMap.get(country));
					}
				}
			} else {
				System.out.println("Invalid choice");
			}
			System.out.println("Do you want to continue? Enter Y or N");
			cont = scan.nextLine().trim();
		} while (cont.equalsIgnoreCase("y"));
		scan.close();
	}

}
