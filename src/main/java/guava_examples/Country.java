package guava_examples;

public class Country {
	private String name;
	private String capital;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	@Override
	public String toString() {
		return "Country{" +
				"name='" + name + '\'' +
				", capital='" + capital + '\'' +
				'}';
	}

	public static class CountryBuilder {
		private String name;
		private String capital;

		public CountryBuilder name (String value){
			name = value;
			return this;
		}

		public CountryBuilder capital (String value){
			capital = value;
			return this;
		}

		public Country build(){
			Country country = new Country();
			country.setName(name);
			country.setCapital(capital);
			return country;
		}
	}
}
