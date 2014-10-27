package guava_examples;

import com.google.common.base.*;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

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
	public void predicateTest(){
		Predicate<Country> countryHasCapitalPredicate = new Predicate<Country>() {
			@Override
			public boolean apply(Country country) {
				return !Strings.isNullOrEmpty(country.getCapital());
			}
		};

		boolean countriesHaveCapitals = Iterables.all(
				Lists.newArrayList(poland, england, unknown),
				countryHasCapitalPredicate
		); // any(), getFirst(), getLast(), partition()

		assertThat(countriesHaveCapitals, is(false));
	}

	@Test
	public void predicateComposingTest(){
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

		Collection<Country> filteredCollection = Collections2.filter(
				Lists.newArrayList(poland, england, unknown),
				Predicates.and(countryHasCapitalPredicate, countryCapitalIsLondon)
		);

		assertThat(filteredList, hasItems(england));
		assertThat(filteredCollection, hasItems(england));
	}

	@Test
	public void functionsAndTransformTest(){
		Function<Country, String> countryToCapital = new Function<Country, String>() {

			@Override
			public String apply(@Nullable Country country) {
				if (country == null){
					return "";
				}
				return country.getCapital();
			}
		};

		Collection<String> capitalsCollection = Collections2.transform(Lists.newArrayList(poland, england, germany), countryToCapital);

		assertThat(capitalsCollection, hasItems("Warsaw", "London", "Berlin"));
	}

	@Test
	public void functionsAndTransformComposingTest(){
		Function<Country, String> countryToCapital = new Function<Country, String>() {

			@Override
			public String apply(@Nullable Country country) {
				if (country == null){
					return "";
				}
				return country.getCapital();
			}
		};

		Function<String, String> capitalToUpperCase = new Function<String, String>() {

			@Override
			public String apply(String capital) {
				return capital.toUpperCase();
			}
		};

		Function<Country, String> composedFunctions = Functions.compose(capitalToUpperCase, countryToCapital);

		Collection<String> capitalsCollection = Collections2.transform(Lists.newArrayList(poland, england, germany), composedFunctions);

		assertThat(capitalsCollection, hasItems("WARSAW", "LONDON", "BERLIN"));
	}

	@Test
	public void joinerTest(){
		List<String> capitals = Lists.newArrayList(germany.getCapital(), poland.getCapital(), england.getCapital(), null);

		String listOfCapitals = Joiner.on(',').skipNulls().join(capitals);

		assertThat(listOfCapitals, is("Berlin,Warsaw,London"));
	}

	@Test
	public void splitterTest(){
		String listOfCapitals = "Berlin,Warsaw,London";

		List<String> capitals = Splitter.on(',').splitToList(listOfCapitals);

		assertThat(capitals.size(), is(3));
		assertThat(capitals, hasItems("London"));
	}
}
