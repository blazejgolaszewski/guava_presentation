package guava_examples;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AppTest
{
	private static Country poland;
	private static Country germany;
	private static Country england;
	private static Country unknown;

	@BeforeClass
	public static void setUp(){
		poland = new Country.CountryBuilder().name("Poland").capital("Warsaw").build();
		germany = new Country.CountryBuilder().name("Germany").capital("Berlin").build();
		england = new Country.CountryBuilder().name("England").capital("London").build();
		unknown = new Country.CountryBuilder().name("unknown").build();
	}

	@Test
	public void predicateTest_1(){
		Predicate<Country> countryHasCapitalPredicate = new Predicate<Country>() {
			@Override
			public boolean apply(Country country) {
				return !Strings.isNullOrEmpty(country.getCapital());
			}
		};

		boolean countriesHaveCapitals = Iterables.all(
				Lists.newArrayList(poland, england, unknown),
				countryHasCapitalPredicate
		);

		assertThat(countriesHaveCapitals, is(false));
	}

	@Test
	public void predicateTest_2(){
		Predicate<Country> countryHasCapitalPredicate = new Predicate<Country>() {
			@Override
			public boolean apply(Country country) {
				return !Strings.isNullOrEmpty(country.getCapital());
			}
		};

		Predicate<Country> countryCapitalIsLondon = new Predicate<Country>() {
			@Override
			public boolean apply(Country country) {
				return country.getCapital().equals("London");
			}
		};

		Iterable<Country> filteredList = Iterables.filter(
				Lists.newArrayList(poland, england, unknown),
				Predicates.and(countryHasCapitalPredicate, countryCapitalIsLondon)
		);

		assertThat(filteredList, hasItems(england));
	}
}
